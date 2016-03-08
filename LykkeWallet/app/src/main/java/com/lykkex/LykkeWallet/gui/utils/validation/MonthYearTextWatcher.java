package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.gui.fragments.addcard.FieldType;

/**
 * Created by LIZA on 06.03.2016.
 */
public class MonthYearTextWatcher extends SimpleTextWatcher {

    private int prevCount = 0;
    public MonthYearTextWatcher(ImageView imgWell, Button imgClear, EditText editText, int minCount,
                                CallBackListener listener, FieldType type) {
        super(imgWell, imgClear, editText, minCount, listener, type);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        prevCount = editText.getText().toString().length();
    }
    @Override
    public void afterTextChanged(Editable editable) {
        String result = editText.getText().toString();
        if (result.length() > prevCount) {
            for (int k = 0; k < result.toCharArray().length; k++) {
                char symbol = result.toCharArray()[k];
                if (k == 0 && Character.isDigit(symbol)
                        && Integer.valueOf(String.valueOf(symbol)) >= 2) {
                    result = "0" + String.valueOf(symbol) + "/";
                    changeDataEditText(result);
                }
                if (k == 1) {
                    String month = result.substring(0, 2);
                    char m1 = month.charAt(0);
                    char m2 = month.charAt(1);
                    if ((Character.isDigit(m1) && Character.isDigit(m2)
                            && Integer.valueOf(month) > 12) ||
                            (!Character.isDigit(m2) && symbol != '/')) {
                        result = result.substring(0, 1);
                        changeDataEditText(result);
                    } else if (Character.isDigit(m1) && Character.isDigit(m2)
                            && result.length() == 2) {
                        result = result + "/";
                        changeDataEditText(result);
                    } else if (!Character.isDigit(m2) && symbol == '/') {
                        result = "0" + result;
                        changeDataEditText(result);
                    }
                } else if (k == 2 && symbol != '/') {
                    result = result.replaceAll(String.valueOf(symbol), "");
                    changeDataEditText(result);
                } else if (k != 2 && !Character.isDigit(symbol)) {
                    result = result.replaceAll(String.valueOf(symbol), "");
                    changeDataEditText(result);
                }
            }
        }
    }

    private void setUpMaxLength(int max) {
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(max);
        editText.setFilters(fArray);
    }

    private void changeDataEditText(String result){
        editText.setText(result);
        editText.setSelection(editText.getText().toString().length());
    }

}
