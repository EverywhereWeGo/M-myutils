package tools.duxiewenjian.replacebyline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tools.duxiewenjian.BianliWenJianJia.getFiles;
import static tools.duxiewenjian.FileTools.readLine;
import static tools.pinying.DuoYinZi.getFirstPinYin;
import static utils.basicutil.i_StringUtil.getFirstSubString;

/**
 * @author everywherewego
 * @date 2020-04-17 16:27
 */

public class ReplaceByLine {
    static Map<String, String> map = new HashMap<>();

    public static void replaceByLine(String filepath, Callback callback) {
        List<String> list = readLine(filepath);
        StringBuffer sb = new StringBuffer();
        for (String s : list) {
            callback.eachline(s, sb);
        }
        System.out.println(sb.toString());
//        writeFile(filepath, sb.toString());
    }


    public static void main(String[] args) throws Exception {
        List<String> eachLine = readLine("/Users/everywherewego/IdeaProjects/myutils/src/main/java/tools/sql/createtable/aaa.txt");
        for (String line : eachLine) {
            //每一行针对空格分隔
            if (line.length() == 0) {
                continue;
            }
            String tablename = line.split("=")[0].trim().replace("#", "");
            map.put("sys_paiwuxuke_" + getFirstPinYin(tablename), "\"sys_paiwuxuke_\" + getFirstPinYin(\"" + tablename + "\")");
        }

        ArrayList<String> files = getFiles("/Users/everywherewego/IdeaProjects/spiderinit/王冲_排污许可/src/main/java/com/bfd");
        replaceByLine("/Users/everywherewego/IdeaProjects/myutils/src/main/java/helloworld/asdf", new Callback() {
            @Override
            public StringBuffer eachline(String s, StringBuffer sb) {
                if (s.contains("sys_paiwuxuke_")) {
                    String context = null;
                    try {
                        context = getFirstSubString(s, "\"", "\";");
                        if (map.containsKey(context)) {
                            sb.append(s.replace("\"" + context + "\"", map.get(context))).append("\n");
                        }
//                            System.out.println(i + ":" + s);
                    } catch (Exception e) {

                        sb.append(s).append("\n");
                    }

                } else {
                    sb.append(s).append("\n");
                }
                return sb;
            }
        });
    }
}
