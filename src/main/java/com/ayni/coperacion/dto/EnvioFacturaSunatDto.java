package com.ayni.coperacion.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnvioFacturaSunatDto {

    private String ublVersion;
    private String fecVencimiento;
    private String tipoOperacion;
    private String tipoDoc;
    private String serie;
    private String correlativo;
    private String fechaEmision;
    private FormaPagoDtoSunat formaPago;
    private String tipoMoneda;
    private ClientDtoSunat client;
    private CompanySunatDto company;
    private BigDecimal mtoOperGravadas;
    private BigDecimal mtoOperExoneradas;
    private BigDecimal mtoIGV;
    private BigDecimal valorVenta;
    private BigDecimal totalImpuestos;
    private BigDecimal subTotal;
    private BigDecimal mtoImpVenta;
    private List<DetailsSunatDto> details;
    private List<LegendsSunatDto> legends;
 
}
