package com.cdcn.apartmentonlinemarket.common.enums;

public enum OrderStatus {
    CREATED(0), COMPLETED(1), FAILED(2), CANCELED(3),
    PAIDED(4), UNPAID(5), REFUNDED(6);

    private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
