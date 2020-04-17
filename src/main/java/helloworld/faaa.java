package helloworld;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tools.duxiewenjian.FileTools.readLine;
import static tools.pinying.DuoYinZi.getFirstPinYin;

public class faaa {
    public static void main(String[] args) {
        List<String> eachLine = readLine("/Users/everywherewego/IdeaProjects/myutils/src/main/java/tools/sql/createtable/aaa.txt");
        Map<String, String> map = new HashMap<>();
        for (String line : eachLine) {
            //每一行针对空格分隔
            if (line.length() == 0) {
                continue;
            }
            String tablename = line.split("=")[0].trim().replace("#", "");
            map.put("sys_paiwuxuke_" + getFirstPinYin(tablename), "\"sys_paiwuxuke_\" + getFirstPinYin(\"" + tablename + "\")");
        }
        System.out.println(map);
    }


}
