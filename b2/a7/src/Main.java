import java.util.Arrays;

/**
 * Verschiedene Implementationen zur Berechnung der Fibonacci-Zahlen
 */
public class Main {
    /**
     * Rekursive Implementation
     * @param n nichtnegativer Index der zu berechnenden Fibonacci-Zahl
     * @return n-te Fibonacci-Zahl
     */
    public static int recursiveFib(short n) {
        if (n < 0) throw new IllegalArgumentException();
        if (n == 0) return 0;
        if (n == 1) return 1;
        return recursiveFib((short) (n - 1)) + recursiveFib((short) (n - 2));
    }

    /**
     * Iterative Implementation
     * @param n nichtnegativer Index der zu berechnenden Fibonacci-Zahl
     * @return n-te Fibonacci-Zahl
     */
    public static int iterativeFib(short n) {
        if (n < 0) throw new IllegalArgumentException();

        int a = 0, b = 1, tmp;
        while (--n >= 0) {
            tmp = b;
            b = a + b;
            a = tmp;
        }
        return a;
    }

    /**
     * Iterative Implementation mit modularer Arithmetik
     * @param n nichtnegativer Index der zu berechnenden Fibonacci-Zahl
     * @return Reste der n-te Fibonacci-Zahl modulo 999, 1000 bzw. 1001
     */
    public static short[] modularFib(short n) {
        if (n < 0) throw new IllegalArgumentException();

        // 0 in Restedarstellung
        short a[] = {0, 0, 0};

        // 1 in Restedarstellung
        short b[] = {1, 1, 1};

        short tmp[];
        while (--n >= 0) {

            // Erstelle tiefe Kopie des Arrays
            tmp = b.clone();

            b[0] = (short) ((b[0] + a[0]) % 999);
            b[1] = (short) ((b[1] + a[1]) % 1000);
            b[2] = (short) ((b[2] + a[2]) % 1001);
            a = tmp;
        }
        return a;
    }

    /**
     * Konvertiere Integer zu Restedarstellung
     * @param n
     * @return short-Array mit Resten (n % 999, n % 1000, n % 1001)
     */
    public static short[] intToShortAr(int n) {
        return new short[]{(short) (n % 999), (short) (n % 1000), (short) (n % 1001)};
    }

    /**
     * Die groesste Zahl, die eine eindeutige Restedarstellung hat, ist 999 * 1000 * 1001 - 1
     * Berechne mit der iterativen Implementation, wieviele Fibonacci-Zahlen dargestellt werden koennen
     */
    public static void main(String[] args) {
        for (short n = 0;; ++n) {
            int res = iterativeFib(n);
            if (res <= 999998999) {
                System.out.println(n + " " + res);
            } else break;
        }
    }
}
