package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Attendance {
    /*出勤id*/
    private Integer attendanceId;
    public Integer getAttendanceId(){
        return attendanceId;
    }
    public void setAttendanceId(Integer attendanceId){
        this.attendanceId = attendanceId;
    }

    /*出勤日期*/
    @NotEmpty(message="出勤日期不能为空")
    private String attendanceDate;
    public String getAttendanceDate() {
        return attendanceDate;
    }
    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    /*考勤员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*出勤结果*/
    private AttendanceState attendanceState;
    public AttendanceState getAttendanceState() {
        return attendanceState;
    }
    public void setAttendanceState(AttendanceState attendanceState) {
        this.attendanceState = attendanceState;
    }

    /*备注信息*/
    private String attendanceMemo;
    public String getAttendanceMemo() {
        return attendanceMemo;
    }
    public void setAttendanceMemo(String attendanceMemo) {
        this.attendanceMemo = attendanceMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonAttendance=new JSONObject(); 
		jsonAttendance.accumulate("attendanceId", this.getAttendanceId());
		jsonAttendance.accumulate("attendanceDate", this.getAttendanceDate().length()>19?this.getAttendanceDate().substring(0,19):this.getAttendanceDate());
		jsonAttendance.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonAttendance.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonAttendance.accumulate("attendanceState", this.getAttendanceState().getAttendanceStateName());
		jsonAttendance.accumulate("attendanceStatePri", this.getAttendanceState().getAttendanceStateId());
		jsonAttendance.accumulate("attendanceMemo", this.getAttendanceMemo());
		return jsonAttendance;
    }}