package model;

public class Pastor {

    private String nombre;
    private int dinero;
    private int creyentes;
    private String oficio;


    public Pastor(String nombre, int dinero, int creyentes, String oficio) {
        this.nombre = nombre;
        this.dinero = dinero;
        this.creyentes = creyentes;
        this.oficio = oficio;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getDinero() {
        return dinero;
    }
    public void setDinero(int dinero) {
        this.dinero = dinero;
    }
    public int getCreyentes() {
        return creyentes;
    }
    public void setCreyentes(int creyentes) {
        this.creyentes = creyentes;
    }
    public String getOficio() {
        return oficio;
    }
    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

}
