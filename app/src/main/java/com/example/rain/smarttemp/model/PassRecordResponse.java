package com.example.rain.smarttemp.model;

import java.util.List;

public class PassRecordResponse extends HttpResponse {

    private List<PassRecord> list;

    public List<PassRecord> getList() {
        return list;
    }

    public void setList(List<PassRecord> list) {
        this.list = list;
    }
}
