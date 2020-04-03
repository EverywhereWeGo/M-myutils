package tools.pinying;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static utils.basicutil.i_StringUtil.concatString;

public class DuoYinZi {
    private static Map<String, List<String>> pinyinMap = new HashMap<String, List<String>>();

    static {
        try {
            String filePath = "/Users/everywherewego/IdeaProjects/myutils/src/main/java/tools/pinying/dict.txt";
            InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String s = null;
            try {
                while ((s = br.readLine()) != null) {
                    if (s != null) {
                        String[] arr = s.split("=");
                        String pinyin = arr[0].trim();
                        String chinese = arr[1];
                        if (chinese != null) {
                            String[] strs = chinese.split(",");
                            for (int i = 0; i < strs.length; i++) {
                                strs[i] = strs[i].trim();
                            }
                            List<String> list = Arrays.asList(strs);
                            pinyinMap.put(pinyin, list);

                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getPinYin(String hanyu, String charset) {
        String[] pinying = transformToPinYin(hanyu);
        //对拼音进行处理，比如转为驼峰或者加下划线
        String filed = concatString(pinying, charset);
        return filed;
    }

    public static String getPinYin(String hanyu) {
        String[] pinying = transformToPinYin(hanyu);
        //对拼音进行处理，比如转为驼峰或者加下划线
        String filed = concatString(pinying, "");
        return filed;
    }

    public static String getFirstPinYin(String hanyu) {
        StringBuffer sb = new StringBuffer();
        String[] pinying = transformToPinYin(hanyu);
        for (String obj : pinying) {
            sb.append(obj.charAt(0));
        }
        return sb.toString();
    }

    private static String[] transformToPinYin(String chinese) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        //存放汉字
        String[] arr = new String[chinese.length()];
        for (int i = 0; i < chinese.length(); i++) {
            arr[i] = chinese.substring(i, i + 1);
        }

        //存放拼音
        String[] result = new String[arr.length];

        //开始遍历语句中的每一个汉字
        for (int i = 0; i < arr.length; i++) {
            //如果是汉字
            if (arr[i].matches("[\\u4E00-\\u9FA5]+")) {
                String[] pys = new String[0];
                try {
                    pys = PinyinHelper.toHanyuPinyinStringArray(arr[i].charAt(0), format);
                } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                    badHanyuPinyinOutputFormatCombination.printStackTrace();
                }
                //去重
                List<String> list = new LinkedList<String>();
                for (String py : pys) {
                    if (!list.contains(py)) {
                        list.add(py);
                    }
                }

                if (list.size() == 1) {
                    result[i] = list.get(0);
                } else {
                    //遍历该字的每一个读音
                    boolean flag = false;
                    for (String s : list) {
                        if (pinyinMap.containsKey(s)) {
                            List<String> phrase = pinyinMap.get(s);
                            //连上后一个字
                            if (i + 1 < chinese.length()) {
                                String concat = chinese.substring(i, i + 2);
                                if (phrase.contains(concat)) {
                                    result[i] = s;
                                    flag = true;
                                    break;
                                }
                            }
                            //连上前一个字
                            if (0 <= i - 1) {
                                String concat = chinese.substring(i - 1, i + 1);
                                if (phrase.contains(concat)) {
                                    result[i] = s;
                                    flag = true;
                                    break;
                                }
                            }
                            //单字被包含,表示单字时的常用读音,如"否"
                            if (phrase.contains(arr[i])) {
                                result[i] = s;
                                flag = true;
                                break;
                            }
                        }
                    }
                    //如果遍历所有的读音都没有,则直接返回原字符
                    if (!flag) {
                        result[i] = arr[i];
                    }
                }
            } else {
                //如果不是汉字,直接返回原字符
                result[i] = arr[i];
            }
        }
        return result;
    }


    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException, BadHanyuPinyinOutputFormatCombination {
        System.out.println(getPinYin("朝阳"));
//        System.out.println(Arrays.toString(getPinYin("否")));

//        System.out.println(Arrays.toString(getPinYin("单于夜遁逃")));

    }
}