public class Affine {

    // Helper: Finds the modular multiplicative inverse of a mod m
    // Returns x where (a * x) % m == 1
    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return 1; // Should not happen if keys are valid
    }

    // Helper: Computes Greatest Common Divisor
    public static int gcd(int a, int b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    public static String encrypt(String text, int a, int b) {
        if (gcd(a, 26) != 1) {
            throw new IllegalArgumentException("Key 'a' is not coprime to 26.");
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isUpperCase(ch)) {
                // E(x) = (ax + b) mod 26
                int x = ch - 'A';
                char encryptedChar = (char) (((a * x + b) % 26) + 'A');
                result.append(encryptedChar);
            } else if (Character.isLowerCase(ch)) {
                int x = ch - 'a';
                char encryptedChar = (char) (((a * x + b) % 26) + 'a');
                result.append(encryptedChar);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, int a, int b) {
        if (gcd(a, 26) != 1) {
            throw new IllegalArgumentException("Key 'a' is not coprime to 26.");
        }

        StringBuilder result = new StringBuilder();
        int a_inv = modInverse(a, 26);

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isUpperCase(ch)) {
                // D(x) = a^-1 * (x - b) mod 26
                int x = ch - 'A';
                // (x - b) might be negative, so we add 26 before modulo
                int decryptedVal = (a_inv * (x - b + 26)) % 26;
                result.append((char) (decryptedVal + 'A'));
            } else if (Character.isLowerCase(ch)) {
                int x = ch - 'a';
                int decryptedVal = (a_inv * (x - b + 26)) % 26;
                result.append((char) (decryptedVal + 'a'));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // --- Main Execution ---
    public static void main(String[] args) {
        String msg = "AFFINE CIPHER";
        int a = 5;
        int b = 8;

        String encrypted = encrypt(msg, a, b);
        String decrypted = decrypt(encrypted, a, b);

        System.out.println("Original:  " + msg);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
