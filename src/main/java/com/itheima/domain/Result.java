package com.itheima.domain;

import java.io.Serializable;

/**
 * 用于封装后端返回前端数据对象
 */
public class Result implements Serializable {
    private boolean flag;//后端返回结果正常为true，发生异常返回false
    private Object data;//后端返回结果数据对象
    private String message;//发生异常的错误消息

    //无参构造方法
    public Result() {
    }
    public Result(boolean flag) {
        this.flag = flag;
    }
    /**
     * 有参构造方法
     * @param flag
     * @param message
     */
    public Result(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
    /**
     * 有参构造方法
     * @param flag
     * @param data
     * @param message
     */
    public Result(boolean flag, Object data, String message) {
        this.flag = flag;
        this.data = data;
        this.message = message;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
