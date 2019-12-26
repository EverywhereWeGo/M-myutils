package helloworld;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


@Service
public class QuartzService {
    public static final String START_DATE = "startDate";
    public static final String END_DATE = "endDate";
    public static final String REAL_START_DATE = "realStartDate";
    public static final String TYPE = "type";
    public static final String DAY_TIME = "dayTime";

    public static final String CRON = "cron";
    public static final String TASK_ID = "taskId";
    public static final String EMAILS = "emails";

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzService.class);

    @Autowired
    @Qualifier("qzScheduler")
    private Scheduler scheduler;

    public String cronTrigger(String taskId, String requestStr, String emails) {
        JobKey jobKey = getJobKey(taskId);
        TriggerKey triggerKey = getTriggerKey(taskId);
        CronExpress cronExpress = getCronExpression(requestStr);
        Date startDate = cronExpress.getStartDate();
        Date realStartDate = cronExpress.getRealStartDate();
        Date endDate = cronExpress.getEndDate();
        String cron = cronExpress.getCron();
        if (startDate.getTime() > endDate.getTime()) {
            LOGGER.error("Start time or end time error");
            return "Failed";
        }
        try {
            if (scheduler.checkExists(jobKey)) {
                LOGGER.warn("Job or trigger exist");
                removeTask(taskId);
            }
            JobDetail jobDetail = JobBuilder.newJob(CronJob.class)
                    .storeDurably(true)
                    .withIdentity(jobKey.getName(), jobKey.getGroup())
                    .usingJobData(TASK_ID, taskId)
                    .usingJobData(EMAILS, emails)
                    .usingJobData(START_DATE, startDate.toString())
                    .usingJobData(REAL_START_DATE, realStartDate.toString())
                    .usingJobData(END_DATE, endDate.toString())
                    .usingJobData(CRON, cron)
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey.getName(), triggerKey.getGroup())
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron/*" * * * * * ? "*/))
                    .startAt(realStartDate)
                    .endAt(endDate)
                    .forJob(jobDetail)
                    .build();
            LOGGER.info("Schedule com.service.quartz.job: {}...With cronExpression: {}",
                    jobDetail.getKey().toString(), cronExpress.toString());
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            LOGGER.error("CronTrigger task failed, JobKey: {}", jobKey);
            LOGGER.error("Catch Exception: {}", e.getMessage());
            return "Failed";
        }
        return "OK";
    }

    public String removeTask(String taskId) {
        JobKey jobKey = getJobKey(taskId);
        try {
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
            if (scheduler.checkExists(jobKey)) {
                LOGGER.info("JobKey exist, remove task: {}", jobKey, jobKey.getName());
                scheduler.interrupt(jobKey);
                scheduler.deleteJob(jobKey);
            } else {
                LOGGER.error("JobKey not exist, do nothing");
                return "Failed";
            }
        } catch (SchedulerException e) {
            LOGGER.error("Remove task failed, JobKey: {}", jobKey);
            LOGGER.error("Catch Exception: {}", e.getMessage());
            return "Failed";
        }
        return "OK";
    }

    public String execSQL(String taskId) {
        JobKey jobKey = getExecJobKey(taskId);
        TriggerKey triggerKey = getExecTriggerKey(taskId);
        try {
            if (scheduler.checkExists(jobKey)) {
                scheduler.unscheduleJob(triggerKey);
                scheduler.deleteJob(jobKey);
            }
            JobDetail jobDetail = JobBuilder.newJob(ExecJob.class)
                    .storeDurably(true)
                    .withIdentity(jobKey.getName(), jobKey.getGroup())
                    .usingJobData(TASK_ID, taskId)
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey.getName(), triggerKey.getGroup())
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule())
                    .forJob(jobDetail)
                    .startNow()
                    .build();

            if (!scheduler.isStarted()) {
                scheduler.start();
            }
            scheduler.scheduleJob(jobDetail, trigger);
            return "OK";
        } catch (SchedulerException e) {
            LOGGER.error("Exec task failed, JobKey: {}", jobKey);
            LOGGER.error("Catch Exception: {}", e.getMessage());
            return "Failed";
        }
    }

    private static JobKey getJobKey(String taskId) {
        return JobKey.jobKey("JobDetailId_" + taskId, "JobDetailGroup_" + taskId);
    }

    private static TriggerKey getTriggerKey(String taskId) {
        return TriggerKey.triggerKey("TriggerId_" + taskId, "TriggerGroup_" + taskId);
    }

    private static JobKey getExecJobKey(String taskId) {
        return JobKey.jobKey("ExecJobDetailId_" + taskId, "ExecJobDetailGroup_" + taskId);
    }

    private static TriggerKey getExecTriggerKey(String taskId) {
        return TriggerKey.triggerKey("ExecTriggerId_" + taskId, "ExecTriggerGroup_" + taskId);
    }


    private static CronExpress getCronExpression(String requestStr) {
        CronExpress cronExpress = new CronExpress();
        JSONObject cronTab = new JSONObject();
        try {
            cronTab = JSONObject.parseObject(requestStr).getJSONObject("crontab");
        } catch (JSONException e) {
            LOGGER.error("Can't parse the requestStr {}:", requestStr);
            LOGGER.error("Catch exception {}:", e.getMessage());
        }

        System.out.println(cronTab);
        Date startDate = Date.from(Instant.ofEpochMilli(cronTab.getLongValue(START_DATE)));
        Date realStartDate = getRealStartDate(startDate);
        Date endDate = Date.from(Instant.ofEpochMilli(cronTab.getLongValue(END_DATE)));
        String dayTime = cronTab.getString(DAY_TIME);
        String cron = getCron(dayTime);
        if (cronTab.getString(TYPE).equals("DAY")) {
            LOGGER.info("startDate: {}", startDate);
            LOGGER.info("realStartDate: {}", realStartDate);
            LOGGER.info("endDate: {}", endDate);
            LOGGER.info("cron: {}", getCron(dayTime));
            cronExpress.setStartDate(startDate);
            cronExpress.setRealStartDate(realStartDate);
            cronExpress.setEndDate(endDate);
            cronExpress.setCron(cron);
        }
        return cronExpress;
    }

    private static String getCron(String dayTime) {
        String[] dayTimes = dayTime.split(":");
        String minute = String.valueOf(Integer.parseInt(dayTimes[1]));
        String hour = String.valueOf(Integer.parseInt(dayTimes[0]));
        return "0 " + minute + " " + hour + " * * ?";
    }

    /**
     * start time will delay 10 second.
     */
    private static Date getRealStartDate(Date startDate) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        calendar.add(Calendar.SECOND, 10);
        if (startDate.getTime() > now.getTime()) {
            return startDate;
        }
        return now;
    }

    public static void main(String[] args) {
        System.out.println(getCronExpression(
                "{\"crontab\":{\"startDate\":\"1514167200\",\"endDate\":\"1511575200\",\"type\":\"DAY\",\"dayTime\":\"16:54\"}}"));
    }
}
