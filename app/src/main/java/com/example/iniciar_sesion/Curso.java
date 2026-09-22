package com.example.iniciar_sesion;

public class Curso {
    private int id;
    private String nombre;
    private int horas;
    private double precio;
    private String responsable;
    public Curso(int id, String nombre, int horas, double precio, String responsable) {
        this.id = id;
        this.nombre = nombre;
        this.horas = horas;
        this.precio = precio;
        this.responsable = responsable;
    }

    public Curso(String nombre, int horas, double precio, String responsable) {
        this.nombre = nombre;
        this.horas = horas;
        this.precio = precio;
        this.responsable = responsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}
