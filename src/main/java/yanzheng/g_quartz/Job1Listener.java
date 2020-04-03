package yanzheng.g_quartz;


import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * job1任务监听器
 */
public class Job1Listener implements JobListener {

    private static Logger LOG = LoggerFactory.getLogger(Job1Listener.class);

    public String getName() {
        return "job1_to_job2";
    }

    public void jobToBeExecuted(JobExecutionContext inContext) {
        LOG.info("Job1Listener says: 作业即将执行.");
    }

    //当org.quartz被调度程序调用时。JobDetailwas即将执行(一个关联的Triggerhas发生)，但是TriggerListener否决了它的执行。
    public void jobExecutionVetoed(JobExecutionContext inContext) {
        LOG.info("Job1Listener says: 作业执行被否决.");
    }

    public void jobWasExecuted(JobExecutionContext inContext, JobExecutionException inException) {
        LOG.info("Job1Listener says: 作业已执行.");
        // Simple job #2
        JobDetail job2 = newJob(SimpleJob2.class).withIdentity("job2").build();
        Trigger job2Trigger = newTrigger().withIdentity("job2Trigger").startNow().build();

        try {
            //去执行job2
            inContext.getScheduler().scheduleJob(job2, job2Trigger);
        } catch (SchedulerException e) {
            LOG.warn("Unable to schedule job2!");
            e.printStackTrace();
        }

    }

}