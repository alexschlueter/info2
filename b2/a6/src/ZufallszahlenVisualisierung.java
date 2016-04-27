import java.awt.*;
import java.awt.geom.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.KeyStroke;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import java.util.ArrayList;

/**
 * Einfacher Canvas (Zeichenflaeche) fuer simple Grafiken wie die Visualisierung der Zufallszahlen.
 * @author Aaron Scherzinger
 */
public class ZufallszahlenVisualisierung extends JPanel {

    private final int[] a;
    String genName;

    /**
     * Initialisiere a mit den ersten n Zufallszahlen generiert durch gen
     * @param gen Zufallsgenerator
     * @param n Anzahl generierter Zahlen
     * @param genName Name des Zufallsgenerators
     */
    public ZufallszahlenVisualisierung(ZufallszahlenGenerator gen, int n, String genName) {
        this.genName = genName;
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
        final int genNameWidth = g.getFontMetrics().stringWidth(genName);
        final double geNameHeight = g.getFontMetrics().getStringBounds(genName, g).getHeight();
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

        // Zeichne Generatornamen
        g2.drawString(genName, (width - genNameWidth) / 2, (int) (margin + geNameHeight));

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

        // Anzahl zu generierender Zufallszahlen
        final int numSamples = 200;

        final ArrayList<ZufallszahlenGenerator> gens = new ArrayList<>();

        // Aufgabe b)

        // Inkrement 23
        gens.add(new GenMod(21, 23, 100, 1));

        // Inkrement 7
        gens.add(new GenMod(21, 7, 100, 1));

        // Von Java bereitgestellter Generator
        gens.add(new GenJava());

        // Aufgabe c)
        // Erstelle alle 95 Zufallsgeneratoren nach der linearen Kongruenzmethode mit Inkrement c = 0, Startwert r0 = 1,
        // Modulus m = 97 und Multiplikatoren 1 < i < 97
        for (int i = 2; i < 97; ++i) {
            gens.add(new GenMod(i, 0, 97, 1));
        }


        // Lege ein CardLayout an, in dem für jeden Zufallsgenerator ein JPanel existiert
        final CardLayout cardLayout = new CardLayout();
        final JPanel cards = new JPanel(cardLayout);

        for (ZufallszahlenGenerator gen : gens) {
            cards.add(new ZufallszahlenVisualisierung(gen, numSamples, gen.name()));
        }


        // Erstelle Keybindings für die Pfeiltasten zum Wechseln des Generators
        cards.getInputMap().put(KeyStroke.getKeyStroke(
                KeyEvent.VK_RIGHT, 0),
                "nxt");
        cards.getActionMap().put("nxt", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.next(cards);
            }
        });

        cards.getInputMap().put(KeyStroke.getKeyStroke(
                KeyEvent.VK_LEFT, 0),
                "prev");
        cards.getActionMap().put("prev", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.previous(cards);
            }
        });

        JFrame frame = new JFrame("Visualisierung von Zufallszahlengeneratoren (wechseln des Generators mit Pfeiltasten)");
        frame.add(cards);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setVisible(true);
    }
}
