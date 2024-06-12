package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteCierreDetalleDocumento {
    
    BigDecimal getCantidad();
    String getDescripcionProducto();
    BigDecimal getPrecio();
    BigDecimal getImporte();

}
