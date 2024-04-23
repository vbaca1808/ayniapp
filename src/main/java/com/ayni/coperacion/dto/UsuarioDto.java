package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDto {
    
    private String numeroCelular;
    private String nombreUsuario;
    private int cliente;
    private int colaborador;
    private int actualizar;
    private String codigoVerificacion;

}
