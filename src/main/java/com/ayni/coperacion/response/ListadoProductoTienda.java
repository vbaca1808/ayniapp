package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ListadoProductoTienda {
    
    int getIdProducto();
    String getNombreProducto();
    BigDecimal getPrecio();
    BigDecimal getStock();
    String getCodigoBarra();
    String getNumeroLote();
    String getFechaVencimiento();

}
