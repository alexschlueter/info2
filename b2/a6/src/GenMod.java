/**
 * Zufallsgenerator nach der linearen Kongruenzmethode
 * Generiert Integerwerte in {1, ..., m} wobei m der Modulus ist
 */
public class GenMod implements ZufallszahlenGenerator {
    private int a, c, m, r0, r;

    /**
     * Initialisiere die Parameter fuer die lineare Kongruenzmethode
     * @param a Multiplikator
     * @param c Inkrement
     * @param m Modulus
     * @param r0 Startwert
     */
    public GenMod(int a, int c, int m, int r0) {
        this.a = a;
        this.c = c;
        this.m = m;
        this.r = this.r0 = r0;
    }

    public String name() {
        return "Generator nach der linearen Kongruenzmethode (a=" + a + ", c=" + c + ", m=" + m + ", r0=" + r0 + ")";
    }

    /**
     * Generiere naechsten Wert
     * @return Integerwert in {1, ..., m}
     */
    public int nextInt() {
        r = (a * r + c) % m;
        // r liegt in {0, ..., m - 1}
        // aber wir wollen einen Wert in {1, ..., m} (fuer die Visualisierung sollen alle Werte in {1, ... , 100} liegen)
        return r + 1;
    }
}
