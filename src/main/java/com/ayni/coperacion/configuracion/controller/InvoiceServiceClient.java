package com.ayni.coperacion.configuracion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ayni.coperacion.dto.EnvioBoletaSunat;
import com.ayni.coperacion.dto.RespuestaEnvioSunat;
import com.google.gson.Gson;

public class InvoiceServiceClient {
    
    @Value("${invoice.api.url}")
    private String apiUrl;

    @Value("${invoice.api.token}")
    private String apiToken;

    @Autowired
    private RestTemplate restTemplate;

    public RespuestaEnvioSunat sendInvoice(EnvioBoletaSunat envioBoletaSunat) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiToken);

        // Crear el cuerpo de la solicitud
        Gson gson = new Gson();
        String jsonEnvioBoleta = gson.toJson(envioBoletaSunat);

        HttpEntity<String> entity = new HttpEntity<>(jsonEnvioBoleta, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            entity,
            String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return gson.fromJson(response.getBody(), RespuestaEnvioSunat.class);
        } else {
            return null;
        }
    }

}