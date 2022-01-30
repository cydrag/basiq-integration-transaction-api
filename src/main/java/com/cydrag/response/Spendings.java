package com.cydrag.response;

public class Spendings {

    private String code;
    private float average;

    public Spendings() {
    }

    public Spendings(String code, float average) {
        this.code = code;
        this.average = average;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }
}
