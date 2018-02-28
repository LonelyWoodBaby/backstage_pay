package com.pay.schedule.entity;

/**
 * 定时任务执行结果记录
 * @author Lee Yarbin
 */
public class ScheduleExecuteLog {
    /**
     * 执行记录ID，使用当前时间的time值作为ID值，防止重复
     */
    private String executeLogId;
    /**
     * 执行的方法，需要填入全路径
     */
    private String jobClassName;

    /**
     * 定时任务的组名
     */
    private String jobGroupName;

    /**
     * 总执行时间，计时单位为微秒
     */
    private long executeTime;
    /**
     * 发生时间，也就是该执行的开始时间，时间精确到毫秒
     */
    private String occurTime;
    /**
     * 结束时间，也就是该执行完毕时间，时间精确到毫秒
     */
    private String endTime;

    /**
     * 执行状态，未执行为0，执行中为1，成功为2，其他为失败。默认失败值为9
     */
    private String exeStatus;

    /**
     * 如果出现错误，则记录异常信息
     */
    private String exceptionMsg;

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getExeStatus() {
        return exeStatus;
    }

    public void setExeStatus(String exeStatus) {
        this.exeStatus = exeStatus;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExecuteLogId() {
        return executeLogId;
    }

    public void setExecuteLogId(String executeLogId) {
        this.executeLogId = executeLogId;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }
}
