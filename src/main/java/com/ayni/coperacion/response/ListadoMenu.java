package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ListadoMenu {

    int getIdNegocio();
    String getIdProducto();
    String getNombreProducto();
    BigDecimal getCantidad();
    BigDecimal getCantidadParaLlevar();
    BigDecimal getCantidadAtendida();
    BigDecimal getCantidadAtendidaParaLlevar();
    String getPrecio();
    BigDecimal getStock();
    String getRutaProducto();
    String getEstadoPedido();
    String getDescripcion();
    BigDecimal getTotal();
    int getPreparacionMesa();
    int getPreparacionLlevar();
    String getNumeroCelular();
    String getNombreUsuario();
    String getNombreCliente();
    String getDireccionCliente();
    BigDecimal getComisionDelivery();
    int getIdGrupoProducto();
    String getPalabraClave();
    
}
