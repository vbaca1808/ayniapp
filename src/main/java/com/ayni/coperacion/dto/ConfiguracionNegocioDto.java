package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfiguracionNegocioDto {
    
    private int idNegocio; 
    private String nombreNegocio; 
    private String descripcion; 
    private String logo;
    private int estadoNegocio;
	private int rubroNegocio;
	private int usarLectorBarraBusquedaManual;
	private int envioPlatoDirectoACocina;
	private int generarComprobanteVenta;
	private int usarCorrelativoAutomatico;
	private int pedirNombreClientePedidosParaLlevar;
	private String correoElectronico;
	private String correlativos;
	private String grupoProductos;
	private String cocinas;
    
}
