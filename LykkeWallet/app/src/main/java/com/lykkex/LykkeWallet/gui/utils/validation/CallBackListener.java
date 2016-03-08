package com.lykkex.LykkeWallet.gui.utils.validation;


/**
 * Created by e.kazimirova on 10.02.2016.
 */
public interface CallBackListener<T> {

    public void onSuccess(T result);
    public void onFail(Object error);
}
