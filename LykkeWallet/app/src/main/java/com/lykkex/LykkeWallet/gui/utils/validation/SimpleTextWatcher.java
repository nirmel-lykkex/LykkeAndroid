package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class SimpleTextWatcher implements TextWatcher {

    protected int minCount = 0;
    protected ValidationListener listener;

    public SimpleTextWatcher(int count, ValidationListener listener){
        this.minCount = count;
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() >=minCount) {
            listener.onSuccess(null);
        } else {
            listener.onFail(null);//TODO
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
