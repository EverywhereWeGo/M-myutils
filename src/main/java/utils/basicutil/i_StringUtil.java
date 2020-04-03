package utils.basicutil;

public class i_StringUtil {
    public static String getLastSubString(String str, String startstr, String endstr) {
        int start = str.lastIndexOf(startstr);
        int end = str.lastIndexOf(endstr);
        if ((-1 == start) || (-1 == end)) {
            System.out.println("未找到起始子串或结束子串");
            return null;
        }
        return str.substring(start + startstr.length(), end);
    }

    public static String getFirstSubString(String str, String startstr, String endstr) {
        int start = str.indexOf(startstr);
        int end = str.indexOf(endstr);
        if ((-1 == start) || (-1 == end)) {
            System.out.println("未找到起始子串或结束子串");
            return null;
        }
        return str.substring(start + startstr.length(), end);
    }

    //首字母变大写
    public static String firsttoUpperCase(String str) {
        char[] strChar = str.toCharArray();
        strChar[0] -= 32;
        return String.valueOf(strChar);
    }

    public static String concatString(String[] strs, String charset) {
        StringBuffer sb = new StringBuffer();
        for (String p : strs) {
            sb.append(p).append(charset);
        }
        if (charset.length() == 0) {
            return sb.toString();
        } else {
            return sb.deleteCharAt(sb.length() - 1).toString();
        }
    }
}
