package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteCierreDetalleDto {
    
	private int idNegocio; 
    private int anioSeleccionado;
	private int mesSeleccionado;
	private int diaSeleccionado;
	private String numeroCelular;
	private String nombreUsuario;
	private int idProducto;


}
