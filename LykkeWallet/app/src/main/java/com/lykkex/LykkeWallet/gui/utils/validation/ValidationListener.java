package com.lykkex.LykkeWallet.gui.utils.validation;


/**
 * Created by e.kazimirova on 10.02.2016.
 */
public interface ValidationListener<T> {

    public void onSuccess(T result);
    public void onFail(com.lykkex.LykkeWallet.rest.base.models.Error error);
}
