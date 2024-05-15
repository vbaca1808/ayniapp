package com.ayni.coperacion.controller;
 
import java.io.ByteArrayOutputStream;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.http.HttpHeaders;

import javax.mail.internet.MimeMessage;
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
import com.ayni.coperacion.dto.CompraNegocio;
import com.ayni.coperacion.dto.CompraPagoDto;
import com.ayni.coperacion.dto.ConfiguracionNegocioDto;
import com.ayni.coperacion.dto.InsumoDto;
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
 
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CooperacionController {
    
    @Autowired
	private IUsuarioService iUsuarioService;

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

    @GetMapping(value="/listadocargonegocio/{idnegocio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CargoNegocio>> listadoCargoNegocio(@PathVariable int idnegocio) {
        try {
            List<CargoNegocio> lst = iUsuarioService.listadoCargoNegocio(idnegocio); 
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
            pedidoDto.getNombreCliente(), pedidoDto.getTipoDoc(), pedidoDto.getNumeroDocumento());

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

    @GetMapping(value="/listadococina/{idnegocio}/{anio}/{mes}/{dia}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoCocina>> listadoCocina(@PathVariable int idnegocio,@PathVariable int anio,
                                                             @PathVariable int mes, @PathVariable int dia) {
        try {
            List<ListadoCocina> lst = iUsuarioService.listadoCocina(idnegocio, anio, mes, dia);
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
    
    @PostMapping(value="/agregarquitaadminusuario/{idusuario}/{nombreusuario}/{isadmin}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> agregarquitaAdminUsuario(@PathVariable int idusuario,
                                                                       @PathVariable String nombreusuario,
                                                                       @PathVariable int isadmin) {
        try {
            List<RespuestaStd> lst = iUsuarioService.agregarquitaAdminUsuario(idusuario, nombreusuario, isadmin);

             return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
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

    @GetMapping(value="/listadousuarionegocio/{idnegocio}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ListadoUsuario>> listadoUsuarioNegocio(@PathVariable int idnegocio) {
        try {
            List<ListadoUsuario> lst = iUsuarioService.listadoUsuarioNegocio(idnegocio);
            return ResponseEntity.ok().body(lst);
        } catch (Exception e) { 
            return ResponseEntity.status(500).body(null);
        }      
    }
    
    @PostMapping(value="/configuracionnegocio",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RespuestaStd>> configuracionNegocio(@Valid @RequestBody ConfiguracionNegocioDto configuracionNegocioDto) {
        try {
            List<RespuestaStd> lst = iUsuarioService.
            configuracionNegocio(configuracionNegocioDto.getIdNegocio(), configuracionNegocioDto.getNombreNegocio(),
            configuracionNegocioDto.getDescripcion(), configuracionNegocioDto.getLogo(), configuracionNegocioDto.getEstadoNegocio());
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
    
    @GetMapping(value="/listarinventario/{idnegocio}/{aniocorte}/{mescorte}/{diacorte}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Inventario>> listarInventario(@PathVariable int idnegocio,
                                                                        @PathVariable int aniocorte,
                                                                        @PathVariable int mescorte,
                                                                        @PathVariable int diacorte) {
        try { 

            List<Inventario> lst = iUsuarioService.listarInventario(idnegocio, aniocorte,mescorte,diacorte);
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
    
    @PostMapping(value="/enviarreportecorreo/{idnegocio}/{idrubronegocio}/{tiporeporte}/{anio}/{mes}/{dia}/{numerocelular}/{nombreusuario}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RespuestaStd> enviarReporteCorreo(@PathVariable int idnegocio, @PathVariable int idrubronegocio, 
    @PathVariable int tiporeporte, @PathVariable int anio, @PathVariable int mes, @PathVariable int dia, @PathVariable String numerocelular, 
    @PathVariable String nombreusuario) {
        try { 
            
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
            ZoneId zonaLima = ZoneId.of("America/Lima");

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = null;
            String vTitulo = "";
            String vNombreArchivo = "";
            String[] vCabecera = null;


            if (tiporeporte == 1) {
                
                vTitulo = "Por Producto";

                if (idrubronegocio == 1) {
                    vCabecera = new String[] {"Plato" , "Cantidad Platos", "Précio", "Importe Generado"};
                } else if (idrubronegocio == 2) {
                    vCabecera = new String[] {"Producto", "Codigo Barra", "Cantidad", "Précio", "Importe Generado"};
                }
                
                List<ReporteCierre> lReporteCierre = iUsuarioService.reporteCierreTienda(idnegocio, anio, mes, dia, numerocelular, 
                nombreusuario);

                for (int i = 0; i < lReporteCierre.size(); i++) {
                    Row dataRow = sheet.createRow(i+1);
                
                    if (idrubronegocio == 1) {
                        dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato2());
                        dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato3());
                        dataRow.createCell(2).setCellValue(lReporteCierre.get(i).getDato6());
                        dataRow.createCell(3).setCellValue(lReporteCierre.get(i).getDato5());
                    } else if (idrubronegocio == 2) {
                        dataRow.createCell(0).setCellValue(lReporteCierre.get(i).getDato2());
                        dataRow.createCell(1).setCellValue(lReporteCierre.get(i).getDato4());
                        dataRow.createCell(2).setCellValue(lReporteCierre.get(i).getDato3());
                        dataRow.createCell(3).setCellValue(lReporteCierre.get(i).getDato6());
                        dataRow.createCell(3).setCellValue(lReporteCierre.get(i).getDato5());
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
            }

            sheet = workbook.createSheet(vTitulo);
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < vCabecera.length; i++) {
                headerRow.createCell(i).setCellValue(vCabecera[i]);                    
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            // Crear un recurso ByteArrayResource para el archivo Excel
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setUsername("victor.baca.h@gmail.com");
            mailSender.setPassword("ggzg kopc uqtv frru");

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("victorbaca2@yahoo.es");
            helper.setSubject("Adjunto: Archivo Excel");
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
            return ResponseEntity.status(500).body(null);
        }      
    }

}
