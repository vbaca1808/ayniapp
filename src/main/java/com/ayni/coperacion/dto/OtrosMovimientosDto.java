package com.ayni.coperacion.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtrosMovimientosDto {
    
    private int tipoMovimiento;
    private int idNegocio; 
    private int idProducto; 
    private BigDecimal cantidad;
    private Date fechaMovimiento;

}
