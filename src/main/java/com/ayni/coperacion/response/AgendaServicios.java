package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface AgendaServicios {
    
    String getFechaProgramacionServicio(); 
    String getNombreDia(); 
    String getHora();
    int getIdProducto(); 
    String getDescripcionProducto(); 
    String getNombreCliente(); 
    String getNumeroTelefonoCliente();
    String getDireccion();
    int getTiempoServicio();
    int getIdPedido();
    BigDecimal getPrecio();
    String getBitacoraInicial();

}
