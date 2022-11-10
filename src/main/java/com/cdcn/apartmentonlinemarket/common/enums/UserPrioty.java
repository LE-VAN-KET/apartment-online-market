package com.cdcn.apartmentonlinemarket.common.enums;

public enum UserPrioty {
    LOWEST(0), LOW(1), MEDIUM(2), HIGH(3), HIGHEST(4);
    private final int value;

    UserPrioty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
