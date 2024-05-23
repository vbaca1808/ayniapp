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
    private String palabraClave;
    private BigDecimal precio; 
    private int idGrupoProducto; 
    private int estado; 
    private BigDecimal stockInicial;
    private int idNegocioCocina; 
    private String codigoBarra;
    private String recetaInsumo;
    private int ordenLista; 
    private int productoCocina; 

}
