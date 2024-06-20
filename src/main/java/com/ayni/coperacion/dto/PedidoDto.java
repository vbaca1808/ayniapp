package com.ayni.coperacion.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoDto {

    private int idNegocio;
    private int idPedido;
    private List<ListadoMenuDto> detalleProducto;
    private int mesa;
    private String numeroCelular;
    private String nombreUsuario; 
    private String docCliente; 
    private String nombreCliente; 
    private String direccionCliente; 
    private int tipoDoc; 
    private String numeroDocumento;
    private BigDecimal comisionDelivery;
    private int diasSalida; 
    
}
