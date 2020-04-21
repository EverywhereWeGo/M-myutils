package tools.httpretry;

/**
 * @author wangchong
 */
public interface Callback {
    /**
     * 检查返回值是否能解析出数据
     *
     * @param result html页面
     * @return 返回代码
     */
    int checkResult(String result);
}