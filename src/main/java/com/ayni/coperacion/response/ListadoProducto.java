package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ListadoProducto {
    
    int getIdProducto(); 
    String getNombreProducto(); 
    String getPalabraClave(); 
    BigDecimal getPrecio(); 
    int getEstado(); 
	BigDecimal getStock();
    String getRecetaCompleta();
    int getOrdenLista(); 
    int getIrCocina(); 
    String getGrupoProducto();
    String getNegocioCocina();
    
}
