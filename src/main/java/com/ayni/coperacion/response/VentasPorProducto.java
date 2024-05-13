package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface VentasPorProducto {
    
    int getIdPedido(); 
    String getFechaPedido(); 
    int getIdProducto();
    String getDescripcionProducto(); 
	BigDecimal getCantidad();
    BigDecimal getPrecio();
    BigDecimal getImporte(); 
    
}
