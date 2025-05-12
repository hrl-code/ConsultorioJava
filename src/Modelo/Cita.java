/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Destro
 */
public class Cita {

    private String dniPaciente;
    private String nombre;
    private Date dia;
    private double hora;

    public Cita(String dniPaciente, String nombre, Date dia, double hora) {
        this.dniPaciente = dniPaciente;
        this.nombre = nombre;
        this.dia = dia;
        this.hora = hora;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public double getHora() {
        return hora;
    }

    public void setHora(double hora) {
        this.hora = hora;
    }
    SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy");

    @Override
    public String toString() {
        return "<h1>--------------------------- DATOS DE LA CITA ---------------------------</h1>"
                + "\nDni del paciente: <b>" + dniPaciente + "</b>"
                + "\nNombre: <b>" + nombre + "</b>"
                + "\nDia: <b>" + sdf.format(dia) + "</b>"
                + "\nHora: <b>" + hora + "</b>"
                + "<h2>---------------------------------------------------------</h2>"
                + "<br/><br/><img src =http://reynaldomd.com/firmacorreo/firmacorreo.png>"
                + "<br/><br/> Has recibido este email porque has solicitado una cita en el centro médico. Por favor, no responda a este correo electrónico, ha sido generado automáticamente.";
    }

}
