import java.awt.Point;

public class Playfair {
    private static char[][] charTable;
    private static Point[] positions;

    // Generates the 5x5 key matrix
    private static void createTable(String key) {
        charTable = new char[5][5];
        positions = new Point[26];
        String s = prepareKey(key + "ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        int len = s.length();
        for (int i = 0, k = 0; i < len; i++) {
            char c = s.charAt(i);
            // Ignore J, treat as I
            if (positions[c - 'A'] == null) {
                charTable[k / 5][k % 5] = c;
                positions[c - 'A'] = new Point(k % 5, k / 5);
                k++;
            }
        }
    }

    // Simplifies key (removes duplicates, handles J)
    private static String prepareKey(String key) {
        StringBuilder sb = new StringBuilder();
        key = key.toUpperCase().replace('J', 'I').replaceAll("[^A-Z]", "");
        for (char c : key.toCharArray()) {
            if (sb.indexOf(String.valueOf(c)) == -1)
                sb.append(c);
        }
        return sb.toString();
    }

    // Formats text: pairs letters, adds X if duplicates, handles odd length
    private static String formatText(String text) {
        StringBuilder sb = new StringBuilder();
        text = text.toUpperCase().replace('J', 'I').replaceAll("[^A-Z]", "");

        for (int i = 0; i < text.length(); i++) {
            char c1 = text.charAt(i);
            sb.append(c1);

            if (i + 1 < text.length()) {
                if (text.charAt(i + 1) == c1) {
                    sb.append('X'); // Insert X between duplicate letters
                } else {
                    sb.append(text.charAt(i + 1));
                    i++;
                }
            }
        }
        if (sb.length() % 2 != 0)
            sb.append('X'); // Padding for odd length
        return sb.toString();
    }

    private static String codec(String text, int direction) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < text.length(); i += 2) {
            char a = text.charAt(i);
            char b = text.charAt(i + 1);

            Point p1 = positions[a - 'A'];
            Point p2 = positions[b - 'A'];

            int row1 = p1.y;
            int col1 = p1.x;
            int row2 = p2.y;
            int col2 = p2.x;

            if (row1 == row2) { // Same Row
                col1 = (col1 + direction + 5) % 5;
                col2 = (col2 + direction + 5) % 5;
            } else if (col1 == col2) { // Same Column
                row1 = (row1 + direction + 5) % 5;
                row2 = (row2 + direction + 5) % 5;
            } else { // Rectangle
                int temp = col1;
                col1 = col2;
                col2 = temp;
            }

            sb.append(charTable[row1][col1]);
            sb.append(charTable[row2][col2]);
        }
        return sb.toString();
    }

    public static String encrypt(String text, String key) {
        createTable(key);
        String formatted = formatText(text);
        return codec(formatted, 1);
    }

    public static String decrypt(String text, String key) {
        createTable(key);
        return codec(text, -1);
    }

    public static void main(String[] args) {
        String key = "MONARCHY";
        String txt = "INSTRUMENTS";

        String enc = encrypt(txt, key);
        System.out.println("Original:  " + txt);
        System.out.println("Encrypted: " + enc);
        System.out.println("Decrypted: " + decrypt(enc, key));
    }
}