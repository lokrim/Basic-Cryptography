public class Hill {

    // Finds modular inverse of a number
    private static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }

    // Calculates the inverse of a 2x2 matrix modulo 26
    private static int[][] getInverseMatrix(int[][] key) {
        int det = (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % 26;
        if (det < 0)
            det += 26;

        int detInv = modInverse(det, 26);
        if (detInv == -1) {
            throw new IllegalArgumentException("Key matrix is not invertible.");
        }

        int[][] invKey = new int[2][2];

        // Adjugate matrix * detInv
        // [ d -b ]
        // [ -c a ]
        invKey[0][0] = (key[1][1] * detInv) % 26;
        invKey[0][1] = ((-key[0][1] % 26 + 26) * detInv) % 26;
        invKey[1][0] = ((-key[1][0] % 26 + 26) * detInv) % 26;
        invKey[1][1] = (key[0][0] * detInv) % 26;

        return invKey;
    }

    // Encrypts or Decrypts based on the matrix provided
    private static String process(String text, int[][] key) {
        StringBuilder result = new StringBuilder();
        text = text.toUpperCase().replaceAll("[^A-Z]", "");

        if (text.length() % 2 != 0)
            text += "X"; // Padding

        for (int i = 0; i < text.length(); i += 2) {
            int p1 = text.charAt(i) - 'A';
            int p2 = text.charAt(i + 1) - 'A';

            // Matrix multiplication
            int c1 = (key[0][0] * p1 + key[0][1] * p2) % 26;
            int c2 = (key[1][0] * p1 + key[1][1] * p2) % 26;

            result.append((char) (c1 + 'A'));
            result.append((char) (c2 + 'A'));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        // Key Matrix:
        // 3 3
        // 2 5
        int[][] key = { { 3, 3 }, { 2, 5 } };
        String message = "HELP";

        // Encryption
        String encrypted = process(message, key);
        System.out.println("Encrypted: " + encrypted);

        // Decryption
        // We must calculate the inverse matrix first
        try {
            int[][] invKey = getInverseMatrix(key);
            String decrypted = process(encrypted, invKey);
            System.out.println("Decrypted: " + decrypted);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}