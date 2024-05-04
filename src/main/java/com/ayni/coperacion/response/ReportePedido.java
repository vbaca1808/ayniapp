package com.ayni.coperacion.response;

import java.math.BigDecimal;

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
    String getNumeroTelefonoCliente(); 
    String getNombreCliente(); 
    String getDireccion(); 
    String getFechaProgramacionServicio();
    int getTiempoServicio();
    String getBitacoraInicial();
    int getEstadoMesa();
    int getEstadoLlevar();
    BigDecimal getTotal();

}
