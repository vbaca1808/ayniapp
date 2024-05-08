package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteCierreDetalle {
    
    String getDescripcionProducto();  
    String getObsMesa();  
    String getObsLlevar();  
	BigDecimal getPrecio();
    BigDecimal getCantidad(); 
    BigDecimal getCantidadAtendidaParaLlevar(); 
	BigDecimal getTotal();

}
