package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class SimpleTextAuthWatcher extends SimpleTextWatcher {

    protected CallBackListener listener;

    public SimpleTextAuthWatcher(ImageView imgWell,
                                 Button imgClear, final EditText editText,
                                 CallBackListener listener,
                                 int minCount){
        super(imgWell, imgClear, editText, minCount, listener, null);
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        super.onTextChanged(charSequence, i, i1, i2);
        if (charSequence.toString().length() >= minCount) {
            listener.onSuccess(null);
        } else {
            listener.onFail(null);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
