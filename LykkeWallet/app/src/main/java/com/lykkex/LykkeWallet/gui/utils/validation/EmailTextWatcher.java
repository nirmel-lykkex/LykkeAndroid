package com.lykkex.LykkeWallet.gui.utils.validation;

import android.text.Editable;
import android.text.TextWatcher;

import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.registration.RegistrationApi;
import com.lykkex.LykkeWallet.rest.registration.callback.AccountExisDataCallback;
import com.lykkex.LykkeWallet.rest.registration.models.AccountExisData;
import com.lykkex.LykkeWallet.rest.registration.models.AcountExisResult;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public class EmailTextWatcher implements TextWatcher {

    private AccountExisDataCallback callback;

    public EmailTextWatcher(ValidationListener<AcountExisResult> listener) {
        this.callback = new AccountExisDataCallback(listener);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (charSequence.toString().length() > Constants.MIN_COUNT_SYMBOL &&
                charSequence.toString().contains(Constants.SYMBOL_COMA)){
            Call<AccountExisData> call  = LykkeApplication_.getInstance().getRegistrationApi().accountExis(charSequence.toString());
            call.enqueue(callback);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {}
}
