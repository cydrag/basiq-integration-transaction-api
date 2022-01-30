package com.cydrag.dto;

public class TransactionDataDto {

    private float amount;
    private SubClassDto subClass;

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setSubClass(SubClassDto subClass) {
        this.subClass = subClass;
    }

    public float getAmount() {
        return amount;
    }

    public SubClassDto getSubClass() {
        return subClass;
    }
}
