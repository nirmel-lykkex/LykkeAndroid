package com.lykkex.LykkeWallet.gui.utils.validation;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by e.kazimirova on 10.02.2016.
 */
public class PasswordTextWatcher extends SimpleTextAuthWatcher {

    private String passwordFirst;

    public PasswordTextWatcher(ImageView imgWell,
                               Button imgClear, final EditText editText,
                               CallBackListener listener,
                               int minCount, String passwordFirst){
        super(imgWell, imgClear, editText, listener, minCount);
        this.passwordFirst = passwordFirst;
    }


    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() >=minCount && passwordFirst.equals(charSequence.toString())) {
            imgWell.setVisibility(View.VISIBLE);
            listener.onSuccess(null);
        } else {
            imgWell.setVisibility(View.GONE);
            listener.onFail(null);//TODO
        }
    }
}
