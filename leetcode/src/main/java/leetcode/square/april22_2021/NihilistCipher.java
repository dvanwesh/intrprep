package leetcode.square.april22_2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * Let's learn how to encode messages using a Polybius square!
 * <p>
 * In order to use a Polybius square, we first need to build one.
 * <p>
 * To build a Polybius square you need an alphabet with a perfect square number of letters (for this example, let's use a-z + 0-9 - 36 letters).
 * <p>
 * Take this alphabet and then arrange them in a squareRoot(n) * squareRoot(n) grid (so in our example it will by 6x6), like so:
 * <p>
 * 1 2 3 4 5 6
 * ------------
 * 1 |a b c d e f
 * 2 |g h i j k l
 * 3 |m n o p q r
 * 4 |s t u v w x
 * 5 |y z 0 1 2 3
 * 6 |4 5 6 7 8 9
 * To encode with a Polybius square, you take each letter and find the row + column number.
 * <p>
 * e.g. for the letter "b", you get row 1, column 2.
 * <p>
 * You append the numbers together to get your cipher number (12).
 * <p>
 * We'll want to support encoding full strings, something like:
 * <p>
 * encode("hello") == [22, 15, 26, 26, 33]
 * To decode, for each set of numbers, look up the first digit as the row key and the second digit as the column key.
 * <p>
 * Write the encoder and decoder for this cipher.
 **/

/**
 The Nihilist cipher was a twist on the Polybius square basic cipher, used extensively by Russian Nihilists in the late 1800s

 This adds a secret to the mix.

 To encode, you first generate the cipher numbers representing the message using the Polybius square (the output of stage 1).

 You then generate equivalent cipher numbers representing the secret key using the Polybius square, repeating the key as is needed.

 e.g. if the message was "helloworld" and the key was "bob", you would generate the cipher numbers for "bobbobbobb"

 Finally you generate a new list by adding the first number of the first list and the first number of the second list, and so on.

 e.g. "helloworld" -> 22 15 26 26 33 45 33 36 26 14
 +    "bobbobbobb" -> 12 33 12 12 33 12 12 33 12 12
 =============================
 cipher -> 34 48 38 38 66 57 45 69 38 26


 To decode the message, you take the cipher, subtract the secret key in the same way you added the secret key to encode, and then look up the result in the Polybius square.

 Write the encoder/decoder for the Nihilist Cipher.
 **/

class NihilistCipher {
    private char[][] grid = null;
    private Map<Character, String> map;

    public static void main(String[] args) {
        NihilistCipher sol = new NihilistCipher(6);
        String[] encodedStr = sol.encode("hello");
        System.out.println(Arrays.toString(encodedStr));
        System.out.println(sol.decode(encodedStr));

        String[] newEncodedAr = sol.encode("helloworld", "bob");
        System.out.println(Arrays.toString(newEncodedAr));
        System.out.println(sol.decode(newEncodedAr, "bob"));
    }

    public NihilistCipher(int n) {
        grid = new char[n][n];
        map = new HashMap<>();
        char ch = 'a';
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (ch == 'z' + 1) {
                    ch = '0';
                }
                map.put(ch, (i + 1) + "" + (j + 1));
                grid[i][j] = ch++;
            }
        }
    }

    private String[] encode(String str, String secret) {
        String[] encodedStrAr = encode(str);
        String[] encodedSecret = encode(secret);
        int j = 0;
        for (int i = 0; i < encodedStrAr.length; i++) {
            if (i % encodedSecret.length == 0) {
                j = 0;
            }
            encodedStrAr[i] = Integer.parseInt(encodedStrAr[i]) + Integer.parseInt(encodedSecret[j++]) + "";
        }
        return encodedStrAr;
    }

    private String decode(String[] encodedStrAr, String secret) {
        String[] encodedSecret = encode(secret);
        int j = 0;
        for (int i = 0; i < encodedStrAr.length; i++) {
            j = i % encodedSecret.length;
            int num = Integer.parseInt(encodedStrAr[i]) - Integer.parseInt(encodedSecret[j++]);
            encodedStrAr[i] = num + "";
        }
        return decode(encodedStrAr);
    }


    // O(n) time:
    private String[] encode(String str) {
        String[] res = new String[str.length()];
        for (int i = 0; i < str.length(); i++) {
            res[i] = map.get(str.charAt(i));
        }
        return res;
    }

    // O(n)
    private String decode(String[] encodedStrArray) {
        StringBuffer sb = new StringBuffer();
        for (String num : encodedStrArray) {
            sb.append(grid[(num.charAt(0) - '0') - 1][(num.charAt(1) - '0') - 1]);
        }
        return sb.toString();
    }

}
