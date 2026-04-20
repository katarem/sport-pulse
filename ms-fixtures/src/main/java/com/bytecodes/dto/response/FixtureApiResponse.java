package com.bytecodes.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Collection;
import java.util.Map;

@Data
@Builder
public class FixtureApiResponse<T> {
    private Map<String, String> parameters;
    private FixtureApiPaging paging;
    private Integer results;
    private Object errors;
    private T response;
}
