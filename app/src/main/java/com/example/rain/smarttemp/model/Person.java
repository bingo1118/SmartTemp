package com.example.rain.smarttemp.model;

public class Person {

    private static final long serialVersionUID = 1L;

    private int id;
    private String personid;
    private String named;//名称
    private int sexId;//性别id 0/1 男/女
    private String sex;//性别(男/女)
    private String departmentId;//部门id
    private String department;//部门
    private String temperature;//最近一次测量的体温值
    private int tempState;//体温状态 1/2 正常/异常
    private String tempraturestate;//体温状态(正常/异常)
    private String lastpasstime;//最近一次通行时间
    private String areaId;//区域id
    private String area;//区域名称
    private String personReg;//

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPersonid() {
        return personid;
    }
    public void setPersonid(String personid) {
        this.personid = personid;
    }
    public String getNamed() {
        return named;
    }
    public void setNamed(String named) {
        this.named = named;
    }
    public int getSexId() {
        return sexId;
    }
    public void setSexId(int sexId) {
        this.sexId = sexId;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getDepartment() {
        return department;
    }
    public void setDepartment(String department) {
        this.department = department;
    }
    public String getTemperature() {
        return temperature;
    }
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
    public String getTempraturestate() {
        return tempraturestate;
    }
    public void setTempraturestate(String tempraturestate) {
        this.tempraturestate = tempraturestate;
    }
    public String getLastpasstime() {
        return lastpasstime;
    }
    public void setLastpasstime(String lastpasstime) {
        this.lastpasstime = lastpasstime;
    }
    public String getAreaId() {
        return areaId;
    }
    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    public int getTempState() {
        return tempState;
    }
    public void setTempState(int tempState) {
        this.tempState = tempState;
    }
    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getPersonReg() {
        return personReg;
    }
    public void setPersonReg(String personReg) {
        this.personReg = personReg;
    }


}
