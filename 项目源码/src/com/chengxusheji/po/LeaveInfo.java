package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class LeaveInfo {
    /*请假id*/
    private Integer leaveId;
    public Integer getLeaveId(){
        return leaveId;
    }
    public void setLeaveId(Integer leaveId){
        this.leaveId = leaveId;
    }

    /*请假类型*/
    private LeaveType leaveTypeObj;
    public LeaveType getLeaveTypeObj() {
        return leaveTypeObj;
    }
    public void setLeaveTypeObj(LeaveType leaveTypeObj) {
        this.leaveTypeObj = leaveTypeObj;
    }

    /*请假标题*/
    @NotEmpty(message="请假标题不能为空")
    private String leaveTitle;
    public String getLeaveTitle() {
        return leaveTitle;
    }
    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

    /*请假内容*/
    @NotEmpty(message="请假内容不能为空")
    private String leaveContent;
    public String getLeaveContent() {
        return leaveContent;
    }
    public void setLeaveContent(String leaveContent) {
        this.leaveContent = leaveContent;
    }

    /*请假时长*/
    @NotEmpty(message="请假时长不能为空")
    private String days;
    public String getDays() {
        return days;
    }
    public void setDays(String days) {
        this.days = days;
    }

    /*请假员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*提交时间*/
    @NotEmpty(message="提交时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /*审核状态*/
    @NotEmpty(message="审核状态不能为空")
    private String shenHeState;
    public String getShenHeState() {
        return shenHeState;
    }
    public void setShenHeState(String shenHeState) {
        this.shenHeState = shenHeState;
    }

    /*负责人回复*/
    private String replyContent;
    public String getReplyContent() {
        return replyContent;
    }
    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonLeaveInfo=new JSONObject(); 
		jsonLeaveInfo.accumulate("leaveId", this.getLeaveId());
		jsonLeaveInfo.accumulate("leaveTypeObj", this.getLeaveTypeObj().getLeaveTypeName());
		jsonLeaveInfo.accumulate("leaveTypeObjPri", this.getLeaveTypeObj().getLeaveTypeId());
		jsonLeaveInfo.accumulate("leaveTitle", this.getLeaveTitle());
		jsonLeaveInfo.accumulate("leaveContent", this.getLeaveContent());
		jsonLeaveInfo.accumulate("days", this.getDays());
		jsonLeaveInfo.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonLeaveInfo.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonLeaveInfo.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		jsonLeaveInfo.accumulate("shenHeState", this.getShenHeState());
		jsonLeaveInfo.accumulate("replyContent", this.getReplyContent());
		return jsonLeaveInfo;
    }}