package com.cdcn.apartmentonlinemarket.common.enums;

public enum FilterOperator {
    EQUAL("eq"),
    NOT_EQUAL("neq"),
    GREATER_THAN("gt"),
    LESS_THAN("lt"),
    GREATER_THAN_OR_EQUAL_TO("gte"),
    LESS_THAN_OR_EQUAL_TO("lte"),
    NIN("min"),
    MAX("max"),
    NOT_IN("nin"),
    BETWEEN("btn"),
    CONTAINS("like"),
    NOT_CONTAINS("notLike"),
    IS_NULL("isnull"),
    IS_NOT_NULL("isnotnull"),
    START_WITH("startwith"),
    END_WITH("endwith"),
    IS_EMPTY("isempty"),
    IS_NOT_EMPTY("isnotempty");

    private final String value;

    FilterOperator(String value) {
        this.value = value;
    }

    public static FilterOperator fromValue(String value) {
        for (FilterOperator searchOperation : FilterOperator.values()) {
            if (String.valueOf(searchOperation.value).equalsIgnoreCase(value)) {
                return searchOperation;
            }
        }
        return null;
    }
}
