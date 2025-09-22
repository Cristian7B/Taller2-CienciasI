package model;

import java.util.Random;

/**
 * Enum que contiene los posibles oficios para pastores eclesiásticos
 * 
 * @author crisc
 */
public enum OficiosPastores {
    PASTOR_PRINCIPAL("Pastor Principal"), PASTOR_ASOCIADO("Pastor Asociado"), 
    PASTOR_JOVEN("Pastor de Jóvenes"), PASTOR_NINOS("Pastor de Niños"),
    PASTOR_ADORACION("Pastor de Adoración"), PASTOR_EVANGELISMO("Pastor de Evangelismo"),
    PASTOR_MISIONERO("Pastor Misionero"), PASTOR_ASISTENTE("Pastor Asistente"),
    MINISTRO_MUSICA("Ministro de Música"), MINISTRO_EDUCACION("Ministro de Educación"),
    CAPELLAN("Capellán"), EVANGELISTA("Evangelista"),
    MAESTRO_BIBLIA("Maestro de Biblia"), CONSEJERO_PASTORAL("Consejero Pastoral"),
    DIRECTOR_MINISTERIO("Director de Ministerio"), PASTOR_ANCIANOS("Pastor de Ancianos"),
    PASTOR_FAMILIAS("Pastor de Familias"), PASTOR_DISCIPULADO("Pastor de Discipulado"),
    LIDER_CELULAS("Líder de Células"), COORDINADOR_MINISTERIOS("Coordinador de Ministerios"),
    PASTOR_INTERCESION("Pastor de Intercesión"), MINISTRO_VISITACION("Ministro de Visitación"),
    DIRECTOR_ESCUELA_DOMINICAL("Director de Escuela Dominical"), PASTOR_HOSPITALARIO("Pastor Hospitalario"),
    MINISTRO_BENEVOLENCIA("Ministro de Benevolencia"), LIDER_DIACONOS("Líder de Diáconos"),
    PASTOR_RESTAURACION("Pastor de Restauración"), MINISTRO_COMUNICACIONES("Ministro de Comunicaciones"),
    PASTOR_PLANTACION("Pastor de Plantación de Iglesias"), COORDINADOR_VOLUNTARIOS("Coordinador de Voluntarios");

    private final String oficio;

    OficiosPastores(String oficio) {
        this.oficio = oficio;
    }

    public String getOficio() {
        return oficio;
    }

    /**
     * Obtiene un oficio aleatorio del enum
     * 
     * @return String con un oficio aleatorio
     */
    public static String obtenerOficioAleatorio() {
        OficiosPastores[] oficios = values();
        Random random = new Random();
        return oficios[random.nextInt(oficios.length)].getOficio();
    }

    @Override
    public String toString() {
        return oficio;
    }
}