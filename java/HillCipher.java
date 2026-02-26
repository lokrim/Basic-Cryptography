public class HillCipher{

    // helper to get modular inverse
    private static int getModularInverse(int a){

        // normalise key
        a = (a % 26 + 26) % 26;

        for (int i=0; i<26; i++){
            if ((a * i) % 26 == 1){
                return i;
            }
        }
        System.out.println("No modular Inverse Exists");
        return 1;
    }

    public static String encrypt(String plain, int[][] key){

        // process plain text
        plain = plain.toUpperCase().replaceAll("[^A-Z]", "");
        if (plain.length() % 2 != 0){
            plain += "X";
        }

        StringBuilder result = new StringBuilder();

        for (int i=0; i<plain.length(); i+=2){

            int p1 = plain.charAt(i) - 'A';
            int p2 = plain.charAt(i+1) - 'A';

            // E(X) = (K * X) % 26
            char c1 = (char) (((key[0][0] * p1 + key[0][1] * p2) % 26) + 'A');
            char c2 = (char) (((key[1][0] * p1 + key[1][1] * p2) % 26) + 'A');

            result.append(c1).append(c2);
        }

        return result.toString();
    }

    public static String decrypt(String cipher, int[][] key){

        StringBuilder result = new StringBuilder();

        // K_inv = Det_inv * Adj(K)
        int det = (key[0][0] * key[1][1]) - (key[0][1] * key[1][0]);
        int det_inv = getModularInverse(det);
        int[][] key_inv = {{((det_inv * key[1][1] + 26) % 26) % 26, ((-det_inv * key[0][1])+ 26) % 26}, {((-det_inv * key[1][0])+ 26) % 26, ((det_inv * key[0][0])+ 26) % 26}};

        for (int i=0; i<cipher.length(); i+=2){

            int p1 = cipher.charAt(i) - 'A';
            int p2 = cipher.charAt(i+1) - 'A';

            // D(X) = (K_inv * X) % 26
            char c1 = (char) (((key_inv[0][0] * p1 + key_inv[0][1] * p2) % 26) + 'A');
            char c2 = (char) (((key_inv[1][0] * p1 + key_inv[1][1] * p2) % 26) + 'A');

            result.append(c1).append(c2);
        }

        return result.toString();
    }

    public static void main(String args[]){

        int[][] key = {{3, 3}, {2, 5}};
        String plain = "Hello World";

        String encrypted = encrypt(plain, key);
        System.out.println("Encrypted String: " + encrypted);

        String decrypted = decrypt(encrypted, key);
        System.out.println("Decrypted String: " + decrypted);
    }
}