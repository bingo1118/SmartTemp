package com.example.rain.smarttemp.model;

public class HttpResponse {

    private int errorCode;//返回码

    private String error;//返回信息

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }




}
