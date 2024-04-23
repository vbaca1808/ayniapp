package com.ayni.coperacion.response;

import java.math.BigDecimal;
import java.util.Date;

public interface Inventario {
    
    String getFechaCorte(); 
    int getIdProducto(); 
    String getDescripcionProducto(); 
    String getCodigoBarra(); 
    String getStockInicial();
    String getPrecio(); 
    String getMotivo(); 
    String getDocumento();
    String getTipo();

}
