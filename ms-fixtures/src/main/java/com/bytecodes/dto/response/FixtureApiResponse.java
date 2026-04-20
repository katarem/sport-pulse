package com.bytecodes.dto.response;

import lombok.Data;

import java.util.Collection;
import java.util.Map;

@Data
public class FixtureApiResponse<T> {
    private Map<String, String> parameters;
    private FixtureApiPaging paging;
    private Integer results;
    private Collection<?> errors;
    private T response;
}
