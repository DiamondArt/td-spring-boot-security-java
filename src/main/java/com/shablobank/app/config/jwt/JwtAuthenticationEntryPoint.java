package com.shablobank.app.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shablobank.app.controller.exception.ApiException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = 1L;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        OutputStream responseStream = response.getOutputStream();
        final ApiException body = new ApiException(HttpServletResponse.SC_UNAUTHORIZED, request.getServletPath(), authException.getMessage());
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(responseStream, body);
        responseStream.flush();
    }
    private org.slf4j.Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger(this.getClass().getSimpleName());
    }
}
