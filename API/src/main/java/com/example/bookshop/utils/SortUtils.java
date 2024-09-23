package com.example.bookshop.utils;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class SortUtils {
    public static Sort parseSortParams(List<String> sortParams , List<String> listSearch  ) {
        List<Sort.Order> orders = new ArrayList<>();
        for (String param : sortParams) {
            String[] sortPair = param.split(",");
            if(!listSearch.contains(sortPair[0])) {
               continue;
            }
            if (sortPair.length == 2) {
                Sort.Direction direction = Sort.Direction.fromString(sortPair[1]);
                orders.add(new Sort.Order(direction, sortPair[0]).nullsLast());
            }
        }
        return Sort.by(orders);
    }
    public static Sort parseSortParam(String sortParam ,List<String> listSearch ) {
        if (sortParam == null || sortParam.trim().isEmpty()) {
            return Sort.unsorted();
        }
        List<Sort.Order> orders = new ArrayList<>();
            String[] sortPair = sortParam.trim().split(",");
            if(!listSearch.contains(sortPair[0])) {
                return Sort.unsorted();
            }
        return Sort.by(orders);

    }
}
