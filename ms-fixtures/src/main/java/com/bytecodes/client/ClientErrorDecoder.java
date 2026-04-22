package com.bytecodes.client;

import com.bytecodes.exception.ExternalApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        Map<String, String> errors = Map.of("EXTERNAL_API_CODE", response.status() + ": " + response.reason());
        return new ExternalApiException(errors);
    }
}
