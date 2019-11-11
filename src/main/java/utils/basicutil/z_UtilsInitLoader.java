package utils.basicutil;

public class z_UtilsInitLoader {
    public static void loadeutils(){
        try {
            Class clazz1 = Class.forName("com.utils.b_DBUtil_ConnectionPool");
            Class clazz2 = Class.forName("com.utils.c_RedisUtil_ConnectionPool");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
