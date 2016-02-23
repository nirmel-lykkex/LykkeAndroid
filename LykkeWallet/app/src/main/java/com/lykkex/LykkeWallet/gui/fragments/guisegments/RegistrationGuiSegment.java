package com.lykkex.LykkeWallet.gui.fragments.guisegments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.PasswordTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.widgets.ValidationEditText;
import com.lykkex.LykkeWallet.rest.registration.callback.RegistrationDataCallback;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import org.androidannotations.annotations.EBean;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 11.02.2016.
 */
@EBean
public class RegistrationGuiSegment implements CallBackListener {

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
    private ActionBar actionBar;
    private Activity activity;
    private ImageView imageViewWhat;
    private TextView textViewWhat;

    public void init(EditText editText, ImageView imageWell, Button buttonClear,
                     Button buttonAction,
                     FieldController controller, ProgressBar progressBar, ImageView imageViewLogo,
                     RelativeLayout relProgress, TextView tvInfo,  ActionBar actionBar,
                     Activity activity, TextView textViewWhat, ImageView imageViewWhat){
        this.editTextField = editText;
        this.progressBar = progressBar;
        this.actionBar = actionBar;
        this.imageViewLogo = imageViewLogo;
        this.relProgress = relProgress;
        this.buttonAction = buttonAction;
        this.tvInfo = tvInfo;
        this.controller = controller;
        this.activity = activity;
        this.textViewWhat = textViewWhat;
        this.imageViewWhat = imageViewWhat;

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
        imageViewWhat.setVisibility(View.VISIBLE);
        textViewWhat.setVisibility(View.VISIBLE);
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        editTextField.removeTextChangedListener(simpleTextWatcher);
        editTextField.addTextChangedListener(emailTextWatcher);
        editTextField.setText(editTextField.getText().toString());
        editTextField.setSelection(editTextField.getText().toString().length());
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
        imageViewWhat.setVisibility(View.GONE);
        textViewWhat.setVisibility(View.GONE);
        tvInfo.setText(R.string.complete_your_profile);
        if (actionBar != null) {
            actionBar.setTitle(R.string.registration);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        tvInfo.setVisibility(View.VISIBLE);
        relProgress.setVisibility(View.VISIBLE);
        imageViewLogo.setVisibility(View.GONE);
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);

        validationEditText.setReady(false);
        buttonAction.setEnabled(false);
        model.setEmail(editTextField.getText().toString());
        editTextField.setText(model.getFullName());
        editTextField.setSelection(editTextField.getText().toString().length());
        editTextField.setHint(R.string.fullname_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initMobileState() {
        model.setIsReady(false);
        imageViewWhat.setVisibility(View.GONE);
        textViewWhat.setVisibility(View.GONE);
        if (actionBar != null) {
            actionBar.setTitle(R.string.registration);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tvInfo.setText(R.string.complete_your_profile);
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);

        relProgress.setVisibility(View.VISIBLE);
        imageViewLogo.setVisibility(View.GONE);
        validationEditText.setReady(false);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);

        buttonAction.setEnabled(false);
        model.setFullName(editTextField.getText().toString());
        editTextField.setText(model.getMobile());
        editTextField.setSelection(editTextField.getText().toString().length());
        editTextField.setHint(R.string.mobile_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initFirstPasswordState() {
        model.setIsReady(false);
        imageViewWhat.setVisibility(View.GONE);
        textViewWhat.setVisibility(View.GONE);
        if (actionBar != null) {
            actionBar.setTitle(R.string.registration);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
        tvInfo.setText(R.string.complete_your_profile);
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

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
        editTextField.setSelection(editTextField.getText().toString().length());
        editTextField.addTextChangedListener(firstPasswordTextWatcher);
        editTextField.setHint(R.string.first_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void initSecondPasswordState() {
        model.setIsReady(false);
        imageViewWhat.setVisibility(View.GONE);
        textViewWhat.setVisibility(View.GONE);
        if (actionBar != null) {
            actionBar.setTitle(R.string.registration);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        relProgress.setVisibility(View.VISIBLE);
        imageViewLogo.setVisibility(View.GONE);
        validationEditText.setReady(false);
        model.setPasswordFirst(editTextField.getText().toString());
        secondeTextWatcher = new PasswordTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this,
                model.getPasswordFirst(), validationEditText);
        editTextField.addTextChangedListener(secondeTextWatcher);

        buttonAction.setEnabled(false);
        editTextField.setText(model.getSecondPassword());
        editTextField.setSelection(editTextField.getText().toString().length());
        editTextField.setHint(R.string.seond_password_hint);
        buttonAction.setText(R.string.action_next);
    }

    public void clearEditText(){
        editTextField.removeTextChangedListener(emailTextWatcher);
        validationEditText.setReady(false);
    }

    ProgressDialog dialog;
    public void sendRegistrationRequest(){
        dialog = new ProgressDialog(activity);
        dialog.setMessage(activity.getString(
                R.string.waiting));
        dialog.setCancelable(false);
        if (model.isReady()) {
            dialog.show();
            buttonAction.setEnabled(false);
            RegistrationDataCallback callback = new RegistrationDataCallback(progressBar, this, activity);
            Call<RegistrationData> call = LykkeApplication_.getInstance().getRestApi().registration(model);
            call.enqueue(callback);
        }
    }

    public void initBackPressedFullName(){
        model.setIsReady(true);
        imageViewWhat.setVisibility(View.VISIBLE);
        textViewWhat.setVisibility(View.VISIBLE);
        tvInfo.setText(R.string.complete_your_profile);
        if (actionBar != null) {
            actionBar.setTitle(R.string.app_name);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        relProgress.setVisibility(View.GONE);
        imageViewLogo.setVisibility(View.VISIBLE);
        tvInfo.setVisibility(View.GONE);
        editTextField.removeTextChangedListener(simpleTextWatcher);
        editTextField.removeTextChangedListener(secondeTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(emailTextWatcher);

        model.setFullName(editTextField.getText().toString());
        editTextField.setText(model.getEmail());
        editTextField.setSelection(editTextField.getText().toString().length());
        editTextField.setHint(R.string.email_hint);
        buttonAction.setText(R.string.action_sign_up);
    }

    public void initBackPressedMobile() {
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        imageViewWhat.setVisibility(View.GONE);
        textViewWhat.setVisibility(View.GONE);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.registration);
        }
        tvInfo.setText(R.string.complete_your_profile);

        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(secondeTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);

        editTextField.setHint(R.string.fullname_hint);
        model.setIsReady(true);
        model.setMobile(editTextField.getText().toString());
        editTextField.setText(model.getFullName());
        editTextField.setSelection(editTextField.getText().toString().length());
    }

    public void initBackPressedFirstPasswordScreen(){
        model.setIsReady(true);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.registration);
        }
        imageViewWhat.setVisibility(View.GONE);
        textViewWhat.setVisibility(View.GONE);
        tvInfo.setText(R.string.complete_your_profile);

        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
        editTextField.setHint(R.string.email_hint);
        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(firstPasswordTextWatcher);
        editTextField.removeTextChangedListener(secondeTextWatcher);
        editTextField.addTextChangedListener(simpleTextWatcher);

        model.setPasswordFirst(editTextField.getText().toString());
        editTextField.setText(model.getMobile());
        editTextField.setSelection(editTextField.getText().toString().length());
    }

    public void initBackPressedSecondPasswordScreen(){
        model.setIsReady(true);
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.registration);
        }
        imageViewWhat.setVisibility(View.GONE);
        textViewWhat.setVisibility(View.GONE);
        tvInfo.setText(R.string.complete_your_profile);
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextField.setHint(R.string.first_password_hint);

        editTextField.removeTextChangedListener(emailTextWatcher);
        editTextField.removeTextChangedListener(simpleTextWatcher);
        editTextField.removeTextChangedListener(secondeTextWatcher);

        editTextField.addTextChangedListener(firstPasswordTextWatcher);
        model.setSecondPassword(editTextField.getText().toString());
        editTextField.setText(model.getPasswordFirst());
        editTextField.setSelection(editTextField.getText().toString().length());
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
        model.setIsReady(true);
        switch (controller.getCurrentState()) {
            case FullNameScreenBack:
                if (result != null) {
                    model.setIsReady(!((AcountExistResult) result).isEmailRegistered());
                    if (!model.isReady()) {
                        editTextField.removeTextChangedListener(emailTextWatcher);
                        buttonAction.setText(R.string.action_sing_in);
                        controller.fire(FieldTrigger.EmailSignInScreen);
                    }
                }
                break;
            case EmailScreen:
                if (result != null) {
                    model.setIsReady(!((AcountExistResult) result).isEmailRegistered());
                    if (!model.isReady()) {
                        editTextField.removeTextChangedListener(emailTextWatcher);
                        buttonAction.setText(R.string.action_sing_in);
                        controller.fire(FieldTrigger.EmailSignInScreen);
                        model.setIsReady(true);
                    }
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
            case SendRegistrationRequst:
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setClass(activity,  CameraActivity_.class);
                new UserPref_(activity).authToken().put(((RegistrationData) result).getResult().getToken());
                new UserPref_(activity).fullName().put(((RegistrationData) result).getResult().getPersonalData().getFullName());
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
            default:
                model.setIsReady(false);
                buttonAction.setEnabled(false);
                validationEditText.setReady(false);
                break;
        }
    }
}
