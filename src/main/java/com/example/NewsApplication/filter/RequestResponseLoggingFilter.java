package com.example.NewsApplication.filter;

import com.example.NewsApplication.wrapper.RequestWrapper;
import com.example.NewsApplication.wrapper.ResponseWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class RequestResponseLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Capture the request and response payloads
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) request);
        ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) response);

        chain.doFilter(requestWrapper, responseWrapper);

        // Log the request and response
        logRequest(requestWrapper);
        logResponse(responseWrapper);

        byte[] responseBytes = responseWrapper.toByteArray();
        response.getOutputStream().write(responseBytes);
    }

    private void logRequest(RequestWrapper requestWrapper) {
        String requestBody = requestWrapper.getBody();
        // Log the request body using SLF4J
        logger.info("Request Body: {}", requestBody);
    }
    private void logResponse(ResponseWrapper responseWrapper) {
        String responseBody = responseWrapper.getBody();
        // Log the response body using SLF4J
        logger.info("Response Body: {}", responseBody);
    }


}
