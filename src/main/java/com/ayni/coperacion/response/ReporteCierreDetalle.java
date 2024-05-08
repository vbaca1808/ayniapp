package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteCierreDetalle {
    
    String getDescripcionProducto();  
	BigDecimal getPrecio();
    BigDecimal getCantidad(); 
    BigDecimal getCantidadAtendidaParaLlevar(); 
	BigDecimal getTotal();

}
