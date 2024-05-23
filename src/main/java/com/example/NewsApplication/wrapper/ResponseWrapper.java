package com.example.NewsApplication.wrapper;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;


public class ResponseWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private ServletOutputStream servletOutputStream;

    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (servletOutputStream == null) {
            servletOutputStream = new ServletOutputStream() {
                @Override
                public void write(int b) {
                    byteArrayOutputStream.write(b);
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setWriteListener(WriteListener writeListener) {

                }


            };
        }
        return servletOutputStream;
    }

    public byte[] toByteArray() {
        return byteArrayOutputStream.toByteArray();
    }

    public String getBody() {
        return byteArrayOutputStream.toString();
    }
}