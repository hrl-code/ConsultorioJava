/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import Modelo.Cita;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.apache.commons.mail.DefaultAuthenticator;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Destro
 */
public class UtilidadEmail {

    public static void enviaMailHtml(Cita c, String correo) {

        try {
            HtmlEmail email = new HtmlEmail();

            email.setHostName("smtp.hostinger.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("consultorio@reynaldomd.com", "2024-Consultorio"));
            email.setSSLOnConnect(true);

            email.setCharset("UTF-8");
            email.setFrom("consultorio@reynaldomd.com");
            email.setSubject("Correo enviado automáticamente");

            email.setMsg(c.toString());

            email.addTo(correo);
            email.send();
        } catch (EmailException ex) {
            Logger.getLogger(UtilidadEmail.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Ha habido un error al enviar el correo electrónico");
        }

    }

}
