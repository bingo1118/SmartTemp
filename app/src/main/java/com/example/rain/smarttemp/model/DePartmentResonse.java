package com.example.rain.smarttemp.model;

import java.util.List;

public class DePartmentResonse extends HttpResponse {

    private List<Department> list;

    public List<Department> getList() {
        return list;
    }

    public void setList(List<Department> list) {
        this.list = list;
    }
}