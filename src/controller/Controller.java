package controller;

import view.JuegoFrame;

public class Controller {


    private final JuegoFrame juegoFrame;

    
    public Controller() {
        this.juegoFrame = new JuegoFrame();

    }


    public JuegoFrame getJuegoFrame() {
        return juegoFrame;
    }


}
