package tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tools.duxiewenjian.FileTools.readLine;
import static tools.duxiewenjian.FileTools.writeFile;
import static tools.pinying.DuoYinZi.getFirstPinYin;
import static utils.basicutil.i_StringUtil.getFirstSubString;

/**
 * @author everywherewego
 * @date 2020-04-17 16:27
 */

public class ReplaceByLine {
    static Map<String, String> map = new HashMap<>();

    public static void replaceByLine(String filepath) {
        List<String> list = readLine(filepath);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.contains("sys_paiwuxuke_")) {
                System.out.println(i + ":" + s);
                String context = getFirstSubString(s, "\"", "\";");
                if (map.containsKey(context)) {
                    sb.append(s.replace("\"" + context + "\"", map.get(context))).append("\n");
                }
            } else {
                sb.append(s).append("\n");
            }
        }
        writeFile(filepath, sb.toString());
    }


    public static void main(String[] args) {
        List<String> eachLine = readLine("/Users/everywherewego/IdeaProjects/myutils/src/main/java/tools/sql/createtable/aaa.txt");
        for (String line : eachLine) {
            //每一行针对空格分隔
            if (line.length() == 0) {
                continue;
            }
            String tablename = line.split("=")[0].trim().replace("#", "");
            map.put("sys_paiwuxuke_" + getFirstPinYin(tablename), "\"sys_paiwuxuke_\" + getFirstPinYin(\"" + tablename + "\")");
        }

        replaceByLine("/Users/everywherewego/IdeaProjects/myutils/src/main/java/helloworld/asdf");
    }
}
