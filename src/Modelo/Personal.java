/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Destro
 */
public class Personal {

    private int numero_colegiado;
    private String nombre;
    private String apellidso;
    private int telefono;
    private String usuario;
    private String contrasenya;
    private String tipo;

    public Personal(int numero_colegiado, String nombre, String apellidso, int telefono, String usuario, String contrasenya, String tipo) {
        this.numero_colegiado = numero_colegiado;
        this.nombre = nombre;
        this.apellidso = apellidso;
        this.telefono = telefono;
        this.usuario = usuario;
        this.contrasenya = contrasenya;
        this.tipo = tipo;
    }

    public int getNumero_colegiado() {
        return numero_colegiado;
    }

    public void setNumero_colegiado(int numero_colegiado) {
        this.numero_colegiado = numero_colegiado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidso() {
        return apellidso;
    }

    public void setApellidso(String apellidso) {
        this.apellidso = apellidso;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
