package com.bytecodes.util;

import com.bytecodes.dto.response.FixtureApiResponse;
import com.bytecodes.exception.ExternalApiException;

import java.util.List;
import java.util.Map;

public class ApiUtil {

    private ApiUtil(){}

    /**
     * La api siempre devuelve el campo errors como array si no tiene errores
     * así que esta es la solución más rápida y limpia que se me ha ocurrido
     */
    public static void checkError(FixtureApiResponse<?> apiResponse) {
        if (!(apiResponse.getErrors() instanceof List<?>)) {
            var errorMap = (Map<String, String>) apiResponse.getErrors();
            var errorKey = errorMap.keySet().stream().findFirst().get();
            throw new ExternalApiException(errorKey + ":" + errorMap.get(errorKey));
        }
    }

}
