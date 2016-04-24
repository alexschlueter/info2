import java.awt.*;
import java.awt.geom.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Einfacher Canvas (Zeichenflaeche) fuer simple Grafiken wie die Visualisierung der Zufallszahlen.
 * @author Aaron Scherzinger
 */
public class ZufallszahlenVisualisierung extends JPanel {

    private final int[] a;

    /**
     * Initialisiere a mit den ersten n Zufallszahlen generiert durch gen
     * @param gen Zufallsgenerator
     * @param n Anzahl generierter Zahlen
     */
    public ZufallszahlenVisualisierung(ZufallszahlenGenerator gen, int n) {
        a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = gen.nextInt();
        }
    }
    /**
     * Diese Funktion wird zur Laufzeit immer dann aufgerufen, wenn der Canvas neu gezeichnet werden muss.
     * @param g Graphics-Objekt zum Zeichnen
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // caste zu Graphics2D um mit double - Genauigkeit zeichnen zu koennen
        final Graphics2D g2 = (Graphics2D) g;

        final int width = getWidth();
        final int height = getHeight();

        // Abstand der Achsen und des Textes zum Fensterrand
        final int margin = 10;

        // Achsenbeschriftungen
        final String xlab = "Durchgang";
        final String ylab = "Zufallszahl";

        // Frage Ausmasse der Beschriftungen ab
        final int xlabWidth = g.getFontMetrics().stringWidth(xlab);
        final double ylabHeight = g.getFontMetrics().getStringBounds(ylab, g).getHeight();

        // Berechne Ausmasse des Bereichs, in dem tatsaechlich Punkte gezeichnet werden
        // 3.0 * margin setzt sich zusammen aus:
        // Abstand der Achse zum Fensterrand + Abstand Text zur Achse + Abstand Text zu Fensterrand
        final double plotWidth = width - 3.0  * margin - xlabWidth;
        final double plotHeight = height - 3.0 * margin - ylabHeight;

        // Markierungen sollen quadratisch sein.
        // Teste zuerst, ob Breite oder Hoehe des Fensters die Quadratgroesse limitiert
        final double testWidth = plotWidth / a.length;
        final double testHeight = plotHeight / 100.0;
        // Quadratgroesse ist minwh
        final double minwh = Math.min(testWidth, testHeight);

        // In der Dimension, die die Quadratgroesse limitiert, werden Quadrate dicht (ohne Luecke) gepackt
        // In der anderen Dimension wird der restliche Platz auf die Luecken aufgeteilt
        final double gapHeight = (plotHeight - 100.0 * minwh) / 100.0;
        final double gapWidth = (plotWidth - a.length * minwh) / a.length;

        // Zeichne Achsen mit Abstand margin zum Fensterrand, lasse Platz fuer Text
        g2.draw(new Line2D.Double(margin, height - margin, width - xlabWidth - 2 * margin, height - margin));
        g2.draw(new Line2D.Double(margin, height - margin, margin, 2 * margin + ylabHeight));

        // Zeichne Achsenbeschriftungen
        g2.drawString(ylab, margin, margin + (int) ylabHeight);
        g2.drawString(xlab, width - xlabWidth - margin, height - margin);

        // Zeichne Quadrate mit Groesse minwh und den zuvor berechneten Luecken
        // In der i-ten Spalte ist der j-te Eintrag genau dann markiert, wenn a[i] = j ist
        for (int i = 0; i < a.length; ++i) {
            g2.fill(new Rectangle2D.Double(margin + i * (minwh + gapWidth) + gapWidth,
                    2 * margin + ylabHeight + (100 - a[i]) * (gapHeight + minwh), minwh, minwh
            ));
        }
    }

    /**
     * Main-Methode, die ein Fenster erzeugt und diesem ein Objekt der Klasse ZufallszahlenVisualisierung hinzufuegt.
     * Ausserdem werden die Zufallsgeneratoren fuer Aufgabenteil b) und c) initialisiert.
     */
    public static void main(String[] args) {
        // Aufgabe b)

        // Inkrement 23
        GenMod gen23 = new GenMod(21, 23, 100, 1);

        // Inkrement 7
        GenMod gen7 = new GenMod(21, 7, 100, 1);

        // Von Java bereitgestellter Generator
        ZufallszahlenGenerator genjava = new GenJava();

        // Aufgabe c)
        // Erstelle alle 95 Zufallsgeneratoren nach der linearen Kongruenzmethode mit Inkrement c = 0, Startwert r0 = 1,
        // Modulus m = 97 und Multiplikatoren 1 < i < 97
        GenMod[] gens = new GenMod[95];
        for (int i = 2; i < 97; ++i) {
            gens[i - 2] = new GenMod(i, 0, 97, 1);
        }

        JFrame frame = new JFrame("Visualisierung von Zufallszahlengeneratoren");

        // Hier wird der Zufallsgenerator uebergeben, der tatsaechlich genutzt werden soll
        frame.getContentPane().add(new ZufallszahlenVisualisierung(genjava, 200), BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}
