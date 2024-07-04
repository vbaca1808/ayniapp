package com.ayni.coperacion.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDtoSunat {
    
    private String tipoDoc;
    private Long numDoc;
    private String rznSocial;
    private AdressSunatDto address;

}
