import java.util.Arrays;

public class Main {

    /**
     * Dekodiert das interne Format (ein Integer), um die Ausgangswerte (drei Integers) zurueck zu erhalten.
     * @param internalFormat kodierter Integer
     * @return die drei dekodierten Integers
     */
    public static final int[] decode(int internalFormat) {
        int res[] = {0, 0, 0};

        // Setze zuerst die Vorzeichenbits. Im internalFormat stehen diese ganz rechts,
        // im Resultat muessen sie nach ganz links
        for (int i = 0; i < 3; ++i) {
            res[i] |= (internalFormat & (1 << i)) << (31 - i);
        }

        // Beim Kodieren wurden nur die 9 bzw. 10 am wenigsten signifikanten Bits beachtet, alle anderen wurden
        // abgeschnitten. Damit betraglich kleine Integers korrekt dekodiert werden, muessen negative Werte von
        // links mit Einsen aufgefuellt werden, positive mit Nullen.
        // Da die Vorzeichenbits schon gesetzt wurden, kann dies mit dem signed right shift >> erreicht werden.
        // 32 - 3 = 29 ist nicht glatt durch 3 teilbar, deshalb bekommmen res[0], res[1] 10 Bits und res[2] nur 9.
        res[0] >>= 21;
        res[1] >>= 21;
        res[2] >>= 22;

        // Jetzt werden die restlichen Bits dekodiert. Die Bits gehoeren abwechselnd in die drei res-Eintraege.
        for (int i = 0; i < 9; ++i) {
            res[0] |= (internalFormat & (1 << (3 * i + 3))) >>> (2 * i + 3);
            res[1] |= (internalFormat & (1 << (3 * i + 4))) >>> (2 * i + 4);
            res[2] |= (internalFormat & (1 << (3 * i + 5))) >>> (2 * i + 5);
        }

        // res[0] und res[1] bekommen ein Bit mehr als res[2]
        // Benutze unsigned right shift >>>, damit nicht links mit Einsen aufgefuellt wird,
        // falls das ganz linke Bit gesetzt ist.
        res[0] |= (internalFormat & (1 << (3 * 9 + 3))) >>> (2 * 9 + 3);
        res[1] |= (internalFormat & (1 << (3 * 9 + 4))) >>> (2 * 9 + 4);

        return res;
    }

    /**
     * Kodiert drei Integers zu einem Integer
     * @param threeIntegersArray drei Integers
     * @return der kodierte Integer
     */
    public static final int encode(int[] threeIntegersArray) {
        int res = 0;

        // Setze zuerst die Vorzeichenbits ganz rechts im Resultat
        for (int i = 0; i < 3; ++i) {
            if (threeIntegersArray[i] < 0) {
                res |= 1 << i;
            }
        }

        // Die Bits der drei Integers werden abwechselnd in das Resultat geschrieben
        for (int i = 0; i < 9; ++i) {
            res |= (threeIntegersArray[0] & (1 << i)) << (2 * i + 3);
            res |= (threeIntegersArray[1] & (1 << i)) << (2 * i + 4);
            res |= (threeIntegersArray[2] & (1 << i)) << (2 * i + 5);
        }

        // Die ersten beiden Integers duerfen ein Bit mehr speichern
        res |= (threeIntegersArray[0] & (1 << 9)) << (2 * 9 + 3);
        res |= (threeIntegersArray[1] & (1 << 9)) << (2 * 9 + 4);

        return res;
    }

    /**
     * Teste die encode / decode - Methoden mit den Werten aus Aufgabe (c)
     * @param args
     */
    public static void main(String[] args) {
        int a = 222000123;
        int b[] = {123, 124, 125};
        System.out.println(Arrays.toString(decode(a)));
        System.out.println(encode(decode(a)));
        System.out.println((encode(b)));
        System.out.println(Arrays.toString(decode(encode(b))));
    }
}
