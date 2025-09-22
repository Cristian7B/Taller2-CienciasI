package controller;

import model.ListaCircularDoble;
import model.Pastor;
import view.JuegoFrame;

public final class ControllerVista {
    private Controller controller;
    private JuegoFrame juegoFrame;
    private final int PASOS;

    public ControllerVista(Controller controller) {
        this.controller = controller;
        this.juegoFrame = new JuegoFrame();
        this.PASOS = 1; // Número fijo de pasos para eliminar vecino
        agregarAcciones();
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

    public void agregarAcciones() {
        juegoFrame.setBtnAtacarListener(e -> {
            controller.eliminarVecino(PASOS); // Eliminar vecino a partir de la posición actual
            if(controller.getControllerJuego().verificarFinJuego()) {
                juegoFrame.mostrarMensaje("Fin del juego. Ganador: " + controller.getControllerJuego().getPastorGanador().getNombre());
                System.exit(0);
            }
        });
        juegoFrame.setBtnResucitarListener(e -> {
            controller.rescatarDePila(); // Resucitar desde pila
            if(controller.getControllerJuego().verificarFinJuego()) {
                juegoFrame.mostrarMensaje("Fin del juego. Ganador: " + controller.getControllerJuego().getPastorGanador().getNombre());
                System.exit(0);
            };
        });
        juegoFrame.setBtnRobarListener(e -> {
            controller.robarRicoAPobre();
            if(controller.getControllerJuego().verificarFinJuego()) {
                juegoFrame.mostrarMensaje("Fin del juego. Ganador: " + controller.getControllerJuego().getPastorGanador().getNombre());
                System.exit(0);
            };
        });
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