package com.ayni.coperacion.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfiguracionNegocioDto {
    
    private int idNegocio; 
    private String nombreNegocio; 
    private String descripcion; 
    private String rucEmpresa;
    private String razonSocial;
    private String direccion;
    private BigDecimal porcentajeIgv;
    private String logo;
    private int estadoNegocio;
	private int rubroNegocio;
	private int usarLectorBarraBusquedaManual;
	private int envioPlatoDirectoACocina;
	private int generarComprobanteVenta;
	private int usarCorrelativoAutomatico;
	private int pedirNombreClientePedidosParaLlevar;
	private int pedirMarcajePersonal;
	private String correoElectronico;
	private String correlativos;
	private String grupoProductos;
	private String cocinas;
	private String direccionBluetoothCocina;
	private String direccionBluetoothMesero;
	private String uuidCocina;
	private String uuidMesero;
	private int minutosEntregaCocina;
	private int minutosEntregaNoCocina;
	private int minutosMesaParalizada;
	private int codigoProductoTaper;
    
}
