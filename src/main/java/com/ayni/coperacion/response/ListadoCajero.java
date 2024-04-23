package com.ayni.coperacion.response;

import java.math.BigDecimal;
import java.util.Date;

public interface ListadoCajero {
    
    int getMesa();
    int getIdPedido();
    BigDecimal getTotalPedido();
    String getNombreUsuario();
    String getHora();
    String getHoraPago();
    String getTipoPago();
    BigDecimal getMontoPago();
    String getTipo(); 

}
