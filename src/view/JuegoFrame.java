package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.*;
import javax.swing.*;
import model.Pastor;

public final class JuegoFrame extends JFrame {

    private final JLabel infoJugador; // etiqueta para mostrar datos
    private final JPanel panelMesa; // panel para la mesa y pila
    private final List<Pastor> pastores;
    private final List<Pastor> pila;

    public JuegoFrame() {
        setTitle("Mesa de Pastores");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Datos de prueba
        pastores = new ArrayList<>();
        pastores.add(new Pastor("Pedro", 50, 20, "cura"));
        pastores.add(new Pastor("Juan", 30, 10, "fraile"));
        pastores.add(new Pastor("Andrés", 70, 25, "monje"));
        pastores.add(new Pastor("Santiago", 40, 15, "cura"));
        pastores.add(new Pastor("Tomás", 60, 30, "fraile"));
        pastores.add(new Pastor("Felipe", 20, 8, "monje"));
        pastores.add(new Pastor("Mateo", 15, 5, "cura"));
        pastores.add(new Pastor("Bartolomé", 25, 12, "fraile"));
        pastores.add(new Pastor("Simón", 35, 18, "monje"));


        pila = new ArrayList<>();
        pila.add(new Pastor("Mateo", 15, 5, "cura"));
        pila.add(new Pastor("Bartolomé", 25, 12, "fraile"));
        pila.add(new Pastor("Simón", 35, 18, "monje"));
        pila.add(new Pastor("Judas", 45, 22, "cura"));
        pila.add(new Pastor("Matías", 55, 28, "fraile"));
        pila.add(new Pastor("Jacobo", 65, 32, "monje"));
        pila.add(new Pastor("Tadeo", 75, 35, "cura"));
        pila.add(new Pastor("Andrés", 85, 40, "fraile"));
        pila.add(new Pastor("Pedro", 95, 45, "monje"));

        // === PANEL DE LA MESA Y LA PILA ===
        panelMesa = new JPanel(new BorderLayout());
        panelMesa.setBackground(new Color(0, 102, 51)); 
        add(panelMesa, BorderLayout.CENTER);

        // === PANEL INFERIOR (info jugador + botones) ===
        JPanel panelInfo = new JPanel(new BorderLayout());
        panelInfo.setBackground(new Color(230, 230, 230));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Info del jugador (izquierda)
        infoJugador = new JLabel("Turno de: ---");
        infoJugador.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(infoJugador, BorderLayout.WEST);

        // Botones (derecha)
        JPanel panelBotones = crearBotones();
        panelInfo.add(panelBotones, BorderLayout.EAST);

        add(panelInfo, BorderLayout.SOUTH);

        // Inicializar mesa y pila
        actualizarMesaYPila();
        mostrarTurno(pastores.get(0));

        setVisible(false);
    }

    

    private JPanel crearBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        Font emojiFont = new Font("Segoe UI Emoji", Font.BOLD, 14);

        JButton btnAccion1 = new JButton("⚔️ Atacar");
        JButton btnAccion2 = new JButton("✝️ Resucitar");
        JButton btnAccion3 = new JButton("🛡️ Robar");

        for (JButton btn : Arrays.asList(btnAccion1, btnAccion2, btnAccion3)) {
            btn.setBackground(new Color(200, 200, 200));
            btn.setFont(emojiFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }

        //  agregar ActionListeners
        panelBotones.add(btnAccion1);
        panelBotones.add(btnAccion2);
        panelBotones.add(btnAccion3);

        return panelBotones;
    }

    // Método para refrescar mesa y pila
    public void actualizarMesaYPila() {
        panelMesa.removeAll();
        MesaPanel mesaPanel = new MesaPanel(pastores, pila);
        mesaPanel.setBackground(new Color(0, 102, 51));
        panelMesa.add(mesaPanel, BorderLayout.CENTER);
        panelMesa.revalidate();
        panelMesa.repaint();
    }

     //  Ejemplo de acción: mover jugador de la mesa a la pila para usar en controlador
    public void enviarAPila(Pastor p) {
        pastores.remove(p);
        pila.add(p);
        actualizarMesaYPila();
    }

    //  Ejemplo de acción: resucitar jugador de la pila para usar en controlador
    public void resucitarDePila() {
        if (!pila.isEmpty()) {
            Pastor p = pila.remove(pila.size() - 1); // LIFO
            pastores.add(p);
            actualizarMesaYPila();
        }
    }


    public String cantidadJugadores() {
        String input = JOptionPane.showInputDialog(null,
                "Ingrese la cantidad de jugadores:",
                "Cantidad de jugadores",
                JOptionPane.QUESTION_MESSAGE);
                return input;
    }

    // Método para actualizar la info del turno
    public void mostrarTurno(Pastor pastor) {
        infoJugador.setText("Turno de: " + pastor.getNombre()
                + " | Riqueza: " + pastor.getDinero()
                + " | Feligreses: " + pastor.getCreyentes()
                + " | Oficio: " + pastor.getOficio());
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }

}