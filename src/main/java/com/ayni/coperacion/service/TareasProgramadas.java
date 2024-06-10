package com.ayni.coperacion.service;

import java.util.List;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ayni.coperacion.response.ReporteIncidenciasAyni;

@Component
public class TareasProgramadas {
 
    @Autowired
	private IUsuarioService iUsuarioService;

    @Scheduled(cron = "0 */2 * * * *") 
    public void sendHourlyEmail() {
        try {
            
            int[] vNegocios = {1};

            for (int i = 0; i < vNegocios.length; i++) {
                List<ReporteIncidenciasAyni> lstReporteIncidenciasAyni = 
                iUsuarioService.reporteIncidenciasAyni(vNegocios[i]);

                if (lstReporteIncidenciasAyni.size() > 0) {
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
                    helper.setSubject("Reporte de incidencias Ayni " + vNegocios[i]);
            
                    String htmlBody = "<h1>Confirmaciones Tard&iacute;as de Productos</h1>"
                                    + "<table border=\"1\">"
                                    + "<tr>"
                                    + "<th>Descripci&oacute;n producto</th>"
                                    + "<th>Cantidad (Mesa)</th>"
                                    + "<th>Cantidad atendida</th>"
                                    + "<th>Hora solicitada</th>"
                                    + "<th>Hora atendida</th>"
                                    + "<th>Tiempo demorado</th>"
                                    + "</tr>";

                    for (int j = 0; j < lstReporteIncidenciasAyni.size(); j++) {
                        htmlBody = htmlBody + "<tr>"
                        + "<td>" + lstReporteIncidenciasAyni.get(j).getDescripcionProducto() + "</td>"
                        + "<td>" + lstReporteIncidenciasAyni.get(j).getCantidad() + "</td>"
                        + "<td>" + lstReporteIncidenciasAyni.get(j).getCantidadAtendida() + "</td>"
                        + "<td>" + lstReporteIncidenciasAyni.get(j).getFechaModificacion() + "</td>"
                        + "<td>" + lstReporteIncidenciasAyni.get(j).getFechaAtencion() + "</td>"
                        + "<td>" + lstReporteIncidenciasAyni.get(j).getMinutosDemora() + "</td>"
                        + "</tr>";
                    }

                    htmlBody = htmlBody + "</table>";                    
                    helper.setText(htmlBody, true);
            
                    mailSender.send(message);
                    System.out.println("Correo enviado exitosamente.");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
