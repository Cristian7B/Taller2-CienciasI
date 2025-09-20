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
    

    public JuegoFrame() {
        setTitle("Mesa de Pastores");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Datos de prueba

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
    public void actualizarMesaYPila(ArrayList<Pastor> pastores, ArrayList<Pastor> pila) {
        panelMesa.removeAll();
        MesaPanel mesaPanel = new MesaPanel(pastores, pila);
        mesaPanel.setBackground(new Color(0, 102, 51));
        panelMesa.add(mesaPanel, BorderLayout.CENTER);
        panelMesa.revalidate();
        panelMesa.repaint();
    }

    public String cantidadJugadores() {
        String input = JOptionPane.showInputDialog(null,
        "Ingrese la cantidad de jugadores:",
        "Cantidad de jugadores",
        JOptionPane.QUESTION_MESSAGE);
        return input;
    }

     //  Ejemplo de acción: mover jugador de la mesa a la pila para usar en controlador
    /*
      * public void enviarAPila(Pastor p) {
        pastores.remove(p);
        pila.add(p);
        actualizarMesaYPila();
    }

    public void resucitarDePila() {
        if (!pila.isEmpty()) {
            Pastor p = pila.remove(pila.size() - 1); // LIFO
            pastores.add(p);
            actualizarMesaYPila();
        }
    }
    

    //  Ejemplo de acción: resucitar jugador de la pila para usar en controlador

     */

    // Método para actualizar la info del turn
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