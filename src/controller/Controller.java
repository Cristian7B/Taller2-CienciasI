package controller;

public final class Controller {
    private ControllerVista controllerVista;
    private ControllerPastor controllerPastor;
    private ListaCircularDoble<Pastor> pastorList;
    
    public Controller() {
        this.controllerVista = new ControllerVista(this);
        this.controllerPastor = new ControllerPastor(this);
        this.pastorList = new ListaCircularDoble<>();
        run();
    }

    public void run() {
        int numJugadores = validarJugadores();
        controllerPastor.crearListaPastores(numJugadores);
        controllerVista.mostrarJuego();
    }


    public int validarJugadores() {
        int numJugadoresInt;
        String numJugadores = controllerVista.pedirJugadores();

        if(numJugadores == null || numJugadores.isEmpty()) {
            controllerVista.mostrarMensaje("Número inválido. Por favor, ingrese un número entre 2 y 9.");
        }
        
        try {
            numJugadoresInt = Integer.parseInt(numJugadores);
        } catch (NumberFormatException e) {
            numJugadoresInt = 0;
        }
        
        while (numJugadoresInt < 2 || numJugadoresInt > 9) {
            controllerVista.mostrarMensaje("Entrada inválida. Por favor, ingrese un número entre 2 y 9.");
            numJugadoresInt = validarJugadores();
        }
        
        return numJugadoresInt;
    }

    public ListaCircularDoble<Pastor> getPastorList() {
        return pastorList;
    }

    public void setPastorList(ListaCircularDoble<Pastor> pastorList) {
        this.pastorList = pastorList;
    }
}
