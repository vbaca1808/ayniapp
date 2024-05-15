package com.ayni.coperacion._implementation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ayni.coperacion.dto.ActualizarEstadoProductoCocinaDto;
import com.ayni.coperacion.dto.ActualizarNegocioPedidoDto;
import com.ayni.coperacion.dto.CompraNegocio;
import com.ayni.coperacion.dto.CompraPagoDto;
import com.ayni.coperacion.dto.InsumoDto;
import com.ayni.coperacion.dto.PedidoPagadoDto;
import com.ayni.coperacion.dto.UsuarioDto;
import com.ayni.coperacion.repository.UsuarioRepository;
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

@Service
public class UsuarioServiceImpl implements IUsuarioService {
 
    @Autowired
	private UsuarioRepository usuarioRepository;

    @Override
    public UsuarioReponse registro(UsuarioDto usuarioDto) {
        try {
            
            UsuarioReponse usuarioReponse = new UsuarioReponse();
            List<RespuestaStd> lstStd = null;
 
            if (usuarioDto.getActualizar() == 0) {
                System.out.println("BBBBB");
                //Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
                
                double codigoVerificacion = 1000 + Math.random() * 9000;
                BigDecimal bCodigoVerificacion = new BigDecimal(codigoVerificacion).setScale(0,RoundingMode.HALF_UP);

                /*Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+51906653212"),// + usuarioDto.getNumeroCelular()),
                new com.twilio.type.PhoneNumber("+16026352908"), 
                String.valueOf(bCodigoVerificacion)).create();*/
                
                lstStd = usuarioRepository.registrarUsuario(usuarioDto.getNumeroCelular(), 
                usuarioDto.getNombreUsuario(), usuarioDto.getCliente(),usuarioDto.getColaborador(), 
                usuarioDto.getActualizar(),String.valueOf(bCodigoVerificacion));

                usuarioReponse.setCodigoVerificacion(String.valueOf(bCodigoVerificacion));
                usuarioReponse.setNumeroTelefono(usuarioDto.getNumeroCelular());
                usuarioReponse.setMensajeRespuesta(lstStd.get(0).getCodigo() + "&&" + lstStd.get(0).getMensaje());
            } else {
                System.out.println("CCCCC");
                lstStd = usuarioRepository.registrarUsuario(usuarioDto.getNumeroCelular(), 
                usuarioDto.getNombreUsuario(), usuarioDto.getCliente(),usuarioDto.getColaborador(), 
                usuarioDto.getActualizar(),usuarioDto.getCodigoVerificacion());

                usuarioReponse.setCodigoVerificacion("");
                usuarioReponse.setNumeroTelefono(usuarioDto.getNumeroCelular());
                usuarioReponse.setMensajeRespuesta(lstStd.get(0).getCodigo() + "&&" + lstStd.get(0).getMensaje());
            }

            return usuarioReponse;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'registro'");
        }

    }

    @Override
    public List<Negocio> listadoNegocio() {
        try {
            return usuarioRepository.listadoNegocio();
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'listadoNegocio'");
        }
    }

    @Override
    public UsuarioReponse validarCodigoVerificador(UsuarioDto usuarioDto) {
        try {
            List<RespuestaStd> lst = usuarioRepository.registrarUsuario(usuarioDto.getNumeroCelular(), 
            usuarioDto.getNombreUsuario(), usuarioDto.getCliente(),usuarioDto.getColaborador(), 
            usuarioDto.getActualizar(),usuarioDto.getCodigoVerificacion());
            
            UsuarioReponse usuarioReponse = new UsuarioReponse();
            usuarioReponse.setCodigoVerificacion("");
            usuarioReponse.setNumeroTelefono("");
            usuarioReponse.setMensajeRespuesta(lst.get(0).getCodigo() + "&&" + lst.get(0).getMensaje());

            return usuarioReponse;

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'validarCodigoVerificador'");
        }
    }

    @Override
    public List<ListadoMenu> obtenerListadoMenu(int pdIdNegocio, int pIdPedido) {
        try {
            return usuarioRepository.listadoMenu(pdIdNegocio, pIdPedido);            
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'obtenerListadoMenu'");
        }
    }

