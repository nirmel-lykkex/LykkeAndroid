package com.lykkex.LykkeWallet.gui.fragments.guisegments;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.MainActivity_;
import com.lykkex.LykkeWallet.gui.SelfieActivity_;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.gui.widgets.ValidationEditText;
import com.lykkex.LykkeWallet.rest.base.models.*;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.w3c.dom.Text;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 11.02.2016.
 */
@EBean
public class LoginGuiSegment implements ValidationListener{

    private ValidationEditText validationEditText;
    private AuthModelGUI authRequest;
    private EditText editTextField;
    private Button buttonAction;
    private FieldController controller;
    private SimpleTextWatcher passwordTextWatcher;
    private EmailTextWatcher emailTextWatcher;
    private EmailTextWatcher emailTextWatcherLogin;
    private RelativeLayout relProgress;
    private ImageView imageLogo;
    private ProgressBar progressBar;
    private ActionBar actionbar;
    private Activity activity;
    private EditText editTextLogin;
    private RelativeLayout validationFieldLogin;
    private Button buttonClearLogin;
    private ImageView imageWellLogin;
    private TextView tvInfo;

    private ValidationEditText validationEditTextLogin;

    public void init(EditText editTextField,  Button buttonAction,ImageView imageWell, Button buttonClear,
                     FieldController controller, ProgressBar progressBar, TextView tvInfo,
                     RelativeLayout relProgress, ImageView imageLogo, ActionBar actionbar,
                      Activity activity,  EditText editTextFieldLogin,
                             RelativeLayout validationFieldLogin,
                             Button buttonClearLogin,
                             ImageView imageWellLogin){
        authRequest = new AuthModelGUI();
        this.editTextField = editTextField;
        this.imageLogo = imageLogo;
        this.tvInfo = tvInfo;
        this.progressBar = progressBar;
        this.relProgress = relProgress;
        this.actionbar = actionbar;
        this.activity = activity;
        this.editTextLogin = editTextFieldLogin;
        this.validationFieldLogin = validationFieldLogin;

        validationEditText =  new ValidationEditText(editTextField, imageWell, buttonClear, buttonAction);
        validationEditTextLogin = new ValidationEditText(editTextFieldLogin, imageWellLogin, buttonClearLogin, buttonAction);
        passwordTextWatcher = new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this,
                validationEditText);
        emailTextWatcher = new EmailTextWatcher(this, validationEditText, progressBar, buttonAction);
        emailTextWatcherLogin= new EmailTextWatcher(this, validationEditTextLogin, progressBar, buttonAction);
        this.buttonAction = buttonAction;
        this.controller = controller;
    }

    public void initPasswordSignInScreen(){
        actionbar.setDisplayHomeAsUpEnabled(true);
        authRequest.setIsReady(false);
        actionbar.setTitle(R.string.login);
        validationEditTextLogin.setButtonClearVisibilty(true);
        validationEditText.setReady(false);
        editTextLogin.setHint(R.string.email_hint);
        buttonAction.setEnabled(false);
        imageLogo.setVisibility(View.GONE);
        tvInfo.setVisibility(View.VISIBLE);
        tvInfo.setText(R.string.inside);
        buttonAction.setText(R.string.action_log_in);
        validationEditTextLogin.setEditTextEnabled(false);
        validationFieldLogin.setVisibility(View.VISIBLE);
        editTextLogin.setText(editTextField.getText().toString());
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        authRequest.setEmail(editTextField.getText().toString());
        editTextField.setText(authRequest.getPassword());
        authRequest.setIsReady(false);
        validationEditTextLogin.setReady(true);
        editTextField.setHint(R.string.first_password_hint);
        editTextField.setSelection(editTextField.getText().toString().length());
        buttonAction.setText(R.string.action_next);
        validationEditText.setButtonClearVisibilty(false);
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextLogin.addTextChangedListener(emailTextWatcherLogin);
        editTextField.addTextChangedListener(passwordTextWatcher);
    }

    public void initEmailSignIn(){
        actionbar.setTitle(R.string.app_name);
        actionbar.setDisplayHomeAsUpEnabled(false);
        authRequest.setIsReady(true);
        validationEditText.setEditTextEnabled(true);
        validationFieldLogin.setVisibility(View.GONE);
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        validationEditText.setReady(true);
        editTextField.setSelection(editTextField.getText().toString().length());
        editTextField.addTextChangedListener(emailTextWatcher);
    }


    public void sendAuthRequest(){
        authRequest.setPassword(editTextField.getText().toString());
        if (authRequest.isReady()) {
            buttonAction.setEnabled(false);
            LoginDataCallback callback = new LoginDataCallback(progressBar, this);
            Call<AuthModelData> call = LykkeApplication_.getInstance().getRegistrationApi().getAuth(authRequest);
            call.enqueue(callback);
        }
    }

    public void initBackPressedPasswordSignIn(){
        authRequest.setIsReady(true);
        actionbar.setTitle(R.string.app_name);
        actionbar.setDisplayHomeAsUpEnabled(false);
        authRequest.setPassword(editTextField.getText().toString());
        editTextField.removeTextChangedListener(passwordTextWatcher);
        editTextField.addTextChangedListener(emailTextWatcher);
        editTextField.setText(authRequest.getEmail());
        validationFieldLogin.setVisibility(View.GONE);
        imageLogo.setVisibility(View.VISIBLE);
        validationEditText.setEditTextEnabled(true);
        tvInfo.setVisibility(View.GONE);
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editTextField.setSelection(editTextField.getText().toString().length());
        buttonAction.setText(R.string.action_sing_in);
    }

    public void clickAction() {
        if (authRequest.isReady()) {
            controller.fire();
        }
    }

    @Override
    public void onSuccess(Object result) {
        switch (controller.getCurrentState()) {
            case PasswordSignInScreen:
                if (result != null) {
                    //validationEditTextLogin.setReady(((AcountExistResult) result).isEmailRegistered());
                }
                if (editTextField.getText().toString().isEmpty() &&
                        editTextField.getText().toString().length()<Constants.MIN_COUNT_SYMBOL_PASSWORD){
                    validationEditText.setReady(false);
                    buttonAction.setEnabled(false);
                    authRequest.setIsReady(false);
                } else {
                    validationEditText.setButtonClearVisibilty(true);
                    validationEditText.setReady(true);
                    buttonAction.setEnabled(true);
                    authRequest.setIsReady(true);
                }
                validationEditText.setReady(false);
                validationEditText.setButtonClearVisibilty(false);
                validationEditTextLogin.setReady(false);
                validationEditTextLogin.setButtonClearVisibilty(false);
                validationEditTextLogin.setEditTextEnabled(false);
                break;
            case PasswordSignInScreenBack:
                buttonAction.setEnabled(true);
                authRequest.setIsReady(((AcountExistResult) result).isEmailRegistered());
                validationEditTextLogin.setReady(((AcountExistResult) result).isEmailRegistered());
                if (!authRequest.isReady()) {
                    editTextField.removeTextChangedListener(emailTextWatcher);
                    buttonAction.setText(R.string.action_sign_up);
                    controller.fire(FieldTrigger.EmailScreen);
                }
                break;
            case EmailSignInScreen:
                buttonAction.setEnabled(true);
                validationEditText.setReady(true);
                authRequest.setIsReady(((AcountExistResult) result).isEmailRegistered());
                if (!authRequest.isReady()) {
                    editTextField.removeTextChangedListener(emailTextWatcher);
                    buttonAction.setText(R.string.action_sign_up);
                    controller.fire(FieldTrigger.EmailScreen);
                }
                break;
            case SendAuthRequest:
                Intent intent = new Intent();
                intent.setClass(activity, MainActivity_.class);
                activity.startActivity(intent);
                activity.finish();
                break;
        }
    }

    @Override
    public void onFail(com.lykkex.LykkeWallet.rest.base.models.Error error) {
        switch (controller.getCurrentState()){
            case SendRegistrationRequst:
                buttonAction.setEnabled(true);
                Toast.makeText(activity, "Something going wrong. Try again", Toast.LENGTH_LONG).show();
                break;
            case PasswordSignInScreen:
                validationEditText.setButtonClearVisibilty(false);
                break;
            default:
                authRequest.setIsReady(false);
                buttonAction.setEnabled(false);
                validationEditText.setReady(false);
                break;
        }

    }
}
