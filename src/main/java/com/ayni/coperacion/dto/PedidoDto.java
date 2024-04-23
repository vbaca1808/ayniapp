package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDto {

    private int idNegocio;
    private int idPedido;
    private String detalleProducto;
    private int mesa;
    private String numeroCelular;
    private String nombreUsuario; 
    private String docCliente; 
    private String nombreCliente; 
    private int tipoDoc; 
    private String numeroDocumento;
    
}
