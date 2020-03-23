package yanzheng.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * job2任务类
 */
public class SimpleJob2 implements Job {

    private static Logger LOG = LoggerFactory.getLogger(SimpleJob2.class);

    // 必须要有public修饰的无参构造函数
    public SimpleJob2() {
    }

    // 定时器执行方法
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        LOG.info("---------- 作业2执行。SimpleJob2 says: " + jobKey + " executing at " + new Date());
    }

}