package com.user.activity.real_name.bean;

public class RealNameBean {


    /**
     * state : 1
     * message : 操作成功
     * model : null
     */

    private int state;
    private String message;
    private Object model;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getModel() {
        return model;
    }

    public void setModel(Object model) {
        this.model = model;
    }
}
