public class Caesar {

    // Encrypts text using Caesar Cipher
    public static String encrypt(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isUpperCase(ch)) {
                // Apply shift to uppercase (A=65)
                char c = (char) (((int) ch + shift - 65) % 26 + 65);
                result.append(c);
            } else if (Character.isLowerCase(ch)) {
                // Apply shift to lowercase (a=97)
                char c = (char) (((int) ch + shift - 97) % 26 + 97);
                result.append(c);
            } else {
                // Keep non-alphabetic characters unchanged
                result.append(ch);
            }
        }
        return result.toString();
    }

    // Decrypts text using Caesar Cipher
    public static String decrypt(String text, int shift) {
        // To decrypt, we shift backwards.
        // In modular arithmetic, subtracting 'shift' is the same as adding '26 - shift'
        return encrypt(text, 26 - (shift % 26));
    }

    // --- Main Execution ---
    public static void main(String[] args) {
        String text = "Hello, Java Cryptography!";
        int shift = 4;

        String encrypted = encrypt(text, shift);
        String decrypted = decrypt(encrypted, shift);

        System.out.println("Original:  " + text);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
