package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ListadoCocina {
    
    int getIdPedido();
    String getMesa();
    String getIdProducto();
    String getDescripcionProducto();
    String getCantidadMesa();
    String getCantidadLlevar();
    int getEstadoCocina();
    int getLecturaCocina();
    String getTipo();
    int getPreparacionMesa();
    int getPreparacionLlevar();
    BigDecimal getTotalPedido();
    int getEstadoPedido();
    int getMinutos();

}
