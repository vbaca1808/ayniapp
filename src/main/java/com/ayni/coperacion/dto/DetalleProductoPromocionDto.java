package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class DetalleProductoPromocionDto {
 
    private int idProducto;
    private BigDecimal cantidad;

}
