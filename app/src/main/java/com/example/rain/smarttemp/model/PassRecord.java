package com.example.rain.smarttemp.model;

import java.io.Serializable;

public class PassRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String passrecordId;
    private String personId;
    private String deviceKey;
    private String passTime;
    private String type;
    private String typeName;
    private String img;
    private String searchScore;
    private String livenessScore;
    private String temperature;
    private String standard;
    private String tempratureState;
    private int tempState;

    private String deviceName; // 设备名称
    private String address; //设备所在地址
    private String pname; //人员名字
    private String dname; //部门名字

    private String startTime; //有效期开始时间
    private String endTime; //有效期结束时间
    private String departmentId;

    private String area;
    private String areaId;

    private String alarm;

    private String personType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassrecordId() {
        return passrecordId;
    }

    public void setPassrecordId(String passrecordId) {
        this.passrecordId = passrecordId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public String getPassTime() {
        return passTime;
    }

    public void setPassTime(String passTime) {
        this.passTime = passTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSearchScore() {
        return searchScore;
    }

    public void setSearchScore(String searchScore) {
        this.searchScore = searchScore;
    }

    public String getLivenessScore() {
        return livenessScore;
    }

    public void setLivenessScore(String livenessScore) {
        this.livenessScore = livenessScore;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getTempratureState() {
        return tempratureState;
    }

    public void setTempratureState(String tempratureState) {
        this.tempratureState = tempratureState;
    }

    public int getTempState() {
        return tempState;
    }

    public void setTempState(int tempState) {
        this.tempState = tempState;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

}
