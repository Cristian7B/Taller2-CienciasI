package controller;

import java.util.ArrayList;
import model.ListaCircularDoble;
import model.Pastor;

/**
 * Clase principal del controlador del juego "Mesa de Pastores".
 * 
 * <Se encarga de coordinar la interacción entre el modelo (lista de pastores, pila)
 * y la vista (interfaz gráfica). Aquí se inicializa el flujo del juego,
 * validando el número de jugadores, creando los pastores y actualizando
 * la vista con la información necesaria.
 * 
 * Sigue el patrón MVC, donde:
 * 
 *   Modelo: {@link Pastor}, {@link ListaCircularDoble}
 *   Vista: {@link ControllerVista}
 *   Controlador:</b> esta clase y {@link ControllerPastor}
 * @author Juan
 */
public final class Controller {

    /** Controlador de la vista, encargado de manejar la interfaz gráfica. */
    private final ControllerVista controllerVista;

    /** Controlador del modelo Pastor, encargado de crear y manipular la lista circular. */
    private final ControllerPastor controllerPastor;

    /** Lista circular doble que contiene a los pastores activos en el juego. */
    private ListaCircularDoble<Pastor> pastorList;

    /** Lista lineal de pastores (para uso directo en la vista). */
    private ArrayList<Pastor> pastores;

    /** Pila de pastores desposeídos (para uso directo en la vista). */
    private ArrayList<Pastor> pila;

    /**
     * Constructor principal del controlador.
     * 
     * Inicializa la vista, el controlador de pastores y las listas internas.
     * Finalmente, ejecuta el flujo principal del juego mediante {@link #run()}.
     */
    public Controller() {
        this.controllerVista = new ControllerVista(this);
        this.controllerPastor = new ControllerPastor(this);
        this.pastorList = new ListaCircularDoble<>();
        this.pastores = new ArrayList<>();
        this.pila = new ArrayList<>();
        run();
    }

    /**
     * Método principal de ejecución del juego.
     * Muestra mensaje de bienvenida.
     *   Pide la cantidad de jugadores al usuario y valida la entrada.
     *   Crea la lista circular de pastores con {@link ControllerPastor}.
     *   Llena la lista auxiliar de {@link #pastores} para la vista.
     *   Actualiza la interfaz gráfica con los jugadores y la pila.
     */
    public void run() {
        controllerVista.mostrarMensaje("¡Bienvenido al juego de Pastores!");
        String jugadores = controllerVista.pedirJugadores();
        while (!validarJugadores(jugadores)) {
            controllerVista.mostrarMensaje("Número inválido de jugadores. Por favor, ingrese un número entre 2 y 6.");
            jugadores = controllerVista.pedirJugadores();
        }
        int numJugadores = Integer.parseInt(jugadores);
        controllerPastor.crearListaPastores(numJugadores);

        for (int i = 0; i < pastorList.getTamanno(); i++) {
            Pastor p = pastorList.obtenerPastorPorPosicion(i);
            pastores.add(p);
        }

        controllerVista.mostrarTurno(pastorMasRico(pastores));
        controllerVista.actualizarMesaYPila(pastores, pila);
        controllerVista.mostrarJuego();
    }

    /**
     * Valida que la entrada del usuario corresponda a un número de jugadores válido.
     *
     * @param numJugadores Cadena ingresada por el usuario.
     * @return {@code true} si el número es un entero válido entre 2 y 6, {@code false} en caso contrario.
     */
    public boolean validarJugadores(String numJugadores) {
        try {
            int num = Integer.parseInt(numJugadores);
            return num >= 2 && num <= 6;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public Pastor pastorMasRico(ArrayList<Pastor> pastores) {
        Pastor masRico = pastores.get(0);
        for (Pastor p : pastores) {
            if (p.getDinero() > masRico.getDinero()) {
                masRico = p;
            }
        }
        controllerVista.mostrarMensaje("El pastor más rico es: " + masRico.getNombre() + " con " + masRico.getDinero() + " monedas.");
        return masRico;
    }

    // ========================
    //        GETTERS/SETTERS
    // ========================

    /**
     * @return la lista circular de pastores activos en el juego.
     */
    public ListaCircularDoble<Pastor> getPastorList() {
        return pastorList;
    }

    /**
     * @param pastorList lista circular de pastores a establecer.
     */
    public void setPastorList(ListaCircularDoble<Pastor> pastorList) {
        this.pastorList = pastorList;
    }

    /**
     * @return el controlador de la vista.
     */
    public ControllerVista getControllerVista() {
        return controllerVista;
    }

    /**
     * @return el controlador de los pastores.
     */
    public ControllerPastor getControllerPastor() {
        return controllerPastor;
    }

    /**
     * @return lista de pastores para ser usada en la vista.
     */
    public ArrayList<Pastor> getPastores() {
        return pastores;
    }

    /**
     * @param pastores lista de pastores a establecer.
     */
    public void setPastores(ArrayList<Pastor> pastores) {
        this.pastores = pastores;
    }

    /**
     * @return pila de pastores desposeídos.
     */
    public ArrayList<Pastor> getPila() {
        return pila;
    }

    /**
     * @param pila pila de pastores a establecer.
     */
    public void setPila(ArrayList<Pastor> pila) {
        this.pila = pila;
    }
}
