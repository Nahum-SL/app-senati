package com.example.iniciar_sesion;

public class Alumno {

    private int id;
    private String apellidos;
    private String nombres;
    private String telefono;
    private String direccion;
    private String email;

    public Alumno(int id, String apellidos, String nombres, String telefono, String direccion, String email) {
        this.id = id;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    public Alumno(String apellidos, String nombres, String telefono, String direccion, String email) {
        this.id = id;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEmail() {
        return email;
    }
}
