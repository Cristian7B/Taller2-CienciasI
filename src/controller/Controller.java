package controller;

import view.JuegoFrame;

public final class Controller {


    private final JuegoFrame juegoFrame;

    
    public Controller() {
        this.juegoFrame = new JuegoFrame();
        run();
    }

    public void run() {
        int numJugadores = validarJugadores();
        crearListaPastores(numJugadores);
        mostrarJuego();
    }


    public int validarJugadores() {
        int numJugadoresInt;
        String numJugadores = pedirJugadores();
        if(numJugadores == null || numJugadores.isEmpty()) {
            juegoFrame.mostrarMensaje("Número inválido. Por favor, ingrese un número entre 2 y 9.");
        }
        try {
            numJugadoresInt = Integer.parseInt(numJugadores);
        } catch (NumberFormatException e) {
            numJugadoresInt = 0;
        }
        while (numJugadoresInt < 2 || numJugadoresInt > 9) {
            juegoFrame.mostrarMensaje("Entrada inválida. Por favor, ingrese un número entre 2 y 9.");
            numJugadoresInt = validarJugadores();
        }
        llenarPastores(numJugadoresInt);
        return numJugadoresInt;
    }

    private void llenarPastores(int n) {
        for (int i = 1; i <= n; i++) {
            
        }
    }

    public void crearListaPastores(int n) {
        
    }

    public void mostrarJuego() {
        juegoFrame.setVisible(true);
    }

    private String pedirJugadores() {
        return juegoFrame.cantidadJugadores();
    }


    public JuegoFrame getJuegoFrame() {
        return juegoFrame;
    }


}
