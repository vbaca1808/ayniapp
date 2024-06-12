package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteCierreDetalleCliente {
    
    int getIdPedido();
    BigDecimal getCantidad();
    int getDescripcionProducto();
    BigDecimal getPrecio();
    BigDecimal getImporte();

}
