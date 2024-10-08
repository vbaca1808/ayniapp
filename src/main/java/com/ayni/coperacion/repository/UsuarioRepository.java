package com.ayni.coperacion.repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List; 
import javax.transaction.Transactional; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ayni.coperacion.dto.PedidoEnvioSunat;
import com.ayni.coperacion.entidades.Usuario;
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
import com.ayni.coperacion.response.ListadoEmpresasBolsa;
import com.ayni.coperacion.response.ListadoInsumoProducto;
import com.ayni.coperacion.response.ListadoLimpiezaResponse;
import com.ayni.coperacion.response.ListadoMenu;
import com.ayni.coperacion.response.ListadoProducto;
import com.ayni.coperacion.response.ListadoProductoTienda;
import com.ayni.coperacion.response.ListadoUsuario;
import com.ayni.coperacion.response.Negocio;
import com.ayni.coperacion.response.OtrosMovimientosCajeroResponse; 
import com.ayni.coperacion.response.PedidoGenerado;
import com.ayni.coperacion.response.PedidoInter;
import com.ayni.coperacion.response.PedidoPagoResponse;
import com.ayni.coperacion.response.PedidoResponse;
import com.ayni.coperacion.response.Promociones;
import com.ayni.coperacion.response.ReporteChecksResponse;
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
import com.ayni.coperacion.response.VentasPorProducto; 

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    
    @Query( value = "call sp_crear_negocio(:idNegocio, :nombreNegocio, :descripcion, :logo, " +
    ":estadoNegocio, :rubroNegocio, :usarLectorBarraBusquedaManual, :envioPlatoDirectoACocina, " + 
    ":generarComprobanteVenta, :usarCorrelativoAutomatico, :pedirNombreClientePedidosParaLlevar, "+ 
    ":correoElectronico, :numeroCelularUsuario, :nombreUsuario)", nativeQuery = true)
    List<RespuestaStd> crearNegocio(@Param("idNegocio") int idNegocio, 
                                    @Param("nombreNegocio") String nombreNegocio, 
                                    @Param("descripcion") String descripcion, 
                                    @Param("logo") String logo, 
                                    @Param("estadoNegocio") int estadoNegocio, 
                                    @Param("rubroNegocio") int rubroNegocio, 
                                    @Param("usarLectorBarraBusquedaManual") int usarLectorBarraBusquedaManual, 
                                    @Param("envioPlatoDirectoACocina") int envioPlatoDirectoACocina, 
                                    @Param("generarComprobanteVenta") int generarComprobanteVenta, 
                                    @Param("usarCorrelativoAutomatico") int usarCorrelativoAutomatico, 
                                    @Param("pedirNombreClientePedidosParaLlevar") int pedirNombreClientePedidosParaLlevar, 
                                    @Param("correoElectronico") String correoElectronico, 
                                    @Param("numeroCelularUsuario") String numeroCelularUsuario, 
                                    @Param("nombreUsuario") String nombreUsuario);

    @Modifying
    @Query( value = "call sp_grabar_usuario(:numeroCelular,:nombreUsuario,:cliente," 
                  + ":colaborador,:actualizar, :codigoVerificacion)", nativeQuery = true)
    List<RespuestaStd> registrarUsuario(@Param("numeroCelular") String numeroCelular,
    @Param("nombreUsuario") String nombreUsuario, @Param("cliente") int cliente,
    @Param("colaborador") int colaborador,
    @Param("actualizar") int actualizar, @Param("codigoVerificacion") String codigoVerificacion);

    @Query( value = "call sp_validar_usuario(:numeroTelefono,:nombreUsuario,:codigoVerificacion, :fechaProceso)", nativeQuery = true)
    List<RespuestaStd> validarUsuario(@Param("numeroTelefono") String numeroTelefono,
                                      @Param("nombreUsuario") String nombreUsuario, 
                                      @Param("codigoVerificacion") String codigoVerificacion,
                                      @Param("fechaProceso") Date fechaProceso);

    @Query( value = "call sp_listado_negocio()", nativeQuery = true)
    List<Negocio> listadoNegocio();

    @Query( value = "call sp_listar_cargos(:idNegocio, :numeroCelular, :nombreUsuario)", nativeQuery = true)
    List<CargoNegocio> listadoCargoNegocio(@Param("idNegocio") int idNegocio,
                                           @Param("numeroCelular") String numeroCelular,
                                           @Param("nombreUsuario") String nombreUsuario);

    @Query( value = "call sp_listado_menu(:idNegocio,:idPedido)", nativeQuery = true)
    List<ListadoMenu> listadoMenu(@Param("idNegocio") int idNegocio, 
                                  @Param("idPedido") int idPedido);

    @Query( value = "call sp_validar_mesa_ocupada(:idNegocio, :mesa, :numeroCelular, :fechaProceso)", nativeQuery = true)
    List<PedidoResponse> validarMesaOcupada(@Param("idNegocio") int idNegocio, 
                                            @Param("mesa") int mesa, 
                                            @Param("numeroCelular") String numeroCelular, 
                                            @Param("fechaProceso") Date fecha);

    @Modifying
    @Query( value = "call sp_crear_menu_pedido(:idNegocio, :idPedido, :fechaPedido, :detalleProducto, " + 
                    ":mesa, :numeroCelular, :nombreUsuario, :docCliente, :nombreCliente, :direccionCliente, " +
                    ":tipoDoc, :numeroDocumento, :comisionDelivery, :esReserva)", nativeQuery = true)
    List<RespuestaStd> crearMenuPedido(@Param("idNegocio") int idNegocio, 
                                       @Param("idPedido") int idPedido, 
                                       @Param("fechaPedido") Date fechaPedido,  
                                       @Param("detalleProducto") String detalleProducto, 
                                       @Param("mesa") int mesa,
                                       @Param("numeroCelular") String numeroCelular, 
                                       @Param("nombreUsuario") String nombreUsuario,
                                       @Param("docCliente") String docCliente,
                                       @Param("nombreCliente") String nombreCliente,
                                       @Param("direccionCliente") String direccionCliente, 
                                       @Param("tipoDoc") int tipoDoc,
                                       @Param("numeroDocumento") String numeroDocumento,
                                       @Param("comisionDelivery") BigDecimal comisionDelivery,
                                       @Param("esReserva") int esReserva);
                                
    @Modifying
    @Query( value = "call sp_borrar_pedido(:idNegocio, :idPedido, :numeroCelular, :nombreUsuario, " + 
                    ":fechaProceso, :borrar)", nativeQuery = true)
    List<RespuestaStd> borrarPedido(@Param("idNegocio") int idNegocio, 
                                    @Param("idPedido") int idPedido,
                                    @Param("numeroCelular") String numeroCelular, 
                                    @Param("nombreUsuario") String nombreUsuario,
                                    @Param("fechaProceso") Date fechaProceso,
                                    @Param("borrar") int borrar);

    @Query( value = "call sp_solicitar_confirmacion(:idNegocio, :idPedido, :numeroCelular, :nombreUsuario)", nativeQuery = true)
    List<RespuestaStd> solicitarConfirmacion(@Param("idNegocio") int idNegocio, 
                                             @Param("idPedido") int idPedido,
                                             @Param("numeroCelular") String numeroCelular,
                                             @Param("nombreUsuario") String nombreUsuario);

    @Query( value = "call sp_confirmar_pedido(:idNegocio, :idPedido, :numeroCelular, :nombreUsuario, :fechaProceso)", nativeQuery = true)
    List<RespuestaStd> confirmarPedido(@Param("idNegocio") int idNegocio, 
                                       @Param("idPedido") int idPedido, 
                                       @Param("numeroCelular") String numeroCelular,
                                       @Param("nombreUsuario") String nombreUsuario,
                                       @Param("fechaProceso") Date fechaProceso);

    @Query( value = "call sp_pedido_atendido(:idNegocio, :idPedido, :numeroCelular, " +
                    ":nombreUsuario, :fechaProceso, :incluirpl)", nativeQuery = true)
    List<RespuestaStd> pedidoAtendido(@Param("idNegocio") int idNegocio, 
                                      @Param("idPedido") int idPedido, 
                                      @Param("numeroCelular") String numeroCelular,
                                      @Param("nombreUsuario") String nombreUsuario,
                                      @Param("fechaProceso") Date fechaProceso,
                                      @Param("incluirpl") int incluirpl);

    @Query( value = "call sp_pedido_atendido_individual(:idNegocio, :idPedido, :numeroCelular, " +
                    ":nombreUsuario, :fechaProceso, :incluirpl, :idProducto, :idCargo)", nativeQuery = true)
    List<RespuestaStd> pedidoAtendidoIndividual(
                                    @Param("idNegocio") int idNegocio, 
                                    @Param("idPedido") int idPedido, 
                                    @Param("numeroCelular") String numeroCelular,
                                    @Param("nombreUsuario") String nombreUsuario,
                                    @Param("fechaProceso") Date fechaProceso,
                                    @Param("incluirpl") int incluirpl,
                                    @Param("idProducto") int idProducto,
                                    @Param("idCargo") int idCargo); 

    @Query( value = "call sp_revertir_atencion_individual(:idNegocio, :idPedido, :numeroCelular, " +
    ":nombreUsuario, :fechaProceso, :incluirpl, :idProducto)", nativeQuery = true)
    List<RespuestaStd> revetirAtendidoIndividual(
                                    @Param("idNegocio") int idNegocio, 
                                    @Param("idPedido") int idPedido, 
                                    @Param("numeroCelular") String numeroCelular,
                                    @Param("nombreUsuario") String nombreUsuario,
                                    @Param("fechaProceso") Date fechaProceso,
                                    @Param("incluirpl") int incluirpl,
                                    @Param("idProducto") int idProducto);
                    
    @Query( value = "call sp_pedido_pagado(:idNegocio, :idPedido, :numeroCelular, :nombreUsuario, " +
    ":fechaProceso, :efectivo, :yape, :plin, :tarjeta, :otros, :credito, :propina, :descuento, " + 
    ":soyCocina, :tipoDocumento, :numeroDocumento)", nativeQuery = true)
    List<RespuestaStd> pedidoPagado(@Param("idNegocio") int idNegocio, 
                                    @Param("idPedido") int idPedido, 
                                    @Param("numeroCelular") String numeroCelular,
                                    @Param("nombreUsuario") String nombreUsuario,
                                    @Param("fechaProceso") Date fechaProceso,
                                    @Param("efectivo") BigDecimal efectivo,
                                    @Param("yape") BigDecimal yape,
                                    @Param("plin") BigDecimal plin,
                                    @Param("tarjeta") BigDecimal tarjeta,
                                    @Param("otros") BigDecimal otros,
                                    @Param("credito") BigDecimal credito,
                                    @Param("propina") BigDecimal propina,
                                    @Param("descuento") BigDecimal descuento,
                                    @Param("soyCocina") int soyCocina,
                                    @Param("tipoDocumento") int tipoDocumento,
                                    @Param("numeroDocumento") String numeroDocumento);

                                    
    @Query( value = "call sp_modificar_pago_pedido(:idNegocio, :idPedido, :numeroCelular, :nombreUsuario, " +
    ":fechaProceso, :efectivo, :yape, :plin, :tarjeta, :otros, :credito, :propina, :descuento, " + 
    ":tipoDocumento, :numeroDocumento)", nativeQuery = true)
    List<RespuestaStd> modificarPagoPedido(@Param("idNegocio") int idNegocio, 
                                           @Param("idPedido") int idPedido, 
                                           @Param("numeroCelular") String numeroCelular,
                                           @Param("nombreUsuario") String nombreUsuario,
                                           @Param("fechaProceso") Date fechaProceso,
                                           @Param("efectivo") BigDecimal efectivo,
                                           @Param("yape") BigDecimal yape,
                                           @Param("plin") BigDecimal plin,
                                           @Param("tarjeta") BigDecimal tarjeta,
                                           @Param("otros") BigDecimal otros,
                                           @Param("credito") BigDecimal credito,
                                           @Param("propina") BigDecimal propina,
                                           @Param("descuento") BigDecimal descuento,
                                           @Param("tipoDocumento") int tipoDocumento,
                                           @Param("numeroDocumento") String numeroDocumento);

    @Query( value = "call sp_obtener_pedido(:idNegocio, :idPedido, :mesa)", nativeQuery = true)
    List<PedidoGenerado> obtenerPedido(@Param("idNegocio") int idNegocio, 
                                       @Param("idPedido") int idPedido, 
                                       @Param("mesa") String mesa);

    @Query( value = "call sp_listado_pedidos(:idNegocio, :mesa, :numeroCelular, :nombreUsuario, :fechaConsulta)", nativeQuery = true)
    List<ReportePedido> listadoPedidos(@Param("idNegocio") int idNegocio, 
                                       @Param("mesa") int mesa, 
                                       @Param("numeroCelular") String numeroCelular, 
                                       @Param("nombreUsuario") String nombreUsuario, 
                                       @Param("fechaConsulta") Date fechaConsulta);

    @Query( value = "call sp_reporte_cierre(:idNegocio, :anioSeleccionado, :mesSeleccionado, :diaSeleccionado, :numeroCelular, :nombreUsuario)", nativeQuery = true)
    List<ReporteCierre> reporteCierre(@Param("idNegocio") int idNegocio, 
                                      @Param("anioSeleccionado") int anioSeleccionado,
                                      @Param("mesSeleccionado") int mesSeleccionado,
                                      @Param("diaSeleccionado") int diaSeleccionado,
                                      @Param("numeroCelular") String numeroCelular,
                                      @Param("nombreUsuario") String nombreUsuario);
                                      
    @Query( value = "call sp_reporte_cierre_tienda(:idNegocio, :anioSeleccionado, :mesSeleccionado, :diaSeleccionado, " +
    ":anioSeleccionadoHasta, :mesSeleccionadoHasta, :diaSeleccionadoHasta, :numeroCelular, :nombreUsuario)", nativeQuery = true)
    List<ReporteCierre> reporteCierreTienda(@Param("idNegocio") int idNegocio, 
                                            @Param("anioSeleccionado") int anioSeleccionado,
                                            @Param("mesSeleccionado") int mesSeleccionado,
                                            @Param("diaSeleccionado") int diaSeleccionado,
                                            @Param("anioSeleccionadoHasta") int anioSeleccionadoHasta,
                                            @Param("mesSeleccionadoHasta") int mesSeleccionadoHasta,
                                            @Param("diaSeleccionadoHasta") int diaSeleccionadoHasta,
                                            @Param("numeroCelular") String numeroCelular,
                                            @Param("nombreUsuario") String nombreUsuario);
                                                
    @Query( value = "call sp_listado_cocina(:idNegocio, :idCocina, :anioSeleccionado, :mesSeleccionado, :diaSeleccionado)", nativeQuery = true)
    List<ListadoCocina> listadoCocina(@Param("idNegocio") int idNegocio, 
                                      @Param("idCocina") int idCocina, 
                                      @Param("anioSeleccionado") int anioSeleccionado,
                                      @Param("mesSeleccionado") int mesSeleccionado,
                                      @Param("diaSeleccionado") int diaSeleccionado);
    
    @Query( value = "call sp_actualizar_estado_producto_cocina(:idNegocio, :idPedido, :idProducto," + 
                    ":cantidadMesa, :cantidadLlevar, :estadoCocina, :fechaProceso)", nativeQuery = true)
    List<RespuestaStd> actualizarEstadoProductoCocina(@Param("idNegocio") int idNegocio, 
                                                      @Param("idPedido") int idPedido,
                                                      @Param("idProducto") int idProducto,
                                                      @Param("cantidadMesa") int cantidadMesa,
                                                      @Param("cantidadLlevar") int cantidadLlevar,
                                                      @Param("estadoCocina") int estadoCocina,
                                                      @Param("fechaProceso") Date fechaProceso);
                                        
                                    
    @Query( value = "call sp_listado_cajero(:idNegocio, :anioSeleccionado, :mesSeleccionado, :diaSeleccionado)", nativeQuery = true)
    List<ListadoCajero> listadoCajero(@Param("idNegocio") int idNegocio, 
                                      @Param("anioSeleccionado") int anioSeleccionado,
                                      @Param("mesSeleccionado") int mesSeleccionado,
                                      @Param("diaSeleccionado") int diaSeleccionado);

    @Query( value = "call sp_actualizar_negocio_pedido(:idNegocio, :idProducto, " +
	":nombreProducto, :palabraClave, :precio, :idGrupoProducto, :estado, :stockInicial, :codigoBarra, " + 
    ":recetaInsumo, :ordenLista, :irCocina, :idNegocioCocina)", nativeQuery = true)
    List<RespuestaStd> actualizarNegocioPedido(@Param("idNegocio") int idNegocio,  
                                               @Param("idProducto") int idProducto,
                                               @Param("nombreProducto") String nombreProducto,
                                               @Param("palabraClave") String palabraClave,
                                               @Param("precio") BigDecimal precio,
                                               @Param("idGrupoProducto") int idGrupoProducto,
                                               @Param("estado") int estado,
                                               @Param("stockInicial") BigDecimal stockInicial,
                                               @Param("codigoBarra") String codigoBarra,
                                               @Param("recetaInsumo") String recetaInsumo,
                                               @Param("ordenLista") int ordenLista,
                                               @Param("irCocina") int irCocina,
                                               @Param("idNegocioCocina") int idNegocioCocina);

    @Query( value = "call sp_agregarquita_admin_usuario(:idNegocio, :idUsuario, :nombreUsuario, " +
    ":isAdmin, :admitir, :detalleCargoPermitidos)", nativeQuery = true)
    List<RespuestaStd> agregarquitaAdminUsuario(@Param("idNegocio") int idNegocio,  
                                                @Param("idUsuario") int idUsuario,  
                                                @Param("nombreUsuario") String nombreUsuario, 
                                                @Param("isAdmin") int isAdmin,
                                                @Param("admitir") int admitir,
                                                @Param("detalleCargoPermitidos") String detalleCargoPermitidos);

    @Query( value = "call sp_actualizar_id_negocio_usuario(:numerotelefono, :idNegocio, :colaborador)", nativeQuery = true)
    List<RespuestaStd> actualizarIdNegocioUsuario(@Param("numerotelefono") String numerotelefono,  
                                                  @Param("idNegocio") int idNegocio,  
                                                  @Param("colaborador") int colaborador);
                                                
    @Query( value = "call sp_listado_productos_negocio(:idNegocio)", nativeQuery = true)
    List<ListadoProducto> listadoProductosNegocio(@Param("idNegocio") int idNegocio);
                                                
    @Query( value = "call sp_listado_usuario_negocio(:idNegocio, :numeroCelular, :nombreUsuario)", nativeQuery = true)
    List<ListadoUsuario> listadoUsuarioNegocio(@Param("idNegocio") int idNegocio,
                                               @Param("numeroCelular") String numeroCelular, 
                                               @Param("nombreUsuario") String nombreUsuario);
    
    @Query( value = "call sp_configuracion_negocio(:idNegocio, :nombreNegocio, :descripcion, " +  
     ":ruc, :razonSocial, :direccion, :igv, :logo, :estadoNegocio, :rubroNegocio, :usarLectorBarraBusquedaManual, :envioPlatoDirectoACocina, " + 
     ":generarComprobanteVenta, :usarCorrelativoAutomatico, :pedirNombreClientePedidosParaLlevar, " +
     ":correoElectronico, :correlativos, :grupoProductos, :cocinas, :direccionBluetoothCocina, :direccionBluetoothMesero," + 
     ":uuidCocina, :uuidMesero, :minutosEntregaCocina, :minutosEntregaNoCocina, :minutosMesaParalizada, :codigoProductoTaper)", nativeQuery = true)
    List<RespuestaStd> configuracionNegocio(@Param("idNegocio") int idNegocio,  
                                            @Param("nombreNegocio") String nombreNegocio,  
                                            @Param("descripcion") String descripcion,  
                                            @Param("ruc") String ruc,  
                                            @Param("razonSocial") String razonSocial,  
                                            @Param("direccion") String direccion,  
                                            @Param("igv") BigDecimal igv,  
                                            @Param("logo") String logo,  
                                            @Param("estadoNegocio") int estadoNegocio,  
                                            @Param("rubroNegocio") int rubroNegocio,  
                                            @Param("usarLectorBarraBusquedaManual") int usarLectorBarraBusquedaManual,  
                                            @Param("envioPlatoDirectoACocina") int envioPlatoDirectoACocina,
                                            @Param("generarComprobanteVenta") int generarComprobanteVenta,
                                            @Param("usarCorrelativoAutomatico") int usarCorrelativoAutomatico,
                                            @Param("pedirNombreClientePedidosParaLlevar") int pedirNombreClientePedidosParaLlevar,
                                            @Param("correoElectronico") String correoElectronico,
                                            @Param("correlativos") String correlativos,
                                            @Param("grupoProductos") String grupoProductos,
                                            @Param("cocinas") String cocinas,
                                            @Param("direccionBluetoothCocina") String direccionBluetoothCocina,
                                            @Param("direccionBluetoothMesero") String direccionBluetoothMesero,
                                            @Param("uuidCocina") String uuidCocina,
                                            @Param("uuidMesero") String uuidMesero,
                                            @Param("minutosEntregaCocina") int minutosEntregaCocina,
                                            @Param("minutosEntregaNoCocina") int minutosEntregaNoCocina,
                                            @Param("minutosMesaParalizada") int minutosMesaParalizada,
                                            @Param("codigoProductoTaper") int ccodigoProductoTaperocinas);

    @Query( value = "call sp_obtener_configuracion_negocio(:idNegocio)", nativeQuery = true)
    List<ConfiguracionNegocio> obtenerConfiguracionNegocio(@Param("idNegocio") int idNegocio);
    
    @Query( value = "call sp_obtener_listado_producto_tienda(:idNegocio, :codigoBarra)", nativeQuery = true)
    List<ListadoProductoTienda> obtenerListadoProductoTienda(@Param("idNegocio") int idNegocio,
                                                             @Param("codigoBarra") String codigoBarra);

    @Query( value = "call sp_obtener_listado_menu_inicial(:idNegocio, :fechaConsulta, :fechaConsultaHasta)", nativeQuery = true)
    List<ListadoMenu> obtenerListadoMenuInicial(@Param("idNegocio") int idNegocio, 
                                                @Param("fechaConsulta") Date fechaConsulta,
                                                @Param("fechaConsultaHasta") Date fechaConsultaHasta);

    @Query( value = "call sp_obtener_menu_pedido(:idNegocio, :idPedido)", nativeQuery = true)
    List<ListadoMenu> obtenerMenuPedido(@Param("idNegocio") int idNegocio, 
                                        @Param("idPedido") int idPedido);

    @Query( value = "call sp_compra_negocio(:idNegocio, :idCompra, :nombreProveedor, :rucProveedor, :fechaCompra, :totalCompra, " + 
                    ":tipoDocumento, :numeroDocumento, :detalleCompra, :efectivo, :yape, :plin, :tarjeta, :otros, " +
                    ":nombreUsuarioCajero,:numeroCelularCajero)", nativeQuery = true)
    List<RespuestaStd> compraNegocio(@Param("idNegocio") int idNegocio,
                                     @Param("idCompra") int idCompra,
                                     @Param("nombreProveedor") String nombreProveedor,
                                     @Param("rucProveedor") String rucProveedor,
                                     @Param("fechaCompra") Date fechaCompra,
                                     @Param("totalCompra") BigDecimal totalCompra,
                                     @Param("tipoDocumento") int tipoDocumento,
                                     @Param("numeroDocumento") String numeroDocumento,
                                     @Param("detalleCompra") String detalleCompra,
                                     @Param("efectivo") BigDecimal efectivo,
                                     @Param("yape") BigDecimal yape,
                                     @Param("plin") BigDecimal plin,
                                     @Param("tarjeta") BigDecimal tarjeta,
                                     @Param("otros") BigDecimal otros,
                                     @Param("nombreUsuarioCajero") String nombreUsuarioCajero,
                                     @Param("numeroCelularCajero") String numeroCelularCajero);
                                     
    @Query( value = "call sp_listar_inventario(:idNegocio, :anioCorte, :mesCorte, :diaCorte, :anioHasta, :mesHasta, :diaHasta)", nativeQuery = true)
    List<Inventario> listarInventario(@Param("idNegocio") int idNegocio,
                                      @Param("anioCorte") int anioCorte,
                                      @Param("mesCorte") int mesCorte,
                                      @Param("diaCorte") int diaCorte,
                                      @Param("anioHasta") int anioHasta,
                                      @Param("mesHasta") int mesHasta,
                                      @Param("diaHasta") int diaHasta);

    @Query( value = "call sp_agenda_servicios(:idNegocio, :tipoFiltro, :fechaReferencia)", nativeQuery = true)
    List<AgendaServicios> agendaServicios(@Param("idNegocio") int idNegocio,
                                          @Param("tipoFiltro") int tipoFiltro,
                                          @Param("fechaReferencia") ZonedDateTime fechaReferencia);
                                          
    @Query( value = "call sp_actualizar_corte_inventario(:fechaCorte)", nativeQuery = true)
    List<RespuestaStd> actualizarCorteInventario(@Param("fechaCorte") Date fechaCorte);
                                      
    @Query( value = "call sp_otros_movimiento(:tipoMovimiento,:idNegocio,:idProducto," +
    ":cantidad,:fechaMovimiento)", nativeQuery = true)
    List<RespuestaStd> otrosMovimiento(@Param("tipoMovimiento") int tipoMovimiento,
                                       @Param("idNegocio") int idNegocio,
                                       @Param("idProducto") int idProducto,
                                       @Param("cantidad") BigDecimal cantidad,
                                       @Param("fechaMovimiento") Date fechaMovimiento);

    @Query( value = "call sp_modificar_compra(:idNegocio,:idCompra,	:nombreProveedor, :rucProveedor, " + 
	":totalCompra, :tipoDocumento, :numeroDocumento,:detalleCompra)", nativeQuery = true)
    List<RespuestaStd> modificarCompra(@Param("idNegocio") int idNegocio,
                                       @Param("idCompra") int idCompra,
                                       @Param("nombreProveedor") String nombreProveedor,
                                       @Param("rucProveedor") String rucProveedor,
                                       @Param("totalCompra") BigDecimal totalCompra,
                                       @Param("tipoDocumento") int tipoDocumento,
                                       @Param("numeroDocumento") String numeroDocumento,
                                       @Param("detalleCompra") String detalleCompra);

    @Query( value = "call sp_obtener_datos_compra(:idNegocio,:idCompra)", nativeQuery = true)
    List<CompraNegocioResponse> obtenerDatosCompra(@Param("idNegocio") int idNegocio,
                                           @Param("idCompra") int idCompra);

    @Query( value = "call sp_documentos_pendientes_pago(:idNegocio)", nativeQuery = true)
    List<DocumentosPendientes> documentosPendientesPago(@Param("idNegocio") int idNegocio);
    
    @Query( value = "call sp_compra_pago(:idNegocio, :idCompra, :fechaPago, :efectivo, :yape, " + 
                    ":plin, :tarjeta, :otros, :nombreUsuarioCajero, :numeroCelularCajero)", nativeQuery = true)
    List<RespuestaStd> compraPago(@Param("idNegocio") int idNegocio,
                                          @Param("idCompra") int idCompra,
                                          @Param("fechaPago") Date fechaPago,
                                          @Param("efectivo") BigDecimal efectivo,
                                          @Param("yape") BigDecimal yape,
                                          @Param("plin") BigDecimal plin,
                                          @Param("tarjeta") BigDecimal tarjeta,
                                          @Param("otros") BigDecimal otros,
                                          @Param("nombreUsuarioCajero") String nombreUsuarioCajero,
                                          @Param("numeroCelularCajero") String numeroCelularCajero);

    @Query( value = "call sp_grabar_insumo(:idNegocio, :idInsumo, :descripcionInsumo, :cantidadInvertida, " + 
                    ":esCombustible, :requiereUsuario)", nativeQuery = true)
    List<RespuestaStd> grabarInsumo(@Param("idNegocio") int idNegocio,@Param("idInsumo") int idInsumo,
                                            @Param("descripcionInsumo") String descripcionInsumo, 
                                            @Param("cantidadInvertida") BigDecimal cantidadInvertida,
                                            @Param("esCombustible") int esCombustible, @Param("requiereUsuario") int requiereUsuario); 
    
    @Query( value = "call sp_listar_insumo_por_producto(:idNegocio, :idProducto)", nativeQuery = true)
    List<ListadoInsumoProducto> listarInsumoPorProducto(@Param("idNegocio") int idNegocio,@Param("idProducto") int idProducto);

    @Query( value = "call sp_obtener_insumos_producto_servicio(:idNegocio, :idPedido, :idProducto)", nativeQuery = true)
    List<ListadoInsumoProducto> obtenerInsumosProductoServicio(@Param("idNegocio") int idNegocio,
                                                               @Param("idPedido") int idPedido,
                                                               @Param("idProducto") int idProducto);

    @Query( value = "call sp_obtener_cobros_ayni(:idNegocio)", nativeQuery = true)
    List<RespuestaStd> obtenerCobrosAyni(@Param("idNegocio") int idNegocio); 

    @Query( value = "call sp_actualizar_lectura_cocina(:idNegocio, :idPedido, :idProducto)", nativeQuery = true)
    List<RespuestaStd> actualizarLecturaCocina(@Param("idNegocio") int idNegocio,
    @Param("idPedido") int idPedido, @Param("idProducto") int idProducto); 

    @Query( value = "call sp_reporte_cierra_tienda_detalle(:idNegocio, :anioSeleccionado, :mesSeleccionado, :diaSeleccionado, " +
    ":anioSeleccionadoHasta, :mesSeleccionadoHasta, :diaSeleccionadoHasta, :numeroCelular, :nombreUsuario, :idProducto)", nativeQuery = true)
    List<ReporteCierreDetalle> reporteCierraTiendaDetalle(@Param("idNegocio") int idNegocio, 
                                                          @Param("anioSeleccionado") int anioSeleccionado,
                                                          @Param("mesSeleccionado") int mesSeleccionado,
                                                          @Param("diaSeleccionado") int diaSeleccionado,
                                                          @Param("anioSeleccionadoHasta") int anioSeleccionadoHasta,
                                                          @Param("mesSeleccionadoHasta") int mesSeleccionadoHasta,
                                                          @Param("diaSeleccionadoHasta") int diaSeleccionadoHasta,
                                                          @Param("numeroCelular") String numeroCelular,
                                                          @Param("nombreUsuario") String nombreUsuario,
                                                          @Param("idProducto") int idProducto);

    @Query( value = "call sp_reporte_cierra_tienda_detalle_efectivo(:idNegocio, :idTipoPago, :anio, :mes, :dia, :nombreUsuario, :numeroCelular)", nativeQuery = true)
    List<ReporteCierreDetalleEfectivo> reporteCierraTiendaDetalleEfectivo(@Param("idNegocio") int idNegocio, 
                                                                          @Param("idTipoPago") int idTipoPago, 
                                                                          @Param("anio") int anio, 
                                                                          @Param("mes") int mes, 
                                                                          @Param("dia") int dia, 
                                                                          @Param("nombreUsuario") String nombreUsuario, 
                                                                          @Param("numeroCelular") String numeroCelular);
                
    @Query( value = "call sp_obtener_pedido_pago(:idNegocio, :idPedido)", nativeQuery = true)
    List<PedidoPagoResponse> obtenerPedidoPago(@Param("idNegocio") int idNegocio, 
                                                         @Param("idPedido") int idPedido);
                
    @Query( value = "call sp_buscar_ventas_por_producto(:idNegocio, :idProducto, :fechaActual, :tipoFiltroFecha)", nativeQuery = true)
    List<VentasPorProducto> buscarVentasPorProducto(@Param("idNegocio") int idNegocio, 
                                                    @Param("idProducto") int idProducto, 
                                                    @Param("fechaActual") Date fechaActual, 
                                                    @Param("tipoFiltroFecha") int tipoFiltroFecha);

    @Query( value = "call sp_obtener_correo_negocio(:idNegocio)", nativeQuery = true)
    List<RespuestaStd> obtenerCorreoNegocio(@Param("idNegocio") int idNegocio);
    
    @Modifying
    @Query( value = "call sp_insertar_grupo_producto(:idNegocio, :idGrupoProducto, :descripcionGrupo, :ordenLista)", nativeQuery = true)
    List<RespuestaStd> insertarGrupoProducto(@Param("idNegocio") int idNegocio,
                                             @Param("idGrupoProducto") int idGrupoProducto,
                                             @Param("descripcionGrupo") String descripcionGrupo,
                                             @Param("ordenLista") int ordenLista);
    @Modifying
    @Query( value = "call sp_insertar_cocina_negocio(:idNegocio, :idCocina, :nombreCocina)", nativeQuery = true)
    List<RespuestaStd> insertarCocinaNegocio(@Param("idNegocio") int idNegocio,
                                             @Param("idCocina") int idCocina,
                                             @Param("nombreCocina") String nombreCocina);

    @Query( value = "call sp_obtener_documento_venta(:idNegocio, :idPedido)", nativeQuery = true)
    List<DocumentoVentaResponse> obtenerDocumentoVenta(@Param("idNegocio") int idNegocio, 
                                                       @Param("idPedido") int idPedido);

    @Query( value = "call sp_generar_documento_venta_a_doc_pagado(:idNegocio, :idPedido, :tipoDocumento, :docCliente, :nombreCliente, :direccionCliente)", nativeQuery = true)
    List<RespuestaStd> generarDocumentoVentaADocPagado(@Param("idNegocio") int idNegocio, 
                                                       @Param("idPedido") int idPedido, 
                                                       @Param("tipoDocumento") int tipoDocumento, 
                                                       @Param("docCliente") String docCliente, 
                                                       @Param("nombreCliente") String nombreCliente, 
                                                       @Param("direccionCliente") String direccionCliente);

    @Query( value = "call sp_obtener_documentos_pendientes_impresion(:idNegocio)", nativeQuery = true)
    List<PedidoInter> obtenerDocumentosPendientesImpresion(@Param("idNegocio") int idNegocio);

    @Query( value = "call sp_cocina_pediente_generado(:idNegocio, :idPedido, :tipoLista)", nativeQuery = true)
    List<ListadoCocina> cocinaPedienteGenerado(@Param("idNegocio") int idNegocio,
                                               @Param("idPedido") int idPedido,
                                               @Param("tipoLista") int tipoLista);

    @Query( value = "call sp_modificar_menu_pedido_unitario(:idNegocio, :idPedido, :idProducto, :cantidad, :cantidadLlevar, :cantidadTaper, " + 
    ":descripcion, :total, :fechaProceso)", nativeQuery = true)
    List<RespuestaStd> modificarMenuPedidoUnitario(@Param("idNegocio") int idNegocio,
                                                   @Param("idPedido") int idPedido,
                                                   @Param("idProducto") int idProducto,
                                                   @Param("cantidad") int cantidad,
                                                   @Param("cantidadLlevar") int cantidadLlevar,
                                                   @Param("cantidadTaper") int cantidadTaper,
                                                   @Param("descripcion") String descripcion,
                                                   @Param("total") BigDecimal total,
                                                   @Param("fechaProceso") Date fechaProceso);

    @Query( value = "call sp_listar_otros_movimientos(:idNegocio, :fechaOperacion)", nativeQuery = true)
    List<OtrosMovimientosCajeroResponse> listarOtrosMovimientosCajero(@Param("idNegocio") int idNegocio,
                                              @Param("fechaOperacion") Date fechaOperacion);
                                        
    @Query( value = "call sp_grabar_otros_movimientos_cajero(:idNegocio, :idOperacion, :fechaOpe, :tipoOpe, :importe)", nativeQuery = true)
    List<RespuestaStd> grabarOtrosMovimientosCajero(@Param("idNegocio") int idNegocio,
                                                    @Param("idOperacion") int idOperacion,
                                                    @Param("fechaOpe") Date fechaOpe,
                                                    @Param("tipoOpe") int tipoOpe,
                                                    @Param("importe") BigDecimal importe);

    @Query( value = "call sp_transferir_mesa(:idNegocio, :idPedido, :numeroCelularDestino, :nombreUsuarioDestino)", nativeQuery = true)
    List<RespuestaStd> transferirMesa(@Param("idNegocio") int idNegocio,
                                      @Param("idPedido") int idPedido,
                                      @Param("numeroCelularDestino") String numeroCelularDestino,
                                      @Param("nombreUsuarioDestino") String nombreUsuarioDestino);

    @Query( value = "call sp_reporte_incidencias_ayni(:idNegocio, :fechaProceso)", nativeQuery = true)
    List<ReporteIncidenciasAyni> reporteIncidenciasAyni(@Param("idNegocio") int idNegocio,
                                                        @Param("fechaProceso") Date fechaProceso);

    @Query( value = "call sp_actualizar_hora_atencion_control_mesa(:idNegocio, :fechaProceso)", nativeQuery = true)
    List<RespuestaStd> actualizarHoraAtencionControlMesa(@Param("idNegocio") int idNegocio,
                                                         @Param("fechaProceso") Date fechaProceso);
                                                         
    @Query( value = "call sp_cambiar_mesa_pedido(:idNegocio, :idPedido, :mesaDestino, :fecha)", nativeQuery = true)
    List<RespuestaStd> cambiarMesaPedido(@Param("idNegocio") int idNegocio, @Param("idPedido") int idPedido,
                                         @Param("mesaDestino") int mesa, @Param("fecha") Date fecha);

    @Query( value = "call sp_reporte_cierre_tienda_detalle_cliente(:idNegocio, :docCliente)", nativeQuery = true)
    List<ReporteCierreDetalleCliente> reporteCierreTiendaDetalleCliente(@Param("idNegocio") int idNegocio, @Param("docCliente") String docCliente);

    @Query( value = "call sp_reporte_cierre_tienda_detalle_documento(:idNegocio, :idPedido)", nativeQuery = true)
    List<ReporteCierreDetalleDocumento> reporteCierreTiendaDetalleDocumento(@Param("idNegocio") int idNegocio, @Param("idPedido") int idPedido);

    @Query( value = "call sp_registra_marca_personal(:idNegocio, :numeroCelular, :tipoMarca, :fechaMarca)", nativeQuery = true)
    List<RespuestaStd> registraMarcaPersonal(@Param("idNegocio") int idNegocio,  
                                             @Param("numeroCelular") String numeroCelular, 
                                             @Param("tipoMarca") int tipoMarca, 
                                             @Param("fechaMarca") Date fechaMarca);

    @Query( value = "call sp_listar_promociones(:idNegocio)", nativeQuery = true)
    List<Promociones> listarPromociones(@Param("idNegocio") int idNegocio);

    @Query( value = "call sp_generar_promocion(:idNegocio, :idPromocion, :nombrePromocion, :detalleProducto, " +
    ":fechaInicioPromocion, :fechaFinalPromocion, :precio, :cantidadProductos)", nativeQuery = true)
    List<RespuestaStd> generarPromocion(@Param("idNegocio") int idNegocio, @Param("idPromocion") int idPromocion, 
    @Param("nombrePromocion") String nombrePromocion, @Param("detalleProducto") String detalleProducto, 
    @Param("fechaInicioPromocion") String fechaInicioPromocion, @Param("fechaFinalPromocion") String fechaFinalPromocion, 
    @Param("precio") BigDecimal precio, @Param("cantidadProductos") int cantidadProductos);

    @Query( value = "call sp_anular_doc_venta(:idNegocio, :idPedido, :fechaProceso)", nativeQuery = true)
    List<RespuestaStd> anularDocVenta(@Param("idNegocio") int idNegocio, 
    @Param("idPedido") int idPedido, @Param("fechaProceso") Date fechaProceso);

    @Query( value = "call sp_operacion_hoteles(:idNegocio, :idPedido, :idProducto, :agregarDiaNoches, :fechaOperacion, :tipoOperacion)", nativeQuery = true)
    List<RespuestaStd> operacionHoteles(@Param("idNegocio") int idNegocio, @Param("idPedido") int idPedido, 
                                        @Param("idProducto") int idProducto, @Param("agregarDiaNoches") int agregarDiaNoches,
                                        @Param("fechaOperacion") Date fechaOperacion, @Param("tipoOperacion") int tipoOperacion);
    
    @Query( value = "call sp_obtener_disponibilidad_cuarto(:idNegocio, :anioConsultaDesde, :mesConsultaDesde, :diaConsultaDesde, " + 
                    ":anioConsultaHasta, :mesConsultaHasta, :diaConsultaHasta)", nativeQuery = true)
    List<DisponibildadCuarto> obtenerDisponibilidadCuarto(@Param("idNegocio") int idNegocio, @Param("anioConsultaDesde") int anioConsultaDesde, 
                                        @Param("mesConsultaDesde") int mesConsultaDesde, @Param("diaConsultaDesde") int diaConsultaDesde,
                                        @Param("anioConsultaHasta") int anioConsultaHasta, @Param("mesConsultaHasta") int mesConsultaHasta, 
                                        @Param("diaConsultaHasta") int diaConsultaHasta);
    
    @Query( value = "call sp_validar_cuartos_insertar(:idNegocio, :detalleProducto, :fechaInicio)", nativeQuery = true)
    List<RespuestaStd> validarCuartosInsertar(@Param("idNegocio") int idNegocio, @Param("detalleProducto") String detalleProducto, 
                                              @Param("fechaInicio") Date fechaInicio);

    @Query( value = "call sp_reporte_ocupacion(:idNegocio, :fechaCorte)", nativeQuery = true)
    List<ReporteOcupacionResponse> reporteOcupacion(@Param("idNegocio") int idNegocio, 
                                                    @Param("fechaCorte") Date fechaCorte);

    @Query( value = "call sp_reporte_ingresos_generados(:idNegocio, :fechaConsulta)", nativeQuery = true)
    List<ReporteIngresosGeneradosResponse> reporteIngresosGenerados(@Param("idNegocio") int idNegocio, 
                                                                    @Param("fechaConsulta") Date fechaConsulta);
                                                                    
    @Query( value = "call sp_reporte_reservas(:idNegocio, :fechaConsulta)", nativeQuery = true)
    List<ReporteReservasResponse> reporteReservas(@Param("idNegocio") int idNegocio, 
                                                  @Param("fechaConsulta") Date fechaConsulta);
                    
    @Query( value = "call sp_reporte_checks(:idNegocio, :fechaConsulta)", nativeQuery = true)
    List<ReporteChecksResponse> reporteChecks(@Param("idNegocio") int idNegocio, 
                                              @Param("fechaConsulta") Date fechaConsulta);
                                                                  
    @Query( value = "call sp_listado_limpieza(:idNegocio, :fechaConsulta, :numeroCelular, :nombreUsuario)", nativeQuery = true)
    List<ListadoLimpiezaResponse> listadoLimpieza(@Param("idNegocio") int idNegocio, 
                                                  @Param("fechaConsulta") Date fechaConsulta, 
                                                  @Param("numeroCelular") String numeroCelular, 
                                                  @Param("nombreUsuario") String nombreUsuario);
              
    @Query( value = "call sp_registrar_bitacora_limpieza(:idNegocio, :idProducto, :revertir, :fechaProceso, :numeroCelular, :nombreUsuario)", nativeQuery = true)
    List<RespuestaStd> registrarBitacoraLimpieza(@Param("idNegocio") int idNegocio, 
                                                 @Param("idProducto") int idProducto, 
                                                 @Param("revertir") int revertir, 
                                                 @Param("fechaProceso") Date fechaProceso, 
                                                 @Param("numeroCelular") String numeroCelular, 
                                                 @Param("nombreUsuario") String nombreUsuario);


    @Query( value = "call sp_validar_reserva_pub(:idNegocio, :mesa, :fechaReserva)", nativeQuery = true)
    List<RespuestaStd> validarReservaPub(@Param("idNegocio") int idNegocio, 
                                         @Param("mesa") int mesa,
                                         @Param("fechaReserva") Date fechaReserva);
                                        
    @Query( value = "call sp_generar_reserva_pub(:idNegocio, :fechaReserva, :mesa, :numeroCelular, :nombreUsuario, :nombreCliente)", nativeQuery = true)
    List<RespuestaStd> generarReservaPub(@Param("idNegocio") int idNegocio, 
                                         @Param("fechaReserva") Date fechaReserva, 
                                         @Param("mesa") int mesa, 
                                         @Param("numeroCelular") String numeroCelular, 
                                         @Param("nombreUsuario") String nombreUsuario, 
                                         @Param("nombreCliente") String nombreCliente);

    @Query( value = "call sp_confirmar_reserva(:idNegocio, :idPedido, :numeroCelular, :nombreUsuario, :fechaReserva)", nativeQuery = true)
    List<RespuestaStd> confirmarReserva(@Param("idNegocio") int idNegocio, 
                                        @Param("idPedido") int idPedido,
                                        @Param("numeroCelular") String numeroCelular, 
                                        @Param("nombreUsuario") String nombreUsuario,
                                        @Param("fechaReserva") Date fechaReserva);

    @Query( value = "call sp_obtener_doc_envio_factura_sunat(:idNegocio, :idPedido)", nativeQuery = true)
    List<PedidoEnvioSunat> obtenerDocEnvioFacturaSunat(@Param("idNegocio") int idNegocio, 
                                                   @Param("idPedido") int idPedido);

    @Query( value = "call sp_modificar_importe_adicional(:idNegocio, :idPedido, :idProducto, :total, :nombreCliente)", nativeQuery = true)
    List<RespuestaStd> modificarImporteAdicional(@Param("idNegocio") int idNegocio, 
                                                 @Param("idPedido") int idPedido, 
                                                 @Param("idProducto") int idProducto, 
                                                 @Param("total") BigDecimal total,
                                                 @Param("nombreCliente") String nombreCliente);
                                               
    @Modifying
    @Query( value = "call sp_registrar_valores_bolsa_valores(:codigoEmpresa, :valorDividendo, :fechaCorte, :fechaRegistro, :fechaPago, " +
    ":textoEncontrado,:exportado)", nativeQuery = true)
    void registrarValoresBolsaValores(@Param("codigoEmpresa") String codigoEmpresa, 
                                      @Param("valorDividendo") BigDecimal valorDividendo, 
                                      @Param("fechaCorte") String fechaCorte, 
                                      @Param("fechaRegistro") String fechaRegistro,
                                      @Param("fechaPago") String fechaPago,
                                      @Param("textoEncontrado") String textoEncontrado,
                                      @Param("exportado") int exportado);

    @Query( value = "call sp_obtener_codigo_empresa_bolsa()", nativeQuery = true)
    List<ListadoEmpresasBolsa> obtenerCodigoEmpresaBolsa();
    
    @Modifying
    @Query( value = "call sp_registrar_historico_precio_accion(:codigoEmpresa, :codigoCompany, :codigoDividendos, :fechaPrecioAccion, :valorAccion)",
            nativeQuery = true)
    void registrarHistoricoPrecioAccion(@Param("codigoEmpresa") String codigoEmpresa, 
                                        @Param("codigoCompany") String valorDividendo, 
                                        @Param("codigoDividendos") String codigoDividendos,
                                        @Param("fechaPrecioAccion") Date fechaCorte, 
                                        @Param("valorAccion") BigDecimal valorAccion);
                                        
    @Modifying
    @Query( value = "call sp_registrar_pago_dividendos(:codigoEmpresa, :fechaPagoDiv, :pagoDiv)",
            nativeQuery = true)
    void registrarPagoDividendos(@Param("codigoEmpresa") String codigoEmpresa, 
                                 @Param("fechaPagoDiv") String fechaPagoDiv, 
                                 @Param("pagoDiv") BigDecimal pagoDiv);

}
