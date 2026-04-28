package com.bytecodes.client;

import com.bytecodes.exception.ExternalApiException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ClientErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return new ExternalApiException(response.status() + ": " + response.reason());
    }
}
