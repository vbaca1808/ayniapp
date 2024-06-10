package com.ayni.coperacion.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TareasProgramadas {
 
    @Scheduled(cron = "0 0 12,15,18,21 * * *") 
    public void sendHourlyEmail() {
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
    }

}
