package com.shop.api.exception;

import com.shop.api.common.ResponseEnum;


public class GlobalException extends RuntimeException {

    private ResponseEnum responseEnum;

    public GlobalException(ResponseEnum responseEnum){
        this.responseEnum = responseEnum;
    }

    public ResponseEnum getResponseEnum() {
        return responseEnum;
    }
}
