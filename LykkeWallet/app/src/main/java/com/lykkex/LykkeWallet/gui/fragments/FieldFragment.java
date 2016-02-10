package com.lykkex.LykkeWallet.gui.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.github.oxo42.stateless4j.delegates.Action;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.FieldStateMachine;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.rest.registration.base.models.*;
import com.lykkex.LykkeWallet.rest.registration.models.AcountExisResult;
import com.lykkex.LykkeWallet.rest.registration.models.RegistrationModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
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

    private RegistrationModel model;

    @AfterViews
    public void afterViews() {
        controller.init(this);
        model = new RegistrationModel();
        //TODO определить на каком этапе регистрации
        initEmailState();
    }

    public void initEmailState() {
        editTextField.setText("");
        editTextField.addTextChangedListener(new EmailTextWatcher(this));
        editTextField.setHint(R.string.email_hint);
        buttonAction.setText(R.string.action_sign_up);
    }

    public void initFullNameState() {
        model.setEmail(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL, this));
        editTextField.setHint(R.string.fullname_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initMobileState() {
        model.setMobile(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL, this));
        editTextField.setHint(R.string.mobile_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initFirstPasswordState() {
        model.setPasswordFirst(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this));
        editTextField.setHint(R.string.first_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initSecondPasswordState() {
        model.setPasswordFirst(editTextField.getText().toString());
        editTextField.setText("");
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this));
        editTextField.setHint(R.string.seond_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    @Click(R.id.buttonAction)
    public void clickAction() {
        controller.fire();
    }

    @Override
    public void onSuccess(Object result) {
        switch (controller.getCurrentState()) {
            case EmailScreen:
                if (((AcountExisResult) result).isEmailRegistered()) {
                    buttonAction.setText(R.string.action_login_in);
                }
                break;
        }
    }

    @Override
    public void onFail(com.lykkex.LykkeWallet.rest.registration.base.models.Error error) {
        switch (controller.getCurrentState()) {
            case EmailScreen:
                break;
        }
    }

}
