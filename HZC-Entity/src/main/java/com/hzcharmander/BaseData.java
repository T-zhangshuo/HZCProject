package com.hzcharmander;

import java.io.Serializable;

//返回的数据
public class BaseData<T> implements Serializable {
    private boolean opt=false;
    private int optCode=1;
    private String message="失败";
    private T data;


    public int getOptCode() {
        return optCode;
    }

    public void setOptCode(int optCode) {
        this.optCode = optCode;
    }

    public boolean isOpt() {
        return opt;
    }

    public void setOpt(boolean opt) {
        this.opt = opt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        if(data!=null) {
            this.data = data;
            this.opt = true;
            this.message = "成功";
        }
    }
}
