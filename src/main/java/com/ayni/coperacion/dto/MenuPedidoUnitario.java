package com.ayni.coperacion.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuPedidoUnitario {
    
	private int idNegocio;
	private int idPedido;
	private int idProducto;
	private int cantidad;
	private int cantidadLLevar;
	private String descripcion;
	private BigDecimal total;

}
