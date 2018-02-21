package q4;

public class A {

    public int gcd(int x, int y) {
        System.out.printf("gcd %d and %d%n", x, y);
        while (x != y) {
            if (x > y) {
                x -= y;
            } else {
                y -= x;
            }
        }
        return y;
    }

    public int gcd2(int x, int y) {
        while (x != y) {
            if (x > y) {
                x -= y;
            } else {
                y -= x;
            }
        }
        System.out.printf("gcd %d and %d%n", x, y);
        return y;
    }
}
