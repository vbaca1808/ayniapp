package com.ayni.coperacion.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MyScheduledTasks {
 
    @Scheduled(cron = "0 0/5 * * * *") // Se ejecuta cada hora
    public void sendHourlyEmail() {
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
        System.out.println("Ejecuto EJECUTO EJCUTO");
    }

}
