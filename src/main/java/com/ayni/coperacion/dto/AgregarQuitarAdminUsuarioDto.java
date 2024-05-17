package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgregarQuitarAdminUsuarioDto {
    
    private int idNegocio;
    private int idUsuario;
    private String nombreUsuario;
    private int isAdmin;
    private int admitir;
    private String detalleCargoPermitidos;

}
