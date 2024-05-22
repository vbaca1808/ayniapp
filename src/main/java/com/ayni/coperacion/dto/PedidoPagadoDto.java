package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PedidoPagadoDto {
    
    private int idNegocio;
	private int idPedido;
	private String numeroCelular;
	private String nombreUsuario;
	private Date fechaProceso;
	private BigDecimal efectivo;
	private BigDecimal yape;
	private BigDecimal plin;
	private BigDecimal tarjeta;
	private BigDecimal otros;
	private BigDecimal credito;
	private BigDecimal propina;
	private int soyCocina;
	private int tipoDocumento;
	private String numeroDocumento;

}
