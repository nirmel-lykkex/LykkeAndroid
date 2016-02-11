package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.TextWatcher;

import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.widgets.ValidationEditText;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class SimpleTextWatcher implements TextWatcher {

    protected int minCount = 0;
    protected ValidationListener listener;
    protected ValidationEditText editText;

    public SimpleTextWatcher(int count, ValidationListener listener, ValidationEditText editText){
        this.minCount = count;
        this.listener = listener;
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() >= Constants.MIN_COUNT_SYMBOL) {
            editText.setButtonClearVisibilty(true);
        } else {
            editText.setButtonClearVisibilty(false);
        }
        if (charSequence.toString().length() >= minCount) {
            listener.onSuccess(null);
        } else {
            listener.onFail(null);//TODO
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
