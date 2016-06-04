package com.lykkex.LykkeWallet.gui.fragments.startscreen;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.registration.RegistrationStep1Fragment;
import com.lykkex.LykkeWallet.gui.fragments.registration.RegistrationStep1Fragment_;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.emailverify.response.model.VerifyCodeData;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.confirm_email_fragment)
public class ConfirmEmailFragment extends Fragment {

    @ViewById
    EditText codeEditText;

    @ViewById
    Button buttonAction;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    TextView confirmCodeMessage;

    @Bean
    UserManager userManager;

    @App
    LykkeApplication lykkeApplication;

    @Click(R.id.buttonAction)
    public void clickButtonAction(){
        Call<VerifyCodeData> call = lykkeApplication.getRestApi().verifyCode(userManager.getRegistrationModel().getEmail(), codeEditText.getText().toString());

        call.enqueue(new Callback<VerifyCodeData>() {
            @Override
            public void onResponse(Call<VerifyCodeData> call, Response<VerifyCodeData> response) {
                if(!response.isSuccess()) {
                    Log.e("ERROR", "Unexpected error while confirming code for email: " +
                            userManager.getRegistrationModel().getEmail() + ", " + response.errorBody());

                    LykkeUtils.showError(getFragmentManager(), "Unexpected error while confirming code.");

                    return;
                }

                if(response.body().getError() != null) {
                    LykkeUtils.showError(getFragmentManager(), response.body().getError().getMessage());
                } else if(!response.body().getResult().getPassed()){
                    LykkeUtils.showError(getFragmentManager(), "Incorrect code.");
                } else {
                    ((BaseActivity) getActivity()).initFragment(new RegistrationStep1Fragment_(), null);
                }
            }

            @Override
            public void onFailure(Call<VerifyCodeData> call, Throwable t) {
                LykkeUtils.showError(getFragmentManager(), "Unexpected error while confirming code.");

                Log.e("ERROR", "Unexpected error while confirming code for email: " +
                        userManager.getRegistrationModel().getEmail(), t);
            }
        });
    }

    @AfterViews
    void afterViews() {
        confirmCodeMessage.setText(getResources().getString(R.string.confirm_code_message, userManager.getRegistrationModel().getEmail()));
    }

    @AfterTextChange(R.id.codeEditText)
    void onCodeTextChange(Editable text) {
        buttonAction.setEnabled(text.toString().length() > 0);
    }
}
