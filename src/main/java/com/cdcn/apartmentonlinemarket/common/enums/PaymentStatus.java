package com.cdcn.apartmentonlinemarket.common.enums;

public enum PaymentStatus {
    SUCCESS("SUCCESS"), FAILED("FAILED");
    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
