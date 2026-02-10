public class Vigenere {

    public static String encrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isLetter(ch)) {
                // Get shift amount from current key character
                int shift = key.charAt(keyIndex % key.length()) - 'A';

                if (Character.isUpperCase(ch)) {
                    char c = (char) ((ch - 'A' + shift) % 26 + 'A');
                    result.append(c);
                } else {
                    char c = (char) ((ch - 'a' + shift) % 26 + 'a');
                    result.append(c);
                }
                keyIndex++; // Only advance key if we processed a letter
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    public static String decrypt(String text, String key) {
        StringBuilder result = new StringBuilder();
        key = key.toUpperCase();
        int keyIndex = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (Character.isLetter(ch)) {
                int shift = key.charAt(keyIndex % key.length()) - 'A';

                if (Character.isUpperCase(ch)) {
                    // (x - shift) might be negative, so add 26
                    char c = (char) ((ch - 'A' - shift + 26) % 26 + 'A');
                    result.append(c);
                } else {
                    char c = (char) ((ch - 'a' - shift + 26) % 26 + 'a');
                    result.append(c);
                }
                keyIndex++;
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // --- Main Execution ---
    public static void main(String[] args) {
        String text = "HELLO WORLD";
        String key = "KEY"; // Becomes KEYKEYKEY

        String encrypted = encrypt(text, key);
        String decrypted = decrypt(encrypted, key);

        System.out.println("Original:  " + text);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}