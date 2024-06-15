package com.ayni.coperacion.response;

public interface ConfiguracionNegocio {
    
    int getId(); 
    String getNombreNegocio(); 
    String getDescripcion(); 
    String getLogo(); 
    int getEstadoNegocio();
    String getRubroNegocio(); 
    int getUsarLectorBarraBusquedaManual(); 
    int getEnvioPlatoDirectoACocina();
    int getGenerarComprobanteVenta();
    int getUsarCorrelativoAutomatico();
    int getPedirNombreClientePedidosParaLlevar();
    String getCorreoElectronico();
    String getCorrelativos();
    String getGrupoProducto();
    String getCocinas();
    String getDireccionBluetoothCocina();
    String getDireccionBluetoothMesero();
    String getUuidCocina();
    String getUuidMesera();
    int getMinutosEntregaCocina();
    int getMinutosEntregaNoCocina();
    int getMinutosMesaParalizada();
    int getCodigoProductoTaper();
    
}
