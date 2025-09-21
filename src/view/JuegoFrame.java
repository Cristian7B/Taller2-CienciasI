package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import model.Pastor;


public final class JuegoFrame extends JFrame {

    private final JLabel infoJugador; // etiqueta para mostrar datos
    private final JPanel panelMesa; // panel para la mesa y pila
    private JButton btnAccion1;
    private JButton btnAccion2;
    private JButton btnAccion3;
    

    public JuegoFrame() {
        setTitle("Mesa de Pastores");
        setSize(1200, 700);
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

        btnAccion1 = new JButton("⚔️ Atacar");
        btnAccion2 = new JButton("✝️ Resucitar");
        btnAccion3 = new JButton("🛡️ Robar");

        for (JButton btn : Arrays.asList(btnAccion1, btnAccion2, btnAccion3)) {
            btn.setBackground(new Color(200, 200, 200));
            btn.setFont(emojiFont);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }

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

    public String direccionJuego() {
        String[] opciones = {"Izquierda", "Derecha"};
        int seleccion = JOptionPane.showOptionDialog(null,
                "Seleccione la dirección del conteo:",
                "Dirección del conteo",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);
        return seleccion == 0 ? "izquierda" : "derecha";
    }

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

    public void setAccionAtacar(ActionListener listener) {
        btnAccion1.addActionListener(listener);
    }

    public void setAccionResucitar(ActionListener listener) {
        btnAccion2.addActionListener(listener);
    }

    public void setAccionRobar(ActionListener listener) {
        btnAccion3.addActionListener(listener);
    }

}