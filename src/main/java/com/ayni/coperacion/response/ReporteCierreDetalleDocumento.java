package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteCierreDetalleDocumento {
    
    BigDecimal getCantidad();
    int getDescripcionProducto();
    BigDecimal getPrecio();
    BigDecimal getImporte();

}
