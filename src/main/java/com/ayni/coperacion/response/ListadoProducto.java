package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ListadoProducto {
    
    int getIdProducto(); 
    String getNombreProducto(); 
    BigDecimal getPrecio(); 
    int getEstado(); 
	BigDecimal getStock();

}
