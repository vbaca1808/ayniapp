package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ConfiguracionNegocio {
    
    int getId();
    String getNombreNegocio();
    String getDescripcion();
    String getRucEmpresa();
    String getRazonSocial();
    String getDireccion();
    BigDecimal getPorcentajeIgv(); 
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
