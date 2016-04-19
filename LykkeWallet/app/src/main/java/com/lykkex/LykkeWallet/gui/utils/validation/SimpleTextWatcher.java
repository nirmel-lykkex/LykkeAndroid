package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.gui.fragments.addcard.FieldType;
import com.lykkex.LykkeWallet.gui.utils.Constants;

/**
 * Created by LIZA on 06.03.2016.
 */
public class SimpleTextWatcher  implements TextWatcher, View.OnFocusChangeListener {

    protected ImageView imgWell;
    protected Button imgClear;
    protected EditText editText;
    protected int minCount;
    protected CallBackListener listener;
    protected FieldType type;
    private boolean isOnFocus;

    public SimpleTextWatcher(ImageView imgWell,
                             Button imgClear, final EditText editText,
                             int minCount, CallBackListener listener,
                             FieldType type){
        this.editText = editText;
        this.imgClear = imgClear;
        this.imgWell = imgWell;
        this.minCount = minCount;
        this.listener = listener;
        this.type = type;
        if (editText != null) {
            this.editText.setOnFocusChangeListener(this);
        }
        if (this.imgClear != null) {
            this.imgClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editText.setText("");
                }
            });
        }
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (imgClear != null) {
            setUpClearButton();
        }

        if (imgWell != null) {
            if (editText.getText().toString().length() >= minCount) {
                imgWell.setVisibility(View.VISIBLE);
                listener.onSuccess(type);
            } else {
                imgWell.setVisibility(View.GONE);
                listener.onFail(type);
            }
        } else {
            if (editText.getText().toString().length() >= minCount) {
               listener.onSuccess(type);
            } else {
               listener.onFail(type);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        isOnFocus = b;
        setUpClearButton();
    }

    private void setUpClearButton(){
        if (imgClear != null) {
            if (isOnFocus && editText.getText().toString().length() >= Constants.MIN_COUNT_SYMBOL) {
                imgClear.setVisibility(View.VISIBLE);
            } else {
                imgClear.setVisibility(View.INVISIBLE);
            }
        }
    }
}