package utils.basicUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class c_Dateutil {
    public static boolean isRightDateStr(String dateStr) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(dateStr);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
