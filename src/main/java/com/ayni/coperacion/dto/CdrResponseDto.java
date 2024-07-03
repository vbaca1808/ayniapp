package com.ayni.coperacion.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CdrResponseDto {
    private String id;
    private String code;
    private String description;
    private List<Object> notes;

}
