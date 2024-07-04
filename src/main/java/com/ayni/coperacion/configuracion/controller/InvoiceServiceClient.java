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
import com.ayni.coperacion.dto.EnvioFacturaSunatDto;
import com.ayni.coperacion.dto.RespuestaEnvioSunat;
import com.google.gson.Gson;

public class InvoiceServiceClient { 

    private RestTemplate restTemplate;

    public RespuestaEnvioSunat sendInvoice(EnvioBoletaSunat envioBoletaSunat, EnvioFacturaSunatDto envioFacturaSunatDto, String token, String apiUrl) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        // Crear el cuerpo de la solicitud
        Gson gson = new Gson();
        String jsonEnvioBoleta = (envioBoletaSunat != null?gson.toJson(envioBoletaSunat):gson.toJson(envioFacturaSunatDto));

        restTemplate = new RestTemplate(); 
        System.out.println("Cuerpo - > " + jsonEnvioBoleta);
        HttpEntity<String> entity = new HttpEntity<>(jsonEnvioBoleta, headers);

        System.out.println("token - > " + token);
        System.out.println("api - > " + apiUrl);

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
