package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface PedidoGenerado {
    
    int getIdPedido();
    String getMesa(); 
    String getIdProducto(); 
    String getNombreProducto(); 
    String getRutaProducto(); 
    BigDecimal getCantidad(); 
    BigDecimal getCantidadParaLLevar(); 
    String getPrecio(); 
    String getTotal();
    int getEstadoPedido();
    int getEstadoItemPedido();
    BigDecimal getCantidadAtendida(); 
    BigDecimal getCantidadAtendidaParaLLevar(); 
    
}
