package utils.basicutil;

import po.SamplePojo;

import java.lang.reflect.Field;

public class f_CreateSqlByObject {
    public static String createInsertPreSql(String talename, Class clazz) {
        String modelsql = "insert into {0} ({1}) values ({2}) ";
        StringBuffer key = new StringBuffer();
        StringBuffer value = new StringBuffer();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            key.append(field.getName());
            key.append(",");
            value.append("?,");
        }
        key.deleteCharAt(key.length() - 1);
        value.deleteCharAt(value.length() - 1);
        String sql = modelsql.replace("{0}", talename).replace("{1}", key).replace("{2}", value);
        return sql;
    }


    public static String createSelectPreSql(String talename, Class clazz) {
        String modelsql = "select {0} from {1}";
        StringBuffer key = new StringBuffer();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            key.append(field.getName());
            key.append(",");
        }
        key.deleteCharAt(key.length() - 1);
        String sql = modelsql.replace("{0}", key).replace("{1}", talename);
        return sql;
    }


    public static void main(String[] args) throws InterruptedException {
        String a = createInsertPreSql("hahhaa", SamplePojo.class);
        System.out.println(a);
        String b = createSelectPreSql("asdfasdf", SamplePojo.class);
        System.out.println(b);
        Thread.sleep(10000000);

    }

}