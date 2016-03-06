package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.gui.utils.Constants;

/**
 * Created by LIZA on 06.03.2016.
 */
public class SimpleTextWatcher  implements TextWatcher {

    protected ImageView imgWell;
    protected Button imgClear;
    protected EditText editText;
    protected int minCount;

    public SimpleTextWatcher(ImageView imgWell,
                             Button imgClear, final EditText editText,
                             int minCount){
        this.editText = editText;
        this.imgClear = imgClear;
        this.imgWell = imgWell;
        this.minCount = minCount;
        if (imgClear != null) {
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
            if (charSequence.toString().length() >= Constants.MIN_COUNT_SYMBOL) {
                imgClear.setVisibility(View.VISIBLE);
            } else {
                imgClear.setVisibility(View.INVISIBLE);
            }
        }

        if (imgWell != null) {
            if (charSequence.toString().length() >= minCount) {
                imgWell.setVisibility(View.VISIBLE);
            } else {
                imgWell.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}