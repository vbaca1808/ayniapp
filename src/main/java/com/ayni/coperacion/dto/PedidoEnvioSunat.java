package com.ayni.coperacion.dto;

import java.math.BigDecimal;

public interface PedidoEnvioSunat {
    
    String getTipoDoc();
    String getNumeroDocumento(); 
    String getFechaPedido();
    int getDocCliente(); 
    String getNombreCliente(); 
    String getDireccionCliente();
    int getRucEmpresa();
    String getRazonSocial();
    String getNombreNegocio(); 
    String getDireccion();
    BigDecimal getTotalPedido();
    BigDecimal getTotal();
    BigDecimal getPorcentajeIgv();
    String getIdProducto(); 
    String getUnidad();
    String getDescripcionProducto(); 
    BigDecimal getCantidad(); 
    BigDecimal getPrecio();
    String getApiUrlEnvioSunat();
    String getTokenEnvioSunat();
    
}
