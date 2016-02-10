package com.lykkex.LykkeWallet.rest.registration.base.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public abstract class BaseModel<T> {

    @SerializedName("Result")
    protected T result;

    @SerializedName("Error")
    protected Error error;

    public Error getError() {
        return error;
    }

    public T getResult() {
        return result;
    }

}
