package com.cydrag.dto;

import java.util.List;

public class TransactionsDto {

    private List<TransactionDataDto> data;

    public void setData(List<TransactionDataDto> data) {
        this.data = data;
    }

    public List<TransactionDataDto> getData() {
        return data;
    }
}
