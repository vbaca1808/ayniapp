package com.ayni.coperacion.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailsSunatDto {
    
    private String codProducto;
    private String unidad;
    private String descripcion;
    private int cantidad;
    private BigDecimal mtoValorUnitario;
    private BigDecimal mtoValorVenta;
    private BigDecimal mtoBaseIgv;
    private BigDecimal porcentajeIgv;
    private BigDecimal igv;
    private int tipAfeIgv;
    private BigDecimal totalImpuestos;
    private BigDecimal mtoPrecioUnitario;

}
