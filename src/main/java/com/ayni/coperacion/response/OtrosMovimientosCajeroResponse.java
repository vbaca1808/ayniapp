package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface OtrosMovimientosCajeroResponse {
    
    int getIdNegocio();
    String getMovimiento();
    BigDecimal getImporte();
}
