package utils.basicutil;

import po.SamplePojo;

import java.lang.reflect.Field;

import static utils.basicutil.i_StringUtil.firsttoUpperCase;

public class f_CreateSqlByObject {
    public static <T> void createInsertPreSql(String talename, Class<T> clazz) {
        String modelsql = "insert into {0} ({1}) values ({2}) ";
        StringBuffer key = new StringBuffer();
        StringBuffer value = new StringBuffer();
        StringBuffer setPreparement = new StringBuffer();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            key.append(field.getName());
            key.append(",");
            value.append("?,");
            setPreparement.append("yjxx.set").append(firsttoUpperCase(field.getName())).append("();\n");
        }
        key.deleteCharAt(key.length() - 1);
        value.deleteCharAt(value.length() - 1);
        String sql = modelsql.replace("{0}", talename).replace("{1}", key).replace("{2}", value);
        String insert = setPreparement.toString();
        System.out.println(sql);
        System.out.println(insert);
    }

    public static <T> void createSelectPreSql(String talename, Class<T> clazz) {
        String modelsql = "select {0} from {1}";
        StringBuffer key = new StringBuffer();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            key.append(field.getName());
            key.append(",");
        }
        key.deleteCharAt(key.length() - 1);
        String sql = modelsql.replace("{0}", key).replace("{1}", talename);
        System.out.println(sql);
    }


    public static void main(String[] args) {
        createInsertPreSql("qwer", SamplePojo.class);
        createSelectPreSql("vehicle_pass", SamplePojo.class);
    }

}