package com.example.demo.exception;


import com.example.demo.constant.ErrorConstants;

/**
 * @author YaLong
 */

public enum BaseExceptionCode implements ExceptionCode {
    PARAM_MISSING(60001, ErrorConstants.PARAM_MISSING),
    DATA_NOT_EXIST(60500, ErrorConstants.DATA_NOT_EXIST),
    PERMISSION_ERROR(61000, ErrorConstants.PERMISSION_ERROR),
    NOT_PERMISSION_ERROR(61001, ErrorConstants.NOT_PERMISSION_ERROR),
    LOGIN_NONE(60000, ErrorConstants.NOT_LOGIN),
    DUPLICATE_KEY(60002, ErrorConstants.DUPLICATE_KEY),
    PARAM_ERROR(60003, ErrorConstants.PARAM_ERROR),
    ;
    private final Integer code;
    private final String message;

    BaseExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


}
