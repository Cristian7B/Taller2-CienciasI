package controller;

import view.JuegoFrame;

public class ControllerVista {
    private Controller controller;
    private JuegoFrame juegoFrame;

    public ControllerVista(Controller controller) {
        this.controller = controller;
        this.juegoFrame = new JuegoFrame();
    }

    public void mostrarJuego() {
        juegoFrame.setVisible(true);
    }

    public String pedirJugadores() {
        return juegoFrame.cantidadJugadores();
    }

    public void mostrarMensaje(String mensaje) {
        juegoFrame.mostrarMensaje(mensaje);
    }

    
}