package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanySunatDto {
    
    private int ruc;
    private String razonSocial;
    private String nombreComercial;
    private AdressSunatDto address; 
    
}
