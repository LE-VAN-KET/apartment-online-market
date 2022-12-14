package com.cdcn.apartmentonlinemarket.helpers.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
    private int totalPages;
    private long totalItems;
    private int currentPage;
    private int firstPage;
    private int lastPage;
    private boolean first;
    private boolean last;
    private int itemsPerPage;
    private int pageSize;

    private Sort sort;
    private List<T> items;

    public void setPageStats(Page pg, List<T> items) {
        first = pg.isFirst();
        last = pg.isLast();
        currentPage = pg.getNumber() + 1;
        pageSize = pg.getSize();
        totalPages = pg.getTotalPages();
        totalItems = pg.getTotalElements();
        sort = pg.getSort();
        firstPage = Math.max(1, pg.getNumber() - 5);
        lastPage = Math.min(this.firstPage + 10, pg.getTotalPages());
        itemsPerPage = pg.getNumberOfElements();
        this.items = items;
    }
}
