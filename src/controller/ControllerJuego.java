package controller;

import java.util.ArrayList;
import model.ListaCircularDoble;
import model.NodoDoble;
import model.Pastor;

/**
 * Controlador encargado de manejar la lógica principal del juego de Pastores.
 * Define las reglas del turno, eliminación, resurrección, robo y fin de
 * partida.
 */
public class ControllerJuego {

    private ListaCircularDoble<Pastor> pastorList;
    private ArrayList<Pastor> pila;

    private Controller controller;

    /**
     * Constructor que recibe la lista circular de pastores y la pila de eliminados.
     *
     * @param pastorList lista circular doble de los pastores activos en el juego
     * @param pila       pila (LIFO) de los pastores eliminados
     * @param controller referencia al controlador principal del juego
     */
    public ControllerJuego(ListaCircularDoble<Pastor> pastorList, ArrayList<Pastor> pila, Controller controller) {
        this.pastorList = pastorList;
        this.controller = controller;
        this.pila = pila;
    }

    /**
     * Obtiene un vecino a partir de un pastor actual, en cierta dirección y número
     * de pasos.
     *
     * @param actual    Pastor actual en turno
     * @param direccion "izquierda" o "derecha"
     * @param pasos     número de posiciones a recorrer
     * @return Pastor vecino encontrado
     */
    public Pastor obtenerVecino(Pastor actual, String direccion, int pasos) {
        if (pastorList.estaVacia()) {
            return null;
        }

        NodoDoble<Pastor> nodoActual = pastorList.buscarNodo(actual);
        if (nodoActual == null) {
            return null; // El pastor actual no está en la lista
        }

        // Normalizamos dirección a minúsculas
        direccion = direccion.toLowerCase();

        for (int i = 0; i < pasos; i++) {
            switch (direccion) {
                case "derecha" ->
                    nodoActual = nodoActual.getSiguiente();
                case "izquierda" ->
                    nodoActual = nodoActual.getAnterior();
                default ->
                    throw new IllegalArgumentException("Dirección inválida: use 'derecha' o 'izquierda'");
            }
        }

        return nodoActual.getDato();
    }

    /**
     * Elimina al vecino con menos feligreses entre los contados
     * y lo envía a la pila, transfiriendo sus recursos al pastor actual.
     *
     * @param actual    Pastor que elimina
     * @param direccion dirección del conteo
     * @param pasos     número de pasos a recorrer
     */
    public boolean eliminarVecino(Pastor actual, String direccion, int pasos) {
        if (pastorList.estaVacia()) {
            return false;
        }
        NodoDoble<Pastor> nodoActual = pastorList.buscarNodo(actual);
        if (nodoActual == null) {
            return false; // El pastor actual no está en la lista
        }

        // Normalizamos dirección a minúsculas
        direccion = direccion.toLowerCase();

        for (int i = 0; i < pasos; i++) {
            switch (direccion) {
                case "derecha" ->
                    nodoActual = nodoActual.getSiguiente();
                case "izquierda" ->
                    nodoActual = nodoActual.getAnterior();
                default ->
                    throw new IllegalArgumentException("Dirección inválida: use 'derecha' o 'izquierda'");
            }
        }
        System.out.println("\nEliminando vecino: " + nodoActual.getDato().getNombre()
                + " con creyentes: " + nodoActual.getDato().getCreyentes() + ", dinero: "
                + nodoActual.getDato().getDinero() + "\n");

        pastorList.eliminar(nodoActual.getDato());
        pila.add(nodoActual.getDato());
        reorganizarMesa();
        return true;
    }

