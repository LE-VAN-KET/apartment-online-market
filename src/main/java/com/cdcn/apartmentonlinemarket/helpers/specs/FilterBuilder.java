package com.cdcn.apartmentonlinemarket.helpers.specs;

import com.cdcn.apartmentonlinemarket.common.enums.FilterOperator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterBuilder {
    private static List<String> split(String search, String delimiter) {
        return Stream.of(search.split(delimiter)).collect(Collectors.toList());
    }

    public static List<FilterCriteria> getFilters(String criteria) {
        List<FilterCriteria> filters = new ArrayList<>();

        if (criteria != null && !criteria.isEmpty()) {

            List<String> values = split(criteria, "&");
            if (!values.isEmpty()) {
                values.forEach(x -> {
                    List<String> filter = split(x, "\\|");
                    if(FilterOperator.fromValue(filter.get(1)) != null){
                        filters.add(new FilterCriteria(filter.get(0), FilterOperator.fromValue(filter.get(1)),
                                filter.get(2)));
                    }
                });
            }
        }
        return filters;
    }
}
