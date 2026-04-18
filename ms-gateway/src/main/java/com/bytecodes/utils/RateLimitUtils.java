package com.bytecodes.utils;

import com.bytecodes.exception.UnknownAddressException;
import org.springframework.web.servlet.function.ServerRequest;
public class RateLimitUtils {

    private RateLimitUtils(){}


    /**
     * Obtiene la IP del cliente desde header si viene de un proxy o la dirección IP original
     * si no viene por el header.
     * @return IP del origen de la request
     * @throws UnknownAddressException si la IP es imposible de obtener a través de la request.
     */
    public static String obtainClientIp(ServerRequest request) {
        var headerIp = request.headers().header("X-Forwarded-For");
        if(!headerIp.isEmpty())
            return headerIp.get(0);
        return request.remoteAddress()
                .map(addr -> addr.getAddress().getHostAddress())
                .orElseThrow(() -> new UnknownAddressException());
    }

}
