package com.ayni.coperacion.response;

public interface ReportePedido {
    
    int getIdPedido();
    String getTotalPedido();
    String getFechaPedido();
    String getMesa(); 
    String getEstadoPedido();
    String getIdProducto();
    String getDescripcionProducto(); 
    int getCantidadAtendida(); 
    int getCantidadParaLLevar();
    String getPrecio();
    String getNombreUsuario();
    String getNumeroCelular();
    String getEstadoItemPedido();
    
    
}
