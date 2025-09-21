package controller;

import model.ListaCircularDoble;
import model.Pastor;
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

    public void actualizarMesaYPila(java.util.ArrayList<Pastor> pastores, java.util.ArrayList<Pastor> pila) {
        juegoFrame.actualizarMesaYPila(pastores, pila);
    }

    public void mostrarTurno(Pastor pastor) {
        juegoFrame.mostrarTurno(pastor);
    }

    public String pedirDireccion() {
        return juegoFrame.direccionJuego();
    }

    public ListaCircularDoble<Pastor> getListaPastores() {
        return controller.getPastorList();
    }


    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public JuegoFrame getJuegoFrame() {
        return juegoFrame;
    }

    public void setJuegoFrame(JuegoFrame juegoFrame) {
        this.juegoFrame = juegoFrame;
    }
    

    

    
}