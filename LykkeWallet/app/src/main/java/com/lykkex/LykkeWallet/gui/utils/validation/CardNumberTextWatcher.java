package com.lykkex.LykkeWallet.gui.utils.validation;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.gui.fragments.addcard.FieldType;

/**
 * Created by LIZA on 06.03.2016.
 */
public class CardNumberTextWatcher extends SimpleTextWatcher {

    public CardNumberTextWatcher(ImageView imgWell, Button imgClear, EditText editText, int minCount,
                                 CallBackListener listener, FieldType type) {
        super(imgWell, imgClear, editText, minCount, listener, type);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String result = editText.getText().toString();
        for (int k =0; k < result.toCharArray().length; k++) {
            char symbol = result.toCharArray()[k];
            if ((k==4 || k ==9 || k == 14) && symbol != ' '){
                result = result.substring(0, k) +' ' + result.substring(k+1);
                changeDataEditText(result);
            } else if (!Character.isDigit(symbol) && (k!=4 && k !=9 && k != 14)) {
                result = result.replaceAll(String.valueOf(symbol), "");
                changeDataEditText(result);
            }
        }
        super.onTextChanged(charSequence, i, i1, i2);
    }

    private void changeDataEditText(String result){
        editText.setText(result);
        editText.setSelection(editText.getText().toString().length());
    }

}
