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

    /** Controlador del juego, encargado de la lógica del juego (turnos, eliminación, etc). */
    private ControllerJuego controllerJuego;

    private Pastor turnoActual;

    /** Lista circular doble que contiene a los pastores activos en el juego. */
    private ListaCircularDoble<Pastor> pastorList;

    /** Dirección actual del turno ("izquierda" o "derecha"). */
    private String direccion;

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
        this.controllerJuego = new ControllerJuego(pastorList, pila);
        this.direccion = ""; // Dirección inicial por defecto
        this.turnoActual = null;
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

        System.out.println("Lista circular creada con " + pastorList.getTamanno() + " pastores.");
        llenarListaAuxiliar();
        turnoActual = pastorMasRico();
        controllerVista.mostrarTurno(turnoActual);
        controllerVista.mostrarMensaje("\"El pastor más rico es: " + turnoActual.getNombre() + " con " + turnoActual.getDinero() + " monedas.");

        
        controllerVista.actualizarMesaYPila(pastores, pila);
        controllerVista.mostrarJuego();
        direccion = controllerVista.pedirDireccion();

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

    public boolean validarPilaVacia() {
        if (pila.isEmpty()) {
            controllerVista.mostrarMensaje("La pila está vacía. No se puede resucitar a ningún pastor.");
            return true;
        } else {
            return false;
        }
    }

    /*
     * Obtiene el pastor más pobre de la mesa.
     * @return Pastor con menos recursos
     * 
     */
    public Pastor pastorMasPobre() {
        return controllerJuego.obtenerMasPobre();
    }

    /*
     * Obtiene el pastor más rico de la mesa.
     * @return Pastor con más recursos
     * 
     */
    public Pastor pastorMasRico() {
        return controllerJuego.obtenerMasRico(pastorList);
    }

    /*
     * El pastor más pobre roba un tercio de los recursos al más rico.
     * @param pobre Pastor más pobre
     * @param rico Pastor más rico
     * 
     */
    public void eliminarVecino(int pasos) {
        if(validarPilaVacia()){
            System.out.println("turn actual: " + turnoActual.getNombre() + ", posición: " + turnoActual.getPosicion() + ", pasos: " + pasos + ", dirección: " + direccion);
            controllerJuego.eliminarPastorMenosFeligreses(turnoActual, direccion, pasos);
        }else{
            controllerJuego.eliminarVecino(turnoActual, direccion, pasos);
        }
        llenarListaAuxiliar();
        cambioDeTurno();
        controllerVista.actualizarMesaYPila(pastores, pila);
    }

    /*
     * Resucita al último pastor de la pila y lo añade a la lista circular.
     * Si la pila está vacía, muestra un mensaje de error.
     * @return true si se resucitó un pastor, false si la pila estaba vacía.
     */
    public void rescatarDePila() {
        if(!validarPilaVacia()){
            controllerJuego.resucitarDesdePila(turnoActual);
            controllerVista.mostrarMensaje("El pastor " + turnoActual.getNombre() + " ha sido resucitado.");
            controllerVista.actualizarMesaYPila(pastores, pila);
            cambioDeTurno();
        }
    }

    /*
     * Resucita al último pastor de la pila, dándole la mitad
     * de los recursos del pastor actual.
     *
     * @param actual Pastor que decide resucitar
     */
    public void resucitarDePila() {
        controllerJuego.resucitarDesdePila(turnoActual);
    }

    public void robarRicoAPobre() {
        if(turnoActual.equals(pastorMasPobre())){
            controllerJuego.robarUnTercio(pastorMasPobre(), pastorMasRico());
            controllerVista.actualizarMesaYPila(pastores, pila);
            cambioDeTurno();
        }else{
            controllerVista.mostrarMensaje("Solo el pastor más pobre puede robar.");
        }
        
    }

    
    /*
     * Cambia el turno al siguiente pastor según la dirección actual.
     * Actualiza la vista para mostrar el nuevo turno.
     */
    public void cambioDeTurno(){
        if (direccion.equals("derecha")) {
            turnoActual = pastorList.obtenerSiguiente(turnoActual);
        } else {
            turnoActual = pastorList.obtenerAnterior(turnoActual);
        }
        controllerVista.mostrarTurno(turnoActual);
    }

    public void llenarListaAuxiliar(){
        pastores.clear(); // Limpiar la lista antes de llenarla

        // Llenar la lista auxiliar para la vista
        System.out.println("tamaño lista circular: " + pastorList.getTamanno());
        for (int i = 0; i < pastorList.getTamanno(); i++) {
            pastores.add(pastorList.obtenerPastorPorPosicion(i));
            System.out.println("Pastor en lista auxiliar: " + pastores.get(i).getNombre());
        }

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

    /**
     * @return el controlador del juego.
     */
    public ControllerJuego getControllerJuego() {
        return controllerJuego;
    }

    /**
     * @param controllerJuego el controlador del juego a establecer.
     */
    public void setControllerJuego(ControllerJuego controllerJuego) {
        this.controllerJuego = controllerJuego;
    }

    /*
     * @return la dirección actual del juego ("izquierda" o "derecha").
     */
    public String getDireccion() {
        return direccion;
    }

    /*
     * @param direccion la dirección del juego a establecer ("izquierda" o "derecha").
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /*
     * @return el pastor cuyo turno es actualmente.
     */
    public Pastor getTurnoActual() {
        return turnoActual;
    }

    /*
     * @param turnoActual el pastor al que se le asigna el turno actual.
     */
    public void setTurnoActual(Pastor turnoActual) {
        this.turnoActual = turnoActual;
    }
}
