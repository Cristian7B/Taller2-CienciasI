package controller;

import java.util.ArrayList;
import model.ListaCircularDoble;
import model.NodoDoble;
import model.Pastor;

/**
 * Controlador encargado de manejar la lógica principal del juego de Pastores.
 * Define las reglas del turno, eliminación, resurrección, robo y fin de partida.
 */
public class ControllerJuego {

    private ListaCircularDoble<Pastor> pastorList;
    private ArrayList<Pastor> pila;

    /**
     * Constructor que recibe la lista circular de pastores y la pila de eliminados.
     *
     * @param pastorList lista circular doble de los pastores activos en el juego
     * @param pila pila (LIFO) de los pastores eliminados
     */
    public ControllerJuego(ListaCircularDoble<Pastor> pastorList, ArrayList<Pastor> pila) {
        this.pastorList = pastorList;
        this.pila = pila;
    }




    /**
     * Obtiene un vecino a partir de un pastor actual, en cierta dirección y número de pasos.
     *
     * @param actual Pastor actual en turno
     * @param direccion "izquierda" o "derecha"
     * @param pasos número de posiciones a recorrer
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
     * @param actual Pastor que elimina
     * @param direccion dirección del conteo
     * @param pasos número de pasos a recorrer
     */
    public boolean eliminarVecino(Pastor actual, String direccion, int pasos) {
        if(pastorList.estaVacia()){
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
         + " con creyentes: " + nodoActual.getDato().getCreyentes() + ", dinero: " + nodoActual.getDato().getDinero()+"\n");
        
        pastorList.eliminar(nodoActual.getDato());
        pila.add(nodoActual.getDato());
        reorganizarMesa();
        return true;
    }

    /**
     * Elimina al vecino con menos feligreses entre los contados
     * y lo envía a la pila, transfiriendo sus recursos al pastor actual.
     *
     * @param actual Pastor que elimina
     * @param direccion dirección del conteo
     * @param pasos número de pasos a recorrer
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
         + " con creyentes: " + menosFeligreses.getCreyentes() + ", dinero: " + menosFeligreses.getDinero()+"\n");

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

        for(Pastor p : pila) {
            System.out.println("\nPila contiene: " + p.getNombre());
        }

        // Transferimos la mitad de los recursos del pastor actual al resucitado
        int dineroTransferido = actual.getDinero() / 2;
        int creyentesTransferidos = actual.getCreyentes() / 2;

        resucitado.setDinero(resucitado.getDinero() + dineroTransferido);
        resucitado.setCreyentes(resucitado.getCreyentes() + creyentesTransferidos);

        pastorList.insertarAlFinal(resucitado); // lo añadimos de nuevo a la lista
        System.out.println("\nResucitando pastor: " + resucitado.getNombre()
         + " con creyentes: " + resucitado.getCreyentes() + ", dinero: " + resucitado.getDinero()+"\n");
        reorganizarMesa();
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
     * @param rico Pastor más rico
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
     */
    public void reorganizarMesa() {
        // TODO: implementar
    }

    /**
     * Verifica si el juego ha terminado.
     *
     * @return true si queda un solo pastor en la lista, false en otro caso
     */
    public boolean verificarFinJuego() {
        // TODO: implementar
        return false;
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
