package utils.basicutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class c_Dateutil {
    public static boolean isRightDateStr(String dateStr, String strformat) {
        boolean convertSuccess = true;
        SimpleDateFormat format = new SimpleDateFormat(strformat);
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(dateStr);
        } catch (ParseException e) {
            convertSuccess = false;
        }
        return convertSuccess;
    }

    /**
     * 字符串转Date方法
     *
     * @param dateStr
     * @param format  如yyyy-MM-dd HH:mm:ss等
     * @return
     * @throws Exception
     */
    public static Date stringToDate(String dateStr, String format) throws Exception {
        if (dateStr == null || "".equals(dateStr)) {
            throw new Exception("stringToDate:要转换的日期参数为空！");
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
    }

    /**
     * Date转字符串方法
     *
     * @param date
     * @param format 如yyyy-MM-dd HH:mm:ss等
     * @return
     * @throws Exception
     */
    public static String dateToString(Date date, String format) throws Exception {
        if (date == null) {
            throw new Exception("dateToString:要转换的日期参数为空！");
        }
        String dateStr = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            dateStr = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateStr;
    }
}
