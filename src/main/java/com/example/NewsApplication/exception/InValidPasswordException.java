package com.example.NewsApplication.exception;

public class InValidPasswordException extends RuntimeException{
    public InValidPasswordException(String message) {
        super(message);
    }
}

