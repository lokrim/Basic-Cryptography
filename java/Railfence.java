import java.util.Arrays;

public class Railfence {

    public static String encrypt(String text, int rails) {
        if (rails <= 1)
            return text;

        StringBuilder[] fence = new StringBuilder[rails];
        for (int i = 0; i < rails; i++)
            fence[i] = new StringBuilder();

        int currentRail = 0;
        boolean goingDown = false;

        for (char c : text.toCharArray()) {
            fence[currentRail].append(c);

            // Reverse direction if we hit top or bottom
            if (currentRail == 0 || currentRail == rails - 1) {
                goingDown = !goingDown;
            }

            currentRail += goingDown ? 1 : -1;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder sb : fence)
            result.append(sb);

        return result.toString();
    }

    public static String decrypt(String text, int rails) {
        if (rails <= 1)
            return text;

        char[][] fence = new char[rails][text.length()];
        for (int i = 0; i < rails; i++)
            Arrays.fill(fence[i], '\n'); // Placeholder

        // Step 1: Mark the pattern with '*'
        boolean goingDown = false;
        int row = 0, col = 0;

        for (int i = 0; i < text.length(); i++) {
            if (row == 0)
                goingDown = true;
            if (row == rails - 1)
                goingDown = false;

            fence[row][col++] = '*';
            row += goingDown ? 1 : -1;
        }

        // Step 2: Fill the fence with the ciphertext characters
        int index = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < text.length(); j++) {
                if (fence[i][j] == '*' && index < text.length()) {
                    fence[i][j] = text.charAt(index++);
                }
            }
        }

        // Step 3: Read the zigzag path
        StringBuilder result = new StringBuilder();
        row = 0;
        col = 0;
        goingDown = false;

        for (int i = 0; i < text.length(); i++) {
            if (row == 0)
                goingDown = true;
            if (row == rails - 1)
                goingDown = false;

            if (fence[row][col] != '\n') {
                result.append(fence[row][col]);
                col++;
            }
            row += goingDown ? 1 : -1;
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String text = "HELLO WORLD";
        int rails = 2;

        String encrypted = encrypt(text, rails);
        String decrypted = decrypt(encrypted, rails);

        System.out.println("Original:  " + text);
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decrypted);
    }
}
