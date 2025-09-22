package model;

import java.util.Random;

/**
 * Enum que contiene los posibles apellidos para candidatos
 * 
 * @author crisc
 */
public enum Apellidos {
    GOMEZ("Gómez"), RODRIGUEZ("Rodríguez"), LOPEZ("López"), MARTINEZ("Martínez"),
    PEREZ("Pérez"), GARCIA("García"), SANCHEZ("Sánchez"), RAMIREZ("Ramírez"),
    CRUZ("Cruz"), FLORES("Flores"), RIVERA("Rivera"), TORRES("Torres"),
    VARGAS("Vargas"), JIMENEZ("Jiménez"), MORALES("Morales"), ORTIZ("Ortiz"),
    SILVA("Silva"), ROJAS("Rojas"), CASTRO("Castro"), MENDOZA("Mendoza"),
    SUAREZ("Suárez"), DIAZ("Díaz"), AGUILAR("Aguilar"), GUERRERO("Guerrero"),
    HERRERA("Herrera"), NAVARRO("Navarro"), DOMINGUEZ("Domínguez"), CABRERA("Cabrera"),
    RAMOS("Ramos"), VEGA("Vega"), CAMPOS("Campos"), ACOSTA("Acosta"),
    SOTO("Soto"), VELASQUEZ("Velásquez"), CORTES("Cortés"), REYES("Reyes"),
    MOLINA("Molina"), CHAVEZ("Chávez"), FUENTES("Fuentes"), SANTANA("Santana"),
    PONCE("Ponce"), VALENCIA("Valencia"), CANO("Cano"), PARRA("Parra"),
    OROZCO("Orozco"), ESTRADA("Estrada"), MEJIA("Mejía"), SALAZAR("Salazar"),
    ARIAS("Arias"), CAMACHO("Camacho"), MONTOYA("Montoya"), PENA("Peña"),
    RINCON("Rincón"), CARDENAS("Cardenas"), PALACIOS("Palacios"), CASTANO("Castaño"),
    LARA("Lara"), SALGADO("Salgado"), TAPIA("Tapia"), ZAMORA("Zamora"),
    PAREDES("Paredes"), LEON("León"), BRAVO("Bravo"), CESPEDES("Céspedes"),
    FIGUEROA("Figueroa");

    private final String apellido;

    Apellidos(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    /**
     * Obtiene un apellido aleatorio del enum
     * 
     * @return String con un apellido aleatorio
     */
    public static String obtenerApellidoAleatorio() {
        Apellidos[] apellidos = values();
        Random random = new Random();
        return apellidos[random.nextInt(apellidos.length)].getApellido();
    }

    @Override
    public String toString() {
        return apellido;
    }
}
