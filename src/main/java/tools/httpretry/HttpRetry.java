package tools.httpretry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static utils.HttpClientHelper.sendGet;
import static utils.HttpClientHelper.sendPost;
import static utils.basicutil.j_SendMailUtil.smtpSSLSend;

public class HttpRetry {

    private static Logger logger = LoggerFactory.getLogger(HttpRetry.class);

    /**
     * 失败重试次数
     */
    public static int retryTime = 5;
    /**
     * 多少次请求暂停一下
     */
    public static int collect = 10;
    /**
     * 每次暂停几秒
     */
    public static int waitTime = 1000;
    /**
     * 用于控制请求次数与collect一起使用
     */
    public static int flag = 0;

    public static Integer execute(String urlname, Map<String, String> requestHeader, Map<String, String> params, String body, Callback callBack, Map<String, String> config, String requestType) {
        flag++;
        //尝试retryTime次
        for (int i = 0; i < retryTime; i++) {
            String result = null;
            try {
                //每次失败等待1,4,9,16,25秒
                Thread.sleep(1000 + i * i * 1000 * 10);

                //统一在这里处理。每5次请求暂停1s
                if (flag % collect == 0) {
                    flag = 0;
                    Thread.sleep(waitTime);
                }

                if ("POST".equals(requestType)) {
                    result = sendPost(urlname, requestHeader, params);
                }

                if ("GET".equals(requestType)) {
                    Map<String, String> re = sendGet(urlname, requestHeader);
                    result = re.get("responseContext");
                }

                int f = callBack.checkResult(result);
                //返回1表示,已经到最后一页了
                if (3 == f) {
                    throw new Exception();
                } else {
                    return f;
                }
            } catch (Exception e) {
                logger.info(result);
                logger.warn("url:" + urlname + ",请求或者返回值异常,尝试次数:" + (i + 1));
                logger.warn("请求或者返回值异常,尝试次数:" + (i + 1));
                if (i == retryTime - 1) {
                    smtpSSLSend("排污许可证平台");
                    System.exit(1);
                }
            }
        }
        return null;
    }
}
