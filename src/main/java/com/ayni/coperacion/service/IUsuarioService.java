package com.ayni.coperacion.service;
 
import java.math.BigDecimal;
import java.util.Date;
import java.util.List; 
import com.ayni.coperacion.dto.ActualizarEstadoProductoCocinaDto;
import com.ayni.coperacion.dto.ActualizarNegocioPedidoDto;
import com.ayni.coperacion.dto.CompraNegocio;
import com.ayni.coperacion.dto.CompraPagoDto;
import com.ayni.coperacion.dto.InsumoDto;
import com.ayni.coperacion.dto.MenuPedidoUnitarioDto;
import com.ayni.coperacion.dto.NegocioDto;
import com.ayni.coperacion.dto.PedidoPagadoDto;
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
import com.ayni.coperacion.response.PedidoInter;
import com.ayni.coperacion.response.PedidoPagoResponse;
import com.ayni.coperacion.response.ReporteCierre;
import com.ayni.coperacion.response.ReporteCierreDetalle;
import com.ayni.coperacion.response.ReporteCierreDetalleEfectivo;
import com.ayni.coperacion.response.ReportePedido;
import com.ayni.coperacion.response.RespuestaStd;
import com.ayni.coperacion.response.UsuarioReponse;
import com.ayni.coperacion.response.VentasPorProducto;

public interface IUsuarioService {
    
    List<RespuestaStd> crearNegocio(NegocioDto negocioDto);

    UsuarioReponse registro(UsuarioDto usuarioDto);

    List<RespuestaStd> validarUsuario(String numeroTelefono,
    String nombreUsuario, String codigoVerificacion);

    UsuarioReponse validarCodigoVerificador(UsuarioDto usuarioDto);

    List<ListadoMenu> obtenerListadoMenu(int pdIdNegocio, int pIdPedido);

    List<Negocio> listadoNegocio();

    List<CargoNegocio> listadoCargoNegocio(int pIdNegocio, String pNumeroCelular, String pNombreUsuario);

    List<PedidoGenerado> obtenerPedido(int pdIdNegocio, int pIdPedido, String mesa);

    int crearMenuPedido(int idNegocio, int idPedido, String detalleProducto, int mesa, 
    String numeroCelular, String nombreUsuario,String docCliente,String nombreCliente, String direccionCliente,
    int tipoDoc, String numeroDocumento, BigDecimal comisionDelivery);

    Pedido borrarMenuPedido(int idnegocio, int idPedido, String numeroCelular, String nombreUsuario, int borrar);
    
    List<RespuestaStd> solicitarConfirmacion(int idnegocio, int idPedido, String numeroCelular, String nombreUsuario);
    
    List<RespuestaStd> confirmarPedido(int idNegocio, int idPedido, String numeroCelular, String nombreUsuario);

    List<RespuestaStd> pedidoPagado(PedidoPagadoDto pedidoPagadoDto);

    List<RespuestaStd> modificarPedidoPago(PedidoPagadoDto pedidoPagadoDto);

    List<RespuestaStd> pedidoAtendido(int idNegocio, int idPedido, String numeroCelular, String nombreUsuario, int incluirpl);
    
    List<RespuestaStd> pedidoAtendidoIndividual(int idNegocio, int idPedido, String numeroCelular, String nombreUsuario, int incluirpl, int idProducto);

    List<RespuestaStd> pedidoAtendidoRevetirIndividual(int idNegocio, int idPedido, String numeroCelular, String nombreUsuario, int incluirpl, int idProducto);

    List<ReportePedido> listadoPedidos(int idNegocio, int mesa, String numerocelular, String nombreusuario, Date fechaConsulta);

    List<ReporteCierre> reporteCierre(int idNegocio, int anioSeleccionado, int mesSeleccionado, int diaSeleccionado, String numerocelular, String nombreusuario);

    List<ReporteCierre> reporteCierreTienda(int idNegocio, int anioSeleccionado, int mesSeleccionado, int diaSeleccionado, int anioSeleccionadoHasta, 
    int mesSeleccionadoHasta, int diaSeleccionadoHasta, String numerocelular, String nombreusuario);

    List<ReporteCierreDetalle> reporteCierraTiendaDetalle(int idNegocio, int anioSeleccionado, int mesSeleccionado, int diaSeleccionado, 
                                                          int anioSeleccionadoHasta, int mesSeleccionadoHasta, int diaSeleccionadoHasta, 
                                                          String numeroCelular, String nombreUsuario, int idProducto);


