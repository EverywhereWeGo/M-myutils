package yanzheng.j_huidiao;

import static yanzheng.j_huidiao.Teacher.hello;

/**
 * @author everywherewego
 * @date 2020-04-03 19:22
 */


public class Test {

    public static void execuete(String url, Callback callback) {
        for (int i = 0; i < 5; i++) {
            String result = "aasdfadfasdf";
            boolean c = callback.dataCheck(result);
            System.out.println(c);

            if (c) {
                return;
            }
        }


    }

    public static void main(String[] args) {
        hello();
    }
}