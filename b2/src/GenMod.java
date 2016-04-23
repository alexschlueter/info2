/**
 * Zufallsgenerator nach der linearen Kongruenzmethode
 * Generiert Integerwerte in {1, ..., m} wobei m der Modulus ist
 */
public class GenMod implements ZufallszahlenGenerator {
    private int a, c, m, r;

    /**
     * Initialisiere die Parameter für die lineare Kongruenzmethode
     * @param a Multiplikator
     * @param c Inkrement
     * @param m Modulus
     * @param r0 Startwert
     */
    public GenMod(int aa, int cc, int mm, int r0) {
        a = aa;
        c = cc;
        m = mm;
        r = r0;
    }

    /**
     * Generiere nächsten Wert
     * @return Integerwert in {1, ..., m}
     */
    public int nextInt() {
        r = (a * r + c) % m;
        // r liegt in {0, ..., m - 1}
        // aber wir wollen einen Wert in {1, ..., m} (für die Visualisierung sollen alle Werte in {1, ... , 100} liegen)
        return r + 1;
    }
}
