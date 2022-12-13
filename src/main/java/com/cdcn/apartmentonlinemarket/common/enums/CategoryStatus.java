package com.cdcn.apartmentonlinemarket.common.enums;

public enum CategoryStatus {
    ENABLE(1), DISABLE(0);
    private int value;

    CategoryStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
