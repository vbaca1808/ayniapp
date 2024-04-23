package com.ayni.coperacion.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarNegocioPedidoDto {
    
    private int idNegocio; 
    private int idProducto; 
    private String nombreProducto;
    private BigDecimal precio; 
    private int estado; 
    private BigDecimal stockInicial;
    private String codigoBarra;

}
