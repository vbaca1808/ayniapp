package com.ayni.coperacion.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListadoMenuDto {
    
    private String codigoProducto;
    private String descripcionProducto;
    private int cantidadMesa;
    private int cantidadLLevar;
    private BigDecimal precio;
    private BigDecimal total;
    private String promocion;

}
