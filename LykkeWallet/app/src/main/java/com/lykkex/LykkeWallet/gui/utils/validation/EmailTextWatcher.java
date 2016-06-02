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
import com.lykkex.LykkeWallet.gui.customviews.RichEditText;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.registration.callback.AccountExistDataCallback;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public class EmailTextWatcher implements TextWatcher {

    private AccountExistDataCallback callback;
    private RichEditText richEditText;
    private ProgressBar progressBar;

    private static String REGEX_VALIDATION = "(?:[a-z0-9!#$%\\&'*+/=?\\^_`{|}~-]+(?:\\.[a-z0-9!#$%\\&'*+/=?\\^_`{|}" +
    "~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\" +
            "x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-" +
            "z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5"+
            "]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-"+
            "9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21"+
            "-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    private Button buttonAction;

    public EmailTextWatcher(CallBackListener<AcountExistResult> listener,
                            RichEditText richEditText,
                            ProgressBar progressBar, Button buttnAction) {
        this.callback = new AccountExistDataCallback(listener, progressBar, null);
        callback.setEmail(richEditText.getText().toString());
        this.richEditText = richEditText;
        this.progressBar = progressBar;
        this.buttonAction = buttnAction;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        buttonAction.setEnabled(false);
        richEditText.setUpClearButton(charSequence.toString());

        if (charSequence.toString().matches(REGEX_VALIDATION)){
            progressBar.setVisibility(View.VISIBLE);
            callback.setEmail(richEditText.getText().toString());
            Call<AccountExistData> call  =
                    LykkeApplication_.getInstance().getRestApi().accountExis(richEditText.
                            getText().toString());
            call.enqueue(callback);
         } else {
            richEditText.setReady(false);
        }

    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
