package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class AttendanceState {
    /*出勤状态id*/
    private Integer attendanceStateId;
    public Integer getAttendanceStateId(){
        return attendanceStateId;
    }
    public void setAttendanceStateId(Integer attendanceStateId){
        this.attendanceStateId = attendanceStateId;
    }

    /*出勤状态名称*/
    @NotEmpty(message="出勤状态名称不能为空")
    private String attendanceStateName;
    public String getAttendanceStateName() {
        return attendanceStateName;
    }
    public void setAttendanceStateName(String attendanceStateName) {
        this.attendanceStateName = attendanceStateName;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonAttendanceState=new JSONObject(); 
		jsonAttendanceState.accumulate("attendanceStateId", this.getAttendanceStateId());
		jsonAttendanceState.accumulate("attendanceStateName", this.getAttendanceStateName());
		return jsonAttendanceState;
    }}