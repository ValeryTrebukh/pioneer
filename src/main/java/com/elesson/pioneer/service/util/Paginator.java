package com.elesson.pioneer.service.util;

import java.util.ArrayList;
import java.util.List;


public class Paginator<T>  {

    private static final int PAGE_SIZE = 3;

    public synchronized List<T> getPage(List<T> list, int pageNumber) {
        List<T> result = new ArrayList<>();

        int pagesCount = getPageCount(list);
        pageNumber = pageNumber < 1 ? 1 : pageNumber > pagesCount ? pagesCount : pageNumber;
        int limit = pageNumber==pagesCount ? list.size() : pageNumber*PAGE_SIZE;

        for(int i = (pageNumber-1)*PAGE_SIZE; i < limit; i++) {
            result.add(list.get(i));
        }

        return result;
    }

    public int getPageCount(List<T> list) {
        return (list.size()-1)/PAGE_SIZE + 1;
    }
}
