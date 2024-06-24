package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisponibilidadCuartosDto {
    
    private int idNegocio;
    private int diaConsultaDesde;
    private int mesConsultaDesde;
    private int anioConsultaDesde;
    private int anioConsultaHasta;
    private int mesConsultaHasta;
    private int diaConsultaHasta;
}
