package helloworld;

import com.sun.deploy.net.URLEncoder;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class JunitTestURLcode {
    public static void main(String[] args) throws UnsupportedEncodingException {

        //将application/x-www-form-urlencoded字符串转换成普通字符串
        //采用UTF-8字符集进行解码
        System.out.println(URLDecoder.decode("%E5%8C%97%E4%BA%AC%E5%A4%A7%E5%AD%A6", "UTF-8"));
        //采用GBK字符集进行解码
        System.out.println(URLDecoder.decode("%B1%B1%BE%A9%B4%F3%D1%A7", "GBK"));

        // 将普通字符串转换成application/x-www-form-urlencoded字符串
        //采用utf-8字符集
        System.out.println(URLEncoder.encode("北京大学", "UTF-8"));
        //采用GBK字符集
        System.out.println(URLEncoder.encode("北京大学", "GBK"));
    }

}