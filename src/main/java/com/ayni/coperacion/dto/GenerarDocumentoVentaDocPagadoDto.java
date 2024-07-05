package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerarDocumentoVentaDocPagadoDto {
    
    private int idNegocio;
	private int idPedido;
	private int tipoDocumento;
	private String docCliente;
	private String nombreCliente;
	private String direccionCliente;

}
