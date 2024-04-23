package com.ayni.coperacion.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompraNegocio {
    
    private int idNegocio;
    private String nombreProveedor;
    private String rucProveedor; 
    private Date fechaCompra;
    private BigDecimal totalCompra; 
    private int tipoDocumento; 
    private String numeroDocumento; 
    private String detalleCompra;
	private BigDecimal efectivo;
	private BigDecimal yape;
	private BigDecimal plin;
	private BigDecimal tarjeta;
	private BigDecimal otros;
	private String nombreUsuarioCajero;
	private String numeroCelularCajero;

}
