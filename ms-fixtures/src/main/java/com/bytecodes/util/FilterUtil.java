package com.bytecodes.util;

import com.bytecodes.dto.request.FixtureFilters;

import java.util.Objects;

public class FilterUtil {

    private FilterUtil(){}

    public static FixtureFilters resolveFilters(FixtureFilters filters) {

        var filtersNotProvided = Objects.isNull(filters.getLeague()) && Objects.isNull(filters.getSeason());

        if(Objects.isNull(filters.getDate()) && filtersNotProvided) {
          filters.setDate(DateUtil.getTodaySimpleDate());
        }

        return filters;
    }


}
