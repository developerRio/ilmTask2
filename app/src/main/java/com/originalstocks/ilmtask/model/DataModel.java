package com.originalstocks.ilmtask.model;

public class DataModel {

    private String tittle;
    private String body;
    private int pos;

    public DataModel(int pos) {
        this.pos = pos;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
