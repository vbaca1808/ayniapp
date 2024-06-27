package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteIngresosGeneradosResponse {
    
    int getIdPedido(); 
    String getDescripcionProducto();
    BigDecimal getHospedaje(); 
    BigDecimal getAdicional();

}
