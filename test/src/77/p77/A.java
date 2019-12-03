package p77;

public class A {

    private static Object obj;

    static {
        obj = new Object() {
            @Override
            public int hashCode() {
                return 0;
            }
        };
    }
}
