package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteCierreDetalle {
    int getIdPedido(); 
    int getMesa();
    String getDescripcionProducto();  
	BigDecimal getPrecio();
    BigDecimal getCantidad(); 
    BigDecimal getCantidadAtendidaParaLlevar(); 
	BigDecimal getTotal();
}
