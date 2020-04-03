package yanzheng.c_jicheng;

public class son extends father {
    int a;
    int b;

    public son() {
        System.out.println("2");
        this.a = 1;
        this.b = 2;

    }

    public son(String a) {
        this();
        System.out.println("1");

    }
}
