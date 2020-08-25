package com.huaxiafinance.www.crecyclerview.crecyclerView;

import java.io.Serializable;

/**
 * Created by chenqun on 2016/6/1.
 */
public class BaseResult<T> implements Serializable{

    public boolean success = false;
    protected String errorCode;
    protected String errorMsg;
    protected T data;
    protected int rows;
    protected int page;
    protected int total;

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
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMsg() {
        return errorMsg;
    }
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
