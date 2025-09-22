package controller;

import java.util.Random;
import model.Pastor;

public class ControllerPastor {
    private Controller controller;
    
    public ControllerPastor(Controller controller) {
        this.controller = controller;
    }

    public void crearListaPastores(int n) {
        for(int i = 0; i < n; i++) {
            Pastor pastor = new Pastor(new Random().nextInt(1000), new Random().nextInt(500));
            pastor.generarNombreCompleto();
            pastor.generarOficio();
            controller.getPastorList().insertarAlFinal(pastor);
            System.out.println("Creado pastor: " + pastor.getNombre() + " con oficio: " + pastor.getOficio() 
            + ", dinero: " + pastor.getDinero() + ", creyentes: " + pastor.getCreyentes());
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


}