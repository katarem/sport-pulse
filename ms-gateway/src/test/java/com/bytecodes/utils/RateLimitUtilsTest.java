package com.bytecodes.utils;

import com.bytecodes.exception.UnknownAddressException;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.function.ServerRequest;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RateLimitUtilsTest {

    @Test
    void should_get_ip_through_headers(){

        // given
        ServerRequest request = mock(ServerRequest.class);
        ServerRequest.Headers headers = mock(ServerRequest.Headers.class);

        // when
        when(headers.header("X-Forwarded-For"))
                .thenReturn(Collections.singletonList("1.1.1.1"));
        when(request.headers()).thenReturn(headers);

        // then
        String clientIp = RateLimitUtils.obtainClientIp(request);
        assertNotNull(clientIp);
        assertEquals("1.1.1.1", clientIp);

    }

    @Test
    void should_get_ip_through_origin_ip(){

        // given
        ServerRequest request = mock(ServerRequest.class);
        ServerRequest.Headers headers = mock(ServerRequest.Headers.class);

        // when
        when(headers.header("X-Forwarded-For"))
                .thenReturn(Collections.emptyList());
        when(request.headers()).thenReturn(headers);
        when(request.remoteAddress())
                .thenReturn(Optional.of(new InetSocketAddress("1.1.1.1", 80)));

        // then
        String clientIp = RateLimitUtils.obtainClientIp(request);
        assertNotNull(clientIp);
        assertEquals("1.1.1.1", clientIp);
    }

    @Test
    void should_throw_on_empty_ip(){

        // given
        ServerRequest request = mock(ServerRequest.class);
        ServerRequest.Headers headers = mock(ServerRequest.Headers.class);

        // when
        when(headers.header("X-Forwarded-For"))
                .thenReturn(Collections.emptyList());
        when(request.headers()).thenReturn(headers);
        when(request.remoteAddress())
                .thenReturn(Optional.empty());

        // then
        assertThrows(UnknownAddressException.class,
                () -> RateLimitUtils.obtainClientIp(request));
    }


}
