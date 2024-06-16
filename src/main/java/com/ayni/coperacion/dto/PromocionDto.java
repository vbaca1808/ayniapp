package com.ayni.coperacion.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromocionDto {

    private int idNegocio;
	private String nombrePromocion;
	private List<DetalleProductoPromocionDto> detalleProducto;
	private String fechaInicioPromocion;
	private String fechaFinalPromocion;
	private BigDecimal precio;
	private int cantidadProductos;

}
