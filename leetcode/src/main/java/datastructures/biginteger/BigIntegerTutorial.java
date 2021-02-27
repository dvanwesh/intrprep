package datastructures.biginteger;

import java.math.BigInteger;

public class BigIntegerTutorial {
    public static void main(String[] args) {
        String s = "3";
        BigInteger bg = new BigInteger(s);
        System.out.println(bg);
        System.out.println(bg.bitLength());
        System.out.println(bg);
        for (int i = 0; i < bg.bitLength(); i++) {
            System.out.print(bg.testBit(i) + "-" + i + "-");
        }
    }
}
