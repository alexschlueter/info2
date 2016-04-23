/**
 * Von Java bereitgestellter Zufallsgenerator
 */
public class GenJava implements ZufallszahlenGenerator {
    public int nextInt() {
        // Math.random() liefert zufälligen double Wert im Intervall [0, 1)
        // Skaliere zuerst auf einen Wert in [0, 100), runde dann auf den nächstkleineren int ab und addiere 1
        // Dadurch erhalten wir einen int Wert in {1, ... , 100}
        return 1 + (int) (Math.random() * 100);
    }
}