package com.moon.moon_commons.util;

import java.util.Date;

public class ResponseBean {

    private int returnCode;
    private String msg;
    private Object data;
    private boolean success;
    private long returnTime;

    public ResponseBean(int returnCode, String msg, Object data, boolean success) {
        this.msg = msg;
        this.data = data;
        this.returnCode = returnCode;
        this.returnTime = (new Date()).getTime();
        this.success = success;
    }

    public static ResponseBean createSuccess(String msg) {
        return new ResponseBean(10000, msg, (Object)null, true);
    }

    public static ResponseBean createSuccess(String msg, Object data) {
        return new ResponseBean(10000, msg, data, true);
    }

    public static ResponseBean createSuccess(int returnCode, String msg) {
        return new ResponseBean(returnCode, msg, (Object)null, true);
    }

    public static ResponseBean createSuccess(int returnCode, String msg, Object data) {
        return new ResponseBean(returnCode, msg, data, true);
    }

    public static ResponseBean createError(String msg) {
        return new ResponseBean(90000, msg, (Object)null, false);
    }

    public static ResponseBean createError(String msg, Object data) {
        return new ResponseBean(90000, msg, data, false);
    }

    public static ResponseBean createError(int returnCode, String msg) {
        return new ResponseBean(returnCode, msg, (Object)null, false);
    }

    public static ResponseBean createError(int returnCode, String msg, Object data) {
        return new ResponseBean(returnCode, msg, data, false);
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public long getReturnTime() {
        return this.returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
