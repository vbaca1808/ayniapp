package com.ayni.coperacion.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

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

    //@Scheduled(cron = "0 0 12,15,18,21 * * *")

    @Scheduled(cron = "0 0 12,15,18,21 * * *")
    public void sendHourlyEmail() {
        try {
            
            int[] vNegocios = {}; 
            String vCorreoElectronico = "";

            for (int i = 0; i < vNegocios.length; i++) {
                vCorreoElectronico = "";
                List<ReporteIncidenciasAyni> lstReporteIncidenciasAyni = 
                iUsuarioService.reporteIncidenciasAyni(vNegocios[i]);
                vCorreoElectronico = "";

                if (lstReporteIncidenciasAyni.size() > 0) {
                    if (lstReporteIncidenciasAyni.get(0).getCorreoElectronico() != null && 
                        !lstReporteIncidenciasAyni.get(0).getCorreoElectronico().equals("") && vCorreoElectronico.equals("")) {
                            vCorreoElectronico = lstReporteIncidenciasAyni.get(0).getCorreoElectronico();
                    }

                    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
                    mailSender.setHost("smtp.gmail.com");
                    mailSender.setPort(587);
                    mailSender.setUsername("ayniapp24@gmail.com");
                    mailSender.setPassword("xmxe dvht ergu egki");
            
                    Properties props = mailSender.getJavaMailProperties();
                    props.put("mail.transport.protocol", "smtp");
                    props.put("mail.smtp.auth", "true");
                    props.put("mail.smtp.starttls.enable", "true");
                    props.put("mail.debug", "true");
            
                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);
                    helper.setTo(vCorreoElectronico);
                    helper.addCc("victor.baca.h@gmail.com");
                    helper.setSubject("Reporte de incidencias Ayni " + 
                    (vNegocios[i] == 1?"Restaurante Prueba":
                     vNegocios[i] == 18?"Aldo's":
                     vNegocios[i] == 25?"Cheff Nautico":
                     vNegocios[i] == 26?"Punta Roca":""));
            
                    List<ReporteIncidenciasAyni> lstProductosNoPreparadosMesa = lstReporteIncidenciasAyni.stream()
                    .filter(x -> x.getTipo().equals("A") && x.getCantidad() > 0).collect(Collectors.toList());
                    String htmlBody = "";

                    if (lstProductosNoPreparadosMesa.size() > 0) {
                        htmlBody = "<h1>Atenciones Tard&iacute;as de Productos no preparados para mesa</h1>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Descripci&oacute;n producto</th>"
                        + "<th>Cantidad (Mesa)</th>"
                        + "<th>Cantidad atendida</th>"
                        + "<th>Mesero</th>"
                        + "<th>Hora solicitada</th>"
                        + "<th>Hora atendida</th>"
                        + "<th>Tiempo demorado</th>"
                        + "</tr>";


                        for (int j = 0; j < lstProductosNoPreparadosMesa.size(); j++) {
                            htmlBody = htmlBody + "<tr>"
                            + "<td>" + lstProductosNoPreparadosMesa.get(j).getDescripcionProducto() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosMesa.get(j).getCantidad() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosMesa.get(j).getCantidadAtendida() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosMesa.get(j).getNombreUsuario() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosMesa.get(j).getFechaModificacion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosMesa.get(j).getFechaAtencion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosMesa.get(j).getMinutosDemora() + "</td>"
                            + "</tr>";
                        }

                        htmlBody = htmlBody + "</table>";     
                    }

                    List<ReporteIncidenciasAyni> lstProductosNoPreparadosLlevar = lstReporteIncidenciasAyni.stream()
                    .filter(x -> x.getTipo().equals("A") && x.getCantidadParaLLevar() > 0).collect(Collectors.toList());
                    
                    if (lstProductosNoPreparadosLlevar.size() > 0) {
                        htmlBody = htmlBody + "<h1>Atenciones Tard&iacute;as de Productos no preparados para Llevar</h1>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Descripci&oacute;n producto</th>"
                        + "<th>Cantidad (Llevar)</th>"
                        + "<th>Cantidad atendida para Llevar</th>"
                        + "<th>Mesero</th>"
                        + "<th>Hora solicitada</th>"
                        + "<th>Hora atendida</th>"
                        + "<th>Tiempo demorado</th>"
                        + "</tr>";


                        for (int j = 0; j < lstProductosNoPreparadosLlevar.size(); j++) {
                            htmlBody = htmlBody + "<tr>"
                            + "<td>" + lstProductosNoPreparadosLlevar.get(j).getDescripcionProducto() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosLlevar.get(j).getCantidadParaLLevar() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosLlevar.get(j).getCantidadAtendidaParaLlevar() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosLlevar.get(j).getNombreUsuario() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosLlevar.get(j).getFechaModificacion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosLlevar.get(j).getFechaAtencion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosNoPreparadosLlevar.get(j).getMinutosDemora() + "</td>"
                            + "</tr>";
                        }

                        htmlBody = htmlBody + "</table>";     
                    }

                    List<ReporteIncidenciasAyni> lstProductosPreparadosMesa = lstReporteIncidenciasAyni.stream()
                    .filter(x -> x.getTipo().equals("B") && x.getCantidad() > 0).collect(Collectors.toList());

                    if (lstProductosPreparadosMesa.size() > 0) {
                            
                        htmlBody = htmlBody + "<br><br><h1>Atenciones Tard&iacute;as de Productos preparados para mesa</h1>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Descripci&oacute;n producto</th>"
                        + "<th>Cantidad (Mesa)</th>"
                        + "<th>Cantidad atendida</th>"
                        + "<th>Mesero</th>"
                        + "<th>Hora solicitada</th>"
                        + "<th>Hora atendida</th>"
                        + "<th>Tiempo demorado</th>"
                        + "</tr>";
                        
                        for (int j = 0; j < lstProductosPreparadosMesa.size(); j++) {
                            htmlBody = htmlBody + "<tr>"
                            + "<td>" + lstProductosPreparadosMesa.get(j).getDescripcionProducto() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosMesa.get(j).getCantidad() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosMesa.get(j).getCantidadAtendida() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosMesa.get(j).getNombreUsuario() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosMesa.get(j).getFechaModificacion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosMesa.get(j).getFechaAtencion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosMesa.get(j).getMinutosDemora() + "</td>"
                            + "</tr>";
                        }
                        htmlBody = htmlBody + "</table>";     

                    }

                    List<ReporteIncidenciasAyni> lstProductosPreparadosLlevar = lstReporteIncidenciasAyni.stream()
                    .filter(x -> x.getTipo().equals("B") && x.getCantidadParaLLevar() > 0).collect(Collectors.toList());

                    if (lstProductosPreparadosLlevar.size() > 0) {
                            
                        htmlBody = htmlBody + "<br><br><h1>Atenciones Tard&iacute;as de Productos preparados para llevar</h1>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Descripci&oacute;n producto</th>"
                        + "<th>Cantidad (Mesa)</th>"
                        + "<th>Cantidad atendida</th>"
                        + "<th>Mesero</th>"
                        + "<th>Hora solicitada</th>"
                        + "<th>Hora atendida</th>"
                        + "<th>Tiempo demorado</th>"
                        + "</tr>";
                        
                        for (int j = 0; j < lstProductosPreparadosLlevar.size(); j++) {
                            htmlBody = htmlBody + "<tr>"
                            + "<td>" + lstProductosPreparadosLlevar.get(j).getDescripcionProducto() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosLlevar.get(j).getCantidad() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosLlevar.get(j).getCantidadAtendida() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosLlevar.get(j).getNombreUsuario() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosLlevar.get(j).getFechaModificacion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosLlevar.get(j).getFechaAtencion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstProductosPreparadosLlevar.get(j).getMinutosDemora() + "</td>"
                            + "</tr>";
                        }
                        htmlBody = htmlBody + "</table>";     

                    }

                    List<ReporteIncidenciasAyni> lstMesasParalizadas = lstReporteIncidenciasAyni.stream().filter(x -> x.getTipo().equals("C"))
                    .collect(Collectors.toList());

                    if (lstMesasParalizadas.size() > 0) {
                            
                        htmlBody = htmlBody + "<br><br><h1>Mesas ocupadas y paralizadas</h1>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Mesero</th>"
                        + "<th>Mesa</th>"
                        + "<th>Ultima Atención</th>"
                        + "<th>Fecha Control</th>"
                        + "<th>Minutos paralizados</th>"
                        + "</tr>";
                        
                        for (int j = 0; j < lstMesasParalizadas.size(); j++) {
                            htmlBody = htmlBody + "<tr>"
                            + "<td>" + lstMesasParalizadas.get(j).getNombreUsuario() + "</td>"
                            + "<td style=\"text-align: center;\">" + 
                            (lstMesasParalizadas.get(j).getFechaModificacion().equals("-99")?"Para Llevar":
                            lstMesasParalizadas.get(j).getFechaModificacion().equals("-98")?"Delivery":
                            lstMesasParalizadas.get(j).getFechaModificacion()) + "</td>"
                            + "<td style=\"text-align: center;\">" + lstMesasParalizadas.get(j).getFechaAtencion() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstMesasParalizadas.get(j).getDescripcionProducto() + "</td>"
                            + "<td style=\"text-align: center;\">" + lstMesasParalizadas.get(j).getCantidad() + "</td>" 
                            + "</tr>";
                        }
                        htmlBody = htmlBody + "</table>";     

                    }

                    List<ReporteIncidenciasAyni> lstRendimiento = lstReporteIncidenciasAyni.stream().filter(x -> x.getTipo().equals("D"))
                    .collect(Collectors.toList());

                    if (lstRendimiento.size() > 0) {
                            
                        htmlBody = htmlBody + "<br><br><h1>Pedidos Atendidos por mesera del Mes</h1>"
                        + "<table border=\"1\">"
                        + "<tr>"
                        + "<th>Mesero</th>"
                        + "<th>Cantidad Pedido</th>"
                        + "<th>Tiempo Promedio Atencion</th>"
                        + "</tr>";
                        
                        for (int j = 0; j < lstRendimiento.size(); j++) {
                            BigDecimal vTiempoPromedio = new BigDecimal(lstRendimiento.get(j).getFechaAtencion().replace(",", ""));
                            String vHoras = "";
                            System.out.println("Tiempo Promedio: " + vTiempoPromedio);
                            if (vTiempoPromedio.compareTo(new BigDecimal("60.00")) > 0) {
                                int vCantidadHoras = vTiempoPromedio.divide(new BigDecimal("60.00"),0, RoundingMode.HALF_UP).intValue();
                               
                                if (vCantidadHoras > 0) {
                                    vHoras = vCantidadHoras + " Hora(s)";
                                }

                                vTiempoPromedio = vTiempoPromedio.subtract(new BigDecimal(vCantidadHoras * 60));

                                if (vTiempoPromedio.compareTo(BigDecimal.ZERO) > 0) {
                                    vHoras = vHoras +  " " + vTiempoPromedio + " Minuto(s)";
                                }
                            } else {
                                vHoras = vTiempoPromedio.toString() + " Minutos"; 
                            }

                            htmlBody = htmlBody + "<tr>"
                            + "<td>" + lstRendimiento.get(j).getNombreUsuario() + "</td>"
                            + "<td style=\"text-align: center;\">" + (lstRendimiento.get(j).getFechaModificacion()) + "</td>"
                            + "<td style=\"text-align: center;\">" + vHoras + "</td>"
                            + "</tr>";
                        }

                        htmlBody = htmlBody + "</table>";     

                    }


                    helper.setText(htmlBody, true);
            
                    mailSender.send(message);
                    System.out.println("Correo enviado exitosamente.");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Scheduled(cron = "0 */10 * * * *")
    public void actualizarFechaControl() {
        int[] aNegocio = {1,18,25};

        for (int i = 0; i < aNegocio.length; i++) {
            iUsuarioService.actualizarHoraAtencionControlMesa(aNegocio[i]);            
        }
        
        System.out.println("Fecha control Actualizada");

    }

    @Scheduled(cron = "0 0 4 * * *")
    public void sbActualizarStockInicial() {
        try {
            iUsuarioService.actualizarCorteInventario(new Date());
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
