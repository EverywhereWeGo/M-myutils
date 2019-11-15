package helloworld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class systest {
    private final static Logger logger = LoggerFactory.getLogger(systest.class);

    public static void main(String[] args) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            System.out.println("1");
        }
        long b = System.currentTimeMillis();


        long c = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
        }
        long d = System.currentTimeMillis();


        long e = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            logger.info("1");
        }
        long f = System.currentTimeMillis();

        System.out.println(b - a + "ms");

        System.out.println(d - c + "ms");


        System.out.println(f - e + "ms");
    }
}
