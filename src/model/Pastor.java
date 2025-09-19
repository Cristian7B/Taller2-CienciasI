package model;

public class Pastor {
    private String nombre;
    private int dinero;
    private int creyentes;
    private String oficio;


    public Pastor(int dinero, int creyentes) {
        this.dinero = dinero;
        this.creyentes = creyentes;
    }

    /**
     * Genera un nombre completo aleatorio usando los enums de nombres y apellidos
     * 
     * @return String con el nombre completo (nombre + apellido + apellido)
     */
    public void generarNombreCompleto() {
        this.nombre =  Nombres.obtenerNombreAleatorio() + " " +
                Apellidos.obtenerApellidoAleatorio() + " " +
                Apellidos.obtenerApellidoAleatorio();
    }

    public void generarOficio() {
        this.oficio = OficiosPastores.obtenerOficioAleatorio();
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
