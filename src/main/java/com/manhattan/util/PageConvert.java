package com.manhattan.util;

/**
 * Created by Administrator on 2014/6/10 0010.
 */
public class PageConvert {

    public static Page convert(org.springframework.data.domain.Page page) {
        Page pages = new Page();
        if (page == null) {
            return pages;
        }
        pages.setPageNo(page.getNumber());
        pages.setRows(page.getContent());
        pages.setPageSize(page.getSize());
        pages.setTotal(page.getTotalElements());
        return pages;
    }
}
