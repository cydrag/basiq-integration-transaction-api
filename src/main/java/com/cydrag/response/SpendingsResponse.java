package com.cydrag.response;

import java.util.List;

public class SpendingsResponse {

    private List<Spendings> spendings;

    public SpendingsResponse() {
    }

    public List<Spendings> getSpendings() {
        return spendings;
    }

    public void setSpendings(List<Spendings> spendings) {
        this.spendings = spendings;
    }
}
