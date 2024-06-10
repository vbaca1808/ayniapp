package com.ayni.coperacion.service;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TareasProgramadas {
 
    @Scheduled(cron = "0 */2 * * * *") 
    public void sendHourlyEmail() {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost("smtp.gmail.com");
            mailSender.setPort(587);
            mailSender.setUsername("ayniapp24@gmail.com");
            mailSender.setPassword("wypq niep foyl whiy");
    
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "true");
    
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo("victorbaca2@yahoo.es");
            helper.setSubject("Reporte de incidencias Ayni");
    
            String htmlBody = "<h1>Reporte Ayni</h1>"
                            + "<table border=\"1\">"
                            + "<tr>"
                            + "<th>Columna 1</th>"
                            + "<th>Columna 2</th>"
                            + "<th>Columna 3</th>"
                            + "<th>Columna 4</th>"
                            + "<th>Columna 5</th>"
                            + "</tr>"
                            + "<tr>"
                            + "<td>Dato 1</td>"
                            + "<td>Dato 2</td>"
                            + "<td>Dato 3</td>"
                            + "<td>Dato 4</td>"
                            + "<td>Dato 5</td>"
                            + "</tr>"
                            // Añadir más filas si es necesario
                            + "</table>";
            helper.setText(htmlBody, true);
    
            mailSender.send(message);
            System.out.println("Correo enviado exitosamente.");            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
