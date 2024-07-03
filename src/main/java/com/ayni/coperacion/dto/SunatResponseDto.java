package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SunatResponseDto {
    
    private boolean success;
    private String cdrZip;
    private CdrResponseDto cdrResponse;
}
