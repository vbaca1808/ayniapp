package com.ayni.coperacion.configuracion.controller;
   
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus; 
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle; 
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import javax.mail.internet.MimeMessage; 
import java.io.ByteArrayOutputStream; 
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ayni.coperacion.dto.ActualizarEstadoProductoCocinaDto;
import com.ayni.coperacion.dto.ActualizarNegocioPedidoDto;
import com.ayni.coperacion.dto.AgregarQuitarAdminUsuarioDto;
import com.ayni.coperacion.dto.CompraNegocio;
import com.ayni.coperacion.dto.CompraPagoDto;
import com.ayni.coperacion.dto.ConfiguracionNegocioDto;
import com.ayni.coperacion.dto.DisponibilidadCuartosDto;
import com.ayni.coperacion.dto.InsumoDto;
import com.ayni.coperacion.dto.ListadoMenuDto;
import com.ayni.coperacion.dto.MenuPedidoUnitarioDto;
import com.ayni.coperacion.dto.NegocioDto;
import com.ayni.coperacion.dto.OtrosMovimientosDto;
import com.ayni.coperacion.dto.PedidoDto;
import com.ayni.coperacion.dto.PedidoPagadoDto;
import com.ayni.coperacion.dto.PromocionDto;
import com.ayni.coperacion.dto.ReporteCierreDetalleDto;
import com.ayni.coperacion.dto.ReporteCierreDto;
import com.ayni.coperacion.dto.UsuarioDto;
import com.ayni.coperacion.response.AgendaServicios;
import com.ayni.coperacion.response.CargoNegocio;
import com.ayni.coperacion.response.CompraNegocioResponse;
import com.ayni.coperacion.response.ConfiguracionNegocio;
import com.ayni.coperacion.response.DisponibildadCuarto;
import com.ayni.coperacion.response.DocumentoVentaResponse;
import com.ayni.coperacion.response.DocumentosPendientes;
import com.ayni.coperacion.response.Inventario;
import com.ayni.coperacion.response.ListadoCajero;
import com.ayni.coperacion.response.ListadoCocina;
import com.ayni.coperacion.response.ListadoInsumoProducto;
import com.ayni.coperacion.response.ListadoLimpiezaResponse;
import com.ayni.coperacion.response.ListadoMenu;
import com.ayni.coperacion.response.ListadoProducto;
import com.ayni.coperacion.response.ListadoProductoTienda;
import com.ayni.coperacion.response.ListadoUsuario;
import com.ayni.coperacion.response.Negocio;
import com.ayni.coperacion.response.OtrosMovimientosCajeroResponse;
import com.ayni.coperacion.response.Pedido;
import com.ayni.coperacion.response.PedidoGenerado;
import com.ayni.coperacion.response.PedidoInter;
import com.ayni.coperacion.response.PedidoPagoResponse;
import com.ayni.coperacion.response.Promociones;
import com.ayni.coperacion.response.ReporteChecksResponse;
import com.ayni.coperacion.response.ReporteCierre;
import com.ayni.coperacion.response.ReporteCierreDetalle;
import com.ayni.coperacion.response.ReporteCierreDetalleCliente;
import com.ayni.coperacion.response.ReporteCierreDetalleDocumento;
import com.ayni.coperacion.response.ReporteCierreDetalleEfectivo;
import com.ayni.coperacion.response.ReporteIngresosGeneradosResponse;
import com.ayni.coperacion.response.ReporteOcupacionResponse;
import com.ayni.coperacion.response.ReportePedido;
import com.ayni.coperacion.response.ReporteReservasResponse;
import com.ayni.coperacion.response.RespuestaStd;
import com.ayni.coperacion.response.UsuarioReponse;
import com.ayni.coperacion.response.VentasPorProducto;
import com.ayni.coperacion.service.IUsuarioService;
import com.google.gson.Gson;

