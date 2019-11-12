package utils.basicutil;

public class i_StringUtil {
    public static String getSubString(String str, String startstr, String endstr) {
        if ((-1 == str.lastIndexOf(startstr)) || (-1 == str.lastIndexOf(endstr))) {
            System.out.println("未找到起始子串或结束子串");
            return null;
        }
        return str.substring(str.lastIndexOf(startstr) + startstr.length(), str.lastIndexOf(endstr));
    }
}
