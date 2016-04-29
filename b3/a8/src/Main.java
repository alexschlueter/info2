import java.util.Arrays;
import java.util.List;

public class Main {
    public static final <T> void printMatrix(T[][] a) {
        for (T[] t : a) {
            System.out.println(Arrays.toString(t));
        }
    }

    public static final Boolean[][] warshall(Boolean[][] a) {
        for (int k = 0; k < a.length; ++k) {
            for (int i = 0; i < a.length; ++i) {
                for (int j = 0; j < a.length; ++j) {
                    a[i][j] = a[i][j] || (a[i][k] && a[k][j]);
                }
            }
        }
        return a;
    }

    public static final Double[][] floyd(Double[][] a) {
        for (int k = 0; k < a.length; ++k) {
            for (int i = 0; i < a.length; ++i) {
                for (int j = 0; j < a.length; ++j) {
                    a[i][j] = Math.min(a[i][j], a[i][k] + a[k][j]);
                }
            }
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
