import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Teste die Fibonacci-Implementationen
 */
public class MainTest {

    // Die ersten 40 Fibonacci-Zahlen zum Vergleich
    private static final int fibs[] = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584,
            4181, 6765, 10946, 17711, 28657, 46368, 75025, 121393, 196418, 317811, 514229, 832040, 1346269, 2178309,
            3524578, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 102334155};

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @org.junit.Test
    public void recursiveFib() throws Exception {
        for (short n = 0; n < fibs.length; ++n) {
            assertEquals(fibs[n], Main.recursiveFib(n));
        }

        exception.expect(IllegalArgumentException.class);
        Main.recursiveFib((short) -1);
    }

    @org.junit.Test
    public void iterativeFib() throws Exception {
        for (short n = 0; n < fibs.length; ++n) {
            assertEquals(fibs[n], Main.iterativeFib(n));
        }

        exception.expect(IllegalArgumentException.class);
        Main.iterativeFib((short) -1);
    }

    @org.junit.Test
    public void modularFib() throws Exception {
        for (short n = 0; n < fibs.length; ++n) {
            assertArrayEquals(Main.modularFib(n), new short[]{(short) (fibs[n] % 999), (short) (fibs[n] % 1000), (short) (fibs[n] % 1001)});
        }

        exception.expect(IllegalArgumentException.class);
        Main.modularFib((short) -1);
    }

}