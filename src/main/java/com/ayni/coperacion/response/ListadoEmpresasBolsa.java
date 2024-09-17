package com.ayni.coperacion.response;

import java.math.BigDecimal;

public interface ListadoEmpresasBolsa {
    
    String getCodigoEmpresa();
    String getCodigoCompany();
    String getCodigoDividendos();
    BigDecimal getValorAccionActual();

}
