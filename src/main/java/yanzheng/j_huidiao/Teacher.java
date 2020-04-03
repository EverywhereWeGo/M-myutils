package yanzheng.j_huidiao;

import static yanzheng.j_huidiao.Test.execuete;

/**
 * @author everywherewego
 * @date 2020-04-03 19:20
 */


public class Teacher {
    public static void hello() {
        execuete("sadf", new Callback() {
            @Override
            public boolean dataCheck(String da) {
                System.out.println(da);
                return "aasdfasdfasdf".equals(da);
            }
        });
    }

}