    /**
     * Elimina al vecino con menos feligreses entre los contados
     * y lo envía a la pila, transfiriendo sus recursos al pastor actual.
     *
     * @param actual    Pastor que elimina
     * @param direccion dirección del conteo
     * @param pasos     número de pasos a recorrer
     */
    public void eliminarPastorMenosFeligreses(Pastor actual, String direccion, int pasos) {
        if (pastorList.estaVacia()) {
            return; // no hay nada que hacer
        }

        NodoDoble<Pastor> nodoActual = pastorList.buscarNodo(actual);
        if (nodoActual == null) {
            return; // el pastor actual no está en la lista
        }

        direccion = direccion.toLowerCase();

        // Inicializamos con el primer vecino en la dirección indicada
        NodoDoble<Pastor> candidato = (direccion.equals("derecha"))
                ? nodoActual.getSiguiente()
                : nodoActual.getAnterior();

        Pastor menosFeligreses = candidato.getDato();

        // Recorremos hasta "pasos" vecinos
        for (int i = 1; i < pasos; i++) {
            candidato = (direccion.equals("derecha"))
                    ? candidato.getSiguiente()
                    : candidato.getAnterior();

            Pastor p = candidato.getDato();
            if (p.getCreyentes() < menosFeligreses.getCreyentes()) {
                System.out.println("\nComparando: " + p.getNombre() + " (" + p.getCreyentes() + " creyentes) < "
                        + menosFeligreses.getNombre() + " (" + menosFeligreses.getCreyentes() + " creyentes)\n");
                menosFeligreses = p;
            }
        }

        // Transferimos recursos del pastor eliminado al actual
        actual.setCreyentes(actual.getCreyentes() + menosFeligreses.getCreyentes());
        actual.setDinero(actual.getDinero() + menosFeligreses.getDinero());
        System.out.println("\nEliminando pastor: " + menosFeligreses.getNombre()
                + " con creyentes: " + menosFeligreses.getCreyentes() + ", dinero: " + menosFeligreses.getDinero()
                + "\n");

        // Eliminamos al que tiene menos creyentes
        pastorList.eliminar(menosFeligreses);
        pila.add(menosFeligreses);
        for (Pastor p : pila) {
            System.out.println("Pila contiene: " + p.getNombre());

        }
        reorganizarMesa();
    }

    /**
     * Resucita al último pastor de la pila, dándole la mitad
     * de los recursos del pastor actual.
     *
     * @param actual Pastor que decide resucitar
     */
    public Pastor resucitarDesdePila(Pastor actual) {
        if (pila.isEmpty()) {
            return null; // no hay pastores para resucitar
        }

        Pastor resucitado = pila.remove(pila.size() - 1); // sacamos el último de la pila
        System.out.println("\nSacando de la pila a: " + resucitado.getNombre() + "\n");

        for (Pastor p : pila) {
            System.out.println("\nPila contiene: " + p.getNombre());
        }

        // Transferimos la mitad de los recursos del pastor actual al resucitado
        int dineroTransferido = actual.getDinero() / 2;
        int creyentesTransferidos = actual.getCreyentes() / 2;

        resucitado.setDinero(resucitado.getDinero() + dineroTransferido);
        resucitado.setCreyentes(resucitado.getCreyentes() + creyentesTransferidos);

        pastorList.insertarAlFinal(resucitado); // lo añadimos de nuevo a la lista
        System.out.println("\nResucitando pastor: " + resucitado.getNombre()
                + " con creyentes: " + resucitado.getCreyentes() + ", dinero: " + resucitado.getDinero() + "\n");
        reorganizarMesa();
        controller.getControllerVista().getJuegoFrame().revalidate();
        controller.getControllerVista().getJuegoFrame().repaint();
        return resucitado;
    }

    /**
     * Obtiene el pastor más pobre de la mesa.
     *
     * @return Pastor con menos recursos
     */
    public Pastor obtenerMasPobre() {
        Pastor masPobre = pastorList.obtenerMasRico((p1, p2) -> Integer.compare(p2.getDinero(), p1.getDinero()));
        return masPobre;
    }

    /**
     * Obtiene el pastor más rico de la mesa.
     *
     * @return Pastor con más recursos
     */
    public Pastor obtenerMasRico(ListaCircularDoble<Pastor> pastorList) {
        Pastor masRico = pastorList.obtenerMasRico((p1, p2) -> Integer.compare(p1.getDinero(), p2.getDinero()));
        return masRico;
    }

    /**
     * El pastor más pobre roba un tercio de los recursos al más rico.
     *
     * @param pobre Pastor más pobre
     * @param rico  Pastor más rico
     */
    public void robarUnTercio(Pastor pobre, Pastor rico) {
        if (pobre == null || rico == null) {
            return; // No hay pastores para robar
        }

        int dineroRobado = rico.getDinero() / 3;
        int creyentesRobados = rico.getCreyentes() / 3;

        rico.setDinero(rico.getDinero() - dineroRobado);
        rico.setCreyentes(rico.getCreyentes() - creyentesRobados);

        pobre.setDinero(pobre.getDinero() + dineroRobado);
        pobre.setCreyentes(pobre.getCreyentes() + creyentesRobados);
    }

