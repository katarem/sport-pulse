package com.bytecodes.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse<Map<String, String>> argumentError(MethodArgumentNotValidException error) {


        Function<ObjectError, String> argumentErrorKeyResolver = err -> {
            if (err.getCodes() == null)
                return err.getCode();
            log.info(Arrays.toString(err.getCodes()));
            var longCode = err.getCodes()[0].split("\\.");
            log.info(Arrays.toString(longCode));
            return longCode[longCode.length - 1];
        };

        Function<ObjectError, String> argumentErrorValueResolver = err -> {
            if(Objects.isNull(err.getDefaultMessage()))
                return "Unknown error";
            return err.getDefaultMessage();
        };

        var errorsMessage = error.getAllErrors()
                .stream().collect(Collectors.toMap(argumentErrorKeyResolver, argumentErrorValueResolver));

        return ErrorResponse.<Map<String, String>>builder()
                .errors(errorsMessage)
                .code("FIELD_ERRORS")
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(ExternalApiException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse<Map<String, String>> externalApiError(ExternalApiException exception) {
        return ErrorResponse.<Map<String, String>>builder()
                .code("EXTERNAL_API_ERROR")
                .errors(exception.getErrors())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(FeignException.FeignServerException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResponse<Map<String, String>> feignError(ExternalApiException exception) {
        return ErrorResponse.<Map<String, String>>builder()
                .code("EXTERNAL_API_ERROR")
                .errors(exception.getErrors())
                .timestamp(Instant.now())
                .build();
    }

    @ExceptionHandler(UnknownFormatException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorResponse<Map<String, String>> unknownErrorFormatError(UnknownFormatException exception) {
        return ErrorResponse.<Map<String, String>>builder()
                .code("EXTERNAL_API_ERROR")
                .errors(Map.of("errors", exception.getMessage()))
                .timestamp(Instant.now())
                .build();
    }


}
