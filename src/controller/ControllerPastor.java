package controller;

public class ControllerPastor {
    private Controller controller;
    
    public ControllerPastor(Controller controller) {
        this.controller = controller;
    }

    public void crearListaPastores(int n) {
        for(int i =0; i < n; i++) {
            Pastor pastor = new Pastor(new Random().nextInt(1000), new Random().nextInt(500));
            pastor.generarNombreCompleto();
            controller.getPastorList().insertarFinal(pastor);
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


}
