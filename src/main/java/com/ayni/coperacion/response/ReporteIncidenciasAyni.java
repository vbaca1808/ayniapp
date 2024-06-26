package com.ayni.coperacion.response;

public interface ReporteIncidenciasAyni {
    
    String getNombreUsuario(); 
    String getFechaModificacion(); 
    String getFechaAtencion();
    String getDescripcionProducto();
    int getCantidad();
    int getCantidadAtendida(); 
    int getCantidadParaLLevar(); 
    int getCantidadAtendidaParaLlevar();
    int getMinutosDemora();
    String getTipo();
    String getCorreoElectronico();

}
