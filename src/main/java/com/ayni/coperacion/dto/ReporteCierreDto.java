package com.ayni.coperacion.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteCierreDto {
    
    private int idNegocio;

    private int anioSeleccionado;
    private int mesSeleccionado;
    private int diaSeleccionado;

    private int anioSeleccionadoHasta;
    private int mesSeleccionadoHasta;
    private int diaSeleccionadoHasta;

    private String numeroCelular;
    private String nombreUsuario;

}
