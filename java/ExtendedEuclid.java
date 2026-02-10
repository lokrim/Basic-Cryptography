public class ExtendedEuclid {

    // Helper class to store results
    static class Result {
        int gcd;
        int x;
        int y;

        Result(int gcd, int x, int y) {
            this.gcd = gcd;
            this.x = x;
            this.y = y;
        }
    }

    // Extended Euclidean Algorithm
    // Returns [gcd(a, b), x, y] such that ax + by = gcd(a, b)
    public static Result extendedGCD(int a, int b) {
        if (a == 0) {
            return new Result(b, 0, 1);
        }

        Result temp = extendedGCD(b % a, a);

        // Update x and y using results from recursive call
        int x = temp.y - (b / a) * temp.x;
        int y = temp.x;

        return new Result(temp.gcd, x, y);
    }

    // Function to find modular inverse of a mod m
    public static int modInverse(int a, int m) {
        Result res = extendedGCD(a, m);

        if (res.gcd != 1) {
            throw new ArithmeticException("Modular inverse does not exist (a and m are not coprime)");
        } else {
            // Ensure result is positive
            return (res.x % m + m) % m;
        }
    }

    public static void main(String[] args) {
        int a = 11;
        int m = 26;

        try {
            int inv = modInverse(a, m);
            System.out.println("Modular Inverse of " + a + " mod " + m + " is: " + inv);
            System.out.println("Check: (" + a + " * " + inv + ") % " + m + " = " + ((a * inv) % m));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}