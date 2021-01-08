package com.huaxiafinance.www.crecyclerview.crecyclerView;

import java.io.Serializable;

/**
 * Created by chenqun on 2016/6/1.
 */
public class BaseResult<T> implements Serializable{

    public boolean success = false;
    protected int code;
    protected String msg;
    protected T data;

    public void setData(T data) {
        this.data = data;
    }
    public T getData() {
        return data;
    }

    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public int getErrorCode() {
        return code;
    }
    public void setErrorCode(int errorCode) {
        this.code = errorCode;
    }
    public String getErrorMsg() {
        return msg;
    }
    public void setErrorMsg(String errorMsg) {
        this.msg = errorMsg;
    }

}
