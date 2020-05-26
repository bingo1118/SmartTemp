package com.example.rain.smarttemp.model;

public class PassSum {

    private int peopleCount; //通行人数
    private int personCount; //通行员工人数
    private int vistorCount;  //通行游客
    private int totalCount; //通行记录总数
    private int normalCount; //正常人数
    private int unNormalCount; //异常人数
    public int getPeopleCount() {
        return peopleCount;
    }
    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }
    public int getPersonCount() {
        return personCount;
    }
    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }
    public int getVistorCount() {
        return vistorCount;
    }
    public void setVistorCount(int vistorCount) {
        this.vistorCount = vistorCount;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
    public int getNormalCount() {
        return normalCount;
    }
    public void setNormalCount(int normalCount) {
        this.normalCount = normalCount;
    }
    public int getUnNormalCount() {
        return unNormalCount;
    }
    public void setUnNormalCount(int unNormalCount) {
        this.unNormalCount = unNormalCount;
    }
}
