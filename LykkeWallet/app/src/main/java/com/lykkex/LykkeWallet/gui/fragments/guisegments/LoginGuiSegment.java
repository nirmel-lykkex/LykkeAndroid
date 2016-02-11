package com.lykkex.LykkeWallet.gui.fragments.guisegments;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.gui.widgets.ValidationEditText;
import com.lykkex.LykkeWallet.rest.base.models.*;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;

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
    private ImageButton buttonAction;
    private FieldController controller;
    private SimpleTextWatcher passwordTextWatcher;
    private EmailTextWatcher emailTextWatcher;
    private TextView textViewButton;

    public void init(EditText editTextField,  ImageButton buttonAction,ImageView imageWell, Button buttonClear,
                     FieldController controller, TextView textViewButton){
        authRequest = new AuthModelGUI();
        this.editTextField = editTextField;
        validationEditText =  new ValidationEditText(editTextField, imageWell, buttonClear, buttonAction);

        passwordTextWatcher = new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this,
                validationEditText);
        emailTextWatcher = new EmailTextWatcher(this, validationEditText);

        this.textViewButton = textViewButton;
        this.buttonAction = buttonAction;
        this.controller = controller;
    }

    public void initPasswordSignInScreen(){
        authRequest.setEmail(editTextField.getText().toString());
        editTextField.setText(authRequest.getPassword());
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.addTextChangedListener(passwordTextWatcher);
        authRequest.setIsReady(false);
        editTextField.setHint(R.string.first_password_hint);
        textViewButton.setText(R.string.action_next);
    }

    public void initEmailSignIn(){
        authRequest.setIsReady(true);
        validationEditText.setReady(true);
    }


    public void sendAuthRequest(){
        authRequest.setPassword(editTextField.getText().toString());
        if (authRequest.isReady()) {
            LoginDataCallback callback = new LoginDataCallback();
            Call<AuthModelData> call = LykkeApplication_.getInstance().getRegistrationApi().getAuth(authRequest);
            call.enqueue(callback);
        }
    }

    public void initBackPressedPasswordSignIn(){
        authRequest.setIsReady(true);
        authRequest.setPassword(editTextField.getText().toString());
        editTextField.removeTextChangedListener(passwordTextWatcher);
        editTextField.addTextChangedListener(emailTextWatcher);
        editTextField.setText(authRequest.getEmail());
        textViewButton.setText(R.string.action_sing_in);
    }

    public void clickAction() {
        if (authRequest.isReady()) {
            controller.fire();
        }
    }

    @Override
    public void onSuccess(Object result) {
        buttonAction.setEnabled(true);
        validationEditText.setReady(true);
        switch (controller.getCurrentState()) {
            case PasswordSignInScreen:
                authRequest.setIsReady(true);
                break;
            case EmailSignInScreen:
                authRequest.setIsReady(true);
                break;
        }
    }

    @Override
    public void onFail(com.lykkex.LykkeWallet.rest.base.models.Error error) {
        authRequest.setIsReady(false);
        buttonAction.setEnabled(false);
        validationEditText.setReady(false);
    }
}
