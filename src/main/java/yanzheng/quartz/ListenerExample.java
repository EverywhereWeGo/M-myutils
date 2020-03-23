package yanzheng.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 演示job任务添加监听器，监听任务执行前、后手动处理方法
 */
public class ListenerExample {
    Logger LOG = LoggerFactory.getLogger(ListenerExample.class);

    public void run() throws Exception {
        // 初始化一个调度工厂，并实例化一个调度类
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        // 作业将被立即执行
        JobDetail job = newJob(SimpleJob1.class).withIdentity("job1").build();
        Trigger trigger = newTrigger().withIdentity("trigger1").startNow().build();
        // 设置job1任务监听器
        JobListener listener = new Job1Listener();
        Matcher<JobKey> matcher = KeyMatcher.keyEquals(job.getKey());
        sched.getListenerManager().addJobListener(listener, matcher);
        sched.scheduleJob(job, trigger);

        sched.start();

        LOG.info("------- Waiting 30 seconds... --------------");
        Thread.sleep(30L * 1000L);

        sched.shutdown(true);
        LOG.info("------- Shutdown Complete -----------------");

        SchedulerMetaData metaData = sched.getMetaData();
        LOG.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");

    }

    public static void main(String[] args) throws Exception {

        ListenerExample example = new ListenerExample();
        example.run();
    }

}