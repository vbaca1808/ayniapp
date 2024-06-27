package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteOcupacionResponse {
    
    int getIdProducto(); 
    String getNombreProducto();
    int getAdultos(); 
    int getNinio();
    String getEstado(); 
    String getCheckIn();
    String getCheckOut(); 
    String getFechaLimpieza();
    int getNoches();
    BigDecimal getIngresoPorNoche();
    BigDecimal getRecaudado();

}