    List<ListadoCocina> listadoCocina(int idNegocio, int negociococina, int anio, int mes, int dia);

    List<ListadoCajero> listadoCajero(int idNegocio, int anio, int mes, int dia);

    List<ListadoProducto> listadoProductoNegocio(int idNegocio);
    
    List<RespuestaStd> actualizarEstadoProductoCocina(ActualizarEstadoProductoCocinaDto actualizarEstadoProductoCocinaDto);
 
    List<RespuestaStd> actualizarNegocioPedido(ActualizarNegocioPedidoDto actualizarNegocioPedidoDto);
 
    List<RespuestaStd> agregarquitaAdminUsuario(int idNegocio, int idUsuario, String nombreusuario, 
    int isAdmin, int admitir, String detalleCargoPermitidos);

    List<RespuestaStd> actualizarIdNegocioUsuario(String idUsuario, int idNegocio, int colaborador);

    List<ListadoUsuario> listadoUsuarioNegocio(int idNegocio, String numeroCelular, String nombreUsuario);
    
    List<RespuestaStd> configuracionNegocio(int idNegocio, String nombreNegocio, String descripcion,  
                                            String logo, int estadoNegocio, int rubroNegocio, 
                                            int usarLectorBarraBusquedaManual, 
                                            int envioPlatoDirectoACocina, int generarComprobanteVenta, 
                                            int usarCorrelativoAutomatico, int pedirNombreClientePedidosParaLlevar,
                                            String correoElectronico,
                                            String correlativos,
                                            String grupoProductos,
                                            String cocinas);

    List<ConfiguracionNegocio> obtenerConfiguracionNegocio(int idNegocio);

    List<ListadoProductoTienda> obtenerListadoProductoTienda(int idNegocio, String codigoBarra);

    List<RespuestaStd> compraNegocio(CompraNegocio compraNegocio);

    List<Inventario> listarInventario(int idNegocio, int anioCorte, int mesCorte, int diaCorte, int anioHasta, int mesHasta, int diaHasta);

    List<AgendaServicios> agendaServicios(int idnegocio, int tipofiltro, int pais, int anio, int mes);

    List<RespuestaStd> otrosMovimiento(int tipoMovimiento,int idNegocio, int idProducto,
                                       BigDecimal cantidad, Date fechaMovimiento);
    
    List<RespuestaStd> modificarCompra(int idCompra, CompraNegocio compraNegocio);
    
    List<CompraNegocioResponse> obtenerDatosCompra(int idNegocio, int idCompra);

    List<DocumentosPendientes> documentosPendientesPago(int idNegocio);

    List<RespuestaStd> compraPago(CompraPagoDto compraPagoDto);

    List<RespuestaStd> grabarInsumo(InsumoDto iInsumoDto);

    List<ListadoInsumoProducto> listarInsumoPorProducto(int idNegocio,int idProducto);

    List<ListadoInsumoProducto> obtenerInsumosProductoServicio(int idNegocio, int idPedido, int idProducto);

    List<RespuestaStd> obtenerCobrosAyni(int idNegocio);

    List<RespuestaStd> actualizarLecturaCocina(int idNegocio, int idPedido, int idProducto); 

    List<ReporteCierreDetalleEfectivo> reporteCierraTiendaDetalleEfectivo(int idNegocio, int idTipoPago, int anio, int mes, int dia, String nombreUsuario, String numeroCelular);
       
    List<PedidoPagoResponse> obtenerPedidoPago(int idNegocio, int idPedido);

    List<VentasPorProducto> buscarVentasPorProducto(int idNegocio, int idPedido, int tipoFiltroFecha);

    List<RespuestaStd> obtenerCorreoNegocio(int idNegocio);

    List<RespuestaStd> insertarGrupoProducto(int idNegocio, int idGrupoProducto, 
    String descripcionGrupo, int ordenLista);

    List<RespuestaStd> insertarCocinaNegocio(int idNegocio, int idCocina,String nombreCocina);

    List<DocumentoVentaResponse> obtenerDocumentoVenta(int idNegocio, int idPedido);
    
    List<RespuestaStd> generarDocumentoVentaADocPagado(int idNegocio, int idPedido, int tipoDocumento);

    List<PedidoInter> obtenerDocumentosPendientesImpresion(int idNegocio);
    
    List<ListadoCocina> cocinaPedienteGenerado(int idNegocio, int idPedido, int tipolista);

    List<RespuestaStd> modificarMenuPedidoUnitario(MenuPedidoUnitarioDto menuPedidoUnitario);
}
