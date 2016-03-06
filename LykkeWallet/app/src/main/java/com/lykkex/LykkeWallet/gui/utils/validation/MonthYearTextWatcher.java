package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by LIZA on 06.03.2016.
 */
public class MonthYearTextWatcher extends SimpleTextWatcher {

    public MonthYearTextWatcher(ImageView imgWell, Button imgClear, EditText editText, int minCount) {
        super(imgWell, imgClear, editText, minCount);
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String result = editText.getText().toString();
        for (int k =0; k< result.toCharArray().length; k++) {
            char symbol  = result.toCharArray()[k];
            if (k == 2 && symbol != '/') {
                result = result.replaceAll(String.valueOf(symbol), "");
                changeDataEditText(result);
            } else if (k != 2  && !Character.isDigit(symbol)){
                result = result.replaceAll(String.valueOf(symbol), "");
                changeDataEditText(result);
            }
        }
        super.onTextChanged(charSequence, i, i1, i2);
    }

    private void changeDataEditText(String result){
        editText.setText(result);
        editText.setSelection(result.length());
    }

}