    /**
     * Reorganiza la mesa para asegurar que a la derecha de un pastor
     * no se encuentre otro con el mismo oficio.
     * Utiliza un algoritmo similar al ordenamiento por inserción:
     * - Itera con un for hacia adelante
     * - Utiliza un while hacia atrás para reposicionar pastores cuando es necesario
     */
    public void reorganizarMesa() {
        System.out.println("Reorganizando mesa...");

        if (pastorList.estaVacia() || pastorList.getTamanno() <= 1) {
            System.out.println("No hay suficientes pastores para reorganizar");
            return;
        }

        int maxIntentos = pastorList.getTamanno() * 2; 
        int intentosRealizados = 0;
        boolean hayConflictos = true;

        while (hayConflictos && intentosRealizados < maxIntentos) {
            hayConflictos = false;
            intentosRealizados++;

            NodoDoble<Pastor> nodoActual = pastorList.getCabeza();
            int tamanio = pastorList.getTamanno();

            for (int i = 0; i < tamanio && !hayConflictos; i++) {
                NodoDoble<Pastor> pastorActual = nodoActual;
                NodoDoble<Pastor> pastorDerecha = pastorActual.getSiguiente();

                if (pastorActual.getDato().getOficio().equals(pastorDerecha.getDato().getOficio())) {
                    System.out.println("Conflicto encontrado: " + pastorActual.getDato().getNombre()
                            + " (" + pastorActual.getDato().getOficio() + ") tiene el mismo oficio que "
                            + pastorDerecha.getDato().getNombre() + " (" + pastorDerecha.getDato().getOficio() + ")");

                    hayConflictos = true;

                    Pastor pastorConflictivo = pastorDerecha.getDato();
                    pastorList.eliminar(pastorConflictivo);

                    NodoDoble<Pastor> posicionInsercion = pastorActual;
                    boolean posicionEncontrada = false;
                    int pasosBusqueda = 0;

                    while (!posicionEncontrada && pasosBusqueda < pastorList.getTamanno()) {
                        posicionInsercion = posicionInsercion.getAnterior();
                        pasosBusqueda++;

                        String oficioAnterior = posicionInsercion.getDato().getOficio();
                        String oficioSiguiente = posicionInsercion.getSiguiente().getDato().getOficio();

                        if (!pastorConflictivo.getOficio().equals(oficioAnterior) &&
                                !pastorConflictivo.getOficio().equals(oficioSiguiente)) {
                            posicionEncontrada = true;
                            System.out.println("Posición adecuada encontrada después de "
                                    + posicionInsercion.getDato().getNombre());
                        }
                    }

                    if (posicionEncontrada) {
                        pastorList.insertarDespuesDe(posicionInsercion.getDato(), pastorConflictivo);
                        System.out.println("Pastor " + pastorConflictivo.getNombre() + " reubicado exitosamente");
                    } else {
                        pastorList.insertarAlFinal(pastorConflictivo);
                        System.out.println(
                                "Pastor " + pastorConflictivo.getNombre() + " insertado al final como alternativa");
                    }
                    break;
                } else {
                    nodoActual = nodoActual.getSiguiente();
                }
            }
        }
    }

    /**
     * Verifica si el juego ha terminado.
     *
     * @return true si queda un solo pastor en la lista, false en otro caso
     */
    public boolean verificarFinJuego() {
        if (pastorList.getTamanno() == 1) {
            System.out.println("Juego terminado. Ganador: " + pastorList.getCabeza().getDato().getNombre());
            return true;
        }
        return false;
    }

    /**
     * Método para obtener el ganador
     * 
     * @return Pastor ganador
     */
    public Pastor getPastorGanador() {
        if (pastorList.getTamanno() == 1) {
            return pastorList.getCabeza().getDato();
        }
        return null; 
    }

    /**
     * Verifica si existen conflictos de oficios en la mesa
     * (pastores con el mismo oficio sentados uno al lado del otro).
     *
     * @return true si hay conflictos, false si la mesa está correctamente
     *         organizada
     */
    public boolean hayConflictosOficio() {
        if (pastorList.estaVacia() || pastorList.getTamanno() <= 1) {
            return false;
        }

        NodoDoble<Pastor> nodoActual = pastorList.getCabeza();

        for (int i = 0; i < pastorList.getTamanno(); i++) {
            Pastor pastorActual = nodoActual.getDato();
            Pastor pastorDerecha = nodoActual.getSiguiente().getDato();

            if (pastorActual.getOficio().equals(pastorDerecha.getOficio())) {
                return true; // Se encontró un conflicto
            }
            nodoActual = nodoActual.getSiguiente();
        }

        return false; // No se encontraron conflictos
    }

    // Getters y setters opcionales
    public ListaCircularDoble<Pastor> getPastorList() {
        return pastorList;
    }

    public void setPastorList(ListaCircularDoble<Pastor> pastorList) {
        this.pastorList = pastorList;
    }

    public ArrayList<Pastor> getPila() {
        return pila;
    }

    public void setPila(ArrayList<Pastor> pila) {
        this.pila = pila;
    }
}
