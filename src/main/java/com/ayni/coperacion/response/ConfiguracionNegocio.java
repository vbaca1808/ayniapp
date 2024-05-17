package com.ayni.coperacion.response;

public interface ConfiguracionNegocio {
    
    int getId(); 
    String getNombreNegocio(); 
    String getDescripcion(); 
    String getLogo(); 
    int getEstadoNegocio();
    int getRubroNegocio(); 
    int getUsarLectorBarraBusquedaManual(); 
    int getEnvioPlatoDirectoACocina();
}
