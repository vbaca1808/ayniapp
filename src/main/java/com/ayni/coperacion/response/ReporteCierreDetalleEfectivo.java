package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ReporteCierreDetalleEfectivo {
    
    String getMesa();  
    int getIdPedido();  
    String getFechaPago(); 
    BigDecimal getMontoPago();
      
}
