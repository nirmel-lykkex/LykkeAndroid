package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class PasswordTextWatcher extends SimpleTextWatcher {

    private String passwordFirst;

    public PasswordTextWatcher(int count, ValidationListener listener, String passwordFirst){
        super(count, listener);
        this.passwordFirst = passwordFirst;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() >=minCount && passwordFirst.equals(charSequence.toString())) {
            listener.onSuccess(null);
        } else {
            listener.onFail(null);//TODO
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
