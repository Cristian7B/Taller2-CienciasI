package view;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.Pastor;

public class MesaPanel extends JPanel {
    private final List<Pastor> pastores; // la lista circular
    private final List<Pastor> pila; // la pila de eliminados

    public MesaPanel(List<Pastor> pastores, List<Pastor> pila) {
        this.pastores = pastores;
        this.pila = pila;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int ancho = getWidth();
        int alto = getHeight();

        // === Dibujar la mesa circular (jugadores) ===
        int radio = 220;
        int centroX = ancho / 2 - 150; // lo corremos a la izquierda para dejar espacio a la pila
        int centroY = alto / 2;

        int n = pastores.size();
        for (int i = 0; i < n; i++) {
            double angulo = 2 * Math.PI * i / n - Math.PI / 2; // empieza arriba
            int x = (int) (centroX + radio * Math.cos(angulo));
            int y = (int) (centroY + radio * Math.sin(angulo));

            Pastor p = pastores.get(i);

            // === "Carta" redonda ===
            int size = 80;
            g2d.setColor(new Color(230, 230, 250)); // lila claro
            g2d.fillOval(x - size / 2, y - size / 2, size, size);

            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawOval(x - size / 2, y - size / 2, size, size);

            // === Texto del nombre arriba ===
            g2d.setFont(new Font("Serif", Font.BOLD, 14));
            FontMetrics fm = g2d.getFontMetrics();
            String nombre = p.getNombre();
            int textWidth = fm.stringWidth(nombre);
            g2d.drawString(nombre, x - textWidth / 2, y - size / 2 - 5);

            // === Icono ♟ en el centro ===
            g2d.setFont(new Font("Serif", Font.PLAIN, 24));
            String icono = "♟";
            int iconWidth = g2d.getFontMetrics().stringWidth(icono);
            int iconHeight = g2d.getFontMetrics().getAscent();
            g2d.drawString(icono, x - iconWidth / 2, y + iconHeight / 4);
        }

        // === Dibujar la pila (columna a la derecha) ===
        int baseX = ancho - 150;
        int margenSuperior = 60;
        int margenInferior = 50;

        int espacioTotal = alto - (margenSuperior + margenInferior);
        int alturaBloque = Math.min(40, espacioTotal / Math.max(1, pila.size()));
        int anchoBloque = 100;

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Serif", Font.BOLD, 16));
        g2d.drawString("Pila de desposeídos", baseX - 20, margenSuperior - 20);

        for (int i = 0; i < pila.size(); i++) {
            Pastor p = pila.get(i);
            int y = margenSuperior + i * alturaBloque;

            g2d.setColor(Color.LIGHT_GRAY);
            g2d.fillRect(baseX, y, anchoBloque, alturaBloque - 5);

            g2d.setColor(Color.BLACK);
            g2d.drawRect(baseX, y, anchoBloque, alturaBloque - 5);
            g2d.drawString(p.getNombre(), baseX + 10, y + alturaBloque / 2);
        }
    }
}
