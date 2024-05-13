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
import com.ayni.coperacion.entidades.Usuario;
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
import com.ayni.coperacion.response.PedidoGenerado;
import com.ayni.coperacion.response.PedidoPagoResponse;
import com.ayni.coperacion.response.ReporteCierre;
import com.ayni.coperacion.response.ReporteCierreDetalle;
import com.ayni.coperacion.response.ReporteCierreDetalleEfectivo;
import com.ayni.coperacion.response.ReportePedido;
import com.ayni.coperacion.response.RespuestaStd;
import com.ayni.coperacion.response.VentasPorProducto; 

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
 
    @Modifying
    @Query( value = "call sp_grabar_usuario(:numeroCelular,:nombreUsuario,:cliente," 
                  + ":colaborador,:actualizar, :codigoVerificacion)", nativeQuery = true)
    List<RespuestaStd> registrarUsuario(@Param("numeroCelular") String numeroCelular,
    @Param("nombreUsuario") String nombreUsuario, @Param("cliente") int cliente,
    @Param("colaborador") int colaborador,
    @Param("actualizar") int actualizar, @Param("codigoVerificacion") String codigoVerificacion);

    @Query( value = "call sp_validar_usuario(:numeroTelefono,:nombreUsuario,:codigoVerificacion)", nativeQuery = true)
    List<RespuestaStd> validarUsuario(@Param("numeroTelefono") String numeroTelefono,
    @Param("nombreUsuario") String nombreUsuario, @Param("codigoVerificacion") String codigoVerificacion);

    @Query( value = "call sp_listado_negocio()", nativeQuery = true)
    List<Negocio> listadoNegocio();

    @Query( value = "call sp_listar_cargos(:idNegocio)", nativeQuery = true)
    List<CargoNegocio> listadoCargoNegocio(@Param("idNegocio") int idNegocio);

    @Query( value = "call sp_listado_menu(:idNegocio,:idPedido)", nativeQuery = true)
    List<ListadoMenu> listadoMenu(@Param("idNegocio") int idNegocio, 
                                  @Param("idPedido") int idPedido);

    @Modifying
    @Query( value = "call sp_crear_menu_pedido(:idNegocio, :idPedido, :fechaPedido, :detalleProducto, " + 
                    ":mesa, :numeroCelular, :nombreUsuario, :docCliente, :nombreCliente, :tipoDoc, :numeroDocumento)", nativeQuery = true)
    List<RespuestaStd> crearMenuPedido(@Param("idNegocio") int idNegocio, 
                                       @Param("idPedido") int idPedido, 
                                       @Param("fechaPedido") Date fechaPedido, 
                                       @Param("detalleProducto") String detalleProducto, 
                                       @Param("mesa") int mesa,
                                       @Param("numeroCelular") String numeroCelular, 
                                       @Param("nombreUsuario") String nombreUsuario,
                                       @Param("docCliente") String docCliente,
                                       @Param("nombreCliente") String nombreCliente, 
                                       @Param("tipoDoc") int tipoDoc,
                                       @Param("numeroDocumento") String numeroDocumento);
                                
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
                    ":nombreUsuario, :fechaProceso, :incluirpl, :idProducto)", nativeQuery = true)
    List<RespuestaStd> pedidoAtendidoIndividual(
                                    @Param("idNegocio") int idNegocio, 
                                    @Param("idPedido") int idPedido, 
                                    @Param("numeroCelular") String numeroCelular,
                                    @Param("nombreUsuario") String nombreUsuario,
                                    @Param("fechaProceso") Date fechaProceso,
                                    @Param("incluirpl") int incluirpl,
                                    @Param("idProducto") int idProducto); 

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
    ":fechaProceso, :efectivo, :yape, :plin, :tarjeta, :otros, :credito, :soyCocina)", nativeQuery = true)
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
                                    @Param("soyCocina") int soyCocina);

                                    
    @Query( value = "call sp_modificar_pago_pedido(:idNegocio, :idPedido, :numeroCelular, :nombreUsuario, " +
    ":fechaProceso, :efectivo, :yape, :plin, :tarjeta, :otros, :credito)", nativeQuery = true)
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
                                             @Param("credito") BigDecimal credito);

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
    ":numeroCelular, :nombreUsuario)", nativeQuery = true)
    List<ReporteCierre> reporteCierreTienda(@Param("idNegocio") int idNegocio, 
                                            @Param("anioSeleccionado") int anioSeleccionado,
                                            @Param("mesSeleccionado") int mesSeleccionado,
                                            @Param("diaSeleccionado") int diaSeleccionado,
                                            @Param("numeroCelular") String numeroCelular,
                                            @Param("nombreUsuario") String nombreUsuario);
                                                
    @Query( value = "call sp_listado_cocina(:idNegocio, :anioSeleccionado, :mesSeleccionado, :diaSeleccionado)", nativeQuery = true)
    List<ListadoCocina> listadoCocina(@Param("idNegocio") int idNegocio, 
                                      @Param("anioSeleccionado") int anioSeleccionado,
                                      @Param("mesSeleccionado") int mesSeleccionado,
                                      @Param("diaSeleccionado") int diaSeleccionado);
    
    @Query( value = "call sp_actualizar_estado_producto_cocina(:idNegocio, :idPedido, :idProducto," + 
                    ":cantidadMesa, :cantidadLlevar, :estadoCocina)", nativeQuery = true)
    List<RespuestaStd> actualizarEstadoProductoCocina(@Param("idNegocio") int idNegocio, 
                                                      @Param("idPedido") int idPedido,
                                                      @Param("idProducto") int idProducto,
                                                      @Param("cantidadMesa") int cantidadMesa,
                                                      @Param("cantidadLlevar") int cantidadLlevar,
                                                      @Param("estadoCocina") int estadoCocina);
                                        
                                    
    @Query( value = "call sp_listado_cajero(:idNegocio, :anioSeleccionado, :mesSeleccionado, :diaSeleccionado)", nativeQuery = true)
    List<ListadoCajero> listadoCajero(@Param("idNegocio") int idNegocio, 
                                      @Param("anioSeleccionado") int anioSeleccionado,
                                      @Param("mesSeleccionado") int mesSeleccionado,
                                      @Param("diaSeleccionado") int diaSeleccionado);

    @Query( value = "call sp_actualizar_negocio_pedido(:idNegocio, :idProducto, " +
	":nombreProducto, :precio, :estado, :stockInicial, :codigoBarra, :recetaInsumo, :ordenLista, :irCocina)", nativeQuery = true)
    List<RespuestaStd> actualizarNegocioPedido(@Param("idNegocio") int idNegocio,  
                                               @Param("idProducto") int idProducto,
                                               @Param("nombreProducto") String nombreProducto,
                                               @Param("precio") BigDecimal precio,
                                               @Param("estado") int estado,
                                               @Param("stockInicial") BigDecimal stockInicial,
                                               @Param("codigoBarra") String codigoBarra,
                                               @Param("recetaInsumo") String recetaInsumo,
                                               @Param("ordenLista") int ordenLista,
                                               @Param("irCocina") int irCocina);

    @Query( value = "call sp_agregarquita_admin_usuario(:idUsuario, :nombreUsuario, :isAdmin)", nativeQuery = true)
    List<RespuestaStd> agregarquitaAdminUsuario(@Param("idUsuario") int idUsuario,  
                                                @Param("nombreUsuario") String nombreUsuario, 
                                                @Param("isAdmin") int isAdmin);

    @Query( value = "call sp_actualizar_id_negocio_usuario(:numerotelefono, :idNegocio, :colaborador)", nativeQuery = true)
    List<RespuestaStd> actualizarIdNegocioUsuario(@Param("numerotelefono") String numerotelefono,  
                                                  @Param("idNegocio") int idNegocio,  
                                                  @Param("colaborador") int colaborador);
                                                
    @Query( value = "call sp_listado_productos_negocio(:idNegocio)", nativeQuery = true)
    List<ListadoProducto> listadoProductosNegocio(@Param("idNegocio") int idNegocio);
                                                
    @Query( value = "call sp_listado_usuario_negocio(:idNegocio)", nativeQuery = true)
    List<ListadoUsuario> listadoUsuarioNegocio(@Param("idNegocio") int idNegocio);
    
    @Query( value = "call sp_configuracion_negocio(:idNegocio, :nombreNegocio, :descripcion, " +  
     ":logo, :estadoNegocio)", nativeQuery = true)
    List<RespuestaStd> configuracionNegocio(@Param("idNegocio") int idNegocio,  
                                            @Param("nombreNegocio") String nombreNegocio,  
                                            @Param("descripcion") String descripcion,  
                                            @Param("logo") String logo,  
                                            @Param("estadoNegocio") int estadoNegocio);

    @Query( value = "call sp_obtener_configuracion_negocio(:idNegocio)", nativeQuery = true)
    List<ConfiguracionNegocio> obtenerConfiguracionNegocio(@Param("idNegocio") int idNegocio);
    
    @Query( value = "call sp_obtener_listado_producto_tienda(:idNegocio, :codigoBarra)", nativeQuery = true)
    List<ListadoProductoTienda> obtenerListadoProductoTienda(@Param("idNegocio") int idNegocio,
                                                             @Param("codigoBarra") String codigoBarra);

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
                                     
    @Query( value = "call sp_listar_inventario(:idNegocio, :anioCorte, :mesCorte, :diaCorte)", nativeQuery = true)
    List<Inventario> listarInventario(@Param("idNegocio") int idNegocio,
                                      @Param("anioCorte") int anioCorte,
                                      @Param("mesCorte") int mesCorte,
                                      @Param("diaCorte") int diaCorte);

    @Query( value = "call sp_agenda_servicios(:idNegocio, :tipoFiltro, :fechaReferencia)", nativeQuery = true)
    List<AgendaServicios> agendaServicios(@Param("idNegocio") int idNegocio,
                                          @Param("tipoFiltro") int tipoFiltro,
                                          @Param("fechaReferencia") ZonedDateTime fechaReferencia);
                                          
    @Query( value = "call sp_actualizar_corte_inventario(:fechaCorte)", nativeQuery = true)
    List<RespuestaStd> actualizarCorteInventario(@Param("fechaCorte") ZonedDateTime fechaCorte);
                                      
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
    ":numeroCelular, :nombreUsuario, :idProducto)", nativeQuery = true)
    List<ReporteCierreDetalle> reporteCierraTiendaDetalle(@Param("idNegocio") int idNegocio, 
                                                          @Param("anioSeleccionado") int anioSeleccionado,
                                                          @Param("mesSeleccionado") int mesSeleccionado,
                                                          @Param("diaSeleccionado") int diaSeleccionado,
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
                                                         
}
