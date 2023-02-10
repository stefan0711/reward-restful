package com.moyi.rewardrestful.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Zhipeng Yin
 * @date 2023-02-10 14:41
 */
public class TransactionException extends RuntimeException {
    private HttpStatus httpStatus;
    private String message;

    public TransactionException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public TransactionException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
