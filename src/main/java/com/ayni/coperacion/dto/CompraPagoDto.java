package com.ayni.coperacion.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraPagoDto {
    
    private int idNegocio;
    private int idCompra; 
    private Date fechaPago; 
    private BigDecimal efectivo;
    private BigDecimal yape; 
    private BigDecimal plin; 
    private BigDecimal tarjeta; 
    private BigDecimal otros; 
    private String nombreUsuarioCajero;
    private String numeroCelularCajero;
    
}
