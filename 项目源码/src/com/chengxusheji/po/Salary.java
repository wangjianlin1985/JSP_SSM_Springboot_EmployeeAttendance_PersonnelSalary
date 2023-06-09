package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Salary {
    /*工资id*/
    private Integer salaryId;
    public Integer getSalaryId(){
        return salaryId;
    }
    public void setSalaryId(Integer salaryId){
        this.salaryId = salaryId;
    }

    /*员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*工资年份*/
    @NotEmpty(message="工资年份不能为空")
    private String salaryYear;
    public String getSalaryYear() {
        return salaryYear;
    }
    public void setSalaryYear(String salaryYear) {
        this.salaryYear = salaryYear;
    }

    /*工资月份*/
    @NotEmpty(message="工资月份不能为空")
    private String salaryMonth;
    public String getSalaryMonth() {
        return salaryMonth;
    }
    public void setSalaryMonth(String salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    /*基本工资*/
    @NotNull(message="必须输入基本工资")
    private Float basicWage;
    public Float getBasicWage() {
        return basicWage;
    }
    public void setBasicWage(Float basicWage) {
        this.basicWage = basicWage;
    }

    /*考勤扣除*/
    @NotNull(message="必须输入考勤扣除")
    private Float atttendanceReduce;
    public Float getAtttendanceReduce() {
        return atttendanceReduce;
    }
    public void setAtttendanceReduce(Float atttendanceReduce) {
        this.atttendanceReduce = atttendanceReduce;
    }

    /*实得工资*/
    @NotNull(message="必须输入实得工资")
    private Float realSalary;
    public Float getRealSalary() {
        return realSalary;
    }
    public void setRealSalary(Float realSalary) {
        this.realSalary = realSalary;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSalary=new JSONObject(); 
		jsonSalary.accumulate("salaryId", this.getSalaryId());
		jsonSalary.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonSalary.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonSalary.accumulate("salaryYear", this.getSalaryYear());
		jsonSalary.accumulate("salaryMonth", this.getSalaryMonth());
		jsonSalary.accumulate("basicWage", this.getBasicWage());
		jsonSalary.accumulate("atttendanceReduce", this.getAtttendanceReduce());
		jsonSalary.accumulate("realSalary", this.getRealSalary());
		return jsonSalary;
    }}