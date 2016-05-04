import java.util.Arrays;
import java.util.function.Consumer;

public class Main {
    public static final <T> void printMatrix(T[][] a) {
        for (T[] t : a) {
            System.out.println(Arrays.toString(t));
        }
    }

    public static final <T> void latexMatrix(Integer k, T[][] a, Consumer<T> printEl, String matr) {
        if (k % 2 == 0) System.out.print("& ");
        System.out.println(matr + "^{" + k.toString() + "} &=");
        System.out.println("\\begin{bmatrix}");
        for (int i = 0; i < a.length; ++i) {
            for (int j = 0; j < a[i].length; ++j) {
                printEl.accept(a[i][j]);
                if (j != a[i].length - 1) {
                    System.out.print(" &");
                }
                System.out.print(" ");
            }
            if (i != a.length - 1) {
                System.out.print(" \\\\");
            }
            System.out.print("\n");
        }
        System.out.print("\\end{bmatrix}");
        if (k % 2 == 0) System.out.print(" \\\\");
        System.out.print("\n");
    }

    public static final void printBool(Boolean b) {
        System.out.print("\\text{" + (b ? "T" : "F") + "}");
    }

    public static final void printDouble(Double d) {
        if (d.isInfinite()) {
            System.out.print("\\infty");
        } else {
            System.out.print((int)d.doubleValue());
        }
    }

    public static final Boolean[][] warshall(Boolean[][] a) {
        for (int i = 0; i < a.length; ++i) {
            a[i][i] = true;
        }
        latexMatrix(-1, a, (b) -> printBool(b), "B");
        for (int k = 0; k < a.length; ++k) {
            for (int i = 0; i < a.length; ++i) {
                for (int j = 0; j < a.length; ++j) {
                    a[i][j] = a[i][j] || (a[i][k] && a[k][j]);
                }
            }
            latexMatrix(k, a, (b) -> printBool(b), "B");
        }
        return a;
    }

    public static final Double[][] floyd(Double[][] a) {
        latexMatrix(-1, a, (d) -> printDouble(d), "D");
        for (int k = 0; k < a.length; ++k) {
            for (int i = 0; i < a.length; ++i) {
                for (int j = 0; j < a.length; ++j) {
                    a[i][j] = Math.min(a[i][j], a[i][k] + a[k][j]);
                }
            }
            latexMatrix(k, a, (d) -> printDouble(d), "D");
        }
        return a;
    }

    public static void main(String[] args) {
        Boolean a[][] = {
                {false, false, true,  false, false, false},
                {false, false, true,  false, true,  false},
                {false, false, false, true,  false, true},
                {false, false, true,  false, false, false},
                {false, true,  false, false, false, false},
                {true,  false, false, false, true,  false}
        };

        double inf = Double.POSITIVE_INFINITY;

        Double l[][] = {
                {0.0, 1.0, 9.0, inf, inf, inf},
                {inf, 0.0, 6.0, 4.0, 1.0, inf},
                {inf, inf, 0.0, 1.0, inf, 1.0},
                {inf, inf, 1.0, 0.0, inf, inf},
                {inf, 1.0, inf, inf, 0.0, inf},
                {1.0, inf, inf, inf, 1.0, 0.0}

        };
        printMatrix(warshall(a));
        printMatrix(floyd(l));
    }
}
