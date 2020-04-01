package tools;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;

import static utils.basicutil.f_SqlUtil.querySql;

public class CreateSqlByConnection {
    static String databaseName = "spider";

    public static void createInsertSql(String tableName, LinkedList<String> fields) {
        String modelsql = "insert into {0} ({1}) values ({2}) ";
        StringBuffer fi = new StringBuffer();
        StringBuffer va = new StringBuffer();
        for (String field : fields) {
            fi.append(field).append(",");
            va.append("?,");
        }
        fi.deleteCharAt(fi.length() - 1);
        va.deleteCharAt(va.length() - 1);
        String sql = modelsql.replace("{0}", tableName).replace("{1}", fi).replace("{2}", va);
        System.out.println(sql);




    }



    public static void main(String[] args) {
        JSONArray a = querySql("show tables");
        for (Object obj : a) {
            JSONObject jsonObject = (JSONObject) obj;
            String tablename = jsonObject.getString("Tables_in_" + databaseName);
            JSONArray tables = querySql("desc " + tablename);

            LinkedList<String> fileds = new LinkedList<>();
            for (Object ob : tables) {
                JSONObject jsonObject1 = (JSONObject) ob;
                String b = jsonObject1.getString("Field");
                fileds.add(b);
            }
            createInsertSql(tablename, fileds);
        }
    }
}
