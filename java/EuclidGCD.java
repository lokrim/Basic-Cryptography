public class EuclidGCD {

    // Recursive implementation
    public static int gcdRecursive(int a, int b) {
        if (b == 0) {
            return a;
        }
        return gcdRecursive(b, a % b);
    }

    // Iterative implementation (More stack-friendly)
    public static int gcdIterative(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static void main(String[] args) {
        int num1 = 48;
        int num2 = 18;

        System.out.println("GCD of " + num1 + " and " + num2 + " (Recursive): " + gcdRecursive(num1, num2));
        System.out.println("GCD of " + num1 + " and " + num2 + " (Iterative): " + gcdIterative(num1, num2));
    }
}