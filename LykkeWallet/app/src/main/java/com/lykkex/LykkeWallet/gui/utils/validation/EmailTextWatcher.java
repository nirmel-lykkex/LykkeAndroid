package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.registration.callback.AccountExistDataCallback;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public class EmailTextWatcher implements TextWatcher, View.OnFocusChangeListener {

    private AccountExistDataCallback callback;
    private EditText editText;
    private ImageView imgWell;
    private Button clear;
    private ProgressBar progressBar;

    private static String REGEX_VALIDATION = "(?:[a-z0-9!#$%\\&'*+/=?\\^_`{|}~-]+(?:\\.[a-z0-9!#$%\\&'*+/=?\\^_`{|}" +
    "~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\" +
            "x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-" +
            "z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5"+
            "]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-"+
            "9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"+
            "-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private Button buttonAction;
    private boolean isOnFocus;

    public EmailTextWatcher(CallBackListener<AcountExistResult> listener,
                            ImageView imgWell, Button clear, final EditText editText,
                            ProgressBar progressBar, Button buttnAction) {
        this.callback = new AccountExistDataCallback(listener, progressBar, null);
        this.editText = editText;
        this.progressBar = progressBar;
        this.buttonAction = buttnAction;
        this.imgWell = imgWell;
        this.clear = clear;
        if (editText != null) {
            this.editText.setOnFocusChangeListener(this);
        }
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        buttonAction.setEnabled(false);
        setUpClearButton(charSequence.toString());
            if (charSequence.toString().matches(REGEX_VALIDATION)){
                progressBar.setVisibility(View.VISIBLE);
                Call<AccountExistData> call  =
                        LykkeApplication_.getInstance().getRestApi().accountExis(editText.
                                getText().toString());
                call.enqueue(callback);
             } else {
                imgWell.setVisibility(View.GONE);
            }

    }

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onFocusChange(View view, boolean b) {
        isOnFocus = b;
        setUpClearButton(editText.getText().toString());
    }

    private void setUpClearButton(String charSequence){
        if (clear != null) {
            if (charSequence.toString().length() >= Constants.MIN_COUNT_SYMBOL && isOnFocus) {
                clear.setVisibility(View.VISIBLE);
            }else {
                clear.setVisibility(View.GONE);
            }
        }
    }
}
