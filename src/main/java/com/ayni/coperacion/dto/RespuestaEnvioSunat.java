package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespuestaEnvioSunat {
    
    private String xml;
    private String hash;
    private SunatResponseDto sunatResponse;

}
