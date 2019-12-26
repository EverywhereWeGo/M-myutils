package helloworld;

import java.util.Date;

public class CronExpress {

  private Date startDate;
  private Date endDate;
  private Date realStartDate;
  private String cron;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRealStartDate() {
        return realStartDate;
    }

    public void setRealStartDate(Date realStartDate) {
        this.realStartDate = realStartDate;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    @Override
    public String toString() {
        return "CronExpress{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", realStartDate=" + realStartDate +
                ", cron='" + cron + '\'' +
                '}';
    }
}
