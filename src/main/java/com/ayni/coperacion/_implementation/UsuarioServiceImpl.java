package com.ayni.coperacion._implementation;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream; 
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL; 
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.persistence.criteria.CriteriaBuilder.In;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ayni.coperacion.dto.ActualizarEstadoProductoCocinaDto;
import com.ayni.coperacion.dto.ActualizarNegocioPedidoDto;
import com.ayni.coperacion.dto.CompraNegocio;
import com.ayni.coperacion.dto.CompraPagoDto;
import com.ayni.coperacion.dto.ConfiguracionNegocioDto;
import com.ayni.coperacion.dto.DisponibilidadCuartosDto;
import com.ayni.coperacion.dto.InsumoDto; 
import com.ayni.coperacion.dto.MenuPedidoUnitarioDto;
import com.ayni.coperacion.dto.NegocioDto;
import com.ayni.coperacion.dto.PedidoPagadoDto;
import com.ayni.coperacion.dto.PromocionDto;
import com.ayni.coperacion.dto.UsuarioDto;
import com.ayni.coperacion.repository.UsuarioRepository;
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
import com.ayni.coperacion.response.Promociones;
import com.ayni.coperacion.response.ReporteCierre;
import com.ayni.coperacion.response.ReporteCierreDetalle;
import com.ayni.coperacion.response.ReporteCierreDetalleCliente;
import com.ayni.coperacion.response.ReporteCierreDetalleDocumento;
import com.ayni.coperacion.response.ReporteCierreDetalleEfectivo;
import com.ayni.coperacion.response.ReporteIncidenciasAyni;
import com.ayni.coperacion.response.ReporteIngresosGeneradosResponse;
import com.ayni.coperacion.response.ReporteOcupacionResponse;
import com.ayni.coperacion.response.ReportePedido;
import com.ayni.coperacion.response.ReporteReservasResponse;
import com.ayni.coperacion.response.RespuestaStd;
import com.ayni.coperacion.response.UsuarioReponse;
import com.ayni.coperacion.response.VentasPorProducto;
import com.ayni.coperacion.service.IUsuarioService;
import com.google.gson.Gson;  

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
                               String nombreCliente, String direccionCliente, int tipoDoc, String numeroDocumento, 
                               BigDecimal comisionDelivery, String fechaReserva, int esReserva) {
        try {

            List<PedidoResponse> lstValidarMesaOcupada = usuarioRepository.validarMesaOcupada(idNegocio, mesa, numeroCelular,
            new Date());
 
            if (lstValidarMesaOcupada.size() <= 0 || idPedido > 0) { 

                Calendar calendar = Calendar.getInstance();
                Calendar calendarFechaActual = Calendar.getInstance();

                List<RespuestaStd> lstResp = new ArrayList<>();

                System.out.println("Fecha texto -> " + fechaReserva);
                if (!fechaReserva.equals("")) {
                    String vDia = fechaReserva.substring(0,2);
                    String vMes = fechaReserva.substring(3,5);
                    String vAnio = fechaReserva.substring(6,10);

                    calendar.set(Calendar.YEAR, Integer.parseInt(vAnio));
                    calendar.set(Calendar.MONTH, Integer.parseInt(vMes) -1);
                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(vDia));
                    calendar.set(Calendar.HOUR_OF_DAY, 0);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);

                    calendarFechaActual.set(Calendar.HOUR_OF_DAY, 0);
                    calendarFechaActual.set(Calendar.MINUTE, 0);
                    calendarFechaActual.set(Calendar.SECOND, 0);
                    calendarFechaActual.set(Calendar.MILLISECOND, 0);

                    lstResp = usuarioRepository.validarCuartosInsertar(idNegocio, detalleProducto, calendar.getTime());

                    if (calendar.getTime().before(calendarFechaActual.getTime())) {
                        return -10;
                    }

                } else {
                    lstResp = usuarioRepository.validarCuartosInsertar(idNegocio, detalleProducto, new Date());
                }

                if (lstResp.size() > 0 ) {
                    if (!lstResp.get(0).getCodigo().equals("OK")) {
                        return -9;
                    }
                }


                System.out.print("Fecha inicial -> " + calendar.getTime());

                List<RespuestaStd> lst = usuarioRepository.crearMenuPedido(idNegocio, idPedido, 
                calendar.getTime(), detalleProducto, mesa, numeroCelular, nombreUsuario,docCliente, nombreCliente, direccionCliente, 
                tipoDoc, numeroDocumento, comisionDelivery, esReserva);

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
            
            if (pedidoPagadoDto.getPropina() == null) {
                pedidoPagadoDto.setPropina(BigDecimal.ZERO);
            }

            if (pedidoPagadoDto.getDescuento() == null) {
                pedidoPagadoDto.setDescuento(BigDecimal.ZERO);
            }
            
            return usuarioRepository.pedidoPagado(pedidoPagadoDto.getIdNegocio(), pedidoPagadoDto.getIdPedido(),
            pedidoPagadoDto.getNumeroCelular(), pedidoPagadoDto.getNombreUsuario(), new Date(),
            pedidoPagadoDto.getEfectivo(), pedidoPagadoDto.getYape(), pedidoPagadoDto.getPlin(), pedidoPagadoDto.getTarjeta(),
            pedidoPagadoDto.getOtros(), pedidoPagadoDto.getCredito(), pedidoPagadoDto.getPropina(),
            pedidoPagadoDto.getDescuento(), pedidoPagadoDto.getSoyCocina(), pedidoPagadoDto.getTipoDocumento(),
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
            return usuarioRepository.validarUsuario(numeroTelefono, nombreUsuario, codigoVerificacion, new Date());
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
            configuracionNegocioDto.getDescripcion(), configuracionNegocioDto.getRucEmpresa(),
            configuracionNegocioDto.getRazonSocial(), configuracionNegocioDto.getDireccion(),
            configuracionNegocioDto.getPorcentajeIgv(),  configuracionNegocioDto.getLogo(),
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

            if (pedidoPagadoDto.getDescuento() == null) {
                pedidoPagadoDto.setDescuento(BigDecimal.ZERO);
            }

            return usuarioRepository.modificarPagoPedido(pedidoPagadoDto.getIdNegocio(), 
            pedidoPagadoDto.getIdPedido(), pedidoPagadoDto.getNumeroCelular(), pedidoPagadoDto.getNombreUsuario(), 
            new Date(), pedidoPagadoDto.getEfectivo(), pedidoPagadoDto.getYape(), pedidoPagadoDto.getPlin(), 
            pedidoPagadoDto.getTarjeta(), pedidoPagadoDto.getOtros(), pedidoPagadoDto.getCredito(),
            pedidoPagadoDto.getPropina(), pedidoPagadoDto.getDescuento(), 
            pedidoPagadoDto.getTipoDocumento(), pedidoPagadoDto.getNumeroDocumento());
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
            String facturaXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?> ";
            facturaXml = facturaXml + "<Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\" ";
            facturaXml = facturaXml + "xmlns:cac=\"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\" ";
            facturaXml = facturaXml + "xmlns:cbc=\"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\" "; 
            facturaXml = facturaXml + "xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\" ";
            facturaXml = facturaXml + "xmlns:sac=\"urn:oasis:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1\" ";
            facturaXml = facturaXml + "xmlns:udt=\"urn:un:unece:uncefact:data:draft:UnqualifiedDataTypesSchemaModule:2\">";
            facturaXml = facturaXml + "<ext:UBLExtensions><ext:UBLExtension><ext:ExtensionContent>"; 
            facturaXml = facturaXml + "<ds:Signature Id=\"VitekeySign\">";
            facturaXml = facturaXml + "<ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>";
            facturaXml = facturaXml + "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"/>";
            facturaXml = facturaXml + "<ds:Reference URI=\"\"><ds:Transforms><ds:Transform Algorithm=\"";
            facturaXml = facturaXml + "http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><ds:Transform Algorithm=\"";
            facturaXml = facturaXml + "http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"/>";
            facturaXml = facturaXml + "</ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>";
            facturaXml = facturaXml + "<ds:DigestValue>+pruib33lOapq6GSw58GgQLR8VGIGqANloj4EqB1cb4=</ds:DigestValue></ds:Reference></ds:SignedInfo>";
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
            facturaXml = facturaXml + "<cbc:IssueDate>2024-06-18</cbc:IssueDate>";
            facturaXml = facturaXml + "<cbc:IssueTime>11:00:00</cbc:IssueTime>";
            facturaXml = facturaXml + "<cbc:InvoiceTypeCode listID=\"0101\">01</cbc:InvoiceTypeCode>";
            facturaXml = facturaXml + "<cbc:Note languageLocaleID=\"1000\"><![CDATA[CIENTO DIECIOCHO Y 00/100]]></cbc:Note>";
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

            /*String facturaXml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>";
            facturaXml = facturaXml + 
            "<Invoice xmlns=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\" xmlns:cac=\"" + 
            "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2\" xmlns:cbc=\"" + 
            "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"" + 
            "xmlns:ext=\"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2\" xmlns:sac=\"" + 
            "urn:oasis:names:specification:ubl:peru:schema:xsd:SunatAggregateComponents-1\" xmlns:udt=\"" + 
            "urn:un:unece:uncefact:data:draft:UnqualifiedDataTypesSchemaModule:2\">" +
            "<ext:UBLExtensions>" + 
            "<ext:UBLExtension>" +
            "<ext:ExtensionContent>" +
            "<ds:Signature Id=\"signatureKG\">" + 
            "<ds:SignedInfo>" + 
            "<ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n20010315#WithComments\"/>" + 
            "<ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#dsa-sha1\"/>" + 
            "<ds:Reference URI=\"\">" + 
            "<ds:Transforms>" + 
            "<ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#envelopedsignature\"/>" + 
            "</ds:Transforms>" + 
            "<ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"/>" + 
            "<ds:DigestValue>+pruib33lOapq6GSw58GgQLR8VGIGqANloj4EqB1cb4=</ds:DigestValue>" + 
            "</ds:Reference>" + 
            "</ds:SignedInfo>" + 
            "<ds:SignatureValue>Oatv5xMfFInuGqiX9SoLDTy2yuLf0tTlMFkWtkdw1z/Ss6kiDz+vIgZhgKfIaxp+JbVy57GT52f10VLMLatdwPVRbrWmz1/NIy5CWp1xWMaM6fC/9SXV0O1Lqopk0UeX2I2yuf05QhmVfjgUu6GnS3m6o6zM9J36iDvMVZyj7vbJTwI8SfWjTSNqxXlqPQ==</ds:SignatureValue>" + 
            "<ds:KeyInfo>" +
            "<ds:X509Data>" +
            "<ds:X509Certificate>MIIF9TCCBN2gAwIBAgIGAK0oRTg/MA0GCSqGSIb3DQEBCwUAMFkxCzAJBgNVBAYTAlRSMUowSAYD" +
            "VQQDDEFNYWxpIE3DvGjDvHIgRWxla3Ryb25payBTZXJ0aWZpa2EgSGl6bWV0IFNhxJ9sYXnEsWPEsXPEsSAtIFRlc3QgMTAeFw0wOTEwMjAxMTM3MTJaFw0xNDEwMTkxMTM3MTJaMIGgMRowGAYDVQQL" +
            "DBFHZW5lbCBNw7xkw7xybMO8azEUMBIGA1UEBRMLMTAwMDAwMDAwMDIxbDBqBgNVBAMMY0F5ZMSxbiBHcm91cCAtIFR1cml6bSDEsHRoYWxhdCDEsGhyYWNhdCBUZWtzdGlsIMSwbsWfYWF0IFBhemFy" +
            "iMwtPnC2DRjdsyGv3bxwRZr9wXMRrMNwRjyFe9JPA7bSscEgaXwzDUG5FCvfS/PNT+XCce+VECAx6Q3R1ZRSA49fYz6tDB4Ia5HVBXZODmrCs26XisHF6kuS5N/yGg8E7VC1BRr/SmxXeLTdjQYAfo7l" +
            "xCz4dT6wP5TOiBvF+lyWW1bi9nbliXyb/e5HjCp4k/ra9LTskjbY/Ukl5O8G9JEAViZkjvxDX7T0yVRHgMGiioIKVMwU6Lrtln607BNurLwED0OeoZ4wBgkBiB5vXofreXrfN2pHZ2=</ds:X509Certificate>" +
            "</ds:X509Data>" +
            "</ds:KeyInfo>" +
            "</ds:Signature>" +
            "</ext:ExtensionContent>" +
            "</ext:UBLExtension>" +
            "</ext:UBLExtensions>" +
            "<cbc:UBLVersionID>2.1</cbc:UBLVersionID>" +
            "<cbc:CustomizationID>2.0</cbc:CustomizationID>" +
            "<cbc:ProfileID" +
            "schemeName=\" SUNAT:Identificador de Tipo de Operación" +
            "schemeAgencyName=\"PE:SUNAT\"" +
            "schemeURI=\" urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo17\">0101</cbc:ProfileID> " +
            "<cbc:ID>F002-10</cbc:ID>" +
            "<cbc:IssueDate>2017-04-28</cbc:IssueDate>" +
            "<cbc:IssueTime>11:40:21</cbc:IssueTime>" +
            "<cbc:DueDate>2017-05-28</cbc:DueDate>" +
            "<cbc:InvoiceTypeCode" +
            "listAgencyName=\"PE:SUNAT\"" +
            "listName=\"SUNAT:Identificador de Tipo de Documento\"" +
            "listURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01\">01</cbc:InvoiceTypeCode>" +
            "<cbc:Note" +
            "languageLocaleID=\"1000\">MIL OCHOCIENTOS CINCUENTA Y OCHO CON 59/100 Soles</cbc:Note>" +
            "<cbc:Note" +
            "languageLocaleID=\"3000\">05010020170428000005</cbc:Note>" +
            "<cbc:DocumentCurrencyCode" +
            "listID=\"ISO 4217 Alpha\"" +
            "listName=\"Currency\"" +
            "listAgencyName=\"United Nations Economic Commission for Europe\">PEN</cbc:DocumentCurrencyCode>" +
            "<cac:DespatchDocumentReference>" + 
            "<cbc:ID>031-002020</cbc:ID>" + 
            "<cbc:DocumentTypeCode" + 
            "listAgencyName=\"PE:SUNAT\"" + 
            "listName=\"SUNAT:Identificador de guía relacionada\"" +
            "listURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo01\">09</cbc:DocumentTypeCode>" +
            "</cac:DespatchDocumentReference>" + 
            "<cac:AdditionalDocumentReference>" + 
            "<cbc:ID>024099</cbc:ID>" + 
            "<cbc:DocumentTypeCode" + 
            "listAgencyName=\"PE:SUNAT\"" + 
            "listName=\"SUNAT: Identificador de documento relacionado\"" + 
            "listURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo12\">99</cbc:DocumentTypeCode>" + 
            "</cac:AdditionalDocumentReference>" + 
            "<cac:AccountingSupplierParty>" +
            "<cac:Party>" +
            "<cac:PartyName>" +
            "<cbc:Name><![CDATA[K&G Laboratorios]]></cbc:Name>" +
            "</cac:PartyName>" +
            "<cac:PartyTaxScheme>" +
            "<cbc:RegistrationName><![CDATA[K&G Asociados S. A.]]></cbc:RegistrationName>" +
            "<CompanyID" +
            "schemeID=\"6\"" +
            "schemeName=\"SUNAT:Identificador de Documento de Identidad\"" +
            "schemeAgencyName=\"PE:SUNAT\"" +
            "schemeURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06\">20100113612</CompanyID>" +
            "<cac:TaxScheme>" +
            "<cbc:ID>-</cbc:ID>" +
            "</cac:TaxScheme>" +
            "<cac:RegistrationAddress>" +
            "<cbc:AddressTypeCode>0001</cbc:AddressTypeCode>" +
            "</cac:RegistrationAddress>" +
            "</cac:PartyTaxScheme>" +
            "</cac:Party>" +
            "</cac:AccountingSupplierParty>" +
            "<cac:AccountingCustomerParty>" +
            "<cac:Party>" +
            "<cac:PartyTaxScheme>" +
            "<cbc:RegistrationName><![CDATA[CECI FARMA IMPORT S.R.L.]]></cbc:RegistrationName>" +
            "<cbc:CompanyID schemeID=\"6\"" +
            "schemeName=\"SUNAT:Identificador de Documento de Identidad\"" +
            "schemeAgencyName=\"PE:SUNAT\"" +
            "schemeURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo06\">20102420706</cbc:CompanyID>" +
            "<cac:TaxScheme>" + 
            "<cbc:ID>-</cbc:ID>" + 
            "</cac:TaxScheme>" + 
            "</cac:PartyTaxScheme>" + 
            "</cac:Party>" + 
            "</cac:AccountingCustomerParty>" + 
            "<cac:DeliveryTerms>" + 
            "<cac:DeliveryLocation >" + 
            "<cac:Address>" + 
            "<cbc:StreetName>CALLE NEGOCIOS # 420</cbc:StreetName>" + 
            "<cbc:CitySubdivisionName/>" + 
            "<cbc:CityName>LIMA</cbc:CityName>" + 
            "<cbc:CountrySubentity>LIMA</cbc:CountrySubentity>" + 
            "<cbc:CountrySubentityCode>150141</cbc:CountrySubentityCode>" + 
            "<cbc:District>SURQUILLO</cbc:District>" + 
            "<cac:Country>" + 
            "<cbc:IdentificationCode" + 
            "listID=\"ISO 3166-1\"" + 
            "listAgencyName=\"United Nations Economic Commission for Europe\"" + 
            "listName=\"Country\">PE</cbc:IdentificationCode>" + 
            "</cac:Country>" + 
            "</cac:Address>" + 
            "</cac:DeliveryLocation>" + 
            "</cac:DeliveryTerms>" + 
            "<cac:AllowanceCharge>" + 
            "<cbc:ChargeIndicator>False</cbc:ChargeIndicator>" + 
            "<cbc:AllowanceChargeReasonCode>00</cbc:AllowanceChargeReasonCode>" + 
            "<cbc:Amount currencyID=\"PEN\">60.00</cbc:Amount>" + 
            "<cbc:BaseAmount currencyID=\"PEN\">1439.48</cbc:BaseAmount>" + 
            "</cac:AllowanceCharge>" + 
            "<cac:TaxTotal>" + 
            "<cbc:TaxAmount currencyID=\"PEN\">259.11</cbc:TaxAmount>" + 
            "<cac:TaxSubtotal>" + 
            "<cbc:TaxableAmount currencyID=\"PEN\">1439.48</cbc:TaxableAmount>" + 
            "<cbc:TaxAmount currencyID=\"PEN\">259.11</cbc:TaxAmount>" + 
            "<cac:TaxCategory>" + 
            "<cbc:ID" + 
            "schemeID=\"UN/ECE 5305\"" + 
            "schemeName=\"Tax Category Identifier\"" + 
            "schemeAgencyName=\"United Nations Economic Commission for Europe\">S</cbc:ID>" + 
            "<cac:TaxScheme>" + 
            "<cbc:ID schemeID=\"UN/ECE 5305\" schemeAgencyID=\"6\">1000</cbc:ID>" + 
            "<cbc:Name>IGV</cbc:Name>" + 
            "<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>" + 
            "</cac:TaxScheme>" + 
            "</cac:TaxCategory>" + 
            "</cac:TaxSubtotal>" + 
            "<cac:TaxSubtotal>" + 
            "<cbc:TaxableAmount currencyID=\"PEN\">320.00</cbc:TaxableAmount>" + 
            "<cbc:TaxAmount currencyID=\"PEN\">0.00</cbc:TaxAmount>" + 
            "<cac:TaxCategory>" + 
            "<cbc:ID" + 
            "schemeID=\"UN/ECE 5305\"" + 
            "schemeName=\"Tax Category Identifier\"" + 
            "schemeAgencyName=\"United Nations Economic Commission for Europe\">E</cbc:ID>" + 
            "<cac:TaxScheme>" + 
            "<cbc:ID schemeID=\"UN/ECE 5305\" schemeAgencyID=\"6\">9997</cbc:ID>" + 
            "<cbc:Name>EXONERADO</cbc:Name>" + 
            "<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>" + 
            "</cac:TaxScheme>" + 
            "</cac:TaxCategory>" + 
            "</cac:TaxSubtotal>" + 
            "</cac:TaxTotal>" + 
            "<cac:LegalMonetaryTotal>" + 
            "<cbc:LineExtensionAmount currencyID=\"PEN\">1439.48</cbc:LineExtensionAmount> " + 
            "<cbc:TaxInclusiveAmount currencyID=\"PEN\">1698.59</cbc:TaxInclusiveAmount> " + 
            "<cbc:AllowanceTotalAmount currencyID=\"PEN\">60.00</cbc:AllowanceTotalAmount> " + 
            "<cbc:ChargeTotalAmount currencyID=\"PEN\">320.00</cbc:ChargeTotalAmount> " + 
            "<cbc:PrepaidAmount currencyID=\"PEN\">100.00</cbc:PrepaidAmount>" + 
            "<cbc:PayableAmount currencyID=\"PEN\">1858.59</cbc:PayableAmount>" + 
            "</cac:LegalMonetaryTotal>" + 
            "<cac:InvoiceLine>" + 
            "<cbc:ID>1</cbc:ID>" + 
            "<cbc:InvoicedQuantity" + 
            "unitCode=\"CS\"" + 
            "unitCodeListID=\"UN/ECE rec 20\"" + 
            "unitCodeListAgencyName=\"United Nations Economic Commission for Europe\">50</cbc:InvoicedQuantity>" + 
            "<cbc:LineExtensionAmount currencyID=\"PEN\">1439.48</cbc:LineExtensionAmount>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:PricingReference>" + 
            "<cac:AlternativeConditionPrice>" + 
            "<cbc:PriceAmount currencyID=\"PEN\">34.99</cbc:PriceAmount>" + 
            "<cbc:PriceTypeCode listName=\"SUNAT:Indicador de Tipo de Precio\" listAgencyName=\"PE:SUNAT\"" + 
            "listURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16\">01</cbc:PriceTypeCode>" + 
            "</cac:AlternativeConditionPrice>" + 
            "</cac:PricingReference>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:PricingReference>" + 
            "<cac:AlternativeConditionPrice>" + 
            "<cbc:PriceAmount currencyID=\"PEN\">250.00</cbc:PriceAmount>" + 
            "<cbc:PriceTypeCode" + 
            "listName=\"SUNAT:Indicador de Tipo de Precio\"" + 
            "listAgencyName=\"PE:SUNAT\"" + 
            "listURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo16\">02</cbc:PriceTypeCode>" + 
            "</cac:AlternativeConditionPrice>" + 
            "</cac:PricingReference>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:AllowanceCharge>" + 
            "<cbc:ChargeIndicator>false</cbc:ChargeIndicator>" + 
            "<cbc:AllowanceChargeReasonCode>00</cbc:AllowanceChargeReasonCode>" + 
            "<cbc:Amount currencyID=\"PEN\">143.95</cbc:Amount>" + 
            "<cbc:BaseAmount currencyID=\"PEN\">1439.48</cbc:BaseAmount>" + 
            "</cac:AllowanceCharge>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:AllowanceCharge>" + 
            "<cbc:ChargeIndicator>true</cbc:ChargeIndicator>" + 
            "<cbc:AllowanceChargeReasonCode>50</cbc:AllowanceChargeReasonCode>" + 
            "<cbc:MultiplierFactorNumeric>0.10</cbc:MultiplierFactorNumeric>" + 
            "<cbc:Amount currencyID=\"PEN\">44.82</cbc:Amount>" + 
            "<cbc:BaseAmount currencyID=\"PEN\">448.20</cbc:BaseAmount>" + 
            "</cac:AllowanceCharge>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:TaxTotal>" + 
            "<cbc:TaxAmount currencyID=\"PEN\">259.11</cbc:TaxAmount>" + 
            "<cac:TaxSubtotal>" + 
            "<cbc:TaxableAmount currencyID=\"PEN\">1439.48</cbc:TaxableAmount>" + 
            "<cbc:TaxAmount currencyID=\"PEN\">259.11</cbc:TaxAmount>" + 
            "<cac:TaxCategory>" + 
            "<cbc:ID" + 
            "schemeID=\"UN/ECE 5305\"" + 
            "schemeName=\"Tax Category Identifier\"" + 
            "schemeAgencyName=\"United Nations Economic Commission for Europe\">S</cbc:ID>" + 
            "<cbc:Percent>18.00</cbc:Percent>" + 
            "<cbc:TaxExemptionReasonCode" + 
            "listAgencyName=\"PE:SUNAT\"" + 
            "listName=\"SUNAT:Codigo de Tipo de Afectación del IGV\"" + 
            "listURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07\">10</cbc:TaxExemptionReasonCode>" + 
            "<cac:TaxScheme>" + 
            "<cbc:ID schemeID=\"UN/ECE 5153\" schemeName=\"Tax Scheme Identifier\"" + 
            "schemeAgencyName=\"United Nations Economic Commission for \"" + 
            "Europe\">1000</cbc:ID>" + 
            "<cbc:Name>IGV</cbc:Name>" + 
            "<cbc:TaxTypeCode>VAT</cbc:TaxTypeCode>" + 
            "</cac:TaxScheme>" + 
            "</cac:TaxCategory>" + 
            "</cac:TaxSubtotal>" + 
            "</cac:TaxTotal>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:TaxTotal>" + 
            "<cbc:TaxAmount currencyID=\"PEN\">1750.52</cbc:TaxAmount>" + 
            "<cac:TaxSubtotal>" + 
            "<cbc:TaxableAmount currencyID=\"PEN\">8752.60</cbc:TaxableAmount>" + 
            "<cbc:TaxAmount currencyID=\"PEN\">1750.52</cbc:TaxAmount>" + 
            "<cac:TaxCategory>" + 
            "<cbc:ID" + 
            "schemeID=\"UN/ECE 5305\"" + 
            "schemeName=\"Tax Category Identifier\"" + 
            "schemeAgencyName=\"United Nations Economic Commission for Europe\">S</cbc:ID>" + 
            "<cbc:Percent>20.00</cbc:Percent>" + 
            "<cbc:TaxExemptionReasonCode" + 
            "listAgencyName=\"PE:SUNAT\"" + 
            "listName=\"SUNAT:Codigo de Tipo de Afectación del IGV\"" + 
            "listURI=\"urn:pe:gob:sunat:cpe:see:gem:catalogos:catalogo07\">10</cbc:TaxExemptionReasonCode>" + 
            "<cac:TierRange>01</cac:TierRange>" + 
            "<cac:TaxScheme>" + 
            "<cbc:ID schemeID=\"UN/ECE 5153\" schemeName=\"Tax Scheme Identifier\"" + 
            "schemeAgencyName=\"United Nations Economic Commission for Europe\">2000</cbc:ID>" + 
            "<cbc:Name>ISC</cbc:Name>" + 
            "<cbc:TaxTypeCode>EXC</cbc:TaxTypeCode>" + 
            "</cac:TaxScheme>" + 
            "</cac:TaxCategory>" + 
            "</cac:TaxSubtotal>" + 
            "</cac:TaxTotal>" +  
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:Item>" + 
            "<cbc:Description><![CDATA[CAPTOPRIL 1000mg X 30]]></cbc:Description>" + 
            "</cac:Item>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:Item>" + 
            "<cbc:SellersItemIdentification>" + 
            "<ID>Cap-258963</ID>" + 
            "</cbc:SellersItemIdentification>" + 
            "<cac:CommodityClassification>" + 
            "<ItemClassificationCode" + 
            "listID=\"UNSPSC\"" + 
            "listAgencyName=\"GS1 US\"" + 
            "listName=\"Item Classification\">51121703</ItemClassificationCode>" + 
            "</cac:CommodityClassification>" + 
            "</cac:Item>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:Item>" + 
            "<cac:AdditionalItemProperty >" + 
            "<Name>Gastos Art. 37 Renta: Número de Placa</ Name>" + 
            "<NameCode" + 
            "listName=\"SUNAT :Identificador de la propiedad del ítem\"" + 
            "listAgencyName=\"PE:SUNAT\">7000</NameCode>" + 
            "<Value>B6F-045</Value>" + 
            "</cac:AdditionalItemProperty>" + 
            "</cac:Item>" + 
            "</cac:InvoiceLine>" + 
            "<cac:InvoiceLine>" + 
            "<cac:Price>" + 
            "<cbc:PriceAmount CurrencyID=\"PEN\">785.20</cbc:PriceAmount>" + 
            "</cac:Price>" + 
            "</cac:InvoiceLine>" + 
            "</Invoice>"; */
            
            System.out.println(facturaXml);

            byte[] xmlBytes = facturaXml.getBytes("UTF-8");  
            byte[] zipBytes = crearArchivoZip("10437413903-01-F002-10.xml", xmlBytes); 
            System.out.println("Bytes del ZIP generados con éxito.");

            // Configurar las credenciales para el servicio SOAP
            String username = "10437413903MODDATOS";
            String password = "moddatos";

            // URL del servicio SOAP
            String endpoint = "https://e-beta.sunat.gob.pe:443/ol-ti-itcpfegem-beta/billService";
            URL url = new URL(endpoint);

            // Crear conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

            // Agregar cabecera de seguridad WSSE (UsernameToken)
            String wsseHeader = generateWSSEHeader(username, password);
            conn.setRequestProperty("Authorization", wsseHeader);

            // Crear el mensaje SOAP con el archivo ZIP adjunto
            String soapMessage = generateSOAPMessage(zipBytes);

            // Enviar el mensaje SOAP
            OutputStream os = conn.getOutputStream();
            os.write(soapMessage.getBytes("UTF-8"));
            os.flush();

            // Verificar la respuesta del servidor
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                // Convertir la respuesta a bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
                byte[] zipBytesReturn = baos.toByteArray();
                
                // Cerrar streams
                baos.close();
                is.close();

                ByteArrayInputStream bis = new ByteArrayInputStream(zipBytesReturn);
                ZipInputStream zis = new ZipInputStream(bis);

                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    
                    // Si el archivo es el XML que buscas, leer su contenido
                    if (fileName.endsWith(".xml")) {
                        ByteArrayOutputStream baosXml = new ByteArrayOutputStream();
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            baosXml.write(buffer, 0, length);
                        }
                        String xmlContent = baosXml.toString("UTF-8");
                        
                        // Aquí puedes procesar xmlContent según tus necesidades
                        System.out.println("Contenido del XML:");
                        System.out.println(xmlContent);
                        
                        // Cerrar el ByteArrayOutputStream del XML
                        baosXml.close();
                    }
                    
                    // Avanzar al siguiente archivo dentro del ZIP
                    zis.closeEntry();
                    zipEntry = zis.getNextEntry();
                }
                
                zis.close();

            } else {

                // Respuesta de error
                InputStream errorStream = conn.getErrorStream();
                if (errorStream != null) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
                    StringBuilder errorResponse = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        errorResponse.append(line);
                    }
                    reader.close();
                    
                    // Aquí puedes manejar el cuerpo de la respuesta de error
                    System.out.println("Código de error HTTP: " + responseCode);
                    System.out.println("Cuerpo de la respuesta de error:");
                    System.out.println(errorResponse.toString());
                } else {
                    // Si no hay errorStream disponible, manejarlo según tu caso
                    System.out.println("Código de error HTTP: " + responseCode);
                    System.out.println("No se pudo obtener el cuerpo de la respuesta de error.");
                }
                
            }

            System.out.println("Response Code: " + responseCode);
            conn.disconnect();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'envioFacturaElectronica'");
        }
    }

    private static byte[] crearArchivoZip(String nombreArchivo, byte[] contenido) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zipOut = new ZipOutputStream(baos);
        ZipEntry zipEntry = new ZipEntry(nombreArchivo);
        zipOut.putNextEntry(zipEntry);
        zipOut.write(contenido);
        zipOut.closeEntry();
        zipOut.close();
        return baos.toByteArray();
    }

     // Método para generar la cabecera WSSE (UsernameToken)
     private static String generateWSSEHeader(String username, String password) {
        String wsseHeader = "UsernameToken Username=\"" + username + "\", PasswordDigest=\"" + password + "\", Nonce=\"" + generateNonce() + "\"";
        return wsseHeader;
    }

    // Método para generar un nonce aleatorio (simulado)
    private static String generateNonce() {
        return "nonce12345";
    }

    // Método para generar el mensaje SOAP con el archivo ZIP adjunto
    private static String generateSOAPMessage(byte[] zipBytes) {
        StringBuilder sb = new StringBuilder();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" ");
        sb.append("xmlns:ser=\"http://service.sunat.gob.pe\" xmlns:wsse=\"http://docs.oasisopen.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">");
        sb.append("<soapenv:Header>");
        sb.append("<wsse:Security>");
        sb.append("<wsse:UsernameToken>");
        sb.append("<wsse:Username>10437413903MODDATOS</wsse:Username>");
        sb.append("<wsse:Password>moddatos</wsse:Password>");
        sb.append("</wsse:UsernameToken>");
        sb.append("</wsse:Security>");
        sb.append("</soapenv:Header>");
        sb.append("<soapenv:Body>");
        sb.append("<ser:sendBill>");
        sb.append("<fileName>10437413903-01-F002-10.zip</fileName>");
        sb.append("<contentFile>cid:10437413903-01-F002-10.zip</contentFile>");
        sb.append("</ser:sendBill>");
        sb.append("</soapenv:Body>");
        sb.append("</soapenv:Envelope>");

        String soapMessage = sb.toString();

        // Agregar el archivo ZIP como parte del mensaje SOAP (en realidad, deberías adjuntar el ZIP como archivo adjunto real usando MIME, aquí se simula con Content ID 'cid')
        // Enviar el archivo ZIP como contenido adjunto

        String soapMessageWithAttachment = soapMessage.replace("cid:10437413903-01-F002-10.zip", Base64.getEncoder().encodeToString(zipBytes));
        return soapMessageWithAttachment;
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
    public List<ListadoMenu> obtenerListadoMenuInicial(int idNegocio, Date fechaConsulta, Date fechaConsultaHasta) {
        try {
            return usuarioRepository.obtenerListadoMenuInicial(idNegocio, fechaConsulta, fechaConsultaHasta);
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public List<RespuestaStd> registraMarcaPersonal(int idNegocio, String numeroCelular, 
            int tipoMarca) {
        try {
            return usuarioRepository.registraMarcaPersonal(idNegocio, numeroCelular, tipoMarca, new Date());
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'registraMarcaPersonal'");
        }
    }

    @Override
    public List<Promociones> listarPromociones(int idNegocio) {
        try {
            return usuarioRepository.listarPromociones(idNegocio);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'listarPromociones'");
        }
    }

    @Override
    public List<RespuestaStd> generarPromocion(PromocionDto promocionDto) {
        try {
            Gson gson = new Gson();
            String jsonDetalleProducto = gson.toJson(promocionDto.getDetalleProducto());
            
            if (promocionDto.getFechaInicioPromocion().contains("p.m.")) {
                String[] aFecha = promocionDto.getFechaInicioPromocion().split(" ");
                String[] aHora = aFecha[1].split(":");
                
                aHora[0] = aHora[0].replace("01", "13");
                aHora[0] = aHora[0].replace("02", "14");
                aHora[0] = aHora[0].replace("03", "15");
                aHora[0] = aHora[0].replace("04", "16");
                aHora[0] = aHora[0].replace("05", "17");
                aHora[0] = aHora[0].replace("06", "18");
                aHora[0] = aHora[0].replace("07", "19");
                aHora[0] = aHora[0].replace("08", "20");
                aHora[0] = aHora[0].replace("09", "21");
                aHora[0] = aHora[0].replace("10", "22");
                aHora[0] = aHora[0].replace("11", "23");
                
                promocionDto.setFechaInicioPromocion(aFecha[0] + " " + aHora[0] + ":" + aHora[1] + ":" + aHora[2]);
            } else {
                promocionDto.setFechaInicioPromocion(promocionDto.getFechaInicioPromocion().replace(" a.m.", ""));
            }

            if (promocionDto.getFechaFinalPromocion().contains("p.m.")) {
                String[] aFecha = promocionDto.getFechaFinalPromocion().split(" ");
                String[] aHora = aFecha[1].split(":");
                
                aHora[0] = aHora[0].replace("01", "13");
                aHora[0] = aHora[0].replace("02", "14");
                aHora[0] = aHora[0].replace("03", "15");
                aHora[0] = aHora[0].replace("04", "16");
                aHora[0] = aHora[0].replace("05", "17");
                aHora[0] = aHora[0].replace("06", "18");
                aHora[0] = aHora[0].replace("07", "19");
                aHora[0] = aHora[0].replace("08", "20");
                aHora[0] = aHora[0].replace("09", "21");
                aHora[0] = aHora[0].replace("10", "22");
                aHora[0] = aHora[0].replace("11", "23");

                promocionDto.setFechaFinalPromocion(aFecha[0] + " " + aHora[0] + ":" + aHora[1] + ":" + aHora[2]);
            } else {
                promocionDto.setFechaFinalPromocion(promocionDto.getFechaFinalPromocion().replace(" a.m.", ""));
            }

            return usuarioRepository.generarPromocion(promocionDto.getIdNegocio(), promocionDto.getIdPromocion(),
            promocionDto.getNombrePromocion(), jsonDetalleProducto, 
            promocionDto.getFechaInicioPromocion(),
            promocionDto.getFechaFinalPromocion(), promocionDto.getPrecio(),
            promocionDto.getCantidadProductos());        
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'generarpromocion'");
        }
    }

    @Override
    public List<RespuestaStd> anularDocVenta(int idNegocio, int idPedido) {
        try {
            return usuarioRepository.anularDocVenta(idNegocio, idPedido, new Date());            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'anularDocVenta'");
        }
    }

    @Override
    public List<RespuestaStd> operacionHoteles(int idNegocio, int idPedido, int idProducto, int agregarDiaNoches,
            int tipoOperacion) {
        try {
            return usuarioRepository.operacionHoteles(idNegocio, idPedido, idProducto, agregarDiaNoches, new Date(), tipoOperacion);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'operacionHoteles'");
        }
    }

    @Override
    public List<DisponibildadCuarto> obtenerDisponibilidadCuarto(DisponibilidadCuartosDto disponibilidadCuartosDto) {
        try {
            return usuarioRepository.obtenerDisponibilidadCuarto(disponibilidadCuartosDto.getIdNegocio(), disponibilidadCuartosDto.getAnioConsultaDesde(), 
            disponibilidadCuartosDto.getMesConsultaDesde(), disponibilidadCuartosDto.getDiaConsultaDesde(), disponibilidadCuartosDto.getAnioConsultaHasta(),
            disponibilidadCuartosDto.getMesConsultaHasta(), disponibilidadCuartosDto.getDiaConsultaHasta());
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnsupportedOperationException("Unimplemented method 'obtenerDisponibilidadCuarto'");
        }
    }

    @Override
    public List<ReporteOcupacionResponse> reporteOcupacion(int idNegocio, Date fechaCorte) {
        try {
            return usuarioRepository.reporteOcupacion(idNegocio, fechaCorte);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'reporteOcupacion'");
        }
    }

    @Override
    public List<ReporteIngresosGeneradosResponse> reporteIngresosGenerados(int idNegocio, Date fechaConsulta) {
        try {
            return usuarioRepository.reporteIngresosGenerados(idNegocio, fechaConsulta);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'reporteIngresosGenerados'");
        }
    }

    @Override
    public List<ReporteReservasResponse> reporteReservas(int idNegocio, Date fechaConsulta, int tipoReporte) {
        try {
            return usuarioRepository.reporteReservas(idNegocio, fechaConsulta, tipoReporte);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'reporteReservas'");
        }
    }

    
 
}
