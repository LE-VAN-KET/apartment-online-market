package com.cdcn.apartmentonlinemarket.products.domain.dto.request;

import com.cdcn.apartmentonlinemarket.helpers.specs.FilterCriteria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductRequest {
    private int size = 20;
    private int page = 0;
    private List<SortRequest> sorts;
    private List<FilterCriteria> filterCriteriaList;

    public List<FilterCriteria> filterCriteriaList() {
        if (Objects.isNull(this.filterCriteriaList)) return new ArrayList<>();
        return this.filterCriteriaList;
    }

    public List<SortRequest> getSorts() {
        if (Objects.isNull(this.sorts)) return new ArrayList<>();
        return this.sorts;
    }
}
