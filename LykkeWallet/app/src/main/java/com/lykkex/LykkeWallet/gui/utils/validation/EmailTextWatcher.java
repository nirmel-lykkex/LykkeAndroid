package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.TextWatcher;

import com.lykkex.LykkeWallet.gui.utils.Constants;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public class EmailTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() > Constants.MIN_COUNT_SYMBOL &&
                charSequence.toString().endsWith(Constants.SYMBOL_COMA)){
            //TODO send request
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
