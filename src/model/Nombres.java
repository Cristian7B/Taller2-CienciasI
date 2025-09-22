package model;

import java.util.Random;

/**
 * Enum que contiene los posibles nombres para candidatos
 * 
 * @author crisc
 */
public enum Nombres {
    ANA("Ana"), LUIS("Luis"), CARLOS("Carlos"), MARIA("María"), JUAN("Juan"),
    SOFIA("Sofía"), PEDRO("Pedro"), LUCIA("Lucía"), JORGE("Jorge"), ELENA("Elena"),
    VALENTINA("Valentina"), ANDRES("Andrés"), CAMILA("Camila"), MATEO("Mateo"),
    ISABELLA("Isabella"), SEBASTIAN("Sebastián"), GABRIELA("Gabriela"), FELIPE("Felipe"),
    PAULA("Paula"), RICARDO("Ricardo"), DANIELA("Daniela"), TOMAS("Tomás"),
    FERNANDA("Fernanda"), ALEJANDRO("Alejandro"), CAROLINA("Carolina"), MARTIN("Martín"),
    JOSEFINA("Josefina"), ESTEBAN("Esteban"), PATRICIA("Patricia"), MANUEL("Manuel"),
    ADRIANA("Adriana"), CRISTIAN("Cristian"), SIMON("Simón"), CATALINA("Catalina"),
    HECTOR("Héctor"), VERONICA("Verónica"), RAMIRO("Ramiro"), VICTORIA("Victoria"),
    RODRIGO("Rodrigo"), CLAUDIA("Claudia"), DAVID("David"), FLORENCIA("Florencia"),
    IGNACIO("Ignacio"), NATALIA("Natalia"), MAURICIO("Mauricio"), PATRICIO("Patricio"),
    SILVIA("Silvia"), ANGELA("Ángela"), BENJAMIN("Benjamín"), JULIANA("Juliana"),
    RAFAEL("Rafael"), OLGA("Olga"), HERNAN("Hernán"), GLORIA("Gloria"),
    DIEGO("Diego"), LORENA("Lorena"), FABIAN("Fabián"), BEATRIZ("Beatriz"),
    OSCAR("Óscar"), MONICA("Mónica"), EDUARDO("Eduardo"), DANIEL("Daniel"),
    MARIANA("Mariana"), GUILLERMO("Guillermo"), RENATA("Renata"), PABLO("Pablo"),
    ALEJANDRA("Alejandra"), NICOLAS("Nicolás"), ROSA("Rosa"), CRISTINA("Cristina"),
    ARMANDO("Armando"), TERESA("Teresa"), ALBERTO("Alberto"), MARTA("Marta"),
    HUGO("Hugo"), EMILIA("Emilia"), ALVARO("Álvaro"), PAMELA("Pamela"),
    ENRIQUE("Enrique"), NOELIA("Noelia"), EMILIO("Emilio"), CLARA("Clara"),
    CESAR("César"), MARCOS("Marcos"), ELSA("Elsa"), RUTH("Ruth"),
    FRANCISCO("Francisco"), ANDREA("Andrea"), GUSTAVO("Gustavo"), REBECA("Rebeca"),
    JULIAN("Julián"), MIRANDA("Miranda"), LEANDRO("Leandro"), TATIANA("Tatiana"),
    SAMUEL("Samuel"), CAMILO("Camilo"), JIMENA("Jimena"), ISMAEL("Ismael"),
    SUSANA("Susana"), LUCAS("Lucas"), ANTONIA("Antonia"), BRUNO("Bruno"),
    NICOLE("Nicole"), ALIDA("Álida"), CRISTOBAL("Cristóbal"), VANESA("Vanesa"),
    LEONARDO("Leonardo"), PILAR("Pilar"), FEDERICO("Federico");

    private final String nombre;

    Nombres(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene un nombre aleatorio del enum
     * 
     * @return String con un nombre aleatorio
     */
    public static String obtenerNombreAleatorio() {
        Nombres[] nombres = values();
        Random random = new Random();
        return nombres[random.nextInt(nombres.length)].getNombre();
    }

    @Override
    public String toString() {
        return nombre;
    }
}
