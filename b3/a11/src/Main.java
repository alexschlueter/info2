import java.util.Arrays;

public class Main {
    public static final int[] decode(int internalFormat) {
        int res[] = {0, 0, 0};
        for (int i = 0; i < 9; ++i) {
            res[0] |= (internalFormat & (1 << (3 * i + 3))) >> (2 * i + 3);
            res[1] |= (internalFormat & (1 << (3 * i + 4))) >> (2 * i + 4);
            res[2] |= (internalFormat & (1 << (3 * i + 5))) >> (2 * i + 5);
        }
        for (int i = 0; i < 3; ++i) {
            if ((internalFormat & (1 << i)) != 0) {
                res[i] *= -1;
            }
        }
        return res;
    }

    public static final int encode(int[] threeIntegersArray) {
        int res = 0;
        for (int i = 0; i < 3; ++i) {
            if (threeIntegersArray[i] < 0) {
                res |= 1 << i;
                threeIntegersArray[i] *= -1;
            }
        }
        for (int i = 0; i < 9; ++i) {
            res |= (threeIntegersArray[0] & (1 << i)) << (2 * i + 3);
            res |= (threeIntegersArray[1] & (1 << i)) << (2 * i + 4);
            res |= (threeIntegersArray[2] & (1 << i)) << (2 * i + 5);
        }
        return res;
    }

    public static void main(String[] args) {
        int a = 222000123;
        int b[] = {123, 124, 125};
        System.out.println(Arrays.toString(decode(a)));
        System.out.println(encode(decode(a)));
        System.out.println((encode(b)));
        System.out.println(Arrays.toString(decode(encode(b))));
    }
}
