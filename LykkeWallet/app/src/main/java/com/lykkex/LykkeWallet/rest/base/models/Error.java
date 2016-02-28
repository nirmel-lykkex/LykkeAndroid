package com.lykkex.LykkeWallet.rest.base.models;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public class Error {

    private int Code;

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    private String Field;
    private String Message;
}
