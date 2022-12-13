package com.cdcn.apartmentonlinemarket.helpers.specs;

import com.cdcn.apartmentonlinemarket.common.enums.FieldType;
import com.cdcn.apartmentonlinemarket.common.enums.FilterOperator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FilterCriteria implements Serializable  {
    private String key;
    private FilterOperator operation;
    private FieldType fieldType;
    private transient Object value;
    private transient Object valueTo;
    private transient List<Object> values;

    public FilterCriteria(String key, FilterOperator operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
