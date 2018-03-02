package com.pay.database.mybatis.mapper;

public class ScheduleExecuteLogDtm {
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
}
