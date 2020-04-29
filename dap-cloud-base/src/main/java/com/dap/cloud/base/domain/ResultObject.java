package com.dap.cloud.base.domain;

public class ResultObject<T> {

    private int code;
    private Integer count;
    private String msg;
    private T data;

    public ResultObject(int code, Integer count, T data) {
        super();
        this.code = code;
        this.count = count;
        this.data = data;
    }

    public ResultObject(Integer count, T data) {
        super();
        this.code = 0;
        this.count = count;
        this.data = data;
    }

    public ResultObject(int code, Integer count, String msg) {
        super();
        this.code = code;
        this.count = count;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
