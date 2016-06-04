package com.lykkex.LykkeWallet.gui.fragments.registration;

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
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.ConfirmEmailFragment_;
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
@EFragment(R.layout.registration_step_1_fragment)
public class RegistrationStep1Fragment extends Fragment {

    @ViewById
    StepsIndicator stepsIndicator;

    @ViewById
    EditText passwordEditText;

    @ViewById
    EditText confirmPasswordEditText;

    @ViewById
    Button buttonAction;

    @Bean
    UserManager userManager;

    @Click(R.id.buttonAction)
    public void clickButtonAction(){
        ((BaseActivity) getActivity()).initFragment(new RegistrationStep2Fragment_(), null);
    }

    @AfterViews
    void afterViews() {
        stepsIndicator.setCurrentStep(1);

        passwordEditText.setText(userManager.getRegistrationModel().getPasswordFirst());
        confirmPasswordEditText.setText(userManager.getRegistrationModel().getSecondPassword());
    }

    @AfterTextChange(R.id.passwordEditText)
    void onPasswordChange(Editable text) {
        userManager.getRegistrationModel().setPasswordFirst(text.toString());

        invalidateActionButton();
    }

    @AfterTextChange(R.id.confirmPasswordEditText)
    void onConfirmPasswordChange(Editable text) {
        userManager.getRegistrationModel().setSecondPassword(text.toString());

        invalidateActionButton();
    }

    private void invalidateActionButton() {
        RegistrationModelGUI model = userManager.getRegistrationModel();

        buttonAction.setEnabled(model.getPasswordFirst().length() >= 6 && model.getPasswordFirst().toString().equals(userManager.getRegistrationModel().getSecondPassword()));
    }
}
