package com.ayni.coperacion.configuracion.controller;
  
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook; 
import javax.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
import com.ayni.coperacion.dto.InsumoDto;
import com.ayni.coperacion.dto.NegocioDto;
import com.ayni.coperacion.dto.OtrosMovimientosDto;
import com.ayni.coperacion.dto.PedidoDto;
import com.ayni.coperacion.dto.PedidoPagadoDto;
import com.ayni.coperacion.dto.ReporteCierreDetalleDto;
import com.ayni.coperacion.dto.ReporteCierreDto;
import com.ayni.coperacion.dto.UsuarioDto;
import com.ayni.coperacion.response.AgendaServicios;
import com.ayni.coperacion.response.CargoNegocio;
import com.ayni.coperacion.response.CompraNegocioResponse;
import com.ayni.coperacion.response.ConfiguracionNegocio;
import com.ayni.coperacion.response.DocumentoVentaResponse;
import com.ayni.coperacion.response.DocumentosPendientes;
import com.ayni.coperacion.response.Inventario;
import com.ayni.coperacion.response.ListadoCajero;
import com.ayni.coperacion.response.ListadoCocina;
import com.ayni.coperacion.response.ListadoInsumoProducto;
import com.ayni.coperacion.response.ListadoMenu;
import com.ayni.coperacion.response.ListadoProducto;
import com.ayni.coperacion.response.ListadoProductoTienda;
import com.ayni.coperacion.response.ListadoUsuario;
import com.ayni.coperacion.response.Negocio;
import com.ayni.coperacion.response.Pedido;
import com.ayni.coperacion.response.PedidoGenerado;
import com.ayni.coperacion.response.PedidoPagoResponse;
import com.ayni.coperacion.response.ReporteCierre;
import com.ayni.coperacion.response.ReporteCierreDetalle;
import com.ayni.coperacion.response.ReporteCierreDetalleEfectivo;
import com.ayni.coperacion.response.ReportePedido;
import com.ayni.coperacion.response.RespuestaStd;
import com.ayni.coperacion.response.UsuarioReponse;
import com.ayni.coperacion.response.VentasPorProducto;
import com.ayni.coperacion.service.IUsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CooperacionController {
    
    @Autowired
	private IUsuarioService iUsuarioService;

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

            Pedido pedido = new Pedido();

            int idPedido = iUsuarioService.crearMenuPedido(pedidoDto.getIdNegocio(), 
            pedidoDto.getIdPedido(), pedidoDto.getDetalleProducto(), pedidoDto.getMesa(), 
            pedidoDto.getNumeroCelular(), pedidoDto.getNombreUsuario(), pedidoDto.getDocCliente(), 
            pedidoDto.getNombreCliente(), pedidoDto.getDireccionCliente(), pedidoDto.getTipoDoc(), 
            pedidoDto.getNumeroDocumento(), pedidoDto.getComisionDelivery());

            pedido.setIdPedido(idPedido);
            
            return ResponseEntity.ok().body(pedido);

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
            return ResponseEntity.ok().body(lst);
            
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

    @PostMapping(value="/pedidoatendidoindividual/{idnegocio}/{idpedido}/{numerocelular}/{nombreusuario}/{incluirpl}/{idproducto}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> pedidoAtendidoIndividual(@PathVariable int idnegocio, 
                                                             @PathVariable int idpedido, 
                                                             @PathVariable String numerocelular, 
                                                             @PathVariable String nombreusuario,
                                                             @PathVariable int incluirpl,
                                                             @PathVariable int idproducto) {
        try {
 
            List<RespuestaStd> lst = iUsuarioService.pedidoAtendidoIndividual(idnegocio, idpedido, numerocelular, nombreusuario, incluirpl, idproducto);
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
    public ResponseEntity<List<RespuestaStd>> configuracionNegocio(@Valid @RequestBody ConfiguracionNegocioDto configuracionNegocioDto) {
        try {
            List<RespuestaStd> lst = iUsuarioService.
            configuracionNegocio(configuracionNegocioDto.getIdNegocio(), 
            configuracionNegocioDto.getNombreNegocio(),
            configuracionNegocioDto.getDescripcion(), configuracionNegocioDto.getLogo(), 
            configuracionNegocioDto.getEstadoNegocio(), configuracionNegocioDto.getRubroNegocio(),
            configuracionNegocioDto.getUsarLectorBarraBusquedaManual(), 
            configuracionNegocioDto.getEnvioPlatoDirectoACocina(),
            configuracionNegocioDto.getGenerarComprobanteVenta(),
            configuracionNegocioDto.getUsarCorrelativoAutomatico(),
            configuracionNegocioDto.getPedirNombreClientePedidosParaLlevar(),
            configuracionNegocioDto.getCorreoElectronico(),
            configuracionNegocioDto.getCorrelativos(),
            configuracionNegocioDto.getGrupoProductos(),
            configuracionNegocioDto.getCocinas());

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

    @GetMapping(value="/descargarpdf/{idnegocio}/{idpedido}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> descargarPdf(@PathVariable int idnegocio, @PathVariable int idpedido) {
        try { 
            
            List<DocumentoVentaResponse> lstDocumentoVenta = 
            iUsuarioService.obtenerDocumentoVenta(idnegocio, idpedido);
            
            DocumentoVentaResponse cabecera = null;

            if (lstDocumentoVenta.size() > 0) {
                cabecera = lstDocumentoVenta.get(0);
            }

            try (PDDocument document = new PDDocument()) {
                // Tamaño de página para una tiquetera típica (por ejemplo, 80 mm de ancho y 50 mm de alto)
                PDRectangle pageSize = new PDRectangle(80, 50);
                PDPage page = new PDPage(pageSize);
                document.addPage(page);
            
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 7); // Tamaño de fuente reducido para ajustarse al espacio
                contentStream.newLineAtOffset(5, 40); // Ajusta la posición del texto para que quepa en la tiquetera
                
                if (cabecera != null) {
                    contentStream.showText(cabecera.getRazonSocial());
                    contentStream.showText(cabecera.getRucEmpresa());
                }
                
                contentStream.endText();
                contentStream.close();
            
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                document.save(outputStream);
                document.close();
            
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("filename", "documento_tiquetera.pdf");
            
                return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
 

        } catch (Exception e) { 
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
                if (idrubronegocio == 1) {
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
                        if (idrubronegocio == 1) {
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
                        if (idrubronegocio == 1) {
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
                        if (idrubronegocio == 1) {
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
                        if (idrubronegocio == 1) {
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

                    /*CellStyle dateCellStyle = workbook.createCellStyle();
                    CreationHelper createHelper = workbook.getCreationHelper();
                    dateCellStyle.setDataFormat(
                            createHelper.createDataFormat().getFormat("d/mm/yyyy hh:mm:ss AM/PM"));

                    // Crear una celda para la fecha
                    Cell cell = dataRow.createCell(2);
                    cell.setCellValue(fechaDateSalida);
                    cell.setCellStyle(dateCellStyle);

                    cell = dataRow.createCell(3);
                    cell.setCellValue(fechaDateLLegada);
                    cell.setCellStyle(dateCellStyle);*/

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
                        vTotal = vTotal.add(new BigDecimal(lstInventario.get(i).getStockInicial()));
                        dataRow.createCell(4).setCellValue(lstInventario.get(i).getStockInicial());
                    } else {
                        vTotal = vTotal.subtract(new BigDecimal(lstInventario.get(i).getStockInicial()));
                        dataRow.createCell(5).setCellValue(lstInventario.get(i).getStockInicial());
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
            mailSender.setPassword("wypq niep foyl whiy");

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
        
            mailSender.send(message);

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

}
