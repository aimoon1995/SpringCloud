package com.moon.accept_num.config;

/**
 *@ClassName ResultException
 *@Description TODO
 *@Author zyl
 *@Date 2020/2/16 20:50
 @Version 1.0
 **/
public class ResultException extends RuntimeException{
    public String msg;
    public int errorCode;

    public ResultException() {
    }

    public ResultException(String msg, int errorCode) {
        super(String.format("{HttpStatusCode: %s, Details: %s}", errorCode, msg));
        this.msg = msg;
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
