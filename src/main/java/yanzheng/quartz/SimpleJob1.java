package yanzheng.quartz;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * job1任务类
 */
public class SimpleJob1 implements Job {

    private static Logger LOG = LoggerFactory.getLogger(SimpleJob1.class);

    // 必须要有public修饰的无参构造函数
    public SimpleJob1() {
    }

    // 定时器执行方法
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        LOG.info("---------- 作业1执行。SimpleJob1 says: " + jobKey + " executing at " + new Date());
    }

}