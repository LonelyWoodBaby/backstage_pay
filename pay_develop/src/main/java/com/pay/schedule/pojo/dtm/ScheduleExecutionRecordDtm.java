package com.pay.schedule.pojo.dtm;

import com.pay.beans.dictionary.base.BaseBean;
import com.pay.beans.dictionary.data.ScheduleExecutionStateDict;
import com.pay.beans.rules.entity.ConvertNameBean;
import com.pay.beans.rules.FormatRule;
import com.pay.beans.rules.rulehelper.ConvertNameHelper;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.*;

/**
 * @author LiYabin
 * 定时任务执行记录
 */
@Table(name="Schedule_Execution_Record")
public class ScheduleExecutionRecordDtm extends BaseBean implements ConvertNameHelper{
    @Id
    private String recordId;
    private String jobClassName;
    private String jobGroupName;
    @ColumnType(column = "execution_time",
                jdbcType = JdbcType.INTEGER)
    private Long executionTime;
    private String occurTime;
    private String endTime;
    private String exceptionMsg;
    private String executionState;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public Long getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(Long executionTime) {
        this.executionTime = executionTime;
    }

    public String getOccurTime() {
        return occurTime;
    }

    public void setOccurTime(String occurTime) {
        this.occurTime = occurTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
    }

    public String getExecutionState() {
        return executionState;
    }

    public void setExecutionState(String executionState) {
        this.executionState = executionState;
    }

    @Override
    public Map<String, Class> getMappingDictionaryConfig() {
        Map<String,Class> map = new HashMap<>();
        map.put("executionState",ScheduleExecutionStateDict.class);
        return map;
    }

    @Override
    public List<ConvertNameBean> convertToValueByName() {
        List<ConvertNameBean> convertList = new ArrayList<>();
        convertList.add(new ConvertNameBean("occurTime", FormatRule.formatDateRule("yyyy_MM_dd:HH_mm_ss:SSS")));
        convertList.add(new ConvertNameBean("endTime", FormatRule.formatDateRule("yyyy_MM_dd:HH_mm_ss:SSS")));
        return convertList;
    }

    @Override
    public List<ConvertNameBean> convertToFiledByName() {
        return null;
    }
}
