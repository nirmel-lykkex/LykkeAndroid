package com.lykkex.LykkeWallet.gui.fragments.guisegments;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.PasswordTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.gui.widgets.ValidationEditText;
import com.lykkex.LykkeWallet.rest.base.models.*;
import com.lykkex.LykkeWallet.rest.registration.callback.RegistrationDataCallback;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 11.02.2016.
 */
@EBean
public class RegistrationGuiSegment implements ValidationListener {

    private ValidationEditText validationEditText;
    private FieldController controller;
    private EditText editTextField;
    private Button buttonAction;
    private EmailTextWatcher emailTextWatcher;
    private SimpleTextWatcher simpleTextWatcher;
    private SimpleTextWatcher firstPasswordTextWatcher;
    private PasswordTextWatcher secondeTextWatcher;
    private RegistrationModelGUI model;
    private ProgressBar progressBar;
    private ImageView imageViewLogo;
    private RelativeLayout relProgress;
    private TextView tvInfo;


    public void init(EditText editText, ImageView imageWell, Button buttonClear,
                     Button buttonAction,
                     FieldController controller, ProgressBar progressBar, ImageView imageViewLogo,
                     RelativeLayout relProgress, TextView tvInfo){
        this.editTextField = editText;
        this.progressBar = progressBar;
        this.imageViewLogo = imageViewLogo;
        this.relProgress = relProgress;
        this.buttonAction = buttonAction;
        this.tvInfo = tvInfo;
        this.controller = controller;

        model = new RegistrationModelGUI();

        validationEditText = new ValidationEditText(editTextField, imageWell, buttonClear, buttonAction);

        emailTextWatcher = new EmailTextWatcher(this, validationEditText,progressBar, buttonAction );
        simpleTextWatcher = new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL, this,
                validationEditText);
        firstPasswordTextWatcher = new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this,
                validationEditText);

    }

    public void initEmailState() {
        model.setIsReady(false);
        if (!model.getEmail().isEmpty()) {
            editTextField.setText(model.getEmail());
        }
        editTextField.removeTextChangedListener(simpleTextWatcher);
        editTextField.addTextChangedListener(emailTextWatcher);

        relProgress.setVisibility(View.GONE);
        tvInfo.setVisibility(View.GONE);
        imageViewLogo.setVisibility(View.VISIBLE);
        validationEditText.setReady(false);
        buttonAction.setEnabled(false);
        editTextField.setHint(R.string.email_hint);
        buttonAction.setText(R.string.action_sign_up);
    }

    public void initFullNameState() {
        model.setIsReady(false);
        tvInfo.setVisibility(View.GONE);
        relProgress.setVisibility(View.VISIBLE);
        imageViewLogo.setVisibility(View.GONE);
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);

        validationEditText.setReady(false);
        buttonAction.setEnabled(false);
        model.setEmail(editTextField.getText().toString());
        editTextField.setText(model.getFullName());
        editTextField.setHint(R.string.fullname_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initMobileState() {
        model.setIsReady(false);
        relProgress.setVisibility(View.VISIBLE);
        imageViewLogo.setVisibility(View.GONE);
        validationEditText.setReady(false);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);

        buttonAction.setEnabled(false);
        model.setFullName(editTextField.getText().toString());
        editTextField.setText(model.getMobile());
        editTextField.setHint(R.string.mobile_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initFirstPasswordState() {
        model.setIsReady(false);
        relProgress.setVisibility(View.VISIBLE);
        imageViewLogo.setVisibility(View.GONE);
        editTextField.removeTextChangedListener(simpleTextWatcher);
        if (secondeTextWatcher != null) {
            editTextField.removeTextChangedListener(secondeTextWatcher);
        }

        validationEditText.setReady(false);
        buttonAction.setEnabled(false);
        model.setMobile(editTextField.getText().toString());
        editTextField.setText(model.getPasswordFirst());
        editTextField.addTextChangedListener(firstPasswordTextWatcher);
        editTextField.setHint(R.string.first_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initSecondPasswordState() {
        model.setIsReady(false);
        relProgress.setVisibility(View.VISIBLE);
        imageViewLogo.setVisibility(View.GONE);
        validationEditText.setReady(false);
        secondeTextWatcher = new PasswordTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this,
                model.getPasswordFirst(), validationEditText);
        editTextField.addTextChangedListener(secondeTextWatcher);

        buttonAction.setEnabled(false);
        model.setPasswordFirst(editTextField.getText().toString());
        editTextField.setText(model.getSecondPassword());
        editTextField.setHint(R.string.seond_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void clearEditText(){
        editTextField.removeTextChangedListener(emailTextWatcher);
        validationEditText.setReady(false);
    }

    public void sendRegistrationRequest(){
        if (model.isReady()) {
            buttonAction.setEnabled(false);
            RegistrationDataCallback callback = new RegistrationDataCallback(progressBar);
            Call<RegistrationData> call = LykkeApplication_.getInstance().getRegistrationApi().registration(model);
            call.enqueue(callback);
        }
    }

    public void initBackPressedFullName(){
        model.setIsReady(true);
        relProgress.setVisibility(View.GONE);
        imageViewLogo.setVisibility(View.VISIBLE);
        editTextField.removeTextChangedListener(simpleTextWatcher);
        editTextField.removeTextChangedListener(secondeTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(emailTextWatcher);

        model.setFullName(editTextField.getText().toString());
        editTextField.setText(model.getEmail());
        editTextField.setHint(R.string.email_hint);
        buttonAction.setText(R.string.action_sign_up);
    }

    public void initBackPressedMobile() {
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(secondeTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);

        editTextField.setHint(R.string.fullname_hint);
        model.setIsReady(true);
        model.setMobile(editTextField.getText().toString());
        editTextField.setText(model.getFullName());
    }

    public void initBackPressedFirstPasswordScreen(){
        model.setIsReady(true);

        editTextField.setHint(R.string.email_hint);
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(simpleTextWatcher);
        editTextField.removeTextChangedListener(secondeTextWatcher);
        editTextField.addTextChangedListener(firstPasswordTextWatcher);
        model.setPasswordFirst(editTextField.getText().toString());
        editTextField.setText(model.getMobile());
    }

    public void initBackPressedSecondPasswordScreen(){
        model.setIsReady(true);

        editTextField.setHint(R.string.first_password_hint);
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(simpleTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(secondeTextWatcher);
        model.setSecondPassword(editTextField.getText().toString());
        editTextField.setText(model.getPasswordFirst());
    }


    public void clickAction() {
        if (model.isReady()) {
            controller.fire();
        }
    }

    public ValidationEditText getValidationEditText() {
        return validationEditText;
    }

    @Override
    public void onSuccess(Object result) {
        buttonAction.setEnabled(true);
        validationEditText.setReady(true);
        switch (controller.getCurrentState()) {
            case EmailScreen:
                model.setIsReady(!((AcountExistResult) result).isEmailRegistered());
                if (!model.isReady()) {
                    editTextField.removeTextChangedListener(emailTextWatcher);
                    buttonAction.setText(R.string.action_sing_in);
                    controller.fire(FieldTrigger.EmailSignInScreen);
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
        }
    }

    @Override
    public void onFail(com.lykkex.LykkeWallet.rest.base.models.Error error) {
        model.setIsReady(false);
        buttonAction.setEnabled(false);
        validationEditText.setReady(false);
    }
}
