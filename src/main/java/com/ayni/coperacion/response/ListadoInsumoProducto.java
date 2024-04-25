package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ListadoInsumoProducto {
    
    int getIdInsumo(); 
    String getDescripcionInsumo();
    int getEsCombustible();
    int getRequiereUsuario(); 
    BigDecimal getCantidadInsumo(); 
    String getNombreUsuario();
    
}
