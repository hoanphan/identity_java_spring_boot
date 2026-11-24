package com.indentity.identity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    UNAUTHORIZE_EXCEPTION(9999, "User existed", HttpStatus.INTERNAL_SERVER_ERROR),
    USE_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USE_NOT_EXITED(1002, "User not existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1004, "unauthenticated", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_EXIST(1004, "unauthenticated", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    public int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String message;

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatusCode httpStatusCode;

}
