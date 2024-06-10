package com.xws111.sqlpractice.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 */
@Data
public class BaseResponse<T> implements Serializable {

    private int code;
    private T data;
    private String message;
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse() {
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }
    public BaseResponse(int code, T data) {
        this(code, data,"","");
    }

    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    public static BaseResponse success() {
        return new BaseResponse();
    }

    public static BaseResponse success(Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(data);
        return baseResponse;
    }
}
