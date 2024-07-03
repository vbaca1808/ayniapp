package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanySunatDto {
    
    private String ruc;
    private String razonSocial;
    private String nombreComercial;
    private AdressSunatDto address; 
    
}
