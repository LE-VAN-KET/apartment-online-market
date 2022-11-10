package com.cdcn.apartmentonlinemarket.common.enums;

public enum UserStatus {
    BLOCKED(0), PENDING(1), ACTIVE(2), UN_VERIFY(3);
    private final int value;

    UserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