import java.awt.image.BufferedImage; 
import javax.imageio.ImageIO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CooperacionController {
    
    @Autowired
	private IUsuarioService iUsuarioService;

    
    private final ResourceLoader resourceLoader;

    public CooperacionController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostMapping(value="/crearnegocio",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> crearNegocio(@Valid @RequestBody NegocioDto negocioDto) {
        try {
            List<RespuestaStd> lstRespuestaStd = iUsuarioService.crearNegocio(negocioDto);
            return ResponseEntity.ok().body(lstRespuestaStd);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/crearusuario",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioReponse> registroUsuario(@Valid @RequestBody 
                                                        UsuarioDto usuarioDto) {
        try {
            UsuarioReponse usuarioResponse = new UsuarioReponse();

            if (usuarioDto.getCodigoVerificacion() != null && !usuarioDto.getCodigoVerificacion().equals("")) {
                usuarioResponse = iUsuarioService.validarCodigoVerificador(usuarioDto);
            } else {
                usuarioResponse = iUsuarioService.registro(usuarioDto);
            }

            return ResponseEntity.ok().body(usuarioResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/validarusuario/{numerotelefono}/{nombreusuario}/{codigoverificacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> validarUsuario(@PathVariable String numerotelefono,
                                                       @PathVariable String nombreusuario,
                                                       @PathVariable String codigoverificacion) {
        try {
            List<RespuestaStd> lstRespuestaStd = iUsuarioService.validarUsuario(numerotelefono, nombreusuario, codigoverificacion);
            if (lstRespuestaStd.size() > 0) {
                return ResponseEntity.ok().body(lstRespuestaStd.get(0));
            } else {
                return ResponseEntity.ok().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @GetMapping(value="/listadonegocio",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Negocio>> listadoNegocio() {
        try {
            List<Negocio> lst = iUsuarioService.listadoNegocio(); 
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listadocargonegocio/{idnegocio}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CargoNegocio>> listadoCargoNegocio(@PathVariable int idnegocio,
                                                                  @PathVariable String numerocelular, 
                                                                  @PathVariable String nombreusuario) {
        try {
            List<CargoNegocio> lst = iUsuarioService.listadoCargoNegocio(idnegocio, 
            numerocelular, nombreusuario);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listadomenu/{idnegocio}/{idpedido}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoMenu>> listadoMenu(@PathVariable int idnegocio, 
                                                         @PathVariable int idpedido) {
        try {
            
            List<ListadoMenu> lst = iUsuarioService.obtenerListadoMenu(idnegocio, idpedido); 
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/crearmenupedido",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> crearMenuPedido(@Valid @RequestBody PedidoDto pedidoDto) {
        try {
            
            if (pedidoDto.getNumeroCelular() != null && pedidoDto.getNombreUsuario() != null && 
                !pedidoDto.getNumeroCelular().equals("") && !pedidoDto.getNombreUsuario().equals("")) {
                Pedido pedido = new Pedido();

                if (pedidoDto.getDetalleProducto() != null && pedidoDto.getDetalleProducto().size() > 0) {
                    for (int i = 0; i < pedidoDto.getDetalleProducto().size(); i++) {
                        ListadoMenuDto item = pedidoDto.getDetalleProducto().get(i);
                        if (item != null && (item.getPromocion() == null || item.getPromocion().trim().equals(""))) {
                            item.setPromocion("N");
                        }
                    }
                }

                Gson gson = new Gson();
                String jsonDetalleProducto = gson.toJson(pedidoDto.getDetalleProducto());
                
                System.out.println(jsonDetalleProducto);
 

                int idPedido = iUsuarioService.crearMenuPedido(pedidoDto.getIdNegocio(), 
                pedidoDto.getIdPedido(), jsonDetalleProducto, pedidoDto.getMesa(), 
                pedidoDto.getNumeroCelular(), pedidoDto.getNombreUsuario(), pedidoDto.getDocCliente(), 
                pedidoDto.getNombreCliente(), pedidoDto.getDireccionCliente(), pedidoDto.getTipoDoc(), 
                pedidoDto.getNumeroDocumento(), pedidoDto.getComisionDelivery(), pedidoDto.getFechaReserva(),
                (pedidoDto.getFechaReserva() == null || pedidoDto.getFechaReserva().equals("")?0:1));

                if (pedidoDto.getIdNegocio() == 25 || (pedidoDto.getIdNegocio() == 26 && pedidoDto.getIdPedido() <= 0)) {
                    pedido.setDocumento(sbGenerarDocumentoTextoPlano(pedidoDto.getIdNegocio(), idPedido, 
                    (pedidoDto.getIdPedido() > 0?1:0))); 
                } else {
                    pedido.setDocumento(" ");
                }

                pedido.setIdPedido(idPedido);
                
                if (idPedido == -2) {
                    pedido.setMensaje("Tiene un pago vencido no puede registrar transacciones");
                }

                System.out.println("Celular :" + pedidoDto.getNumeroCelular());
                System.out.println("Usuario :" + pedidoDto.getNombreUsuario());

                return ResponseEntity.ok().body(pedido);
            } else { 
                return ResponseEntity.status(500).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/borrarpedido/{idnegocio}/{idpedido}/{numerocelular}/{nombreusuario}/{borrar}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> borrarMenuPedido(@PathVariable int idnegocio, @PathVariable int idpedido,
                                                   @PathVariable String numerocelular, @PathVariable String nombreusuario,
                                                   @PathVariable int borrar) {
        try {
            Pedido pedido = iUsuarioService.borrarMenuPedido(idnegocio, idpedido, numerocelular, nombreusuario, borrar);
            return ResponseEntity.ok().body(pedido);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/solicitarconfirmacion/{idnegocio}/{idpedido}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> solicitarConfirmacion(@PathVariable int idnegocio, @PathVariable int idpedido,
                                                                    @PathVariable String numerocelular, @PathVariable String nombreUsuario) {
        try {
 
            List<RespuestaStd> lst = iUsuarioService.solicitarConfirmacion(idnegocio, idpedido, numerocelular, nombreUsuario);
            return ResponseEntity.ok().body(lst);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/confirmarpedido/{idnegocio}/{idpedido}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> confirmarPedido(@PathVariable int idnegocio, 
                                                        @PathVariable int idpedido, 
                                                        @PathVariable String numerocelular, 
                                                        @PathVariable String nombreusuario) {
        try {
 
            List<RespuestaStd> lst = iUsuarioService.confirmarPedido(idnegocio, idpedido, numerocelular, 
            nombreusuario);
            
            if (lst != null && lst.size() > 0) {
                return ResponseEntity.ok().body(lst.get(0));
            } else {
                return ResponseEntity.ok().body(null);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/pedidopagado",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> pedidoPagado(@Valid @RequestBody 
                                                            PedidoPagadoDto pedidoPagadoDto) {
        try {
  
            List<RespuestaStd> lst = iUsuarioService.pedidoPagado(pedidoPagadoDto);

            //iUsuarioService.envioFacturaElectronica(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

            return ResponseEntity.ok().body(lst);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/enviosunat",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> envioSunat() {
        try {
   
            List<String> lstItems = new ArrayList<>();
            lstItems.add("PRODUCTO DE PRUEBA##P1");

            iUsuarioService.envioFacturaElectronica(
            "Oatv5xMfFInuGqiX9SoLDTy2yuLf0tTlMFkWtkdw1z/Ss6kiDz+vIgZhgKfIaxp+JbVy57GT52f10VLMLatdwPVRbrWmz1/NIy5CWp1xWMaM6fC/9SXV0O1Lqopk0UeX2I2yuf05QhmVfjgUu6GnS3m6o6zM9J36iDvMVZyj7vbJTwI8SfWjTSNqxXlqPQ==",
            "San Luis", "Victor Alejandro Baca Huaripaucar", 
                    "10437413903", 
                    "MIIF9TCCBN2gAwIBAgIGAK0oRTg/MA0GCSqGSIb3DQEBCwUAMFkxCzAJBgNVBAYTAlRSMUowSAYD" + 
                    "VQQDDEFNYWxpIE3DvGjDvHIgRWxla3Ryb25payBTZXJ0aWZpa2EgSGl6bWV0IFNhxJ9sYXnEsWPE" + 
                    "sXPEsSAtIFRlc3QgMTAeFw0wOTEwMjAxMTM3MTJaFw0xNDEwMTkxMTM3MTJaMIGgMRowGAYDVQQL" + 
                    "DBFHZW5lbCBNw7xkw7xybMO8azEUMBIGA1UEBRMLMTAwMDAwMDAwMDIxbDBqBgNVBAMMY0F5ZMSx" + 
                    "biBHcm91cCAtIFR1cml6bSDEsHRoYWxhdCDEsGhyYWNhdCBUZWtzdGlsIMSwbsWfYWF0IFBhemFy" + 
                    "iMwtPnC2DRjdsyGv3bxwRZr9wXMRrMNwRjyFe9JPA7bSscEgaXwzDUG5FCvfS/PNT+XCce+VECAx" + 
                    "6Q3R1ZRSA49fYz6tDB4Ia5HVBXZODmrCs26XisHF6kuS5N/yGg8E7VC1BRr/SmxXeLTdjQYAfo7l" + 
                    "xCz4dT6wP5TOiBvF+lyWW1bi9nbliXyb/e5HjCp4k/ra9LTskjbY/Ukl5O8G9JEAViZkjvxDX7T0yVRHgMGiioIKVMw" + 
                    "U6Lrtln607BNurLwED0OeoZ4wBgkBiB5vXofreXrfN2pHZ2=", "F002-10", "VICTOR ALEJANDRO BACA HUARIPAUCAR", 
                    "10437413903", "Direccion de prueba", "Contado", "18.00", "100.00", "118.00", "1800", lstItems);

            return ResponseEntity.ok().body(null);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/pedidoatendido/{idnegocio}/{idpedido}/{numerocelular}/{nombreusuario}/{incluirpl}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> pedidoAtendido(@PathVariable int idnegocio, 
                                                             @PathVariable int idpedido, 
                                                             @PathVariable String numerocelular, 
                                                             @PathVariable String nombreusuario,
                                                             @PathVariable int incluirpl) {
        try {
 
            List<RespuestaStd> lst = iUsuarioService.pedidoAtendido(idnegocio, idpedido, 
                                                                  numerocelular, nombreusuario, incluirpl);
            if (lst.size() > 0) {
                return ResponseEntity.ok().body(lst.get(0));
            } else {
                return ResponseEntity.ok().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/pedidoatendidoindividual/{idnegocio}/{idpedido}/{numerocelular}/{nombreusuario}/{incluirpl}/{idproducto}/{idcargo}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> pedidoAtendidoIndividual(@PathVariable int idnegocio, 
                                                                 @PathVariable int idpedido, 
                                                                 @PathVariable String numerocelular, 
                                                                 @PathVariable String nombreusuario,
                                                                 @PathVariable int incluirpl,
                                                                 @PathVariable int idproducto,
                                                                 @PathVariable int idcargo) {
        try {
 
            List<RespuestaStd> lst = iUsuarioService.pedidoAtendidoIndividual(idnegocio, idpedido, numerocelular, nombreusuario, 
                                                                              incluirpl, idproducto, idcargo);
            if (lst.size() > 0) {
                return ResponseEntity.ok().body(lst.get(0));
            } else {
                return ResponseEntity.ok().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/revertiratencion/{idnegocio}/{idpedido}/{numerocelular}/{nombreusuario}/{incluirpl}/{idproducto}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> pedidoAtendido(@PathVariable int idnegocio, 
                                                       @PathVariable int idpedido, 
                                                       @PathVariable String numerocelular, 
                                                       @PathVariable String nombreusuario,
                                                       @PathVariable int incluirpl,
                                                       @PathVariable int idproducto) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.pedidoAtendidoRevetirIndividual(idnegocio, idpedido, numerocelular, nombreusuario, incluirpl, idproducto);
            if (lst.size() > 0) {
                return ResponseEntity.ok().body(lst.get(0));
            } else {
                return ResponseEntity.ok().body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenerpedido/{idnegocio}/{idpedido}/{mesa}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PedidoGenerado>> obtenerPedido(@PathVariable int idnegocio, 
                                                              @PathVariable int idpedido,
                                                              @PathVariable String mesa) {
        try {
            List<PedidoGenerado> lst = iUsuarioService.obtenerPedido(idnegocio, idpedido, mesa);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/reportecierre",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteCierre>> reporteCierre(@Valid @RequestBody 
    ReporteCierreDto reporteCierreDto) {
        try {
            List<ReporteCierre> lst = iUsuarioService.reporteCierre(reporteCierreDto.getIdNegocio(),
            reporteCierreDto.getAnioSeleccionado(), reporteCierreDto.getMesSeleccionado(), reporteCierreDto.getDiaSeleccionado(),
            reporteCierreDto.getNumeroCelular(), reporteCierreDto.getNombreUsuario());

             return ResponseEntity.ok().body(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/reportecierretienda",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteCierre>> reporteCierreTienda(@Valid @RequestBody 
    ReporteCierreDto reporteCierreDto) {
        try {
            List<ReporteCierre> lst = iUsuarioService.reporteCierreTienda(reporteCierreDto.getIdNegocio(),
            reporteCierreDto.getAnioSeleccionado(), reporteCierreDto.getMesSeleccionado(), reporteCierreDto.getDiaSeleccionado(),
            reporteCierreDto.getAnioSeleccionadoHasta(), reporteCierreDto.getMesSeleccionadoHasta(), reporteCierreDto.getDiaSeleccionadoHasta(),
            reporteCierreDto.getNumeroCelular(), reporteCierreDto.getNombreUsuario());

            String vReporteCierre = sbGenerarReporteCierreCajaTextoPlano(lst, reporteCierreDto.getIdNegocio());
            
            if (!lst.isEmpty()) {
                lst = lst.stream()
                         .map(reporte -> new ReporteCierre() {
                             @Override
                             public String getDocumento() {
                                 // Agregar el documento adicional aquí
                                 return vReporteCierre;
                             }

                            @Override
                            public String getDato1() {
                                return reporte.getDato1();
                            }

                            @Override
                            public String getDato2() {
                                return reporte.getDato2();
                            }

                            @Override
                            public String getDato3() {
                                return reporte.getDato3();
                            }

                            @Override
                            public String getDato4() {
                                return reporte.getDato4();
                            }

                            @Override
                            public String getDato5() {
                                return reporte.getDato5();
                            }

                            @Override
                            public String getDato6() {
                                return reporte.getDato6();
                            }

                            @Override
                            public String getDato7() {
                                return reporte.getDato7();
                            }

                            @Override
                            public String getDato8() {
                                return reporte.getDato8();
                            }

                            @Override
                            public String getDato9() {
                                return reporte.getDato9();
                            }

                            @Override
                            public String getTipo() {
                                return reporte.getTipo();
                            }
                         })
                         .collect(Collectors.toList());
            }
             return ResponseEntity.ok().body(lst);
             
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/reportecierretiendadetalle",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteCierreDetalle>> reporteCierraTiendaDetalle(@Valid 
    @RequestBody ReporteCierreDetalleDto reporteCierreDetalleDto) {
        try {
            List<ReporteCierreDetalle> lst = iUsuarioService.reporteCierraTiendaDetalle(
                reporteCierreDetalleDto.getIdNegocio(), 
                reporteCierreDetalleDto.getAnioSeleccionado(), 
                reporteCierreDetalleDto.getMesSeleccionado(), 
                reporteCierreDetalleDto.getDiaSeleccionado(), 
                reporteCierreDetalleDto.getAnioSeleccionadoHasta(), 
                reporteCierreDetalleDto.getMesSeleccionadoHasta(), 
                reporteCierreDetalleDto.getDiaSeleccionadoHasta(), 
                reporteCierreDetalleDto.getNumeroCelular(), 
                reporteCierreDetalleDto.getNombreUsuario(), 
                reporteCierreDetalleDto.getIdProducto());

             return ResponseEntity.ok().body(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listadopedido/{idnegocio}/{mesa}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReportePedido>> listadoPedidos(@PathVariable int idnegocio,@PathVariable int mesa,
                                                              @PathVariable String numerocelular,@PathVariable String nombreusuario) {
        try {
            List<ReportePedido> lst = iUsuarioService.listadoPedidos(idnegocio, mesa, numerocelular, nombreusuario, new Date());
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listadococina/{idnegocio}/{negociococina}/{anio}/{mes}/{dia}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoCocina>> listadoCocina(@PathVariable int idnegocio,@PathVariable int negociococina, 
                                                             @PathVariable int anio, @PathVariable int mes, @PathVariable int dia) {
        try {
            List<ListadoCocina> lst = iUsuarioService.listadoCocina(idnegocio, negociococina, anio, mes, dia);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/actualizarestadoproductococina",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> actualizarEstadoProductoCocina(@Valid @RequestBody 
    ActualizarEstadoProductoCocinaDto actualizarEstadoProductoCocinaDto) {
        try {
            List<RespuestaStd> lst = iUsuarioService.actualizarEstadoProductoCocina(actualizarEstadoProductoCocinaDto);

             return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listadocajero/{idnegocio}/{anio}/{mes}/{dia}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoCajero>> listadoCajero(@PathVariable int idnegocio,@PathVariable int anio,
                                                             @PathVariable int mes, @PathVariable int dia) {
        try {
            List<ListadoCajero> lst = iUsuarioService.listadoCajero(idnegocio, anio, mes, dia);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/actualizarnegociopedido",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> actualizarNegocioPedido(@Valid @RequestBody 
    ActualizarNegocioPedidoDto actualizarNegocioPedidoDto) {
        try {
            List<RespuestaStd> lst = iUsuarioService.actualizarNegocioPedido(actualizarNegocioPedidoDto);
            
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/agregarquitaadminusuario",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> agregarquitaAdminUsuario(@Valid @RequestBody 
                                                AgregarQuitarAdminUsuarioDto agregarQuitarAdminUsuarioDto) {
        try {
            List<RespuestaStd> lst = iUsuarioService.agregarquitaAdminUsuario(
                agregarQuitarAdminUsuarioDto.getIdNegocio(), 
                agregarQuitarAdminUsuarioDto.getIdUsuario(), 
                agregarQuitarAdminUsuarioDto.getNombreUsuario(), 
                agregarQuitarAdminUsuarioDto.getIsAdmin(), 
                agregarQuitarAdminUsuarioDto.getAdmitir(),
                agregarQuitarAdminUsuarioDto.getDetalleCargoPermitidos());

             return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listadoproducto/{idnegocio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoProducto>> listadoProducto(@PathVariable int idnegocio) {
        try {
            List<ListadoProducto> lst = iUsuarioService.listadoProductoNegocio(idnegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/actualizaridnegociousuario/{numerotelefono}/{idnegocio}/{colaborador}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> actualizarIdNegocioUsuario(@PathVariable String numerotelefono,
                                                                         @PathVariable int idnegocio,
                                                                         @PathVariable int colaborador) {
        try {
            List<RespuestaStd> lst = iUsuarioService.actualizarIdNegocioUsuario(numerotelefono, idnegocio, colaborador);
             return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listadousuarionegocio/{idnegocio}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoUsuario>> listadoUsuarioNegocio(@PathVariable int idnegocio,
    @PathVariable String numerocelular,@PathVariable String nombreusuario) {
        try {
            List<ListadoUsuario> lst = iUsuarioService.listadoUsuarioNegocio(idnegocio, numerocelular, nombreusuario);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/configuracionnegocio",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> configuracionNegocio(@Valid @RequestBody 
    ConfiguracionNegocioDto configuracionNegocioDto) {
        try {

            configuracionNegocioDto.setUuidCocina("00001101-0000-1000-8000-00805F9B34FB");
            configuracionNegocioDto.setUuidMesero("00001101-0000-1000-8000-00805F9B34FB");

            List<RespuestaStd> lst = iUsuarioService.
            configuracionNegocio(configuracionNegocioDto);

            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenerconfiguracionnegocio/{idnegocio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConfiguracionNegocio>> obtenerConfiguracionNegocio(@PathVariable int idnegocio) {
        try {
            List<ConfiguracionNegocio> lst = iUsuarioService.obtenerConfiguracionNegocio(idnegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenerlistadoproductotienda/{idnegocio}/{codigobarra}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoProductoTienda>> obtenerListadoProductoTienda(@PathVariable int idnegocio,
                                                                                    @PathVariable String codigobarra) {
        try {
            if (codigobarra.equals("0")) { codigobarra = ""; }

            List<ListadoProductoTienda> lst = iUsuarioService.obtenerListadoProductoTienda(idnegocio, codigobarra);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenerlistadomenuinicial/{idnegocio}/{anio}/{mes}/{dia}/{diasreserva}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoMenu>> obtenerListadoMenuInicial(@PathVariable int idnegocio,
    @PathVariable int anio, @PathVariable int mes, @PathVariable int dia, @PathVariable int diasreserva) {
        try {

            Calendar calendar = Calendar.getInstance();
            Calendar calendarReserva = Calendar.getInstance();

            if (anio > 0) {
                calendar.set(Calendar.YEAR, anio);
                calendar.set(Calendar.MONTH, mes-1);
                calendar.set(Calendar.DAY_OF_MONTH, dia);
            }

            if (diasreserva > 0) {
                calendarReserva.set(Calendar.YEAR, anio);
                calendarReserva.set(Calendar.MONTH, mes-1);
                calendarReserva.set(Calendar.DAY_OF_MONTH, dia);
                calendarReserva.add(Calendar.DAY_OF_MONTH, diasreserva);
            }


            List<ListadoMenu> lst = iUsuarioService.obtenerListadoMenuInicial(idnegocio, calendar.getTime(), calendarReserva.getTime());
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenermenupedido/{idnegocio}/{idpedido}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoMenu>> obtenerMenuPedido(@PathVariable int idnegocio,
                                                               @PathVariable int idpedido) {
        try {
            List<ListadoMenu> lst = iUsuarioService.obtenerMenuPedido(idnegocio, idpedido);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }


    @PostMapping(value="/compranegocio",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> compraNegocio(@Valid @RequestBody CompraNegocio compraNegocio) {
        try { 

            List<RespuestaStd> lst = iUsuarioService.compraNegocio(compraNegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @GetMapping(value="/listarinventario/{idnegocio}/{aniocorte}/{mescorte}/{diacorte}/{aniohasta}/{meshasta}/{diahasta}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Inventario>> listarInventario(@PathVariable int idnegocio,
                                                                        @PathVariable int aniocorte,
                                                                        @PathVariable int mescorte,
                                                                        @PathVariable int diacorte,
                                                                        @PathVariable int aniohasta,
                                                                        @PathVariable int meshasta,
                                                                        @PathVariable int diahasta) {
        try { 
            List<Inventario> lst = iUsuarioService.listarInventario(idnegocio, aniocorte, mescorte, diacorte, aniohasta, meshasta, diahasta);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @GetMapping(value="/agendaservicios/{idnegocio}/{tipofiltro}/{pais}/{anio}/{mes}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AgendaServicios>> agendaServicios(@PathVariable int idnegocio, @PathVariable int tipofiltro,
                                                                 @PathVariable int pais, @PathVariable int anio, 
                                                                 @PathVariable int mes) {
        try { 
            List<AgendaServicios> lst = iUsuarioService.agendaServicios(idnegocio, tipofiltro, pais, anio, mes);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/otrosmovimientos",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> otrosMovimiento(@Valid @RequestBody 
    OtrosMovimientosDto otrosMovimientosDto) {
        try {  
            List<RespuestaStd> lst = iUsuarioService.otrosMovimiento(
                                        otrosMovimientosDto.getTipoMovimiento(),otrosMovimientosDto.getIdNegocio(),
                                        otrosMovimientosDto.getIdProducto(), otrosMovimientosDto.getCantidad(),
                                        new Date());
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }


    @PutMapping(value="/modificarcompra/{idcompra}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> modificarCompra(
    @PathVariable("idcompra") int idCompra, @Valid @RequestBody CompraNegocio compraNegocio) {
        try {  
            List<RespuestaStd> lst = iUsuarioService.modificarCompra(idCompra, compraNegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @GetMapping(value="/obtenerdatoscompra/{idnegocio}/{idcompra}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CompraNegocioResponse>> obtenerDatosCompra(
                                                    @PathVariable int idnegocio,
                                                    @PathVariable int idcompra) {
        try { 
            List<CompraNegocioResponse> lst = iUsuarioService.obtenerDatosCompra(idnegocio, idcompra);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/documentospendientespago/{idnegocio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DocumentosPendientes>> documentospendientespago(@PathVariable int idnegocio) {
        try { 
            List<DocumentosPendientes> lst = iUsuarioService.documentosPendientesPago(idnegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/comprapago",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> compraPago(@Valid @RequestBody CompraPagoDto compraPagoDto) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.compraPago(compraPagoDto);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/grabarinsumo",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> grabarInsumo(@Valid @RequestBody InsumoDto insumoDto) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.grabarInsumo(insumoDto);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listarinsumoporproducto/{idnegocio}/{idproducto}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoInsumoProducto>> listarInsumoPorProducto(
        @PathVariable int idnegocio, @PathVariable int idproducto) {
        try { 
            List<ListadoInsumoProducto> lst = iUsuarioService.listarInsumoPorProducto(idnegocio, idproducto);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenerinsumosproductoservicio/{idnegocio}/{idpedido}/{idproducto}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoInsumoProducto>> obtenerInsumosProductoServicio(
        @PathVariable int idnegocio, @PathVariable int idpedido, @PathVariable int idproducto) {
        try { 

            List<ListadoInsumoProducto> lst = 
                iUsuarioService.obtenerInsumosProductoServicio(idnegocio, idpedido, idproducto);

            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenercobrosayni/{idnegocio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> obtenerCobrosAyni(@PathVariable int idnegocio) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.obtenerCobrosAyni(idnegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/actualizarlecturacocina/{idnegocio}/{idpedido}/{idproducto}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> actualizarLecturaCocina(
        @PathVariable int idnegocio,@PathVariable int idpedido,@PathVariable int idproducto) {
        
        try { 
            List<RespuestaStd> lst = iUsuarioService.actualizarLecturaCocina(idnegocio, idpedido, idproducto);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @GetMapping(value="/reportecierratiendadetalleefectivo/{idnegocio}/{idtipopago}/{anio}/{mes}/{dia}/{nombreusuario}/{numerocelular}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteCierreDetalleEfectivo>> reporteCierraTiendaDetalleEfectivo(@PathVariable int idnegocio, 
    @PathVariable int idtipopago, @PathVariable int anio, @PathVariable int mes, @PathVariable int dia, 
    @PathVariable String nombreusuario, @PathVariable String numerocelular) {
        try { 
            List<ReporteCierreDetalleEfectivo> lst = iUsuarioService.reporteCierraTiendaDetalleEfectivo(idnegocio, idtipopago,anio, mes, dia, nombreusuario, numerocelular);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenerpedidopago/{idnegocio}/{idpedido}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PedidoPagoResponse>> obtenerPedidoPago(@PathVariable int idnegocio, 
    @PathVariable int idpedido) {
        try { 
            List<PedidoPagoResponse> lst = iUsuarioService.obtenerPedidoPago(idnegocio, idpedido);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/modificarpagopedido",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> modificarPagoPedido(@Valid @RequestBody PedidoPagadoDto pedidoPagadoDto) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.modificarPedidoPago(pedidoPagadoDto);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/buscarventasporproducto/{idnegocio}/{idproducto}/{tipofiltrofecha}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VentasPorProducto>> buscarVentasPorProducto(@PathVariable int idnegocio, 
    @PathVariable int idproducto,@PathVariable int tipofiltrofecha) {
        try { 
            List<VentasPorProducto> lst = iUsuarioService.buscarVentasPorProducto(idnegocio, idproducto, 
            tipofiltrofecha);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/obtenerdocumentospendientesimpresion/{idnegocio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PedidoInter>> obtenerDocumentosPendientesImpresion(@PathVariable int idnegocio) {
        try { 
            List<PedidoInter> lst = iUsuarioService.obtenerDocumentosPendientesImpresion(idnegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    
    @PostMapping(value="/insertargrupoproducto/{idnegocio}/{idgrupoproducto}/{descripciongrupo}/{ordenlista}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> insertarGrupoProducto(@PathVariable int idnegocio, @PathVariable int idgrupoproducto, 
                                                                    @PathVariable String descripciongrupo, @PathVariable int ordenlista) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.insertarGrupoProducto(idnegocio, idgrupoproducto, descripciongrupo, ordenlista);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/insertarcocinanegocio/{idnegocio}/{idcocina}/{nombrecocina}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> insertarCocinaNegocio(@PathVariable int idNegocio, @PathVariable  int idCocina,
    @PathVariable String nombreCocina) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.insertarCocinaNegocio(idNegocio, idCocina, nombreCocina);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/generardocumentoventaadocpagado/{idnegocio}/{idpedido}/{tipodocumento}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> generarDocumentoVentaADocPagado(@PathVariable int idnegocio,
                                                                              @PathVariable int idpedido,
                                                                              @PathVariable int tipodocumento) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.generarDocumentoVentaADocPagado(idnegocio,
            idpedido, tipodocumento);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/transferirmesa/{idnegocio}/{idpedido}/{numerocelulardestino}/{nombreusuariodestino}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> transferirMesa(@PathVariable int idnegocio,
                                                             @PathVariable int idpedido,
                                                             @PathVariable String numerocelulardestino,
                                                             @PathVariable String nombreusuariodestino) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.transferirMesa(idnegocio, idpedido, numerocelulardestino, nombreusuariodestino);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/descargarpdf/{idnegocio}/{idpedido}/{nv}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> descargarPdf(@PathVariable int idnegocio, 
    @PathVariable int idpedido, @PathVariable int nv) {
        try { 
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "documento_venta_tiquetera.pdf");
            return new ResponseEntity<>(sbGenerarDocumentoVentaPdf(idnegocio, idpedido, nv), headers, HttpStatus.OK);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/operacionhoteles/{idnegocio}/{idpedido}/{idproducto}/{agregardiasnoches}/{tipooperacion}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> operacionHoteles(@PathVariable int idnegocio, @PathVariable int idpedido,
                                                               @PathVariable int idproducto, @PathVariable int agregardiasnoches,
                                                               @PathVariable int tipooperacion) {
        try { 
            List<RespuestaStd> lst = iUsuarioService.operacionHoteles(idnegocio, idpedido, idproducto, agregardiasnoches, tipooperacion);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/descargarpdftexto/{idnegocio}/{idpedido}/{nv}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> descargarPdfFormatoTexto(@PathVariable int idnegocio, 
    @PathVariable int idpedido, @PathVariable int nv) {
        try { 
             
            List<DocumentoVentaResponse> lstDocumentoVenta = 
            iUsuarioService.obtenerDocumentoVenta(idnegocio, idpedido);
            
            DocumentoVentaResponse cabecera = null;

            if (lstDocumentoVenta.size() > 0) {
                cabecera = lstDocumentoVenta.get(0);
            }
 

            int numeroLetrasMaximoLinea = 32;
            BigDecimal numeroEspacios = BigDecimal.ZERO;
            BigDecimal valorDos = new BigDecimal("2"); 
            String vTextoAnidado = "";

            if (idnegocio==26) {
                numeroLetrasMaximoLinea = 46;
            } else {
                numeroLetrasMaximoLinea = 32;
            }

            if (cabecera != null) {
                String razonSocial = cabecera.getRazonSocial();
                
                if (idnegocio != 26 || (idnegocio == 26 && nv == 0)) {
                    if (razonSocial.length() > 16) {
                        // Dividir la razonSocial en dos líneas
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 16));
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                        String linea1 = repeatString(" ", numeroEspacios.intValue()) + razonSocial.substring(0, 16) + 
                        repeatString(" ", numeroEspacios.intValue());
                        String linea2 = razonSocial.substring(16, razonSocial.length());
                        
                        numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                        linea2 = repeatString(" ", numeroEspacios.intValue()) + linea2 + repeatString(" ", numeroEspacios.intValue());

                        // Ajustar posición del texto para que quepa en la tiquetera
                        vTextoAnidado = vTextoAnidado + linea1 + "\n";
    
                        vTextoAnidado = vTextoAnidado + linea2 + "\n"; // Mostrar la segunda línea

                    } else {
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - razonSocial.length()));
                        
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 
                        vTextoAnidado = vTextoAnidado + repeatString(" ", numeroEspacios.intValue()) + 
                        razonSocial + repeatString(" ", numeroEspacios.intValue()) + "\n";
                    }
                }
            }
        
            if (idnegocio != 26 || (idnegocio == 26 && nv == 0)) {
                numeroEspacios = new BigDecimal(numeroLetrasMaximoLinea - (cabecera.getRucEmpresa().length() + 4));
                numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);

                vTextoAnidado = vTextoAnidado + repeatString(" ", numeroEspacios.intValue()) + "RUC " + cabecera.getRucEmpresa() + 
                repeatString(" ", numeroEspacios.intValue()) + "\n"; // Mostrar la segunda línea
            }

            String vDireccion = cabecera.getDireccion();
            
            if (idnegocio != 26 || (idnegocio == 26 && nv == 0)) {
                if (vDireccion.length() > 36) {
                    // Dividir la razonSocial en dos líneas
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 36));
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                    String linea1 = repeatString(" ", numeroEspacios.intValue()) + vDireccion.substring(0, 36) + 
                    repeatString(" ", numeroEspacios.intValue());
                    String linea2 = vDireccion.substring(36, vDireccion.length());
                    
                    numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                    linea2 = repeatString(" ", numeroEspacios.intValue()) + linea2 + repeatString(" ", numeroEspacios.intValue());
    
                    vTextoAnidado = vTextoAnidado + linea1 + "\n";
    
                    vTextoAnidado = vTextoAnidado + linea2 + "\n" ; // Mostrar la segunda línea

                } else {
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vDireccion.length()));
                    
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 
                    vTextoAnidado = vTextoAnidado + repeatString(" ", numeroEspacios.intValue()) + vDireccion +
                                                    repeatString(" ", numeroEspacios.intValue()) + "\n";
                }
            }

            String vDescripcion = cabecera.getDescripcion();
 
            if (idnegocio != 26 || (idnegocio == 26 && nv == 0)) {
                if (vDescripcion.length() > 25) {
                    // Dividir la razonSocial en dos líneas
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 25));
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                    String linea1 = repeatString(" ", numeroEspacios.intValue()) + vDescripcion.substring(0, 25) + 
                    repeatString(" ", numeroEspacios.intValue());
                    String linea2 = vDescripcion.substring(25, vDescripcion.length());
                    
                    numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                    linea2 = repeatString(" ", numeroEspacios.intValue()) + linea2 + repeatString(" ", numeroEspacios.intValue());

                    vTextoAnidado = vTextoAnidado + linea1 + "\n"; 
                    vTextoAnidado = vTextoAnidado + linea2 + "\n";  

                } else {
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vDescripcion.length()));
                    
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 
                    vTextoAnidado = vTextoAnidado + repeatString(" ", numeroEspacios.intValue()) + vDescripcion +
                    repeatString(" ", numeroEspacios.intValue()) + "\n";
                }
            }
             
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String vHoraAtencion = formato.format(new Date());

            vHoraAtencion = "Fecha y Hora: " + vHoraAtencion;
            
            vTextoAnidado = vTextoAnidado + repeatString(" ", 1) + vHoraAtencion.toUpperCase() + repeatString(" ", numeroEspacios.intValue()) + "\n";
        

            if (nv == 1) {
                numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getTipoDocumento().length()));
                    
                numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 
                vTextoAnidado = vTextoAnidado + repeatString(" ", numeroEspacios.intValue()-5) + "M-" + cabecera.getMesa() + 
                repeatString(" ", 3) + cabecera.getTipoDocumento() + repeatString(" ", numeroEspacios.intValue()) + "\n";

                numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getDocumento().length()));                    
                numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 
                vTextoAnidado = vTextoAnidado + repeatString(" ", numeroEspacios.intValue()) + cabecera.getDocumento() + 
                repeatString(" ", numeroEspacios.intValue()) + "\n";
            } else {
                numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getTipoDocumento().length()));
                    
                numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 
                vTextoAnidado = vTextoAnidado + repeatString(" ", numeroEspacios.intValue()) + "Nota de Venta" + 
                repeatString(" ", numeroEspacios.intValue()) + "\n";

            }

                if (nv == 1) {
                    String vDocumento = "Documento: " + cabecera.getDocCliente();
                    String vCliente = "Cliente: " + cabecera.getNombreCliente();
                    String vDireccionCliente = cabecera.getDireccionCliente();
                    
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vDocumento.length()));
                    //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);
 
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + vDocumento + repeatString(" ", numeroEspacios.intValue()) + "\n";

                    
                    if (vCliente.length() > 25) {
                        // Dividir la razonSocial en dos líneas
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 25));
                        numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 

                        String linea1 = repeatString(" ", 4) +  vCliente.substring(0, 25) + 
                        repeatString(" ", numeroEspacios.intValue());
                        String linea2 = vCliente.substring(25, vCliente.length()).trim();
                        
                        numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                        numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 

                        linea2 = repeatString(" ", 4) + linea2 + repeatString(" ", numeroEspacios.intValue());

                         
                        vTextoAnidado = vTextoAnidado + linea1 + "\n";
 
                        vTextoAnidado = vTextoAnidado + linea2 + "\n"; // Mostrar la segunda línea

                    } else {
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vCliente.length()));
                        
                        numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 4) +  vCliente + repeatString(" ", numeroEspacios.intValue()) + "\n";
                    }


                    if (cabecera.getTipoDocumento().toUpperCase().contains("FACTURA")) {
                        if (vDireccionCliente.length() > 26) {
                            // Dividir la razonSocial en dos líneas
                            numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 26));
                            numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 

                            String linea1 = repeatString(" ", 4) + "Dirección: " + vDireccionCliente.substring(0, 26) + 
                            repeatString(" ", numeroEspacios.intValue());
                            String linea2 = vDireccionCliente.substring(26, vDireccionCliente.length());
                            
                            numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                            numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 

                            linea2 = repeatString(" ", 4) + linea2 + repeatString(" ", numeroEspacios.intValue());
 
                            vTextoAnidado = vTextoAnidado + linea1 + "\n";
 
                            vTextoAnidado = vTextoAnidado + linea2 + "\n"; // Mostrar la segunda línea

                        } else {
                            numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vDireccionCliente.length()));
                            
                            numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 
                            vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + "Dirección: " + vDireccionCliente + 
                            repeatString(" ", numeroEspacios.intValue()) + "\n";
                        }
                        
                    }
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getFechaPedido().length()));
                    //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);

                    vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + "F. Emision: " + cabecera.getFechaPedido() + 
                    repeatString(" ", numeroEspacios.intValue()) + "\n";

                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getMoneda().length()));
                    //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);
 
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + "Moneda: " + cabecera.getMoneda() + 
                    repeatString(" ", numeroEspacios.intValue()) + "\n"; 
                     
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + repeatString("-", numeroLetrasMaximoLinea - 8) + 
                    repeatString(" ", 4) + "\n";
                }
                 
                if (idnegocio != 26) {
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 2) + "Descripcion" + repeatString(" ", 7) + 
                    "P.V." + repeatString(" ", 3) + "TOTAL" + repeatString(" ", 4) + "\n";
                } else {
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 2) + "Descripcion" + repeatString(" ", 16) + 
                    "P.V." + repeatString(" ", 4) + "TOTAL" + repeatString(" ", 4) + "\n";
                }
                for (int i = 0; i < lstDocumentoVenta.size(); i++) {
                    
                    
                    String vProducto = lstDocumentoVenta.get(i).getCantidad() + " " + lstDocumentoVenta.get(i).getDescripcionProducto();

                    if (idnegocio == 26) {
                        vProducto = vProducto.replace("(Personal)", "(P)");
                        vProducto = vProducto.replace("(Mediana)", "(M)");
                        vProducto = vProducto.replace("(Familiar)", "(F)");
                    }

                    vProducto = vProducto.substring(0, 
                    (vProducto.length()> (idnegocio!=26?12:25)?(idnegocio!=26?12:25):vProducto.length()));
                    


                    String vPrecio = lstDocumentoVenta.get(i).getPrecioVenta();
                    String vTotal = lstDocumentoVenta.get(i).getTotalItem();
 
                    if (idnegocio != 26) {
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 2) + vProducto + 
                        repeatString(" ",(vProducto.length()> 17?1:17-vProducto.length())) +  
                        vPrecio + repeatString(" ", 2) + vTotal + repeatString(" ", 4 - 
                        (vTotal.length() > 4?(4 -vTotal.length()):0)) + "\n";
                    } else {
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 2) + vProducto + 
                        repeatString(" ",(vProducto.length()> 26?1:26-vProducto.length())) +  
                        vPrecio + repeatString(" ", 4) + vTotal + repeatString(" ", 4 - 
                        (vTotal.length() > 4?(4 -vTotal.length()):0)) + "\n";
                    }
                }

                if (cabecera != null && cabecera.getComisionDelivery() != null &&
                    cabecera.getComisionDelivery().compareTo(BigDecimal.ZERO) > 0) {
                    
                    String vProducto = "Delivery".substring(0, 
                    ("Delivery".length()> 13?13:"Delivery".length()));
                    String vPrecio = cabecera.getComisionDelivery().setScale(2,RoundingMode.HALF_UP).toString();
                    String vTotal = cabecera.getComisionDelivery().setScale(2,RoundingMode.HALF_UP).toString();

                    vTextoAnidado = vTextoAnidado + repeatString(" ", 2) + vProducto + 
                    repeatString(" ",(vProducto.length()> 18?1:18-vProducto.length())) +  
                    vPrecio + repeatString(" ", 3) + vTotal + repeatString(" ", 4 - 
                    (vTotal.length() > 4?(4 -vTotal.length()):0)) + "\n";

                }
                
                
                vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + repeatString("-", numeroLetrasMaximoLinea - 8) + 
                repeatString(" ", 4) + "\n";

                int vEspacios = 19;
                String vGravado = "S/." + cabecera.getGravado();
                String vIgv = "S/." + cabecera.getIgv();
                String vTotalPedido = "S/." + cabecera.getTotalPedido();
                
                if (idnegocio != 26) {
                    vEspacios = 19;
                } else {
                    vEspacios = 33;
                }

                
                if (idnegocio != 26 || (idnegocio == 26 && nv == 0)) {
                    if (nv == 1) { 
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + "Gravado: " + 
                        repeatString(" ",(vEspacios - 2) - vGravado.length()) + vGravado + "\n";
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + "I.G.V (10%): " + 
                        repeatString(" ",(vEspacios -6) - vIgv.length()) + vIgv + "\n";
                    }
                }

  
                vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + "Total: " + repeatString(" ",vEspacios - vTotalPedido.length()) + 
                vTotalPedido + "\n"; 

                
                if (idnegocio != 26 || (idnegocio == 26 && nv == 0)) {
                    if (nv == 1) {
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + 
                        "SON " + convertirNumeroALetras(new BigDecimal(cabecera.getTotalPedido().replace(",","")).intValue()).toUpperCase() + " Y " +
                        (new BigDecimal(cabecera.getTotalPedido().replace(",","")).remainder(BigDecimal.ONE).compareTo(new BigDecimal("9"))>0?"":"0") +
                        new BigDecimal(cabecera.getTotalPedido().replace(",","")).remainder(BigDecimal.ONE).intValue() + "/100 SOLES" + "\n";
                    }
                }
 
                vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + "\n";
 
                vTextoAnidado = vTextoAnidado + repeatString(" ", 4) +  "Mesera: " + cabecera.getNombreUsuario() + "\n";
 
                vTextoAnidado = vTextoAnidado + repeatString(" ", 4) + "\n\n\n\n";
                final String vTexto = vTextoAnidado;

                RespuestaStd respuestaStd = new RespuestaStd() {

                    @Override
                    public String getCodigo() {
                        // TODO Auto-generated method stub
                        return "OK";
                    }

                    @Override
                    public String getMensaje() {
                        return vTexto;
                    }
                    
                };
                return ResponseEntity.ok().body(respuestaStd);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    
    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        final Set<Object> seen = new HashSet<>();
        return t -> seen.add(keyExtractor.apply(t));
    }
 
    public byte[] sbGenerarDocumentoVentaPdf(int idnegocio, int idpedido, int nv) {
        List<DocumentoVentaResponse> lstDocumentoVenta = 
            iUsuarioService.obtenerDocumentoVenta(idnegocio, idpedido);
            
            DocumentoVentaResponse cabecera = null;

            if (lstDocumentoVenta.size() > 0) {
                cabecera = lstDocumentoVenta.get(0);
            }

            try (PDDocument document = new PDDocument()) {
                // Tamaño de página para una tiquetera típica (por ejemplo, 80 mm de ancho y 50 mm de alto)
                PDRectangle pageSize = new PDRectangle(160,400);
                PDPage page = new PDPage(pageSize);
                document.addPage(page);
                
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                int numeroLetrasMaximoLinea = 34;
                BigDecimal numeroEspacios = BigDecimal.ZERO;
                BigDecimal valorDos = new BigDecimal("2");
                Resource resource = resourceLoader.getResource("classpath:logo_nautico.png"); // Reemplaza "imagen.jpg" con el nombre de tu imagen en los recursos
                BufferedImage bufferedImage = ImageIO.read(resource.getInputStream());
                // Crear un objeto PDImageXObject desde la imagen BufferedImage
                PDImageXObject pdImage = LosslessFactory.createFromImage(document, bufferedImage);

                // Dibujar la imagen en el contenido de la página 
                contentStream.drawImage(pdImage, 55, 340, 50, 50);

                contentStream.beginText();
                contentStream.setFont(PDType1Font.COURIER_BOLD, 8); // Tamaño de fuente reducido para ajustarse al espacio 
                contentStream.newLineAtOffset(0, 325); 

                if (cabecera != null) {
                    String razonSocial = cabecera.getRazonSocial(); 
                    if (idnegocio != 26) {
                        if (razonSocial.length() > 16) {
                            // Dividir la razonSocial en dos líneas
                            numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 16));
                            numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                            String linea1 = repeatString(" ", numeroEspacios.intValue()) + razonSocial.substring(0, 16) + 
                            repeatString(" ", numeroEspacios.intValue());
                            String linea2 = razonSocial.substring(16, razonSocial.length());
                            
                            numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                            numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                            linea2 = repeatString(" ", numeroEspacios.intValue()) + linea2 + repeatString(" ", numeroEspacios.intValue());

                            // Ajustar posición del texto para que quepa en la tiquetera
                            contentStream.showText(linea1);

                            contentStream.newLine(); // Nuevo inicio de línea 
                            contentStream.newLineAtOffset(0, -10); // Ajuste vertical
                            contentStream.showText(linea2); // Mostrar la segunda línea

                        } else {
                            numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - razonSocial.length()));
                            
                            numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                            contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                            contentStream.showText(repeatString(" ", numeroEspacios.intValue()) + razonSocial + repeatString(" ", numeroEspacios.intValue()));
                        }
                    }
                }
        
                numeroEspacios = new BigDecimal(numeroLetrasMaximoLinea - (cabecera.getRucEmpresa().length() + 4));
                numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                if (idnegocio != 26) {
                    contentStream.newLineAtOffset(0, -12); // Ajuste vertical
                    contentStream.showText(repeatString(" ", numeroEspacios.intValue()) + "RUC " + cabecera.getRucEmpresa() + 
                    repeatString(" ", numeroEspacios.intValue())); // Mostrar la segunda línea
                }

                String vDireccion = cabecera.getDireccion();

                if (idnegocio != 26) {
                    if (vDireccion.length() > 36) {
                        // Dividir la razonSocial en dos líneas
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 36));
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                        String linea1 = repeatString(" ", numeroEspacios.intValue()) + vDireccion.substring(0, 36) + 
                        repeatString(" ", numeroEspacios.intValue());
                        String linea2 = vDireccion.substring(36, vDireccion.length());
                        
                        numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                        linea2 = repeatString(" ", numeroEspacios.intValue()) + linea2 + repeatString(" ", numeroEspacios.intValue());

                        // Ajustar posición del texto para que quepa en la tiquetera
                        contentStream.newLineAtOffset(0, -20); // Ajuste vertical
                        contentStream.showText(linea1);

                        contentStream.newLine(); // Nuevo inicio de línea 
                        contentStream.newLineAtOffset(0, -10); // Ajuste vertical
                        contentStream.showText(linea2); // Mostrar la segunda línea

                    } else {
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vDireccion.length()));
                        
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        contentStream.newLineAtOffset(0, 20); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", numeroEspacios.intValue()) + vDireccion + repeatString(" ", numeroEspacios.intValue()));
                    }
                }

                String vDescripcion = cabecera.getDescripcion();

                if (idnegocio != 26) {
                    if (vDescripcion.length() > 25) {
                        // Dividir la razonSocial en dos líneas
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 25));
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                        String linea1 = repeatString(" ", numeroEspacios.intValue()) + vDescripcion.substring(0, 25) + 
                        repeatString(" ", numeroEspacios.intValue());
                        String linea2 = vDescripcion.substring(25, vDescripcion.length());
                        
                        numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP); 

                        linea2 = repeatString(" ", numeroEspacios.intValue()) + linea2 + repeatString(" ", numeroEspacios.intValue());

                        // Ajustar posición del texto para que quepa en la tiquetera
                        contentStream.newLineAtOffset(0, -15); // Ajuste vertical
                        contentStream.showText(linea1);

                        contentStream.newLine(); // Nuevo inicio de línea 
                        contentStream.newLineAtOffset(0, -10); // Ajuste vertical
                        contentStream.showText(linea2); // Mostrar la segunda línea

                    } else {
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vDescripcion.length()));
                        
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        contentStream.newLineAtOffset(0, 15); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", numeroEspacios.intValue()) + vDescripcion + repeatString(" ", numeroEspacios.intValue()));
                    }       
                }

                if (nv == 1) {
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getTipoDocumento().length()));
                        
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    contentStream.newLineAtOffset(0, -15); // Posición inicial para la primera línea
                    contentStream.showText(repeatString(" ", numeroEspacios.intValue()-5) + "M-" + cabecera.getMesa() + repeatString(" ", 3) + cabecera.getTipoDocumento() + repeatString(" ", numeroEspacios.intValue()));

                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getDocumento().length()));                    
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                    contentStream.showText(repeatString(" ", numeroEspacios.intValue()) + cabecera.getDocumento() + repeatString(" ", numeroEspacios.intValue()));
                } else {
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getTipoDocumento().length()));
                        
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    contentStream.newLineAtOffset(0, -15); // Posición inicial para la primera línea
                    contentStream.showText(repeatString(" ", numeroEspacios.intValue()) + "Nota de Venta" + repeatString(" ", numeroEspacios.intValue()));

                }

                if (nv == 1) {
                    if (idnegocio == 26) {
                        String vDocumento = "Documento: " + cabecera.getDocCliente();
                        String vCliente = "Cliente: " + cabecera.getNombreCliente();
                        String vDireccionCliente = cabecera.getDireccionCliente();
                        
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vDocumento.length()));
                        //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);

                        contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", 4) + vDocumento + repeatString(" ", numeroEspacios.intValue()));
    
                        if (vCliente.length() > 25) {
                            // Dividir la razonSocial en dos líneas
                            numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 25));
                            numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 

                            String linea1 = repeatString(" ", 4) +  vCliente.substring(0, 25) + 
                            repeatString(" ", numeroEspacios.intValue());
                            String linea2 = vCliente.substring(25, vCliente.length()).trim();
                            
                            numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                            numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 

                            linea2 = repeatString(" ", 4) + linea2 + repeatString(" ", numeroEspacios.intValue());

                            // Ajustar posición del texto para que quepa en la tiquetera
                            contentStream.newLineAtOffset(0, -10); // Ajuste vertical
                            contentStream.showText(linea1);

                            contentStream.newLine(); // Nuevo inicio de línea 
                            contentStream.newLineAtOffset(0, -10); // Ajuste vertical
                            contentStream.showText(linea2); // Mostrar la segunda línea

                        } else {
                            numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vCliente.length()));
                            
                            numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);
                            contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                            contentStream.showText(repeatString(" ", 4) +  vCliente + repeatString(" ", numeroEspacios.intValue()));
                        }


                        if (cabecera.getTipoDocumento().toUpperCase().contains("FACTURA")) {
                            if (vDireccionCliente.length() > 26) {
                                // Dividir la razonSocial en dos líneas
                                numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - 26));
                                numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 

                                String linea1 = repeatString(" ", 4) + "Dirección: " + vDireccionCliente.substring(0, 26) + 
                                repeatString(" ", numeroEspacios.intValue());
                                String linea2 = vDireccionCliente.substring(26, vDireccionCliente.length());
                                
                                numeroEspacios =  new BigDecimal((numeroLetrasMaximoLinea - linea2.length()));                        
                                numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP); 

                                linea2 = repeatString(" ", 4) + linea2 + repeatString(" ", numeroEspacios.intValue());

                                // Ajustar posición del texto para que quepa en la tiquetera
                                contentStream.newLineAtOffset(0, -10); // Ajuste vertical
                                contentStream.showText(linea1);

                                contentStream.newLine(); // Nuevo inicio de línea 
                                contentStream.newLineAtOffset(0, -10); // Ajuste vertical
                                contentStream.showText(linea2); // Mostrar la segunda línea

                            } else {
                                numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vDireccionCliente.length()));
                                
                                numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);
                                contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                                contentStream.showText(repeatString(" ", 4) + "Dirección: " + vDireccionCliente + repeatString(" ", numeroEspacios.intValue()));
                            }
                            
                        }
                    }
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getFechaPedido().length()));
                    //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);

                    contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                    contentStream.showText(repeatString(" ", 4) + "F. Emision: " + cabecera.getFechaPedido() + repeatString(" ", numeroEspacios.intValue()));

                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - cabecera.getMoneda().length()));
                    //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);

                    if (idnegocio==26) {
                        contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", 4) + "Moneda: " + cabecera.getMoneda() + repeatString(" ", numeroEspacios.intValue()));
                    }
                    contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                    contentStream.showText(repeatString(" ", 4) + repeatString("-", numeroLetrasMaximoLinea - 8) + repeatString(" ", 4));
                }
                
                contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                contentStream.showText(repeatString(" ", 4) + "Descripción" + repeatString(" ", numeroLetrasMaximoLinea - 31) + 
                "P.V." + repeatString(" ", 3) + "TOTAL" + repeatString(" ", 4));
 
                for (int i = 0; i < lstDocumentoVenta.size(); i++) {
                    String vProducto = lstDocumentoVenta.get(i).getDescripcionProducto().substring(0, 
                    (lstDocumentoVenta.get(i).getDescripcionProducto().length()> 13?13:
                    lstDocumentoVenta.get(i).getDescripcionProducto().length()));
                    String vPrecio = lstDocumentoVenta.get(i).getPrecioVenta();
                    String vTotal = lstDocumentoVenta.get(i).getTotalItem();

                    contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                    contentStream.showText(repeatString(" ", 4) + vProducto + " " +  
                    vPrecio + repeatString(" ", 2) + vTotal + repeatString(" ", 4));
                }

                if(cabecera != null && cabecera.getComisionDelivery() != null && 
                   cabecera.getComisionDelivery().compareTo(BigDecimal.ZERO) > 0) {
 
                    String vProducto = "Delivery";
                    String vPrecio = cabecera.getComisionDelivery().setScale(2,RoundingMode.HALF_UP).toString();
                    String vTotal = cabecera.getComisionDelivery().setScale(2,RoundingMode.HALF_UP).toString();

                    contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                    contentStream.showText(repeatString(" ", 4) + vProducto + "     " +  
                    vPrecio + repeatString(" ", 2) + vTotal + repeatString(" ", 4));

                }
                
                
                contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                contentStream.showText(repeatString(" ", 4) + repeatString("-", numeroLetrasMaximoLinea - 8) + repeatString(" ", 4));

                int vEspacios = 19;
                String vGravado = "S/." + cabecera.getGravado();
                String vIgv = "S/." + cabecera.getIgv();
                String vTotalPedido = "S/." + cabecera.getTotalPedido();
                
                if (nv == 1) {
                    if (idnegocio==26) {
                        contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", 4) + "Gravado: " + repeatString(" ",(vEspacios - 2) - vGravado.length()) + vGravado);
                        contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", 4) + "I.G.V (10%): " + repeatString(" ",(vEspacios -6) - vIgv.length()) + vIgv);
                    }
                }

                contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                contentStream.showText(repeatString(" ", 4) + "Total: " + repeatString(" ",vEspacios - vTotalPedido.length()) + vTotalPedido);

                contentStream.newLineAtOffset(0, -20); // Posición inicial para la primera línea

                if (nv == 1) {
                    if (idnegocio==26) {
                        contentStream.showText(repeatString(" ", 4) + 
                        "SON " + convertirNumeroALetras(new BigDecimal(cabecera.getTotalPedido().replace(",","")).intValue()).toUpperCase() + " Y " +
                        (new BigDecimal(cabecera.getTotalPedido().replace(",","")).remainder(BigDecimal.ONE).compareTo(new BigDecimal("9"))>0?"":"0") +
                        new BigDecimal(cabecera.getTotalPedido().replace(",","")).remainder(BigDecimal.ONE).intValue() + "/100 SOLES");
                    }
                }

                contentStream.newLineAtOffset(0, -20);  
                contentStream.showText(repeatString(" ", 4));

                contentStream.newLineAtOffset(0, -5);  
                contentStream.showText(repeatString(" ", 4) +  "Mesera: " + cabecera.getNombreUsuario());

                contentStream.newLineAtOffset(0, -20);  
                contentStream.showText(repeatString(" ", 4));

                contentStream.endText();
                contentStream.close();
            
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                document.save(outputStream);
                document.close();
                    
                return outputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
    }


    public byte[] sbGenerarDocumento(int idnegocio, int idpedido, int tipolista) throws IOException {

        List<ListadoCocina> lstDocumentoVenta = 
            iUsuarioService.cocinaPedienteGenerado(idnegocio, idpedido, tipolista);

            long vCantidadRegistros = lstDocumentoVenta.size(); 
            
            Stream<ListadoCocina> lstPedidosDetalle = lstDocumentoVenta.stream().filter(distinctByKey(p -> p.getIdPedido()));
            Stream<ListadoCocina> lstPedidosVenta = lstDocumentoVenta.stream().filter(x -> x.getDescripcionProducto().contains("&&&"));
            vCantidadRegistros =  vCantidadRegistros + lstPedidosDetalle.count();
            vCantidadRegistros = vCantidadRegistros * 150;
            vCantidadRegistros = vCantidadRegistros + (lstPedidosVenta.count() * 25);

            try (PDDocument document = new PDDocument()) {
                // Tamaño de página para una tiquetera típica (por ejemplo, 80 mm de ancho y 50 mm de alto)
                PDRectangle pageSize = new PDRectangle(160,vCantidadRegistros);
                PDPage page = new PDPage(pageSize);
                document.addPage(page);
                
                // Dibujar la imagen en el contenido de la página
                
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
 
                contentStream.beginText();
                
                contentStream.setFont(PDType1Font.COURIER_BOLD, 10); // Tamaño de fuente reducido para ajustarse al espacio 
                contentStream.newLineAtOffset(3, vCantidadRegistros - 10); 

                int numeroLetrasMaximoLinea = 24;
                BigDecimal numeroEspacios = BigDecimal.ZERO;
                BigDecimal valorDos = new BigDecimal("2"); 
                int vIdPedido = 0;
                String vMesa = "";
                String vPedido = ""; 


                for (int i = 0; i < lstDocumentoVenta.size(); i++) {
                    ListadoCocina listadoCocina = lstDocumentoVenta.get(i);

                    if (vIdPedido != listadoCocina.getIdPedido()) {
                        vMesa = "Número de Mesa: " + listadoCocina.getMesa();
                        vPedido = "N° Pedido: " + listadoCocina.getIdPedido(); 
                        vIdPedido = listadoCocina.getIdPedido();

                        contentStream.setFont(PDType1Font.COURIER, 10); // Tamaño de fuente reducido para ajustarse al espacio 
                            
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vMesa.length()));
                        //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);

                        contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", 5) + vMesa.toUpperCase() + repeatString(" ", numeroEspacios.intValue()));
        
                        contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", 7) + vPedido.toUpperCase() + repeatString(" ", numeroEspacios.intValue()));

                        contentStream.newLineAtOffset(0, -15); // Posición inicial para la primera línea
                        contentStream.showText(listadoCocina.getNombreUsuario());

                        contentStream.newLineAtOffset(0, -11); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", 4) + repeatString("-", 20));
                        contentStream.newLineAtOffset(0, -5); // Posición inicial para la primera línea
                        contentStream.showText(repeatString(" ", 4));
                        
                    }

                    contentStream.setFont(PDType1Font.COURIER_BOLD, 10); // Tamaño de fuente reducido para ajustarse al espacio 
                
                    String vDescripcionProducto = listadoCocina.getDescripcionProducto().split("&&&")[0];
                    if (vDescripcionProducto.length() > 24) {
                        // Dividir la razonSocial en dos líneas
                        int vContador = 0;
                        while (vContador < vDescripcionProducto.length()) {
                            String linea = vDescripcionProducto.substring(vContador, 
                            (vDescripcionProducto.length() > vContador + 28?vContador + 28: vDescripcionProducto.length()));
                            vContador = vContador + 24;
                            contentStream.newLineAtOffset(0, -14);
                            contentStream.showText(linea.trim());
                        }
                    } else {                        
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        contentStream.newLineAtOffset(0, -5);  
                        contentStream.showText(vDescripcionProducto.toUpperCase());
                    }

                    if (listadoCocina.getActualizado() == 1) { 
                        contentStream.newLineAtOffset(0, -10);  
                        contentStream.showText(repeatString(" ", 15) + "(ACTUALIZADO)");
                    }  else if (listadoCocina.getActualizado() == 2) { 
                        contentStream.newLineAtOffset(0, -10);  
                        contentStream.showText(repeatString(" ", 15) + "(AGREGADO)");
                    } else if (listadoCocina.getActualizado() == 3) { 
                        contentStream.newLineAtOffset(0, -10);  
                        contentStream.showText(repeatString(" ", 15) + "(ELIMINADO)");
                    }

                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - listadoCocina.getCantidadMesa().length()));
                    
                    if (!listadoCocina.getCantidadMesa().equals("0")) {
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        contentStream.newLineAtOffset(0, -15);  
                        contentStream.showText("(MESA)CANTIDAD: " + listadoCocina.getCantidadMesa().toUpperCase());

                        if (listadoCocina.getDescripcionProducto().contains("&&&")) {
                            if (!listadoCocina.getDescripcionProducto().split("&&&")[1].trim().equals("")) {
                                contentStream.newLineAtOffset(0, -10);
                                
                                String vDetalle = "DETALLE: " + listadoCocina.getDescripcionProducto().toUpperCase().split("&&&")[1];
                                int vContador = 0;

                                while (vContador < vDetalle.length()) {
                                    String linea = vDetalle.substring(vContador, 
                                    (vDetalle.length() > vContador + 24?vContador + 24: vDetalle.length()));
                                    vContador = vContador + 24;
                                    contentStream.newLineAtOffset(0, -7);
                                    contentStream.showText(linea.trim());
                                }

                                //contentStream.showText("Detalle: " + listadoCocina.getDescripcionProducto().split("&&&")[1]);        
                            }
                        }

                    }


                    if (!listadoCocina.getCantidadLlevar().equals("0")) {
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - listadoCocina.getCantidadMesa().length()));                    
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        contentStream.newLineAtOffset(0, -20); // Posición inicial para la primera línea
                        contentStream.showText("(LLEVAR)CANTIDAD: " + listadoCocina.getCantidadLlevar());
                    
                        if (listadoCocina.getDescripcionProducto().contains("&&&")) {
                            if (!listadoCocina.getDescripcionProducto().split("&&&")[2].trim().equals("")) {
                                contentStream.newLineAtOffset(0, -10);
                                
                                String vDetalle = "DETALLE: " + listadoCocina.getDescripcionProducto().toUpperCase().split("&&&")[2];
                                int vContador = 0;
                                
                                while (vContador < vDetalle.length()) {
                                    String linea = vDetalle.substring(vContador, 
                                    (vDetalle.length() > vContador + 24?vContador + 24: vDetalle.length()));
                                    vContador = vContador + 24;
                                    contentStream.newLineAtOffset(0, -7);
                                    contentStream.showText(linea.trim());
                                }
                                       
                            }
                        }

                    }
                     
                    contentStream.newLineAtOffset(0, -10); // Posición inicial para la primera línea 
                    contentStream.showText(repeatString("-", 24));
                    contentStream.newLineAtOffset(0, -5); // Posición inicial para la primera línea 
                    contentStream.showText(repeatString(" ", 2));

                }
     
                contentStream.endText();
                contentStream.close();
            
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                document.save(outputStream);
                document.close();
            
                return  outputStream.toByteArray();
            }

    }

    public String sbGenerarDocumentoTextoPlano(int idnegocio, int idpedido, int tipolista) throws IOException {

        List<ListadoCocina> lstDocumentoVenta = 
            iUsuarioService.cocinaPedienteGenerado(idnegocio, idpedido, tipolista);

            long vCantidadRegistros = lstDocumentoVenta.size(); 
            
            Stream<ListadoCocina> lstPedidosDetalle = lstDocumentoVenta.stream().filter(distinctByKey(p -> p.getIdPedido()));
            Stream<ListadoCocina> lstPedidosVenta = lstDocumentoVenta.stream().filter(x -> x.getDescripcionProducto().contains("&&&"));
            vCantidadRegistros =  vCantidadRegistros + lstPedidosDetalle.count();
            vCantidadRegistros = vCantidadRegistros * 150;
            vCantidadRegistros = vCantidadRegistros + (lstPedidosVenta.count() * 25);

            int numeroLetrasMaximoLinea = 24;
            BigDecimal numeroEspacios = BigDecimal.ZERO;
            BigDecimal valorDos = new BigDecimal("2"); 
            int vIdPedido = 0;
            String vMesa = "";
            String vPedido = ""; 
            int vMargenSegunNegocio = 0;

            String vTextoAnidado = "";
            if (idnegocio == 26) {
                numeroLetrasMaximoLinea = 46;
                vMargenSegunNegocio = 9;
            } else {
                numeroLetrasMaximoLinea = 24;
                vMargenSegunNegocio = 0;
            }
            if (idnegocio != 26) {
                for (int i = 0; i < lstDocumentoVenta.size(); i++) {
                    ListadoCocina listadoCocina = lstDocumentoVenta.get(i);

                    if (vIdPedido != listadoCocina.getIdPedido()) {
                        vMesa = "Número de Mesa: " + listadoCocina.getMesa();
                        vPedido = "N° Pedido: " + listadoCocina.getIdPedido(); 
                        vIdPedido = listadoCocina.getIdPedido();

                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vMesa.length()));
                        //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);
                        
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 5 + vMargenSegunNegocio) + vMesa.toUpperCase() + repeatString(" ", numeroEspacios.intValue()) + "\n";
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 7 + vMargenSegunNegocio) + vPedido.toUpperCase() + repeatString(" ", numeroEspacios.intValue()) + "\n";
                        vTextoAnidado = vTextoAnidado + listadoCocina.getNombreUsuario() + "\n";
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 4 + vMargenSegunNegocio) + repeatString("-", numeroLetrasMaximoLinea-2) + "\n";
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 15 + vMargenSegunNegocio) + "(MESERA)" + "\n";
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 4 + vMargenSegunNegocio) + "\n";
                        
                    }
                
                    String vDescripcionProducto = listadoCocina.getDescripcionProducto().split("&&&")[0];
                    if (vDescripcionProducto.length() > 32) {
                        // Dividir la razonSocial en dos líneas
                        int vContador = 0;
                        while (vContador < vDescripcionProducto.length()) {
                            String linea = vDescripcionProducto.substring(vContador, 
                            (vDescripcionProducto.length() > vContador + 32?vContador + 32: vDescripcionProducto.length()));
                            vContador = vContador + 32;
                            vTextoAnidado = vTextoAnidado + linea.trim() + "\n";
                        }
                    } else {                        
                        vTextoAnidado = vTextoAnidado + vDescripcionProducto.toUpperCase() + "\n";
                    }

                    if (listadoCocina.getActualizado() == 1) {
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 15) + "(ACTUALIZADO)\n";
                    }  else if (listadoCocina.getActualizado() == 2) { 
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 15) + "(AGREGADO)\n";
                    } else if (listadoCocina.getActualizado() == 3) { 
                        vTextoAnidado = vTextoAnidado + repeatString(" ", 15) + "(ELIMINADO)\n";
                    }

                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - listadoCocina.getCantidadMesa().length()));
                    
                    if (!listadoCocina.getCantidadMesa().equals("0")) {
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        vTextoAnidado = vTextoAnidado + "(MESA)CANTIDAD: " + listadoCocina.getCantidadMesa().toUpperCase() + "\n";

                        if (listadoCocina.getDescripcionProducto().contains("&&&")) {
                            if (!listadoCocina.getDescripcionProducto().split("&&&")[1].trim().equals("")) {
                                
                                String vDetalle = "\nDETALLE: " + listadoCocina.getDescripcionProducto().toUpperCase().split("&&&")[1];
                                int vContador = 0;

                                while (vContador < vDetalle.length()) {
                                    String linea = vDetalle.substring(vContador, 
                                    (vDetalle.length() > vContador + 32?vContador + 32: vDetalle.length()));
                                    vContador = vContador + 32;
                                    vTextoAnidado = vTextoAnidado + linea.trim() + "\n";
                                }

                                //contentStream.showText("Detalle: " + listadoCocina.getDescripcionProducto().split("&&&")[1]);        
                            }
                        }

                    }


                    if (!listadoCocina.getCantidadLlevar().equals("0")) {
                        numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - listadoCocina.getCantidadMesa().length()));                    
                        numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                        vTextoAnidado = vTextoAnidado + "(LLEVAR)CANTIDAD: " + listadoCocina.getCantidadLlevar() + "\n";
                    
                        System.out.println("Descripcion" + listadoCocina.getDescripcionProducto());
                        if (listadoCocina.getDescripcionProducto().contains("&&&")) {
                            if (!listadoCocina.getDescripcionProducto().split("&&&")[2].trim().equals("")) {
                                
                                String vDetalle = "\nDETALLE: " + listadoCocina.getDescripcionProducto().toUpperCase().split("&&&")[2];
                                int vContador = 0;
                                
                                while (vContador < vDetalle.length()) {
                                    String linea = vDetalle.substring(vContador, 
                                    (vDetalle.length() > vContador + 32?vContador + 32: vDetalle.length()));
                                    vContador = vContador + 32;
                                    vTextoAnidado = vTextoAnidado + linea.trim() + "\n";
                                }
                                        
                            }
                        }

                    }
                        
                    vTextoAnidado = vTextoAnidado + repeatString("-", 32) + "\n\n"; 

                }
            }
            vTextoAnidado = vTextoAnidado + repeatString(" ", 32) + "\n\n\n"; 
            
            lstDocumentoVenta = lstDocumentoVenta.stream().filter(x -> x.getIrCocina() == 1).collect(Collectors.toList());
            vIdPedido = 0;

            String vHoraAtencion = "";
            for (int i = 0; i < lstDocumentoVenta.size(); i++) {
                ListadoCocina listadoCocina = lstDocumentoVenta.get(i);

                if (vIdPedido != listadoCocina.getIdPedido()) {
                    vMesa = "Número de Mesa: " + listadoCocina.getMesa();
                    
                    SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    vHoraAtencion = formato.format(new Date());

                    vHoraAtencion = "Hora de Atención: " + vHoraAtencion;
                    vPedido = "N° Pedido: " + listadoCocina.getIdPedido(); 
                    vIdPedido = listadoCocina.getIdPedido();

                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vMesa.length()));
                    //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);
                    
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 5 + vMargenSegunNegocio) + vMesa.toUpperCase() + repeatString(" ", numeroEspacios.intValue()) + "\n";
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 5 + vMargenSegunNegocio + (idnegocio==26?-10:0)) + vHoraAtencion.toUpperCase() + repeatString(" ", numeroEspacios.intValue()) + "\n";
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 7 + vMargenSegunNegocio) + vPedido.toUpperCase() + repeatString(" ", numeroEspacios.intValue()) + "\n";
                    vTextoAnidado = vTextoAnidado + listadoCocina.getNombreUsuario() + "\n";
                    vTextoAnidado = vTextoAnidado + repeatString(" ",1) + repeatString("-",  numeroLetrasMaximoLinea-2) + "\n";
                    vTextoAnidado = vTextoAnidado + repeatString(" ", numeroLetrasMaximoLinea - 10) + "(COCINA)" + "\n";
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 4 + vMargenSegunNegocio) + "\n";
                    
                }
            
                String vDescripcionProducto = listadoCocina.getDescripcionProducto().split("&&&")[0];
                if (vDescripcionProducto.length() > 32) {
                    // Dividir la razonSocial en dos líneas
                    int vContador = 0;
                    while (vContador < vDescripcionProducto.length()) {
                        String linea = vDescripcionProducto.substring(vContador, 
                        (vDescripcionProducto.length() > vContador + 32?vContador + 32: vDescripcionProducto.length()));
                        vContador = vContador + 32;
                        vTextoAnidado = vTextoAnidado + linea.trim() + "\n";
                    }
                } else {                        
                    vTextoAnidado = vTextoAnidado + vDescripcionProducto.toUpperCase() + "\n";
                }

                if (listadoCocina.getActualizado() == 1) {
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 15) + "(ACTUALIZADO)\n";
                }  else if (listadoCocina.getActualizado() == 2) { 
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 15) + "(AGREGADO)\n";
                } else if (listadoCocina.getActualizado() == 3) { 
                    vTextoAnidado = vTextoAnidado + repeatString(" ", 15) + "(ELIMINADO)\n";
                }

                numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - listadoCocina.getCantidadMesa().length()));
                
                if (!listadoCocina.getCantidadMesa().equals("0")) {
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    vTextoAnidado = vTextoAnidado + "(MESA)CANTIDAD: " + listadoCocina.getCantidadMesa().toUpperCase() + "\n";

                    if (listadoCocina.getDescripcionProducto().contains("&&&")) {
                        if (!listadoCocina.getDescripcionProducto().split("&&&")[1].trim().equals("")) {
                            
                            String vDetalle = "\nDETALLE: " + listadoCocina.getDescripcionProducto().toUpperCase().split("&&&")[1];
                            int vContador = 0;

                            while (vContador < vDetalle.length()) {
                                String linea = vDetalle.substring(vContador, 
                                (vDetalle.length() > vContador + 32?vContador + 32: vDetalle.length()));
                                vContador = vContador + 32;
                                vTextoAnidado = vTextoAnidado + linea.trim() + "\n";
                            }

                            //contentStream.showText("Detalle: " + listadoCocina.getDescripcionProducto().split("&&&")[1]);        
                        }
                    }

                }


                if (!listadoCocina.getCantidadLlevar().equals("0")) {
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - listadoCocina.getCantidadMesa().length()));                    
                    numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    vTextoAnidado = vTextoAnidado + "(LLEVAR)CANTIDAD: " + listadoCocina.getCantidadLlevar() + "\n";
                
                    if (listadoCocina.getDescripcionProducto().contains("&&&")) {
                        if (!listadoCocina.getDescripcionProducto().split("&&&")[2].trim().equals("")) {
                            
                            String vDetalle = "\nDETALLE: " + listadoCocina.getDescripcionProducto().toUpperCase().split("&&&")[2];
                            int vContador = 0;
                            
                            while (vContador < vDetalle.length()) {
                                String linea = vDetalle.substring(vContador, 
                                (vDetalle.length() > vContador + 32?vContador + 32: vDetalle.length()));
                                vContador = vContador + 32;
                                vTextoAnidado = vTextoAnidado + linea.trim() + "\n";
                            }
                                    
                        }
                    }

                }
                    
                vTextoAnidado = vTextoAnidado + repeatString("-", numeroLetrasMaximoLinea - 3) + "\n\n"; 

            }
    
            return (vTextoAnidado.trim().equals("")?" ":vTextoAnidado);
        
    }

    public String sbGenerarReporteCierreCajaTextoPlano(List<ReporteCierre> lReporteCierre, int idNegocio) throws IOException {
                                                                                // x.getTipo().equals("C")
            List<ReporteCierre> lReporteCierreProductos = lReporteCierre.stream().filter( x -> x.getTipo().equals("A")).collect(Collectors.toList());
            List<ReporteCierre> lReporteCierreTipoPago = lReporteCierre.stream().filter( x -> x.getTipo().equals("C")).collect(Collectors.toList());

            long vCantidadRegistros = lReporteCierre.size(); 
            vCantidadRegistros = vCantidadRegistros * 150; 

            int numeroLetrasMaximoLinea = 24;
            BigDecimal numeroEspacios = BigDecimal.ZERO;
            BigDecimal valorDos = new BigDecimal("2"); 
            int vIdPedido = 0;
            String vTitulo = "";
            String vPedido = ""; 
            int vMargenSegunNegocio = 0;

            String vTextoAnidado = "";
            if (idNegocio == 26) {
                numeroLetrasMaximoLinea = 46;
                vMargenSegunNegocio = 6;
            } else {
                numeroLetrasMaximoLinea = 24;
                vMargenSegunNegocio = 0;
            }
             
            vTitulo = "Reporte de Cierre de Caja";
                
            String vHoraAtencion = "";
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            vHoraAtencion = formato.format(new Date());

            vHoraAtencion = "Hora de Cierre: " + vHoraAtencion;
            
            vTextoAnidado = vTextoAnidado + repeatString(" ", 5 + vMargenSegunNegocio) + vTitulo.toUpperCase() + repeatString(" ", numeroEspacios.intValue()) + "\n";
            vTextoAnidado = vTextoAnidado + repeatString(" ", vMargenSegunNegocio) + vHoraAtencion.toUpperCase() + repeatString(" ", numeroEspacios.intValue()) + "\n";
            vTextoAnidado = vTextoAnidado + repeatString(" ", 1) + repeatString("-",  numeroLetrasMaximoLinea) + "\n\n";
            vTextoAnidado = vTextoAnidado + "Descripción" + repeatString(" ", 5) + "Cant." + repeatString(" ", 2) + "Total S/.";
            BigDecimal vTotalCantidad = BigDecimal.ZERO;
            BigDecimal vTotalVendido = BigDecimal.ZERO;

            for (int i = 0; i < lReporteCierreProductos.size(); i++) {
                ReporteCierre listadoCierreTienda = lReporteCierreProductos.get(i);
                numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vTitulo.length()));
                //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);
                                
                String vDescripcionProducto = listadoCierreTienda.getDato2();
                
                vDescripcionProducto = vDescripcionProducto.replace("(Personal)", "(P)");
                vDescripcionProducto = vDescripcionProducto.replace("(Mediana)", "(M)");
                vDescripcionProducto = vDescripcionProducto.replace("(Familiar)", "(F)");

                if (vDescripcionProducto.length() > 25) {
                    int vContador = 0;
                    while (vContador < vDescripcionProducto.length()) {
                        String linea = vDescripcionProducto.substring(vContador, 
                        (vDescripcionProducto.length() > vContador + 25?vContador + 25: vDescripcionProducto.length())) + 
                        repeatString(" ", 3);
                        if (vContador == 0) {
                            linea = linea + listadoCierreTienda.getDato3().replace(".00","") + repeatString(" ", 5) + listadoCierreTienda.getDato5(); 
                        }
                        vContador = vContador + 25;
                        vTextoAnidado = vTextoAnidado + linea.trim() + "\n";
                    }
                } else {
                    vTextoAnidado = vTextoAnidado + vDescripcionProducto.toUpperCase() + 
                    repeatString(" ", 28 - vDescripcionProducto.length()) +  
                    listadoCierreTienda.getDato3().replace(".00","") + repeatString(" ", 5) + listadoCierreTienda.getDato5() + "\n";
                }

                vTotalCantidad = vTotalCantidad.add(new BigDecimal(listadoCierreTienda.getDato3().replace(".00","")));
                if (listadoCierreTienda.getDato5() != null) {
                    vTotalVendido = vTotalVendido.add(new BigDecimal(listadoCierreTienda.getDato5().replace(",", "")));
                }
                //vTextoAnidado = vTextoAnidado + repeatString("-", numeroLetrasMaximoLinea - 3) + "\n\n"; 
            }

            vTextoAnidado = vTextoAnidado + repeatString(" ", 1) + repeatString("-",  numeroLetrasMaximoLinea) + "\n\n";
            vTextoAnidado = vTextoAnidado + repeatString(" ", 1) + "Total" + repeatString(" ", 28) + vTotalCantidad + repeatString(" ", 4) + 
            vTotalVendido.setScale(2,RoundingMode.HALF_UP).toString();

            vTextoAnidado = vTextoAnidado + repeatString(" ", 1) + "\n\n\n";

            BigDecimal vTotalCobrado = BigDecimal.ZERO;

            for (int i = 0; i < lReporteCierreTipoPago.size(); i++) {
                ReporteCierre listadoCierreTienda = lReporteCierreTipoPago.get(i);
                if ( listadoCierreTienda != null) {
                    numeroEspacios = new BigDecimal((numeroLetrasMaximoLinea - vTitulo.length()));
                    //numeroEspacios = numeroEspacios.divide(valorDos).setScale(0,RoundingMode.UP);
                    numeroEspacios = numeroEspacios.subtract(new BigDecimal("4")).setScale(0,RoundingMode.UP);
                                    
                    String vDescripcionPago = listadoCierreTienda.getDato2(); 

                    if (vDescripcionPago.length() > 31) {
                        int vContador = 0;
                        while (vContador < vDescripcionPago.length()) {
                            String linea = vDescripcionPago.substring(vContador, 
                            (vDescripcionPago.length() > vContador + 31?vContador + 31: vDescripcionPago.length())) + 
                            repeatString(" ", 3);
                            if (vContador == 0) {
                                linea = linea + repeatString(" ", 10) + listadoCierreTienda.getDato1(); 
                            }
                            vContador = vContador + 31;
                            vTextoAnidado = vTextoAnidado + linea.trim() + "\n";
                        }
                    } else {
                        
                        if (vDescripcionPago == null) { vDescripcionPago = ""; }

                        if (listadoCierreTienda.getDato1() == null) {
                            vTextoAnidado = vTextoAnidado + vDescripcionPago.toUpperCase() + 
                            repeatString(" ", 45 - (vDescripcionPago.length() + 0)) + 
                            "" + "\n";
                        } else {
                            vTextoAnidado = vTextoAnidado + vDescripcionPago.toUpperCase() + 
                            repeatString(" ", 45 - (vDescripcionPago.length() + listadoCierreTienda.getDato1().length())) + 
                            listadoCierreTienda.getDato1() + "\n";
                        }

                        /*vTextoAnidado = vTextoAnidado + vDescripcionPago.toUpperCase() + 
                        repeatString(" ", 45 - (vDescripcionPago.length() + listadoCierreTienda.getDato1().length())) + 
                        listadoCierreTienda.getDato1() + "\n";*/
                    }

                    
                    if (listadoCierreTienda.getDato1() !=  null) {
                        vTotalCobrado = vTotalCobrado.add(new BigDecimal(listadoCierreTienda.getDato1().replace(",", ""))); 
                    }
                }
                //vTextoAnidado = vTextoAnidado + repeatString("-", numeroLetrasMaximoLinea - 3) + "\n\n"; 
            }

            vTextoAnidado = vTextoAnidado + repeatString(" ", 1) + repeatString("-",  numeroLetrasMaximoLinea) + "\n\n";
            vTextoAnidado = vTextoAnidado + repeatString(" ", 1) + "Total" + repeatString(" ", 32) + 
            vTotalCobrado.setScale(2,RoundingMode.HALF_UP).toString();
            
            vTextoAnidado = vTextoAnidado + repeatString(" ", 1) + "\n\n\n";            

            return vTextoAnidado;
        
    }



    @GetMapping(value="/descargarpedidopdf/{idnegocio}/{idpedido}/{tipolista}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> descargarPedidoPdf(@PathVariable int idnegocio, @PathVariable int idpedido, 
    @PathVariable int tipolista) {
        try { 
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "documento_tiquetera.pdf");
        
            return new ResponseEntity<>(sbGenerarDocumento(idnegocio, idpedido, tipolista), headers, HttpStatus.OK);
            
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listarotrosmovimientoscajero/{idnegocio}/{hoy}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OtrosMovimientosCajeroResponse>> listarOtrosMovimientosCajero(@PathVariable int idnegocio, @PathVariable int hoy) {
        try {  
            if (hoy==1) {
                return ResponseEntity.ok().body(iUsuarioService.listarOtrosMovimientosCajero(idnegocio, new Date()));
            } else {
                return ResponseEntity.ok().body(iUsuarioService.listarOtrosMovimientosCajero(idnegocio, new Date()));
            }
            
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/grabarotrosmovimientoscajero/{idnegocio}/{idoperacion}/{tipoope}/{importe}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> grabarOtrosMovimientosCajero(
        @PathVariable int idnegocio, @PathVariable int idoperacion, @PathVariable int tipoope, @PathVariable BigDecimal importe) {
        try {  
           return ResponseEntity.ok().body(
            iUsuarioService.grabarOtrosMovimientosCajero(idnegocio, idoperacion, new Date(), tipoope, importe));
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/modificarmenupedidounitario",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pedido> modificarMenuPedidoUnitario(@Valid @RequestBody 
    MenuPedidoUnitarioDto menuPedidoUnitario) {
        try {  
            
            if (menuPedidoUnitario.getTaper() == null) {
                menuPedidoUnitario.setTaper(BigDecimal.ZERO);
            } else {
                
            }

            List<RespuestaStd> lst = iUsuarioService.modificarMenuPedidoUnitario(menuPedidoUnitario);

            if(lst.size() > 0) {
                Pedido pedido = new Pedido();
                pedido.setIdPedido(menuPedidoUnitario.getIdPedido());
                pedido.setMensaje("");

                if (menuPedidoUnitario.getIdPedido() > 0 && menuPedidoUnitario.getIdNegocio() == 26) {
                    pedido.setDocumento(" ");
                } else {
                    pedido.setDocumento(sbGenerarDocumentoTextoPlano(menuPedidoUnitario.getIdNegocio(), menuPedidoUnitario.getIdPedido(), 
                    (menuPedidoUnitario.getIdPedido() > 0?1:0)));
                }

                return ResponseEntity.ok().body(pedido);
            } else {
                return ResponseEntity.ok().body(null);
            }

        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/cambiarmesapedido/{idnegocio}/{idpedido}/{mesadestino}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> modificarMenuPedidoUnitario(@PathVariable int idnegocio, @PathVariable int idpedido, 
    @PathVariable int mesadestino) {
        try {
            List<RespuestaStd> lst = iUsuarioService.cambiarMesaPedido(idnegocio, idpedido, mesadestino);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/reportecierretiendadetallecliente/{idnegocio}/{doccliente}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteCierreDetalleCliente>> reporteCierreTiendaDetalleCliente(
        @PathVariable int idnegocio, @PathVariable String doccliente) {
        try {
            List<ReporteCierreDetalleCliente> lst = iUsuarioService.reporteCierreTiendaDetalleCliente(idnegocio, doccliente);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/reportecierretiendadetalledocumento/{idnegocio}/{idpedido}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteCierreDetalleDocumento>> reporteCierreTiendaDetalleDocumento(@
    PathVariable int idnegocio, @PathVariable int idpedido) {
        try {
            List<ReporteCierreDetalleDocumento> lst = iUsuarioService.reporteCierreTiendaDetalleDocumento(idnegocio, idpedido);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/registrarmarcapersonal/{idnegocio}/{numerocelular}/{tipomarca}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> registrarMarcaPersonal(
        @PathVariable int idnegocio, @PathVariable String numerocelular, @PathVariable int tipomarca) {
        try {
            List<RespuestaStd> lst = iUsuarioService.registraMarcaPersonal(idnegocio, numerocelular, tipomarca);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @GetMapping(value="/listarpromociones/{idnegocio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Promociones>> listarPromociones(@PathVariable int idnegocio) {
        try {
            List<Promociones> lst = iUsuarioService.listarPromociones(idnegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/generarpromocion",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> generarPromocion(@Valid @RequestBody PromocionDto promocionDto) {
        try {
            List<RespuestaStd> lst = iUsuarioService.generarPromocion(promocionDto);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/anulardocventa/{idnegocio}/{idpedido}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> anularDocVenta(@PathVariable int idnegocio, @PathVariable int idpedido) {
        try {
            List<RespuestaStd> lst = iUsuarioService.anularDocVenta(idnegocio, idpedido);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/obtenerdisponibilidadcuarto",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DisponibildadCuarto>> obtenerDisponibilidadCuarto(@Valid @RequestBody DisponibilidadCuartosDto disponibilidadCuartosDto) {
        try {
            
            Calendar calendarDesde = Calendar.getInstance();
            Calendar calendarHasta = Calendar.getInstance();

            calendarDesde.set(disponibilidadCuartosDto.getAnioConsultaDesde(), disponibilidadCuartosDto.getMesConsultaDesde()-1, 
            disponibilidadCuartosDto.getDiaConsultaDesde());


            calendarHasta.set(disponibilidadCuartosDto.getAnioConsultaHasta(), disponibilidadCuartosDto.getMesConsultaHasta()-1, 
            disponibilidadCuartosDto.getDiaConsultaHasta());


            if (calendarDesde.getTime().before(calendarHasta.getTime())) {
                
                
                List<DisponibildadCuarto> lst = iUsuarioService.obtenerDisponibilidadCuarto(disponibilidadCuartosDto);
                return ResponseEntity.ok().body(lst);
            } else {
                return ResponseEntity.status(500).body(null);
            }
            
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

    }

    @PostMapping(value="/reporteocupacion",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteOcupacionResponse>> reporteOcupacion(@Valid @RequestBody 
    ReporteCierreDto reporteCierreDto) {
        try {
            
            Calendar calendarDesde = Calendar.getInstance();
            Calendar calendarHasta = Calendar.getInstance();

            if (reporteCierreDto.getAnioSeleccionado() != 0 && reporteCierreDto.getMesSeleccionado() != 0 && 
                reporteCierreDto.getDiaSeleccionado() != 0) {
                if (reporteCierreDto.getAnioSeleccionado() == 100 && reporteCierreDto.getMesSeleccionado() == 100 && 
                reporteCierreDto.getDiaSeleccionado() == 100) {
                    calendarDesde.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    calendarDesde.set(reporteCierreDto.getAnioSeleccionado(), reporteCierreDto.getMesSeleccionado()-1, 
                    reporteCierreDto.getDiaSeleccionado());
                }
            }

            if (reporteCierreDto.getAnioSeleccionadoHasta() != 0 && reporteCierreDto.getMesSeleccionadoHasta() != 0 && 
                reporteCierreDto.getDiaSeleccionadoHasta() != 0) {
                if (reporteCierreDto.getAnioSeleccionadoHasta() == 100 && reporteCierreDto.getMesSeleccionadoHasta() == 100 && 
                    reporteCierreDto.getDiaSeleccionadoHasta() == 100) {
                    calendarHasta.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    calendarHasta.set(reporteCierreDto.getAnioSeleccionadoHasta(), reporteCierreDto.getMesSeleccionadoHasta()-1, 
                    reporteCierreDto.getDiaSeleccionadoHasta());
                }
            }


            if (calendarHasta.getTime().before(calendarDesde.getTime())) {                
                return ResponseEntity.status(500).body(null);
            } else {
                List<ReporteOcupacionResponse> lst = iUsuarioService.reporteOcupacion(reporteCierreDto.getIdNegocio(), calendarDesde.getTime());
                return ResponseEntity.ok().body(lst);
            }
            
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

    }

    @PostMapping(value="/reporteingresogenerados",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteIngresosGeneradosResponse>> reporteIngresoGenerados(@Valid @RequestBody 
    ReporteCierreDto reporteCierreDto) {
        try {
            
            Calendar calendarDesde = Calendar.getInstance();
            Calendar calendarHasta = Calendar.getInstance();

            if (reporteCierreDto.getAnioSeleccionado() != 0 && reporteCierreDto.getMesSeleccionado() != 0 && 
                reporteCierreDto.getDiaSeleccionado() != 0) {
                if (reporteCierreDto.getAnioSeleccionado() == 100 && reporteCierreDto.getMesSeleccionado() == 100 && 
                reporteCierreDto.getDiaSeleccionado() == 100) {
                    calendarDesde.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    calendarDesde.set(reporteCierreDto.getAnioSeleccionado(), reporteCierreDto.getMesSeleccionado()-1, 
                    reporteCierreDto.getDiaSeleccionado());
                }
            }

            if (reporteCierreDto.getAnioSeleccionadoHasta() != 0 && reporteCierreDto.getMesSeleccionadoHasta() != 0 && 
                reporteCierreDto.getDiaSeleccionadoHasta() != 0) {
                if (reporteCierreDto.getAnioSeleccionadoHasta() == 100 && reporteCierreDto.getMesSeleccionadoHasta() == 100 && 
                    reporteCierreDto.getDiaSeleccionadoHasta() == 100) {
                    calendarHasta.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    calendarHasta.set(reporteCierreDto.getAnioSeleccionadoHasta(), reporteCierreDto.getMesSeleccionadoHasta()-1, 
                    reporteCierreDto.getDiaSeleccionadoHasta());
                }
            }


            if (calendarHasta.getTime().before(calendarDesde.getTime())) {                
                return ResponseEntity.status(500).body(null);
            } else {
                List<ReporteIngresosGeneradosResponse> lst = iUsuarioService.reporteIngresosGenerados(reporteCierreDto.getIdNegocio(), 
                calendarDesde.getTime());
                return ResponseEntity.ok().body(lst);
            }
            
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

    }
    
    @PostMapping(value="/reportereservas",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteReservasResponse>> reporteReservas(@Valid @RequestBody 
    ReporteCierreDto reporteCierreDto) {
        try {
            
            Calendar calendarDesde = Calendar.getInstance();
            Calendar calendarHasta = Calendar.getInstance();

            if (reporteCierreDto.getAnioSeleccionado() != 0 && reporteCierreDto.getMesSeleccionado() != 0 && 
                reporteCierreDto.getDiaSeleccionado() != 0) {
                if (reporteCierreDto.getAnioSeleccionado() == 100 && reporteCierreDto.getMesSeleccionado() == 100 && 
                reporteCierreDto.getDiaSeleccionado() == 100) {
                    calendarDesde.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    calendarDesde.set(reporteCierreDto.getAnioSeleccionado(), reporteCierreDto.getMesSeleccionado()-1, 
                    reporteCierreDto.getDiaSeleccionado());
                }
            }

            if (reporteCierreDto.getAnioSeleccionadoHasta() != 0 && reporteCierreDto.getMesSeleccionadoHasta() != 0 && 
                reporteCierreDto.getDiaSeleccionadoHasta() != 0) {
                if (reporteCierreDto.getAnioSeleccionadoHasta() == 100 && reporteCierreDto.getMesSeleccionadoHasta() == 100 && 
                    reporteCierreDto.getDiaSeleccionadoHasta() == 100) {
                    calendarHasta.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    calendarHasta.set(reporteCierreDto.getAnioSeleccionadoHasta(), reporteCierreDto.getMesSeleccionadoHasta()-1, 
                    reporteCierreDto.getDiaSeleccionadoHasta());
                }
            }


            if (calendarHasta.getTime().before(calendarDesde.getTime())) {                
                return ResponseEntity.status(500).body(null);
            } else {
                List<ReporteReservasResponse> lst = iUsuarioService.reporteReservas(reporteCierreDto.getIdNegocio(), 
                calendarDesde.getTime());
                return ResponseEntity.ok().body(lst);
            }
            
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping(value="/reportechecks",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReporteChecksResponse>> reporteChecks(@Valid @RequestBody 
    ReporteCierreDto reporteCierreDto) {
        try {
            
            Calendar calendarDesde = Calendar.getInstance();
            Calendar calendarHasta = Calendar.getInstance();

            if (reporteCierreDto.getAnioSeleccionado() != 0 && reporteCierreDto.getMesSeleccionado() != 0 && 
                reporteCierreDto.getDiaSeleccionado() != 0) {
                if (reporteCierreDto.getAnioSeleccionado() == 100 && reporteCierreDto.getMesSeleccionado() == 100 && 
                reporteCierreDto.getDiaSeleccionado() == 100) {
                    calendarDesde.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    calendarDesde.set(reporteCierreDto.getAnioSeleccionado(), reporteCierreDto.getMesSeleccionado()-1, 
                    reporteCierreDto.getDiaSeleccionado());
                }
            }

            if (reporteCierreDto.getAnioSeleccionadoHasta() != 0 && reporteCierreDto.getMesSeleccionadoHasta() != 0 && 
                reporteCierreDto.getDiaSeleccionadoHasta() != 0) {
                if (reporteCierreDto.getAnioSeleccionadoHasta() == 100 && reporteCierreDto.getMesSeleccionadoHasta() == 100 && 
                    reporteCierreDto.getDiaSeleccionadoHasta() == 100) {
                    calendarHasta.add(Calendar.DAY_OF_MONTH, -1);
                } else {
                    calendarHasta.set(reporteCierreDto.getAnioSeleccionadoHasta(), reporteCierreDto.getMesSeleccionadoHasta()-1, 
                    reporteCierreDto.getDiaSeleccionadoHasta());
                }
            }


            if (calendarHasta.getTime().before(calendarDesde.getTime())) {                
                return ResponseEntity.status(500).body(null);
            } else {
                List<ReporteChecksResponse> lst = 
                iUsuarioService.reporteChecks(reporteCierreDto.getIdNegocio(), calendarDesde.getTime());
                return ResponseEntity.ok().body(lst);
            }
            
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

    }

    @GetMapping(value="/listadolimpieza/{idnegocio}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoLimpiezaResponse>> listadoLimpieza(@PathVariable int idnegocio,
                                                                         @PathVariable String numerocelular, 
                                                                         @PathVariable String nombreusuario) {
        try {
            List<ListadoLimpiezaResponse> lst = 
            iUsuarioService.listadoLimpieza(idnegocio, new Date(), numerocelular, nombreusuario);
            return ResponseEntity.ok().body(lst); 
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
    
    @PostMapping(value="/registrarbitacoralimpieza/{idnegocio}/{idproducto}/{revertir}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> registrarBitacoraLimpieza(@PathVariable int idnegocio,
                                                                        @PathVariable int idproducto,
                                                                        @PathVariable int revertir,
                                                                        @PathVariable String numerocelular,
                                                                        @PathVariable String nombreusuario) {
        try {
            List<RespuestaStd> lst = iUsuarioService.registrarBitacoraLimpieza(idnegocio, idproducto, revertir, numerocelular, nombreusuario);
            return ResponseEntity.ok().body(lst); 
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping(value="/validarreservapub/{idnegocio}/{mesa}/{dia}/{mes}/{anio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> validarReservaPub(@PathVariable int idnegocio,
                                                                @PathVariable int mesa,
                                                                @PathVariable int dia,
                                                                @PathVariable int mes,
                                                                @PathVariable int anio) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, dia);
            calendar.set(Calendar.MONTH, mes + 1);
            calendar.set(Calendar.YEAR, anio);

            List<RespuestaStd> lst = iUsuarioService.validarReservaPub(idnegocio, mesa, calendar.getTime());
            return ResponseEntity.ok().body(lst); 
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping(value="/generarreservapub/{idnegocio}/{dia}/{mes}/{anio}/{mesa}/{numerocelular}/{nombreusuario}/{nombrecliente}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> generarReservaPub(@PathVariable int idnegocio,
                                                                @PathVariable int dia,
                                                                @PathVariable int mes,
                                                                @PathVariable int anio,
                                                                @PathVariable int mesa,
                                                                @PathVariable String numerocelular,
                                                                @PathVariable String nombreusuario,
                                                                @PathVariable String nombrecliente) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, dia);
            calendar.set(Calendar.MONTH, mes + 1);
            calendar.set(Calendar.YEAR, anio);
            
            List<RespuestaStd> lst = iUsuarioService.generarReservaPub(idnegocio, calendar.getTime(), 
                                                                       mesa, numerocelular, nombreusuario, 
                                                                       nombrecliente);
            return ResponseEntity.ok().body(lst); 
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping(value="/confirmarreserva/{idnegocio}/{idpedido}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> confirmarReserva(@PathVariable int idnegocio,
                                                               @PathVariable int idpedido,
                                                               @PathVariable String numerocelular,
                                                               @PathVariable String nombreusuario) {
        try {
            List<RespuestaStd> lst = iUsuarioService.confirmarReserva(idnegocio, idpedido, numerocelular, nombreusuario);
            return ResponseEntity.ok().body(lst); 
        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping(value="/enviarreportecorreo/{idnegocio}/{idrubronegocio}/{tiporeporte}/{anio}/{mes}/{dia}/{aniohasta}/{meshasta}/{diahasta}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> enviarReporteCorreo(@PathVariable int idnegocio, @PathVariable int idrubronegocio, 
    @PathVariable int tiporeporte, @PathVariable int anio, @PathVariable int mes, @PathVariable int dia,
    @PathVariable int aniohasta, @PathVariable int meshasta, @PathVariable int diahasta, @PathVariable String numerocelular, 
    @PathVariable String nombreusuario) {
        try { 
             
            Workbook workbook = new XSSFWorkbook();
            String vNombreArchivo = "";

            List<RespuestaStd> lst = iUsuarioService.obtenerCorreoNegocio(idnegocio);
            String vCorreoElectronico = "";

            if (lst.size() > 0) {
                vCorreoElectronico = lst.get(0).getMensaje();
            }

            if (tiporeporte == 1) { 
                String[] vCabeceraPp = null;
                String[] vCabeceraPc = null;
                String[] vCabeceraTp = null;
                String[] vCabeceraPd = null;
                Sheet sheetPp = null;
                Sheet sheetPc = null;
                Sheet sheetTp = null;
                Sheet sheetPd = null; 
                if (idrubronegocio == 1 || idrubronegocio == 4) {
                    vCabeceraPp = new String[] {"Plato" , "Cantidad Platos", "Précio", "Total", "Adicional", "Importe Generado", "Grupo de Producto"};
                    vCabeceraPc = new String[] {"Cliente" , "Cantidad Platos", "Précio", "Importe Generado"};
                    vCabeceraTp = new String[] {"Efectivo" , "Importe Cobrado"};
                    vCabeceraPd = new String[] {"Documento" , "Estado", "Importe Doc.", "Importe Pagado"};
                } else if (idrubronegocio == 2) {
                    vCabeceraPp = new String[] {"Producto" , "Código de barra", "Cantidad Platos", "Précio", "Importe Generado"};
                    vCabeceraPc = new String[] {"Cliente" , "Cantidad Platos", "Importe Generado"};
                    vCabeceraTp = new String[] {"Efectivo" , "Importe Cobrado"};
                    vCabeceraPd = new String[] {"Documento" , "Estado", "Importe Doc.", "Importe Pagado"};
                }
                
                List<ReporteCierre> lReporteCierre = iUsuarioService.reporteCierreTienda(idnegocio, anio, mes, dia, 
                aniohasta, meshasta, diahasta, numerocelular, nombreusuario);
    
                sheetPp = workbook.createSheet("Por Producto");
                sheetPc = workbook.createSheet("Por Cliente");
                sheetTp = workbook.createSheet("Por Tipo Pago");
                sheetPd = workbook.createSheet("Por Documento");

                Row headerRowPp = sheetPp.createRow(0);
                Row headerRowPc = sheetPc.createRow(0);
                Row headerRowTp = sheetTp.createRow(0);
                Row headerRowPd = sheetPd.createRow(0);

                for (int i = 0; i < vCabeceraPp.length; i++) {
                    headerRowPp.createCell(i).setCellValue(vCabeceraPp[i]);                    
                }

                for (int i = 0; i < vCabeceraPc.length; i++) {
                    headerRowPc.createCell(i).setCellValue(vCabeceraPc[i]);                    
                }

                for (int i = 0; i < vCabeceraTp.length; i++) {
                    headerRowTp.createCell(i).setCellValue(vCabeceraTp[i]);                    
                }

                for (int i = 0; i < vCabeceraPd.length; i++) {
                    headerRowPd.createCell(i).setCellValue(vCabeceraPd[i]);                    
                }
                
                int filaPp = 0;
                int filaPc = 0;
                int filaTp = 0;
                int filaPd = 0;

                for (int i = 0; i < lReporteCierre.size(); i++) {
                        
                    if (lReporteCierre.get(i).getTipo().equals("A")) {
                        filaPp++;
                        Row dataRow = sheetPp.createRow(filaPp);
                        if (idrubronegocio == 1 || idrubronegocio == 4) {
                            dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato2());
                            dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato3());
                            dataRow.createCell(2).setCellValue(lReporteCierre.get(i).getDato6());
                            dataRow.createCell(3).setCellValue(new BigDecimal(lReporteCierre.get(i).getDato6().replaceAll(",","")).multiply(
                                                               new BigDecimal(lReporteCierre.get(i).getDato3().replaceAll(",",""))).setScale(2,RoundingMode.HALF_UP).toString());
                            dataRow.createCell(4).setCellValue(lReporteCierre.get(i).getDato9());
                            dataRow.createCell(5).setCellValue(lReporteCierre.get(i).getDato5());
                            dataRow.createCell(6).setCellValue(lReporteCierre.get(i).getDato8());
                        } else if (idrubronegocio == 2) {
                            dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato2());
                            dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato4());
                            dataRow.createCell(2).setCellValue(lReporteCierre.get(i).getDato3());
                            dataRow.createCell(3).setCellValue(lReporteCierre.get(i).getDato6());
                            dataRow.createCell(4).setCellValue(lReporteCierre.get(i).getDato5());
                        }
                    }

                    if (lReporteCierre.get(i).getTipo().equals("B")) {
                        filaPc++;
                        Row dataRow = sheetPc.createRow(filaPc);
                        if (idrubronegocio == 1 || idrubronegocio == 4) {
                            dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato1() + " " + lReporteCierre.get(i).getDato2());
                            dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato4());
                            dataRow.createCell(2).setCellValue(lReporteCierre.get(i).getDato3());
                            dataRow.createCell(3).setCellValue(new BigDecimal(lReporteCierre.get(i).getDato3().replaceAll(",",""))
                                                               .multiply(new BigDecimal(lReporteCierre.get(i).getDato4().replaceAll(",",""))).setScale(2,RoundingMode.HALF_UP).toString());
                        } else if (idrubronegocio == 2) {
                            dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato1() + " " + lReporteCierre.get(i).getDato2());
                            dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato4());
                            dataRow.createCell(2).setCellValue(lReporteCierre.get(i).getDato3());
                            dataRow.createCell(3).setCellValue(lReporteCierre.get(i).getDato6());
                        }
                    }

                    if (lReporteCierre.get(i).getTipo().equals("C")) {
                        filaTp++;
                        Row dataRow = sheetTp.createRow(filaTp);
                        if (idrubronegocio == 1 || idrubronegocio == 4) {
                            dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato2());
                            dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato1()); 
                        } else if (idrubronegocio == 2) {
                            dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato2());
                            dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato1()); 
                        }
                    }

                    if (lReporteCierre.get(i).getTipo().equals("D")) {
                        filaPd++;
                        Row dataRow = sheetPd.createRow(filaPd);
                        if (idrubronegocio == 1 || idrubronegocio == 4) {
                            dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato1() + lReporteCierre.get(i).getDato2());
                            dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato3());
                            dataRow.createCell(2).setCellValue(lReporteCierre.get(i).getDato4());
                            dataRow.createCell(3).setCellValue(lReporteCierre.get(i).getDato5());
                        } else if (idrubronegocio == 2) {
                            dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato1() + "" + lReporteCierre.get(i).getDato2());
                            dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato4());
                            dataRow.createCell(2).setCellValue(lReporteCierre.get(i).getDato5());
                        }
                    }

                }    
                
                for (int i = 0; i < sheetPp.getRow(0).getPhysicalNumberOfCells(); i++) {
                    sheetPp.autoSizeColumn(i);
                }

                for (int i = 0; i < sheetPc.getRow(0).getPhysicalNumberOfCells(); i++) {
                    sheetPc.autoSizeColumn(i);
                }

                for (int i = 0; i < sheetTp.getRow(0).getPhysicalNumberOfCells(); i++) {
                    sheetTp.autoSizeColumn(i);
                }

                for (int i = 0; i < sheetPd.getRow(0).getPhysicalNumberOfCells(); i++) {
                    sheetPd.autoSizeColumn(i);
                }
            }

            if (tiporeporte == 2) {
                    
                Sheet sheet = null;
                String[] vCabecera = null;
                List<Inventario> lstInventario = iUsuarioService.listarInventario(idnegocio, anio, mes, dia,aniohasta,meshasta,diahasta);  

                sheet = workbook.createSheet("Inventario");
                Map<String, Long> countByPrefix = lstInventario.stream()
                .map(x -> x.getIdProducto() + "") // Obtener el prefijo
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())); // Contar los registros por prefijo

                if (countByPrefix != null) {
                    lstInventario = lstInventario.stream()
                            .filter(x -> {
                                String prefix = x.getIdProducto() + ""; // Obtener el prefijo del registro actual
                                Long count = countByPrefix.get(prefix);
                                return count != null && count > 1; // Filtrar los registros que tengan más de una ocurrencia del mismo prefijo
                            })
                            .collect(Collectors.toList());
                }

                vCabecera = new String[] {"Fecha Movimiento" , "Descripción Producto", "Código Barra", "Motivo", "Ingreso", 
                                          "Salida", "Nuevo Saldo", "Documento"}; 
                
                Row headerRowPd = sheet.createRow(0);

                for (int i = 0; i < vCabecera.length; i++) {
                    headerRowPd.createCell(i).setCellValue(vCabecera[i]);                    
                }
                
                int vIdProducto = 0;
                BigDecimal vTotal = BigDecimal.ZERO;

                for (int i = 0; i < lstInventario.size(); i++) {
                    Row dataRow = sheet.createRow(i+1);

                    if (vIdProducto != lstInventario.get(i).getIdProducto()) {
                        vIdProducto = lstInventario.get(i).getIdProducto();
                        vTotal = BigDecimal.ZERO;
                    } 


                    dataRow.createCell(0).setCellValue(lstInventario.get(i).getOrden1()); 
                    dataRow.createCell(1).setCellValue(lstInventario.get(i).getDescripcionProducto());
                    dataRow.createCell(2).setCellValue(lstInventario.get(i).getCodigoBarra());
                    dataRow.createCell(3).setCellValue(lstInventario.get(i).getMotivo());
                    if (lstInventario.get(i).getTipo().contains("I")) {
                        vTotal = vTotal.add(new BigDecimal(lstInventario.get(i).getStockInicial().replace(",", "")));
                        dataRow.createCell(4).setCellValue(lstInventario.get(i).getStockInicial().replace(",", ""));
                    } else {
                        vTotal = vTotal.subtract(new BigDecimal(lstInventario.get(i).getStockInicial().replace(",", "")));
                        dataRow.createCell(5).setCellValue(lstInventario.get(i).getStockInicial().replace(",", ""));
                    }
                    
                    dataRow.createCell(6).setCellValue(vTotal.toString());
                    dataRow.createCell(7).setCellValue(lstInventario.get(i).getDocumento());
                }

            }

            if (tiporeporte == 1) {
                if (diahasta != dia || meshasta != mes || aniohasta != anio) {
                    vNombreArchivo = "Reporte_de_caja_" + dia + "_" + mes + "_" + anio + "_al_" + 
                    diahasta + "_" + meshasta + "_" + aniohasta + ".xlsx";
                } else {
                    vNombreArchivo = "Reporte_de_caja_" + dia + "_" + mes + "_" + anio + ".xlsx";
                } 
            } else if (tiporeporte == 2)  {
                if (diahasta != dia || meshasta != mes || aniohasta != anio) {
                    vNombreArchivo = "Reporte_de_inventario_del_" + dia + "_" + mes + "_" + anio + "_al_" + 
                    diahasta + "_" + meshasta + "_" + aniohasta + ".xlsx";
                } else {
                    vNombreArchivo = "Reporte_de_inventario_corte_al_" + dia + "_" + mes + "_" + anio + ".xlsx";
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            // Crear un recurso ByteArrayResource para el archivo Excel
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setUsername("ayniapp24@gmail.com");
            mailSender.setPassword("xmxe dvht ergu egki");
    
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(vCorreoElectronico);
            
            if (tiporeporte == 1) {
                if (diahasta != dia || meshasta != mes || aniohasta != anio) {
                    helper.setSubject("Reporte Cierre: Del " + anio + "/" + mes + "/" + dia + " al " + aniohasta + "/" + 
                    meshasta + "/" + diahasta);
                } else {
                    helper.setSubject("Reporte Cierre: a la fecha " + anio + "/" + mes + "/" + dia);
                } 
                //vNombreArchivo = "Reporte_de_caja_" + dia + "_" + mes + "_" + anio + ".xlsx";
            } else if (tiporeporte == 2) {
                if (diahasta != dia || meshasta != mes || aniohasta != anio) {
                    helper.setSubject("Reporte Inventario: Del " + anio + "/" + mes + "/" + dia + " al " + aniohasta + "/" + 
                    meshasta + "/" + diahasta);
                } else {
                    helper.setSubject("Reporte Inventario: a la fecha " + anio + "/" + mes + "/" + dia);
                }
            } 
            
            helper.setText("Reporte Ayni");
            helper.addAttachment(vNombreArchivo, resource); 

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=datos.xlsx");

            RespuestaStd respuestaStd = new RespuestaStd() {

                @Override
                public String getCodigo() {
                    return "OK";
                }

                @Override
                public String getMensaje() {
                    return "Se Envio, Correctamente";
                }
 

                
            };
    
            mailSender.send(message);

            return ResponseEntity.ok().body(respuestaStd);

        } catch (Exception e) { 
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }      
    }

    @PostMapping(value="/envarreporteinventariocorreo/{idnegocio}/{anio}/{mes}/{dia}/{aniohasta}/{meshasta}/{diahasta}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> enviarReporteInventarioCorreo(@PathVariable int idnegocio, 
    @PathVariable int anio, @PathVariable int mes, @PathVariable int dia, @PathVariable int aniohasta, 
    @PathVariable int meshasta, @PathVariable int diahasta) {

        try {
            
            Workbook workbook = new XSSFWorkbook();
            String vNombreArchivo = "";
            int vIdProducto = 0;
            List<RespuestaStd> lst = iUsuarioService.obtenerCorreoNegocio(idnegocio);
            
            String vCorreoElectronico = "";

            if (lst.size() > 0) {
                vCorreoElectronico = lst.get(0).getMensaje();
            }

            String[] vCabecera = null;
            Sheet sheet = null;
            vCabecera = new String[] {"Descripción" , "Fecha Mov.", "Hora Mov.", "Motivo", "Documento o Referencia", "Cantidad", "Nuevo Saldo"};

            List<Inventario> lstInventario = iUsuarioService.listarInventario(idnegocio, anio, mes, dia, 
            aniohasta, meshasta, diahasta);

            sheet = workbook.createSheet("Inventario");
            
            Row headerRowPp = sheet.createRow(0);
            
            for (int i = 0; i < vCabecera.length; i++) {
                headerRowPp.createCell(i).setCellValue(vCabecera[i]);                    
            }

            BigDecimal vTotal = BigDecimal.ZERO;            
            for (int i = 0; i < lstInventario.size(); i++) {
                Row dataRow = sheet.createRow(i+1);
                
                if (vIdProducto != lstInventario.get(i).getIdProducto()) {
                    vIdProducto = lstInventario.get(i).getIdProducto();
                } 

                dataRow.createCell(0).setCellValue(lstInventario.get(i).getDescripcionProducto());
                dataRow.createCell(1).setCellValue(lstInventario.get(i).getFechaCorte()); 
                dataRow.createCell(2).setCellValue(lstInventario.get(i).getFechaCorte()); 
                dataRow.createCell(3).setCellValue(lstInventario.get(i).getMotivo());  
                dataRow.createCell(4).setCellValue(lstInventario.get(i).getDocumento()); 
                dataRow.createCell(5).setCellValue(lstInventario.get(i).getStockInicial());
                dataRow.createCell(6).setCellValue("0"); 

            }

            if (dia == diahasta && mes == meshasta && anio == aniohasta) {
                vNombreArchivo = "Reporte_de_inventario_" + dia + "_" + mes + "_" + anio + ".xlsx";    
            } else {
                vNombreArchivo = "Reporte_de_inventario_" + dia + "_" + mes + "_" + anio + "_al_" + 
                diahasta + "_" + meshasta + "_" + aniohasta + ".xlsx";    
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setUsername("ayniapp24@gmail.com");
            mailSender.setPassword("wypq niep foyl whiy");

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(vCorreoElectronico);
            
            if (diahasta != dia || meshasta != mes || aniohasta != anio) {
                helper.setSubject("Reporte Inventario: Del " + anio + "/" + mes + "/" + dia + " al " + aniohasta + "/" + 
                meshasta + "/" + diahasta);
            } else {
                helper.setSubject("Reporte Inventario: a la fecha " + anio + "/" + mes + "/" + dia);
            } 
        
            helper.setText("Reporte Inventario");
            helper.addAttachment(vNombreArchivo, resource);
        
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=datos.xlsx");

            RespuestaStd respuestaStd = new RespuestaStd() {

                @Override
                public String getCodigo() {
                    return "OK";
                }

                @Override
                public String getMensaje() {
                    return "Se Envio, Correctamente";
                }
 

                
            };
    
            return ResponseEntity.ok().body(respuestaStd);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

    }
    

    public static String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }


    // Arrays para las unidades, decenas y centenas
    private static final String[] unidades = {"", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve"};
    private static final String[] decenas = {"", "diez", "veinte", "treinta", "cuarenta", "cincuenta", "sesenta", "setenta", "ochenta", "noventa"};
    private static final String[] especiales = {"", "once", "doce", "trece", "catorce", "quince", "dieciséis", "diecisiete", "dieciocho", "diecinueve"};
    private static final String[] centenas = {"", "ciento", "doscientos", "trescientos", "cuatrocientos", "quinientos", "seiscientos", "setecientos", "ochocientos", "novecientos"};

    // Método para convertir un número entre 0 y 999 a letras
    private static String convertirNumero(int numero) {
        if (numero < 10) {
            return unidades[numero];
        } else if (numero < 20) {
            return especiales[numero - 10];
        } else if (numero < 100) {
            int unidad = numero % 10;
            if (unidad == 0) {
                return decenas[numero / 10];
            } else {
                return decenas[numero / 10] + " y " + unidades[unidad];
            }
        } else {
            int centena = numero / 100;
            int resto = numero % 100;
            if (resto == 0) {
                return centenas[centena];
            } else {
                return centenas[centena] + " " + convertirNumero(resto);
            }
        }
    }

    // Método principal para convertir un número a letras
    public static String convertirNumeroALetras(int numero) {
        if (numero == 0) {
            return "cero";
        }
        String letras = "";
        if (numero < 0) {
            letras = "menos ";
            numero = Math.abs(numero);
        }
        if (numero < 100) {
            letras += convertirNumero(numero);
        } else if (numero < 1000) {
            letras += convertirNumero(numero);
        } else if (numero < 1000000) {
            int mil = numero / 1000;
            int resto = numero % 1000;
            if (mil == 1) {
                letras += "mil";
            } else {
                letras += convertirNumero(mil) + " mil";
            }
            if (resto > 0) {
                letras += " " + convertirNumero(resto);
            }
        } else {
            letras = "Número fuera de rango";
        }
        return letras;
    }

}
