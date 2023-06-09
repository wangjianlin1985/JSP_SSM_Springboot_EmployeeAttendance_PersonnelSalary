package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Manager {
    /*经理用户名*/
    @NotEmpty(message="经理用户名不能为空")
    private String managerUserName;
    public String getManagerUserName(){
        return managerUserName;
    }
    public void setManagerUserName(String managerUserName){
        this.managerUserName = managerUserName;
    }

    /*登录密码*/
    @NotEmpty(message="登录密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*所属部门*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*姓名*/
    @NotEmpty(message="姓名不能为空")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*出生日期*/
    @NotEmpty(message="出生日期不能为空")
    private String bornDate;
    public String getBornDate() {
        return bornDate;
    }
    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonManager=new JSONObject(); 
		jsonManager.accumulate("managerUserName", this.getManagerUserName());
		jsonManager.accumulate("password", this.getPassword());
		jsonManager.accumulate("departmentObj", this.getDepartmentObj().getDepartmentName());
		jsonManager.accumulate("departmentObjPri", this.getDepartmentObj().getDepartmentNo());
		jsonManager.accumulate("name", this.getName());
		jsonManager.accumulate("sex", this.getSex());
		jsonManager.accumulate("bornDate", this.getBornDate().length()>19?this.getBornDate().substring(0,19):this.getBornDate());
		jsonManager.accumulate("telephone", this.getTelephone());
		return jsonManager;
    }}