    @Override
    public int crearMenuPedido(int idNegocio, int idPedido, String detalleProducto, int mesa,
                               String numeroCelular, String nombreUsuario, String docCliente,
                               String nombreCliente, int tipoDoc, String numeroDocumento) {
        try {
            List<RespuestaStd> lst = usuarioRepository.crearMenuPedido(idNegocio, idPedido, 
            new Date(), detalleProducto, mesa, numeroCelular, nombreUsuario,docCliente, nombreCliente, tipoDoc, numeroDocumento);

            if (lst != null && lst.size() > 0) {
                try {
                    return Integer.parseInt(lst.get(0).getCodigo());   
                } catch (Exception e) {
                    return 0;
                }
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'crearMenuPedido'");
        }
    }

    @Override
    public List<RespuestaStd> solicitarConfirmacion(int idnegocio, int idpedido, String numeroCelular, String nombreUsuario) {
        try {
            return usuarioRepository.solicitarConfirmacion(idnegocio, idpedido, numeroCelular, nombreUsuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'solicitarConfirmacion'");
        }
    }

    @Override
    public List<RespuestaStd> confirmarPedido(int idNegocio, int idPedido, String numeroCelular, String nombreUsuario) {
        try {
            return usuarioRepository.confirmarPedido(idNegocio, idPedido, numeroCelular, nombreUsuario,
            new Date());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'confirmarPedido'");
        }
    }

    @Override
    public List<RespuestaStd> pedidoPagado(PedidoPagadoDto pedidoPagadoDto) {
        try {
            return usuarioRepository.pedidoPagado(pedidoPagadoDto.getIdNegocio(), pedidoPagadoDto.getIdPedido(),
            pedidoPagadoDto.getNumeroCelular(), pedidoPagadoDto.getNombreUsuario(), new Date(),
            pedidoPagadoDto.getEfectivo(), pedidoPagadoDto.getYape(), pedidoPagadoDto.getPlin(), pedidoPagadoDto.getTarjeta(),
            pedidoPagadoDto.getOtros(), pedidoPagadoDto.getCredito(), pedidoPagadoDto.getSoyCocina());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'pedidoPagado'");
        }
    }

    @Override
    public List<PedidoGenerado> obtenerPedido(int pdIdNegocio, int pIdPedido, String mesa) {
        try {
            return usuarioRepository.obtenerPedido(pdIdNegocio, pIdPedido, mesa);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'obtenerPedido'");
        }
    }

    @Override
    public Pedido borrarMenuPedido(int idNegocio, int idPedido, String numeroCelular, 
                                   String nombreUsuario, int borrar) {
        try {
            List<RespuestaStd> lst = usuarioRepository.borrarPedido(idNegocio, idPedido, 
            numeroCelular, nombreUsuario, new Date(), borrar);

            if (lst != null && lst.size() > 0 && lst.get(0) != null){
                Pedido pedido = new Pedido();
                pedido.setIdPedido(Integer.parseInt(lst.get(0).getCodigo()));
                pedido.setMensaje(lst.get(0).getMensaje());
                return pedido;
            } else {
                return new Pedido();
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'borrarMenuPedido'");
        }

    }

    @Override
    public List<ReportePedido> listadoPedidos(int idNegocio, int mesa, String numerocelular, String nombreusuario, Date fechaConsulta) {
        try {
            return usuarioRepository.listadoPedidos(idNegocio, mesa, numerocelular, nombreusuario, fechaConsulta);        
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'listadoPedidos'");
        }
    }

    @Override
    public List<RespuestaStd> pedidoAtendido(int idNegocio, int idPedido, String numeroCelular, 
                                             String nombreUsuario, int incluirpl) {
        try {
            return usuarioRepository.pedidoAtendido(idNegocio, idPedido, numeroCelular, 
                                                    nombreUsuario, new Date(), incluirpl);   
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'pedidoAtendido'");
        }
    }

    @Override
    public List<CargoNegocio> listadoCargoNegocio(int pIdNegocio) {
        try {
            return usuarioRepository.listadoCargoNegocio(pIdNegocio);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'listadoCargoNegocio'");
        }
    }

    @Override
    public List<ReporteCierre> reporteCierre(int idNegocio, int anioSelecionado, int mesSeleccionado, int diaSeleccionado, String numerocelular, String nombreusuario) {
        try {
            System.out.println(idNegocio);
            System.out.println(anioSelecionado);
            System.out.println(mesSeleccionado);
            System.out.println(diaSeleccionado);
            System.out.println(numerocelular);
            System.out.println(nombreusuario);

            return usuarioRepository.reporteCierre(idNegocio, anioSelecionado, mesSeleccionado, diaSeleccionado, numerocelular, nombreusuario);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'reporteCierre'");
        }
    }

    @Override
    public List<ListadoCocina> listadoCocina(int idNegocio, int anio, int mes, int dia) {
        try {
            return usuarioRepository.listadoCocina(idNegocio, anio, mes, dia);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listadoCocina'");
        }
    }

    @Override
    public List<RespuestaStd> actualizarEstadoProductoCocina(ActualizarEstadoProductoCocinaDto actualizarEstadoProductoCocinaDto) {
        try {
            return usuarioRepository.actualizarEstadoProductoCocina(actualizarEstadoProductoCocinaDto.getIdNegocio(), 
            actualizarEstadoProductoCocinaDto.getIdPedido(), actualizarEstadoProductoCocinaDto.getIdProducto(), 
            actualizarEstadoProductoCocinaDto.getCantidadMesa(), actualizarEstadoProductoCocinaDto.getCantidadLlevar(),
            actualizarEstadoProductoCocinaDto.getEstadoCocina());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'actualizarEstadoProductoCocina'");
        }
    }

    @Override
    public List<ListadoCajero> listadoCajero(int idNegocio, int anio, int mes, int dia) {
        try {
            return usuarioRepository.listadoCajero(idNegocio, anio, mes, dia);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'listadoCajero'");
        }
    }

    @Override
    public List<RespuestaStd> validarUsuario(String numeroTelefono, String nombreUsuario, String codigoVerificacion) {
        try {
            return usuarioRepository.validarUsuario(numeroTelefono, nombreUsuario, codigoVerificacion);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'validarUsuario'");
        }
    }

    @Override
    public List<RespuestaStd> actualizarNegocioPedido(ActualizarNegocioPedidoDto actualizarNegocioPedidoDto) {
        try { 

            return usuarioRepository.actualizarNegocioPedido(actualizarNegocioPedidoDto.getIdNegocio(), 
            actualizarNegocioPedidoDto.getIdProducto(), actualizarNegocioPedidoDto.getNombreProducto(), 
            actualizarNegocioPedidoDto.getPrecio(), actualizarNegocioPedidoDto.getEstado(), 
            actualizarNegocioPedidoDto.getStockInicial(), actualizarNegocioPedidoDto.getCodigoBarra(),
            actualizarNegocioPedidoDto.getRecetaInsumo(), actualizarNegocioPedidoDto.getOrdenLista(), 
            actualizarNegocioPedidoDto.getProductoCocina());

        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'actualizarNegocioPedido'");
        }
    }

    @Override
    public List<RespuestaStd> agregarquitaAdminUsuario(int idUsuario, String nombreusuario, int isAdmin) {
        try {
            return usuarioRepository.agregarquitaAdminUsuario(idUsuario, nombreusuario, isAdmin);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'agregarquitaAdminUsuario'");
        }
    }

    @Override
    public List<ListadoProducto> listadoProductoNegocio(int idNegocio) {
        try {
            return usuarioRepository.listadoProductosNegocio(idNegocio);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listadoProductoNegocio'");
        }
    }

    @Override
    public List<RespuestaStd> actualizarIdNegocioUsuario(String idUsuario, int idNegocio, int colaborador) {
        try {
            return usuarioRepository.actualizarIdNegocioUsuario(idUsuario, idNegocio, colaborador);            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'actualizarIdNegocioUsuario'");
        }
    }

    @Override
    public List<ListadoUsuario> listadoUsuarioNegocio(int idNegocio) {
        try {
            return usuarioRepository.listadoUsuarioNegocio(idNegocio);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listadoUsuarioNegocio'");
        }
    }

    @Override
    public List<RespuestaStd> configuracionNegocio(int idNegocio, String nombreNegocio, String descripcion, String logo,
            int estadoNegocio) {
        try {
            return usuarioRepository.configuracionNegocio(idNegocio, nombreNegocio, descripcion, logo, estadoNegocio);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'configuracionNegocio'");
        }
    }

    @Override
    public List<ConfiguracionNegocio> obtenerConfiguracionNegocio(int idNegocio) {
        try {
            return usuarioRepository.obtenerConfiguracionNegocio(idNegocio);            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerConfiguracionNegocio'");
        }
    }

    @Override
    public List<ListadoProductoTienda> obtenerListadoProductoTienda(int idNegocio, String codigoBarra) {
        try {
            return usuarioRepository.obtenerListadoProductoTienda(idNegocio, codigoBarra);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerListadoProductoTienda'");
        }
    }

    @Override
    public List<RespuestaStd> compraNegocio(CompraNegocio compraNegocio) {
        try {
            //compraNegocio.getFechaCompra()
            return usuarioRepository.compraNegocio(compraNegocio.getIdNegocio(), compraNegocio.getIdCompra(), compraNegocio.getNombreProveedor(),
            compraNegocio.getRucProveedor(), new Date(), compraNegocio.getTotalCompra(), compraNegocio.getTipoDocumento(),
            compraNegocio.getNumeroDocumento(), compraNegocio.getDetalleCompra(), compraNegocio.getEfectivo(), 
            compraNegocio.getYape(), compraNegocio.getPlin(), compraNegocio.getTarjeta(), compraNegocio.getOtros(),
            compraNegocio.getNombreUsuarioCajero(), compraNegocio.getNumeroCelularCajero());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'compraNegocio'");
        }
    }

    @Override
    public List<Inventario> listarInventario(int idNegocio, int anioCorte, int mesCorte, int diaCorte, int anioHasta, int mesHasta, int diaHasta) {
        try {
            return usuarioRepository.listarInventario(idNegocio, anioCorte, mesCorte, diaCorte, anioHasta, mesHasta, diaHasta);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listarInventario'");
        }
    }

    @Override
    public List<AgendaServicios> agendaServicios(int idnegocio, int tipofiltro, int pais, int anio, int mes) {
        try { 
            ZonedDateTime fechaReferencia = null;

            if (pais == 1) {
                fechaReferencia = ZonedDateTime.now(ZoneId.of("America/Lima"));
            } else if (pais == 2) {
                fechaReferencia = ZonedDateTime.now(ZoneId.of("America/Mexico_City"));
            }

            if (anio > 0) {
                fechaReferencia = fechaReferencia.withYear(anio).withMonth(mes);
                fechaReferencia = fechaReferencia.withDayOfMonth(1);
            }
            
            System.out.println(fechaReferencia);
            return usuarioRepository.agendaServicios(idnegocio, tipofiltro, fechaReferencia);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'agendaServicios'");
        }
    }

    @Override
    public List<ReporteCierre> reporteCierreTienda(int idNegocio, int anioSeleccionado, int mesSeleccionado,
            int diaSeleccionado, String numerocelular, String nombreusuario) {
        try {
            return usuarioRepository.reporteCierreTienda(idNegocio, anioSeleccionado, mesSeleccionado, diaSeleccionado,
            numerocelular, nombreusuario);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'reporteCierreTienda'");
        }
    }

    @Override
    public List<RespuestaStd> otrosMovimiento(int tipoMovimiento, int idNegocio, int idProducto, BigDecimal cantidad,
            Date fechaMovimiento) {
        try {
            return usuarioRepository.otrosMovimiento(tipoMovimiento, idNegocio, idProducto, cantidad, fechaMovimiento);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'otrosMovimiento'");
        }
    }

    @Override
    public List<RespuestaStd> modificarCompra(int idCompra, CompraNegocio compraNegocio) {
        try {
            return usuarioRepository.modificarCompra(compraNegocio.getIdNegocio(), idCompra,
            compraNegocio.getNombreProveedor(), compraNegocio.getNombreProveedor(), 
            compraNegocio.getTotalCompra(), compraNegocio.getTipoDocumento(), 
            compraNegocio.getNumeroDocumento(), compraNegocio.getDetalleCompra());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'modificarCompra'");
        }
    }

    @Override
    public List<CompraNegocioResponse> obtenerDatosCompra(int idNegocio, int idCompra) {
        try {
            return usuarioRepository.obtenerDatosCompra(idNegocio, idCompra);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerDatosCompra'");
        }

    }

    @Override
    public List<DocumentosPendientes> documentosPendientesPago(int idNegocio) {
        try {
            return usuarioRepository.documentosPendientesPago(idNegocio);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'documentosPendientesPago'");
        }
    }

    @Override
    public List<RespuestaStd> compraPago(CompraPagoDto compraPagoDto) {
        try {
            return usuarioRepository.compraPago(compraPagoDto.getIdNegocio(), compraPagoDto.getIdCompra(), 
            new Date(), compraPagoDto.getEfectivo(), compraPagoDto.getYape(), compraPagoDto.getPlin(), 
            compraPagoDto.getTarjeta(), compraPagoDto.getOtros(), compraPagoDto.getNombreUsuarioCajero(), compraPagoDto.getNumeroCelularCajero());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'compraPago'");
        }
    }

    @Override
    public List<RespuestaStd> grabarInsumo(InsumoDto insumoDto) {
        try {
            return usuarioRepository.grabarInsumo(insumoDto.getIdNegocio(), insumoDto.getIdInsumo(), 
            insumoDto.getDescripcionInsumo(), insumoDto.getCantidadInvertida(), 
            insumoDto.getEsCombustible(), insumoDto.getRequiereUsuario());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'grabarInsumo'");
        }
    }

    @Override
    public List<ListadoInsumoProducto> listarInsumoPorProducto(int idNegocio, int idProducto) {
        try {
            return usuarioRepository.listarInsumoPorProducto(idNegocio, idProducto);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listarInsumoPorProducto'");
        }
    }

    @Override
    public List<ListadoInsumoProducto> obtenerInsumosProductoServicio(int idNegocio, int idPedido, int idProducto) {
        try {
            return usuarioRepository.obtenerInsumosProductoServicio(idNegocio, idPedido, idProducto);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerInsumosProductoServicio'");
        }
    }

    @Override
    public List<RespuestaStd> obtenerCobrosAyni(int idNegocio) {
        try {
            return usuarioRepository.obtenerCobrosAyni(idNegocio);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerCobrosAyni'");
        }
    }

    @Override
    public List<RespuestaStd> actualizarLecturaCocina(int idNegocio, int idPedido, int idProducto) {
        try {
            return usuarioRepository.actualizarLecturaCocina(idNegocio,idPedido, idProducto);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'actualizarLecturaCocina'");
        }
    }

    @Override
    public List<RespuestaStd> pedidoAtendidoIndividual(int idNegocio, int idPedido, String numeroCelular,
            String nombreUsuario, int incluirpl, int idProducto) {
            try {
                return usuarioRepository.pedidoAtendidoIndividual(idNegocio, idPedido, numeroCelular, 
                nombreUsuario, new Date(), incluirpl, idProducto);                
            } catch (Exception e) {
                throw new UnsupportedOperationException("Unimplemented method 'pedidoAtendidoIndividual'");
            }
    }

    @Override
    public List<RespuestaStd> pedidoAtendidoRevetirIndividual(int idNegocio, int idPedido, String numeroCelular,
            String nombreUsuario, int incluirpl, int idProducto) {
        try {
            return usuarioRepository.revetirAtendidoIndividual(idNegocio, idPedido, numeroCelular, nombreUsuario, null, incluirpl, idProducto);       
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'pedidoAtendidoRevetirIndividual'");
        }
    }

    @Override
    public List<ReporteCierreDetalle> reporteCierraTiendaDetalle(int idNegocio, int anioSeleccionado,
            int mesSeleccionado, int diaSeleccionado, String numeroCelular, String nombreUsuario, int idProducto) {
        try {
            return usuarioRepository.reporteCierraTiendaDetalle(idNegocio, anioSeleccionado, mesSeleccionado, 
                                                                diaSeleccionado, numeroCelular, nombreUsuario, 
                                                                idProducto);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'reporteCierraTiendaDetalle'");
        }
    }

    @Override
    public List<ReporteCierreDetalleEfectivo> reporteCierraTiendaDetalleEfectivo(int idNegocio, int idTipoPago, int anio, int mes, int dia, 
    String nombreUsuario, String numeroCelular) {
        try {
            return usuarioRepository.reporteCierraTiendaDetalleEfectivo(idNegocio, idTipoPago, anio, mes, dia, nombreUsuario, numeroCelular);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'reporteCierraTiendaDetalleEfectivo'");
        }
    }

    @Override
    public List<PedidoPagoResponse> obtenerPedidoPago(int idNegocio, int idPedido) {
        try {
            return usuarioRepository.obtenerPedidoPago(idNegocio, idPedido);            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerPedidoPago'");
        }
    }

    @Override
    public List<RespuestaStd> modificarPedidoPago(PedidoPagadoDto pedidoPagadoDto) {
        try {
            return usuarioRepository.modificarPagoPedido(pedidoPagadoDto.getIdNegocio(), 
            pedidoPagadoDto.getIdPedido(), pedidoPagadoDto.getNumeroCelular(), pedidoPagadoDto.getNombreUsuario(), 
            new Date(), pedidoPagadoDto.getEfectivo(), pedidoPagadoDto.getYape(), pedidoPagadoDto.getPlin(), 
            pedidoPagadoDto.getTarjeta(), pedidoPagadoDto.getOtros(), pedidoPagadoDto.getCredito());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'modificarPedidoPago'");
        }
    }

    @Override
    public List<VentasPorProducto> buscarVentasPorProducto(int idNegocio, int idPedido, int tipoFiltroFecha) {
        try {
            return usuarioRepository.buscarVentasPorProducto(idNegocio, idPedido, new Date(), tipoFiltroFecha);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'buscarVentasPorProducto'");
        }
    }
 

}
