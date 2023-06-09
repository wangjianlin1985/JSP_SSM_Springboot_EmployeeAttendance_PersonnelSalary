package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class LeaveType {
    /*请假类型id*/
    private Integer leaveTypeId;
    public Integer getLeaveTypeId(){
        return leaveTypeId;
    }
    public void setLeaveTypeId(Integer leaveTypeId){
        this.leaveTypeId = leaveTypeId;
    }

    /*请假类型名称*/
    @NotEmpty(message="请假类型名称不能为空")
    private String leaveTypeName;
    public String getLeaveTypeName() {
        return leaveTypeName;
    }
    public void setLeaveTypeName(String leaveTypeName) {
        this.leaveTypeName = leaveTypeName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonLeaveType=new JSONObject(); 
		jsonLeaveType.accumulate("leaveTypeId", this.getLeaveTypeId());
		jsonLeaveType.accumulate("leaveTypeName", this.getLeaveTypeName());
		return jsonLeaveType;
    }}