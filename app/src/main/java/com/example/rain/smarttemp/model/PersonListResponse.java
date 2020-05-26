package com.example.rain.smarttemp.model;

import java.util.List;

public class PersonListResponse extends HttpResponse {

    private List<Person> list;

    public List<Person> getList() {
        return list;
    }

    public void setList(List<Person> list) {
        this.list = list;
    }
}
