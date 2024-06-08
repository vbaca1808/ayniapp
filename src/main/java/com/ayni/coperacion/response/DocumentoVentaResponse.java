package com.ayni.coperacion.response;

public interface DocumentoVentaResponse {
    
    String getRazonSocial();
    String getRucEmpresa();
    String getDireccion();
    String getDescripcion();
    String getTipoDocumento();
    String getDocumento();
    String getDocCliente();
    String getNombreCliente();
    String getDireccionCliente();
    String getFechaPedido();
    String getMoneda();
    int getIdProducto();
    int getCantidad();
    String getDescripcionProducto();
    String getPrecioVenta();
    String getTotalItem();
    String getTotalPedido();
    String getGravado();
    String getIgv();
    String getCondicionPago();
    int getMesa();
    String getNombreUsuario();
    
}
