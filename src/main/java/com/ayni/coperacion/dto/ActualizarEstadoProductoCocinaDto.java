package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarEstadoProductoCocinaDto {
    private int idNegocio; 
    private int idPedido; 
    private int idProducto; 
    private int cantidadMesa;
    private int cantidadLlevar;
    private int estadoCocina;

}
