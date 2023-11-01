package com.springsecurity.demo.Jwt;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    // This class is a custom AuthenticationEntryPoint used in Spring Security.
    // It handles unauthorized (401) responses for unauthenticated requests.

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // This method is invoked when an unauthenticated request is made to a secured endpoint.

        // It sends an HTTP error response with status code 401 (Unauthorized) to the client.

        // The `authException.getMessage()` is used to include the error message from the
        // `AuthenticationException` in the response, providing additional information
        // to the client about why the request was unauthorized.

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
