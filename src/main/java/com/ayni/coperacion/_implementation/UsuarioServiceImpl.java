package com.ayni.coperacion._implementation;

import java.math.BigDecimal;
import java.math.RoundingMode; 
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
import com.ayni.coperacion.dto.ConfiguracionNegocioDto;
import com.ayni.coperacion.dto.InsumoDto; 
import com.ayni.coperacion.dto.MenuPedidoUnitarioDto;
import com.ayni.coperacion.dto.NegocioDto;
import com.ayni.coperacion.dto.PedidoPagadoDto;
import com.ayni.coperacion.dto.UsuarioDto;
import com.ayni.coperacion.repository.UsuarioRepository;
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
import com.ayni.coperacion.response.OtrosMovimientosCajeroResponse;
import com.ayni.coperacion.response.Pedido;
import com.ayni.coperacion.response.PedidoGenerado;
import com.ayni.coperacion.response.PedidoInter;
import com.ayni.coperacion.response.PedidoPagoResponse;
import com.ayni.coperacion.response.PedidoResponse;
import com.ayni.coperacion.response.ReporteCierre;
import com.ayni.coperacion.response.ReporteCierreDetalle;
import com.ayni.coperacion.response.ReporteCierreDetalleCliente;
import com.ayni.coperacion.response.ReporteCierreDetalleDocumento;
import com.ayni.coperacion.response.ReporteCierreDetalleEfectivo;
import com.ayni.coperacion.response.ReporteIncidenciasAyni;
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
    public List<RespuestaStd> crearNegocio(NegocioDto negocioDto) {
        try {
            return usuarioRepository.crearNegocio(negocioDto.getIdNegocio(), 
            negocioDto.getNombreNegocio(), negocioDto.getDescripcion(), 
            negocioDto.getLogo(), negocioDto.getEstadoNegocio(), 
            negocioDto.getRubroNegocio(), negocioDto.getUsarLectorBarraBusquedaManual(), 
            negocioDto.getEnvioPlatoDirectoACocina(), negocioDto.getGenerarComprobanteVenta(), negocioDto.getUsarCorrelativoAutomatico(),
            negocioDto.getPedirNombreClientePedidosParaLlevar(), negocioDto.getCorreoElectronico(), 
            negocioDto.getNumeroCelularUsuario(), negocioDto.getNombreUsuario());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'crearNegocio'");

        }
    }

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
            usuarioReponse.setMensajeRespuesta(lst.get(0).getCodigo() + "##" + lst.get(0).getMensaje());

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
                               String nombreCliente, String direccionCliente,  int tipoDoc, String numeroDocumento, 
                               BigDecimal comisionDelivery) {
        try {

            List<PedidoResponse> lstValidarMesaOcupada = usuarioRepository.validarMesaOcupada(idNegocio, mesa, numeroCelular,
            new Date());
 
            if (lstValidarMesaOcupada.size() <= 0 || idPedido > 0) {
                List<RespuestaStd> lst = usuarioRepository.crearMenuPedido(idNegocio, idPedido, 
                new Date(), detalleProducto, mesa, numeroCelular, nombreUsuario,docCliente, nombreCliente, direccionCliente, 
                tipoDoc, numeroDocumento, comisionDelivery);

                if (lst != null && lst.size() > 0) {
                    try {
                        return Integer.parseInt(lst.get(0).getCodigo());   
                    } catch (Exception e) {
                        return 0;
                    }
                } else {
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
            pedidoPagadoDto.getOtros(), pedidoPagadoDto.getCredito(), pedidoPagadoDto.getPropina(),
            pedidoPagadoDto.getSoyCocina(), pedidoPagadoDto.getTipoDocumento(),
            pedidoPagadoDto.getNumeroDocumento());
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
    public List<CargoNegocio> listadoCargoNegocio(int pIdNegocio, String numerocelular, 
                                                  String nombreUsuario) {
        try {
            return usuarioRepository.listadoCargoNegocio(pIdNegocio, numerocelular, nombreUsuario);
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
    public List<ListadoCocina> listadoCocina(int idNegocio, int negociococina, int anio, int mes, int dia) {
        try {
            return usuarioRepository.listadoCocina(idNegocio, negociococina, anio, mes, dia);
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
            actualizarEstadoProductoCocinaDto.getEstadoCocina(), new Date());
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
            actualizarNegocioPedidoDto.getIdProducto(), actualizarNegocioPedidoDto.getNombreProducto(), actualizarNegocioPedidoDto.getPalabraClave(),
            actualizarNegocioPedidoDto.getPrecio(), actualizarNegocioPedidoDto.getIdGrupoProducto(), actualizarNegocioPedidoDto.getEstado(), 
            actualizarNegocioPedidoDto.getStockInicial(), actualizarNegocioPedidoDto.getCodigoBarra(),
            actualizarNegocioPedidoDto.getRecetaInsumo(), actualizarNegocioPedidoDto.getOrdenLista(), 
            actualizarNegocioPedidoDto.getProductoCocina(), actualizarNegocioPedidoDto.getIdNegocioCocina());

        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'actualizarNegocioPedido'");
        }
    }

    @Override
    public List<RespuestaStd> agregarquitaAdminUsuario(int idNegocio, int idUsuario, 
    String nombreusuario, int isAdmin, int admitir, String detalleCargoPermitidos) {
        try {
            return usuarioRepository.agregarquitaAdminUsuario(idNegocio, idUsuario, nombreusuario, 
            isAdmin, admitir, detalleCargoPermitidos);
        } catch (Exception e) {
            e.printStackTrace();
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
    public List<ListadoUsuario> listadoUsuarioNegocio(int idNegocio, String numerCelular, String nombreUsuario) {
        try {
            return usuarioRepository.listadoUsuarioNegocio(idNegocio, numerCelular, nombreUsuario);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listadoUsuarioNegocio'");
        }
    }

    @Override
    public List<RespuestaStd> configuracionNegocio(ConfiguracionNegocioDto configuracionNegocioDto) {
        try {
            return usuarioRepository.configuracionNegocio(configuracionNegocioDto.getIdNegocio(), 
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
            configuracionNegocioDto.getCocinas(),configuracionNegocioDto.getDireccionBluetoothCocina(),
            configuracionNegocioDto.getDireccionBluetoothMesero(), configuracionNegocioDto.getUuidCocina(),
            configuracionNegocioDto.getUuidMesero(), configuracionNegocioDto.getMinutosEntregaCocina(),
            configuracionNegocioDto.getMinutosEntregaNoCocina(), configuracionNegocioDto.getMinutosMesaParalizada(),
            configuracionNegocioDto.getCodigoProductoTaper());
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
    public List<ReporteCierre> reporteCierreTienda(int idNegocio, 
            int anioSeleccionado, int mesSeleccionado, int diaSeleccionado,
            int anioSeleccionadoHasta, int mesSeleccionadoHasta, int diaSeleccionadoHasta, String numerocelular, String nombreusuario) {
        try {
            return usuarioRepository.reporteCierreTienda(idNegocio, 
            anioSeleccionado, mesSeleccionado, diaSeleccionado, anioSeleccionadoHasta, mesSeleccionadoHasta, diaSeleccionadoHasta,
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
            int mesSeleccionado, int diaSeleccionado, int anioSeleccionadoHasta,
            int mesSeleccionadoHasta, int diaSeleccionadoHasta, String numeroCelular, String nombreUsuario, int idProducto) {
        try {
            return usuarioRepository.reporteCierraTiendaDetalle(idNegocio, anioSeleccionado, mesSeleccionado, 
                                                                diaSeleccionado, anioSeleccionadoHasta, mesSeleccionadoHasta, 
                                                                diaSeleccionadoHasta, numeroCelular, nombreUsuario, 
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
            if (pedidoPagadoDto.getPropina() == null) {
                pedidoPagadoDto.setPropina(BigDecimal.ZERO);
            }

            return usuarioRepository.modificarPagoPedido(pedidoPagadoDto.getIdNegocio(), 
            pedidoPagadoDto.getIdPedido(), pedidoPagadoDto.getNumeroCelular(), pedidoPagadoDto.getNombreUsuario(), 
            new Date(), pedidoPagadoDto.getEfectivo(), pedidoPagadoDto.getYape(), pedidoPagadoDto.getPlin(), 
            pedidoPagadoDto.getTarjeta(), pedidoPagadoDto.getOtros(), pedidoPagadoDto.getCredito(),
            pedidoPagadoDto.getPropina(), pedidoPagadoDto.getTipoDocumento(),
            pedidoPagadoDto.getNumeroDocumento());
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

    @Override
    public List<RespuestaStd> obtenerCorreoNegocio(int idNegocio) {
        try {
            return usuarioRepository.obtenerCorreoNegocio(idNegocio);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerCorreoNegocio'");
        }
    }

    @Override
    public List<RespuestaStd> insertarGrupoProducto(int idNegocio, int idGrupoProducto, String descripcionGrupo,
            int ordenLista) {
        try {
            return usuarioRepository.insertarGrupoProducto(idNegocio, idGrupoProducto, descripcionGrupo, ordenLista);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'insertarGrupoProducto'");
        }
    }

    @Override
    public List<RespuestaStd> insertarCocinaNegocio(int idNegocio, int idCocina, String nombreCocina) {
        try {
            return usuarioRepository.insertarCocinaNegocio(idNegocio, idCocina, nombreCocina);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'insertarCocinaNegocio'");
        }
    }

    @Override
    public List<DocumentoVentaResponse> obtenerDocumentoVenta(int idNegocio, int idPedido) {
        try {
            return usuarioRepository.obtenerDocumentoVenta(idNegocio, idPedido);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerDocumentoVenta'");
        }
    }

    @Override
    public List<RespuestaStd> generarDocumentoVentaADocPagado(int idNegocio, int idPedido, int tipoDocumento) {
        try {
            return usuarioRepository.generarDocumentoVentaADocPagado(idNegocio, idPedido, tipoDocumento);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'generarDocumentoVentaADocPagado'");
        }
    }
 
    @Override
    public List<PedidoInter> obtenerDocumentosPendientesImpresion(int idNegocio) {
        try {
            return usuarioRepository.obtenerDocumentosPendientesImpresion(idNegocio);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'obtenerDocumentosPendientesImpresion'");
        }
    }

    @Override
    public List<ListadoCocina> cocinaPedienteGenerado(int idNegocio, int idPedido, int tipolista) {
        try {
            return usuarioRepository.cocinaPedienteGenerado(idNegocio, idPedido, tipolista);         
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'cocinaPedienteGenerado'");
        }
    }

    @Override
    public List<RespuestaStd> modificarMenuPedidoUnitario(MenuPedidoUnitarioDto menuPedidoUnitario) {
        try {
            return usuarioRepository.modificarMenuPedidoUnitario(menuPedidoUnitario.getIdNegocio(), 
            menuPedidoUnitario.getIdPedido(), menuPedidoUnitario.getIdProducto(), 
            menuPedidoUnitario.getCantidad(), menuPedidoUnitario.getCantidadLLevar(), menuPedidoUnitario.getTaper().intValue(),
            menuPedidoUnitario.getDescripcion(), menuPedidoUnitario.getTotal(), new Date());

        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'modificarMenuPedidoUnitario'");
        }
    }

    @Override
    public boolean envioFacturaElectronica(String firma, String distrito,String razonSocial, String ruc, String certificadoDigital, 
    String numeroDocumento, String razonSocialEmisor, String rucEmisor, 
    String direccionEmisor, String formaPago, String igv, String gravado, String total, String porcIgv,
    List<String> lstItems) {
        try {
            // CABECERA XML
            String facturaXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>';";
            facturaXml = facturaXml + "<Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\" ";
            facturaXml = facturaXml + "xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\" ";
            facturaXml = facturaXml + "xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\" "; 
            facturaXml = facturaXml + "xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\" ";
            facturaXml = facturaXml + "xmlns:sac=\"urn:oasis:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1\" ";
            facturaXml = facturaXml + "xmlns:udt=\"urn:un:unece:uncefact:data:draft:UnqualifiedDataTypesSchemaModule:2\">";
            // ----------- >>

            facturaXml = facturaXml + "<ext:UBLExtensions><ext:UBLExtension><ext:ExtensionContent>"; 
            facturaXml = facturaXml + "<ds:Signature Id=\"VitekeySign\">";
            facturaXml = facturaXml + "<ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>";
            facturaXml = facturaXml + "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>";
            facturaXml = facturaXml + "<ds:Reference URI=\"\"><ds:Transforms><ds:Transform Algorithm=\"";
            facturaXml = facturaXml + "http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><ds:Transform Algorithm=\"";
            facturaXml = facturaXml + "http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>";
            facturaXml = facturaXml + "</ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>";
            facturaXml = facturaXml + "<ds:DigestValue>IBeTfLOITZagGDlFGm8LZCyFA7g=</ds:DigestValue></ds:Reference></ds:SignedInfo>";
            facturaXml = facturaXml + "<ds:SignatureValue>";
            facturaXml = facturaXml + firma + "</ds:SignatureValue>";
            facturaXml = facturaXml + "<ds:KeyInfo><ds:X509Data>";
            facturaXml = facturaXml + "<ds:X509SubjectName>C=PE,L=" + distrito + ",O=";
            facturaXml = facturaXml + razonSocial + ",";
            facturaXml = facturaXml + "OU=Validado por SUNAT,";
            facturaXml = facturaXml + "OU=FACTURA ELECTRONICA RUC " + ruc + ",";
            facturaXml = facturaXml + "CN=SOFTWARE DE FACTURACION ELECTRONICA,";
            facturaXml = facturaXml + "1.2.840.113549.1.9.1=#161a70657263792e616e7469636f6e6140766974656b65792e636f6d,";
            facturaXml = facturaXml + "STREET=CAL.GERMAN SCHEREIBER NRO. 276 INT. 240 URB. SANTA ANA</ds:X509SubjectName>";
            facturaXml = facturaXml + "<ds:X509Certificate>";
            facturaXml = facturaXml + certificadoDigital + "</ds:X509Certificate>";
            facturaXml = facturaXml + "</ds:X509Data></ds:KeyInfo></ds:Signature></ext:ExtensionContent></ext:UBLExtension>";
            facturaXml = facturaXml + "</ext:UBLExtensions><cbc:UBLVersionID>2.1</cbc:UBLVersionID><cbc:CustomizationID>2.0</cbc:CustomizationID>";
            facturaXml = facturaXml + "<cbc:ID>"+ numeroDocumento + "</cbc:ID>";
            facturaXml = facturaXml + "<cbc:IssueDate>2024-05-28</cbc:IssueDate>";
            facturaXml = facturaXml + "<cbc:IssueTime>14:30:18</cbc:IssueTime>";
            facturaXml = facturaXml + "<cbc:InvoiceTypeCode listID=\"0101\">01</cbc:InvoiceTypeCode>";
            facturaXml = facturaXml + "<cbc:Note languageLocaleID=\"1000\"><![CDATA[TRECE Y 00/100]]></cbc:Note>";
            facturaXml = facturaXml + "<cbc:DocumentCurrencyCode>PEN</cbc:DocumentCurrencyCode>";
            facturaXml = facturaXml + "<cac:Signature>";
            facturaXml = facturaXml + "<cbc:ID>KEYFACIL</cbc:ID>";
            facturaXml = facturaXml + "<cac:SignatoryParty><cac:PartyIdentification>";
            facturaXml = facturaXml + "<cbc:ID>" + rucEmisor + "</cbc:ID>";
            facturaXml = facturaXml + "</cac:PartyIdentification><cac:PartyName>";
            facturaXml = facturaXml + "<cbc:Name><![CDATA[" + razonSocialEmisor + "]]></cbc:Name>";
            facturaXml = facturaXml + "</cac:PartyName></cac:SignatoryParty><cac:DigitalSignatureAttachment><cac:ExternalReference>";
            facturaXml = facturaXml + "<cbc:URI>https://keyfacil.com</cbc:URI>";
            facturaXml = facturaXml + "</cac:ExternalReference>";
            facturaXml = facturaXml + "</cac:DigitalSignatureAttachment>";
            facturaXml = facturaXml + "</cac:Signature><cac:AccountingSupplierParty><cac:Party><cac:PartyIdentification>";
            facturaXml = facturaXml + "<cbc:ID schemeAgencyName=\"PE:SUNAT\" schemeID=\"6\" schemeName=\"Documento de Identidad\" ";
            facturaXml = facturaXml + "schemeURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06\">";
            facturaXml = facturaXml + rucEmisor;
            facturaXml = facturaXml + "</cbc:ID></cac:PartyIdentification><cac:PartyName><cbc:Name>";
            facturaXml = facturaXml + "<![CDATA[" +  razonSocialEmisor +"]]></cbc:Name></cac:PartyName><cac:PartyLegalEntity>";
            facturaXml = facturaXml + "<cbc:RegistrationName><![CDATA[" +  razonSocialEmisor + "]]></cbc:RegistrationName>";
            facturaXml = facturaXml + "<cac:RegistrationAddress><cbc:AddressTypeCode listAgencyName=\"PE:SUNAT\" "; 
            facturaXml = facturaXml + "listName=\"Establecimientos anexos\">0000</cbc:AddressTypeCode><cac:AddressLine>";
            facturaXml = facturaXml + "<cbc:Line><![CDATA[" + direccionEmisor + "]]></cbc:Line></cac:AddressLine></cac:RegistrationAddress>";
            facturaXml = facturaXml + "</cac:PartyLegalEntity></cac:Party></cac:AccountingSupplierParty><cac:AccountingCustomerParty>";
            facturaXml = facturaXml + "<cac:Party><cac:PartyIdentification><cbc:ID schemeAgencyName=\"PE:SUNAT\" schemeID=\"6\" ";
            facturaXml = facturaXml + "schemeName=\"Documento de Identidad\" schemeURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06\">";
            facturaXml = facturaXml + rucEmisor + "</cbc:ID></cac:PartyIdentification><cac:PartyLegalEntity><cbc:RegistrationName>";
            facturaXml = facturaXml + "<![CDATA[" + razonSocialEmisor + "]]></cbc:RegistrationName><cac:RegistrationAddress>";
            facturaXml = facturaXml + "<cac:AddressLine><cbc:Line><![CDATA[" + direccionEmisor + "]]></cbc:Line></cac:AddressLine>";
            facturaXml = facturaXml + "</cac:RegistrationAddress></cac:PartyLegalEntity></cac:Party></cac:AccountingCustomerParty>";
            facturaXml = facturaXml + "<cac:PaymentTerms><cbc:ID>FormaPago</cbc:ID><cbc:PaymentMeansID>";
            facturaXml = facturaXml + formaPago + "</cbc:PaymentMeansID></cac:PaymentTerms>";
            facturaXml = facturaXml + "<cac:TaxTotal><cbc:TaxAmount currencyID=\"PEN\">";
            facturaXml = facturaXml + igv + "</cbc:TaxAmount><cac:TaxSubtotal><cbc:TaxableAmount currencyID=\"PEN\">";
            facturaXml = facturaXml + gravado + "</cbc:TaxableAmount>";
            facturaXml = facturaXml + "<cbc:TaxAmount currencyID=\"PEN\">" + igv + "</cbc:TaxAmount>";
            facturaXml = facturaXml + "<cac:TaxCategory><cac:TaxScheme>";
            facturaXml = facturaXml + "<cbc:ID>1000</cbc:ID><cbc:Name>IGV</cbc:Name><cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>";
            facturaXml = facturaXml + "</cac:TaxScheme></cac:TaxCategory></cac:TaxSubtotal></cac:TaxTotal>";
            facturaXml = facturaXml + "<cac:LegalMonetaryTotal><cbc:LineExtensionAmount currencyID=\"PEN\">";
            facturaXml = facturaXml + gravado + "</cbc:LineExtensionAmount>";
            facturaXml = facturaXml + "<cbc:TaxInclusiveAmount currencyID=\"PEN\">"+ total +"</cbc:TaxInclusiveAmount>";
            facturaXml = facturaXml + "<cbc:PayableAmount currencyID=\"PEN\">" + total + "</cbc:PayableAmount>";
            facturaXml = facturaXml + "</cac:LegalMonetaryTotal>";

            int vContador = 0;
            for (int i = 0; i < lstItems.size(); i++) {
                vContador++;
                facturaXml = facturaXml + "<cac:InvoiceLine>";
                facturaXml = facturaXml + "<cbc:ID>" + vContador + "</cbc:ID>";
                facturaXml = facturaXml + "<cbc:InvoicedQuantity unitCode=\"NIU\">1.0000000000</cbc:InvoicedQuantity>";
                facturaXml = facturaXml + "<cbc:LineExtensionAmount currencyID=\"PEN\">"+ gravado +"</cbc:LineExtensionAmount>";
                facturaXml = facturaXml + "<cac:PricingReference><cac:AlternativeConditionPrice>";
                facturaXml = facturaXml + "<cbc:PriceAmount currencyID=\"PEN\">" + total + "</cbc:PriceAmount>";
                facturaXml = facturaXml + "<cbc:PriceTypeCode>01</cbc:PriceTypeCode></cac:AlternativeConditionPrice>";
                facturaXml = facturaXml + "</cac:PricingReference><cac:TaxTotal>";
                facturaXml = facturaXml + "<cbc:TaxAmount currencyID=\"PEN\">" + igv + "</cbc:TaxAmount>";
                facturaXml = facturaXml + "<cac:TaxSubtotal><cbc:TaxableAmount currencyID=\"PEN\">" + gravado + "</cbc:TaxableAmount>";
                facturaXml = facturaXml + "<cbc:TaxAmount currencyID=\"PEN\">" + igv + "</cbc:TaxAmount>";
                facturaXml = facturaXml + "<cac:TaxCategory><cbc:Percent>" + porcIgv + "</cbc:Percent>";
                facturaXml = facturaXml + "<cbc:TaxExemptionReasonCode>10</cbc:TaxExemptionReasonCode><cac:TaxScheme><cbc:ID>1000</cbc:ID>";
                facturaXml = facturaXml + "<cbc:Name>IGV</cbc:Name><cbc:TaxTypeCode>VAT</cbc:TaxTypeCode></cac:TaxScheme></cac:TaxCategory>";
                facturaXml = facturaXml + "</cac:TaxSubtotal></cac:TaxTotal>";
                facturaXml = facturaXml + "<cac:Item>";
                facturaXml = facturaXml + "<cbc:Description><![CDATA[" + lstItems.get(i).split("##")[0] + "]]></cbc:Description>";
                facturaXml = facturaXml + "<cac:SellersItemIdentification><cbc:ID><![CDATA[" + lstItems.get(i).split("##")[1] + "]]></cbc:ID>";
                facturaXml = facturaXml + "</cac:SellersItemIdentification></cac:Item>";
                facturaXml = facturaXml + "<cac:Price><cbc:PriceAmount currencyID=\"PEN\">"+ gravado + "</cbc:PriceAmount>";
                facturaXml = facturaXml + "</cac:Price></cac:InvoiceLine>";
            }
            facturaXml = facturaXml + "</Invoice>";

            return true;
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'envioFacturaElectronica'");
        }
    }

    @Override
    public List<OtrosMovimientosCajeroResponse> listarOtrosMovimientosCajero(int idNegocio, Date fechaOperacion) {
        try {
            return usuarioRepository.listarOtrosMovimientosCajero(idNegocio, fechaOperacion);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listarOtrosMovimientos'");
        }
    }

    @Override
    public List<RespuestaStd> grabarOtrosMovimientosCajero(int idNegocio, int idOperacion, Date fechaOpe, int tipoOpe,
            BigDecimal importe) {
        try {
            return usuarioRepository.grabarOtrosMovimientosCajero(idNegocio, idOperacion, fechaOpe, tipoOpe, importe);
        } catch (Exception e) {    
            throw new UnsupportedOperationException("Unimplemented method 'grabarOtrosMovimientosCajero'");
        }
    }

    @Override
    public List<ListadoMenu> obtenerListadoMenuInicial(int idNegocio) {
        try {
            return usuarioRepository.obtenerListadoMenuInicial(idNegocio);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerListadoMenu'");
        }
    }

    @Override
    public List<ListadoMenu> obtenerMenuPedido(int idNegocio, int idPedido) {
        try {
            return usuarioRepository.obtenerMenuPedido(idNegocio, idPedido);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'obtenerMenuPedido'");
        }
    }

    @Override
    public List<RespuestaStd> transferirMesa(int idNegocio, int idPedido, String numeroCelularDestino,
            String nombreUsuarioDestino) {
        try {
            return usuarioRepository.transferirMesa(idNegocio, idPedido, numeroCelularDestino, nombreUsuarioDestino);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'transferirMesa'");
        }
    }

    @Override
    public List<ReporteIncidenciasAyni> reporteIncidenciasAyni(int idNegocio) {
        try {
            return usuarioRepository.reporteIncidenciasAyni(idNegocio, new Date());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'reporteIncidenciasAyni'");
        }
    }

    @Override
    public List<RespuestaStd> actualizarHoraAtencionControlMesa(int idNegocio) {
        try {
            return usuarioRepository.actualizarHoraAtencionControlMesa(idNegocio, new Date());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'actualizarHoraAtencionControlMesa'");
        }
    }

    @Override
    public List<RespuestaStd> cambiarMesaPedido(int idNegocio, int idPedido, int mesa) {
        try {
            return usuarioRepository.cambiarMesaPedido(idNegocio, idPedido, mesa, new Date());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'cambiarMesaPedido'");
        }
    }

    @Override
    public List<RespuestaStd> actualizarCorteInventario(Date fechaCorte) {
        try {
            return usuarioRepository.actualizarCorteInventario(fechaCorte);            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'actualizarCorteInventario'");
        }
    }

    @Override
    public List<ReporteCierreDetalleCliente> reporteCierreTiendaDetalleCliente(int idNegocio, String docCliente) {
        try {
            return usuarioRepository.reporteCierreTiendaDetalleCliente(idNegocio, docCliente);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'reporteCierreTiendaDetalleCliente'");
        }
    }

    @Override
    public List<ReporteCierreDetalleDocumento> reporteCierreTiendaDetalleDocumento(int idNegocio, int idPedido) {
        try {
            return usuarioRepository.reporteCierreTiendaDetalleDocumento(idNegocio, idPedido);            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'reporteCierreTiendaDetalleDocumento'");
        }
    }

}
