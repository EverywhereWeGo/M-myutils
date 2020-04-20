package tools.sql;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;
import java.util.List;

import static utils.basicutil.f_SqlUtil.ddlSql;
import static utils.basicutil.f_SqlUtil.querySql;

/**
 * @author everywherewego
 * @date 2020-04-20 14:25
 */

public class EachTable {
    public static List<String> eachtable(String databaseName, String prex) {
        List<String> list = new LinkedList<>();
        JSONArray a = querySql("show tables");
        for (Object obj : a) {
            JSONObject jsonObject = (JSONObject) obj;
            String tablename = jsonObject.getString("Tables_in_" + databaseName);
            if (tablename.startsWith(prex)) {
                list.add(tablename);
            }
        }
        return list;
    }

    public static void showtablename() {
        List<String> eachtable = eachtable("spider", "sys_jsswryzdjkxt");
        for (String e : eachtable) {
            System.out.println(e);
        }
    }

    public static void altertable() {
        List<String> eachtable = eachtable("spider", "sys_jsswryzdjkxt");
        for (String e : eachtable) {
            JSONArray tables = querySql("desc " + e);

            for (Object ob : tables) {
                JSONObject jsonObject1 = (JSONObject) ob;
                String b = jsonObject1.getString("Field");

                if ("id".equals(b) || "etl_date".equals(b)) {
                    continue;
                }
                ddlSql("alter table " + e + "  modify " + b + " text");

            }
        }
    }

    public static void counttable() {
        List<String> eachtable = eachtable("spider", "sys_jsswryzdjkxt");
        for (String e : eachtable) {

            JSONArray tables = querySql("desc " + e);

            for (Object ob : tables) {
                JSONObject jsonObject1 = (JSONObject) ob;
                String b = jsonObject1.getString("Field");

                if ("id".equals(b) || "etl_date".equals(b)) {
                    continue;
                }
                ddlSql("alter table " + e + "  modify " + b + " text");

            }
        }
    }


    public static void main(String[] args) {
        showtablename();
    }
}
