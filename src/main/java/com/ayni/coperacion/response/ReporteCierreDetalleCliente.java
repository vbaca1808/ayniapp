package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteCierreDetalleCliente {
    
    int getIdPedido();
    BigDecimal getCantidad();
    String getDescripcionProducto();
    BigDecimal getPrecio();
    BigDecimal getImporte();

}
