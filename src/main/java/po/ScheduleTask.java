package po;

public class ScheduleTask {
    //主键
    private String id;
    //任名称
    private String name;
    //任务sql
    private String task_sql;
    //调度配置详情
    private String cron_json;
    //模型id
    private String model_id;
    //任务执行状态1：执行中2：已完成 3：失败
    private String status;
    //开始时间
    private String start_time;
    //结束时间
    private String end_time;
    //最近执行时间
    private String exec_time;
    //创建人id
    private String create_user;
    //更新人id
    private String update_user;
    //创建时间
    private String create_time;
    //更新时间
    private String update_time;
    //逻辑删除标识位，1：删除 0：可用
    private String is_delete;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getTask_sql() { return task_sql; }
    public String getCron_json() { return cron_json; }
    public String getModel_id() { return model_id; }
    public String getStatus() { return status; }
    public String getStart_time() { return start_time; }
    public String getEnd_time() { return end_time; }
    public String getExec_time() { return exec_time; }
    public String getCreate_user() { return create_user; }
    public String getUpdate_user() { return update_user; }
    public String getCreate_time() { return create_time; }
    public String getUpdate_time() { return update_time; }
    public String getIs_delete() { return is_delete; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setTask_sql(String task_sql) { this.task_sql = task_sql; }
    public void setCron_json(String cron_json) { this.cron_json = cron_json; }
    public void setModel_id(String model_id) { this.model_id = model_id; }
    public void setStatus(String status) { this.status = status; }
    public void setStart_time(String start_time) { this.start_time = start_time; }
    public void setEnd_time(String end_time) { this.end_time = end_time; }
    public void setExec_time(String exec_time) { this.exec_time = exec_time; }
    public void setCreate_user(String create_user) { this.create_user = create_user; }
    public void setUpdate_user(String update_user) { this.update_user = update_user; }
    public void setCreate_time(String create_time) { this.create_time = create_time; }
    public void setUpdate_time(String update_time) { this.update_time = update_time; }
    public void setIs_delete(String is_delete) { this.is_delete = is_delete; }
}