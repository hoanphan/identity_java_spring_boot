package com.indentity.identity.exception;

public enum ErrorCode {
    UNAUTHORIZE_EXCEPTION(9999, "User existed"),
    USE_EXISTED(1001, "User existed"),
    USE_NOT_EXITED(1002, "User not existed"),
    UNAUTHENTICATED(1004, "unauthenticated")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
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

}
