package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteReservasResponse {
    
    String getFechaPedido(); 
    int getIdProducto(); 
    String getDescripcionProducto();
    BigDecimal getCostoNoche(); 
    String getFechaProgramacionServicio();
    BigDecimal getTotal();
    String getTipo();

}
