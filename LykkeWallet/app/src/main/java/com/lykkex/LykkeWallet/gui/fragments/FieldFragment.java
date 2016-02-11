package com.lykkex.LykkeWallet.gui.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.PasswordTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.gui.widgets.ValidationEditText;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.request.model.AuthRequest;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.registration.callback.RegistrationDataCallback;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.field_fragment)
public class FieldFragment extends Fragment implements ValidationListener {

    @Bean FieldController controller;

    @ViewById Button buttonAction;
    @ViewById RelativeLayout validationField;
    @ViewById EditText editTextField;
    @ViewById ImageView imageWell;
    @ViewById Button buttonClear;

    private ValidationEditText validationEditText;

    private EmailTextWatcher emailTextWatcher;
    private SimpleTextWatcher simpleTextWatcher;
    private SimpleTextWatcher firstPasswordTextWatcher;
    private PasswordTextWatcher secondeTextWatcher;

    private RegistrationModelGUI model;
    private AuthModelGUI authRequest;

    @AfterViews
    public void afterViews() {
        controller.init(this, FieldState.EmailScreen);
        model = new RegistrationModelGUI();
        authRequest = new AuthModelGUI();

        validationEditText = new ValidationEditText(editTextField, imageWell, buttonClear);
        emailTextWatcher = new EmailTextWatcher(this, validationEditText);
        simpleTextWatcher = new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL, this,
                validationEditText);
        firstPasswordTextWatcher = new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this,
                validationEditText);
        //TODO определить на каком этапе регистрации
        initEmailState();
    }

    public void initEmailState() {
        model.setIsReady(false);
        validationEditText.setReady(false);
        editTextField.setText("");
        editTextField.removeTextChangedListener(simpleTextWatcher);
        editTextField.addTextChangedListener(emailTextWatcher);
        editTextField.setHint(R.string.email_hint);
        buttonAction.setText(R.string.action_sign_up);
    }

    public void initFullNameState() {
        model.setIsReady(false);
        validationEditText.setReady(false);
        model.setEmail(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);
        editTextField.setHint(R.string.fullname_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initMobileState() {
        model.setIsReady(false);
        validationEditText.setReady(false);
        model.setFullName(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);
        editTextField.setHint(R.string.mobile_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initFirstPasswordState() {
        model.setIsReady(false);
        validationEditText.setReady(false);
        model.setMobile(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.removeTextChangedListener(simpleTextWatcher);
        if (secondeTextWatcher != null) {
            editTextField.removeTextChangedListener(secondeTextWatcher);
        }
        editTextField.addTextChangedListener(firstPasswordTextWatcher);
        editTextField.setHint(R.string.first_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initSecondPasswordState() {
        model.setIsReady(false);
        validationEditText.setReady(false);
        model.setPasswordFirst(editTextField.getText().toString());
        editTextField.setText("");
        secondeTextWatcher = new PasswordTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this,
                model.getPasswordFirst(), validationEditText);
        editTextField.addTextChangedListener(secondeTextWatcher);
        editTextField.setHint(R.string.seond_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initPasswordSignInScreen(){
        authRequest.setEmail(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.addTextChangedListener(firstPasswordTextWatcher);
        authRequest.setIsReady(false);
        editTextField.setHint(R.string.first_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void sendRegistrationRequest(){
        if (model.isReady()) {
            RegistrationDataCallback callback = new RegistrationDataCallback();
            Call<RegistrationData> call = LykkeApplication_.getInstance().getRegistrationApi().registration(model);
            call.enqueue(callback);
        }
    }

    public void sendAuthRequest(){
        authRequest.setPassword(editTextField.getText().toString());
        if (model.isReady()) {
            LoginDataCallback callback = new LoginDataCallback();
            Call<AuthModelData> call = LykkeApplication_.getInstance().getRegistrationApi().getAuth(authRequest);
            call.enqueue(callback);
        }
    }

    @Click(R.id.buttonAction)
    public void clickAction() {
        if (model.isReady()) {
            controller.fire();
        }
    }

    @Override
    public void onSuccess(Object result) {
        buttonAction.setEnabled(true);
        validationEditText.setReady(true);
        switch (controller.getCurrentState()) {
            case EmailScreen:
                model.setIsReady(!((AcountExistResult) result).isEmailRegistered());
                if (!model.isReady()) {
                    buttonAction.setText(R.string.action_sing_in);
                    controller.fire(FieldTrigger.EmailSignInScreen);
                    authRequest.setIsReady(true);
                    model.setIsReady(true);
                }
                break;
            case FullNameScreen:
                model.setIsReady(true);
                break;
            case MobileScreen:
                model.setIsReady(true);
                break;
            case FirstPasswordScreen:
                model.setIsReady(true);
                break;
            case SecondPasswordScreen:
                model.setIsReady(true);
                break;
            case PasswordSignInScreen:
                model.setIsReady(true);
                break;
            case EmailSignInScreen:
                model.setIsReady(true);
                break;

        }
    }

    @Override
    public void onFail(com.lykkex.LykkeWallet.rest.base.models.Error error) {
        model.setIsReady(false);
        buttonAction.setEnabled(false);
        validationEditText.setReady(false);
        model.setIsReady(false);
    }

    public void initOnBackPressed(){
        if (controller.getCurrentState() == FieldState.Idle ||
                controller.getCurrentState() == FieldState.EmailScreen) {
            getActivity().finish();
        } else {
            controller.fireBackPressed();
        }
    }
}
