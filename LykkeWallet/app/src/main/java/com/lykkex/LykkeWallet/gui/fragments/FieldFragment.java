package com.lykkex.LykkeWallet.gui.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExisResult;
import com.lykkex.LykkeWallet.rest.registration.request.models.RegistrationModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.field_fragment)
public class FieldFragment extends Fragment implements ValidationListener {

    @Bean
    FieldController controller;
    @ViewById
    Button buttonAction;
    @ViewById
    EditText editTextField;

    private RegistrationModelGUI model;

    @AfterViews
    public void afterViews() {
        controller.init(this,FieldState.EmailScreen);
        model = new RegistrationModelGUI();
        //TODO определить на каком этапе регистрации
        initEmailState();
    }

    public void initEmailState() {
        model.setIsReady(false);
        editTextField.setText("");
        editTextField.addTextChangedListener(new EmailTextWatcher(this));
        editTextField.setHint(R.string.email_hint);
        buttonAction.setText(R.string.action_sign_up);
    }

    public void initFullNameState() {
        model.setIsReady(false);
        model.setEmail(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL, this));
        editTextField.setHint(R.string.fullname_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initMobileState() {
        model.setIsReady(false);
        model.setMobile(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL, this));
        editTextField.setHint(R.string.mobile_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initFirstPasswordState() {
        model.setIsReady(false);
        model.setPasswordFirst(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this));
        editTextField.setHint(R.string.first_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initSecondPasswordState() {
        model.setIsReady(false);
        model.setSecondPassword(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this));
        editTextField.setHint(R.string.seond_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void sendRegistrationRequest(){
        if (model.isReady()) {
            LykkeApplication_.getInstance().getRegistrationApi().registration(model);
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
        switch (controller.getCurrentState()) {
            case EmailScreen:
                model.setIsReady(!((AcountExisResult) result).isEmailRegistered());
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
        }
    }

    @Override
    public void onFail(com.lykkex.LykkeWallet.rest.base.models.Error error) {
        model.setIsReady(false);
    }

}
