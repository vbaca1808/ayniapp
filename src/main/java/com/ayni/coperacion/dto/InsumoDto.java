package com.ayni.coperacion.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InsumoDto {
    
    private int idNegocio;
    private int idInsumo;
    private String descripcionInsumo;
    private BigDecimal cantidadInvertida;
    private int esCombustible;
    private int requiereUsuario;

}
