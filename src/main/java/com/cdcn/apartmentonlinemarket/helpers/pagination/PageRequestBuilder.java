package com.cdcn.apartmentonlinemarket.helpers.pagination;

import com.cdcn.apartmentonlinemarket.products.domain.dto.request.SortRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class PageRequestBuilder {
    private static final int DEFAULT_SIZE_PAGE = 20;

    public static PageRequest getPageable(int size, int page, List<SortRequest> sortRequestList) {
        int pageSize = (size == 0) ? DEFAULT_SIZE_PAGE : size;
        int currentPage = (page <= 0) ? 1 : page;

        if (sortRequestList.isEmpty()) {
            return PageRequest.of((currentPage - 1), pageSize);
        } else {
            if (sortRequestList.get(0).getDirection().equals(Sort.Direction.DESC) || sortRequestList.get(0)
                    .getDirection().equals(Sort.Direction.ASC)) {
                List<Sort.Order> orders = new ArrayList<>();
                sortRequestList.forEach(sort -> orders.add(new Sort.Order(sort.getDirection(), sort.getKey())));
                Sort sort = Sort.by(orders);
                return PageRequest.of((currentPage - 1), pageSize, sort);
            } else {
                throw new IllegalArgumentException(String.format("Value for param 'order' is not valid , must be 'asc' or 'desc'"));
            }

        }

    }
}
