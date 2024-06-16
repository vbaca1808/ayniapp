package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface Promociones {

    int getId();
    String getNombrePromocion();
    String getFechaInicioPromocion();
    String getFechaFinalPromocion();
    BigDecimal getPrecio();
    BigDecimal getCantidadProductos();
    String getDetalle();
    
}

