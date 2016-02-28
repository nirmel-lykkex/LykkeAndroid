package com.lykkex.LykkeWallet.gui.fragments.startscreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.authentication.FieldActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController_;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.PasswordTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.widgets.ValidationEditText;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.registration.callback.RegistrationDataCallback;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.field_fragment)
public class FieldFragment extends BaseFragment<FieldState> {

    private ProgressDialog dialog;
    FieldController controller;

    @ViewById Button buttonAction;
    @ViewById RelativeLayout validationField;
    @ViewById EditText editTextField;
    @ViewById ImageView imageWell;
    @ViewById Button buttonClear;
    @ViewById ProgressBar progressBar;
    @ViewById RelativeLayout relProgress;
    @ViewById ImageView imageViewLogo;
    @ViewById TextView tvInfo;
    @ViewById EditText editTextFieldLogin;
    @ViewById RelativeLayout validationFieldLogin;
    @ViewById Button buttonClearLogin;
    @ViewById ImageView imageWellLogin;
    @ViewById TextView textView;
    @ViewById ImageView imageView;

    private ActionBar actionBar;
    private RegistrationModelGUI model;

    private ValidationEditText validationEditText;

    public void setUpActionBar(ActionBar actionBar){
        this.actionBar = actionBar;
    }

    @AfterViews
    public void afterViews() {
        validationEditText = new ValidationEditText(editTextField, imageWell, buttonClear, buttonAction);

        controller = ((FieldActivity)getActivity()).getController();
        model = ((FieldActivity)getActivity()).getModel();

        if (controller == null) {
            model = new RegistrationModelGUI();
            controller = FieldController_.getInstance_(getActivity());
            controller.init(this, FieldState.EmailScreen);
        }

        switch (controller.getCurrentState()){
            case EmailScreen:
                initEmailState();
                break;
            case FullNameScreen:
                initFullNameState();
                break;
            case MobileScreen:
                initMobileState();
                break;
            case FirstPasswordScreen:
                initFirstPasswordState();
                break;
            case SecondPasswordScreen:
                initSecondPasswordState();
                break;
        }
    }

    private void clearReadyModel(){
        model.setIsReady(false);
        validationEditText.setReady(false);
        buttonAction.setEnabled(false);
    }

    private void setUpClearEnter(){
        imageView.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        tvInfo.setText(R.string.complete_your_profile);
        if (actionBar != null) {
            actionBar.show();
            actionBar.setTitle(R.string.registration);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        tvInfo.setVisibility(View.VISIBLE);
        relProgress.setVisibility(View.VISIBLE);
        imageViewLogo.setVisibility(View.GONE);
        editTextField.setSelection(editTextField.getText().toString().length());
        buttonAction.setText(R.string.action_next);
        editTextField.requestFocus();

    }

    public void initEmailState() {
        actionBar.hide();
        clearReadyModel();
        imageView.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editTextField.addTextChangedListener
                (new EmailTextWatcher(this, validationEditText,progressBar, buttonAction));
        editTextField.setSelection(editTextField.getText().toString().length());
        relProgress.setVisibility(View.GONE);
        tvInfo.setVisibility(View.GONE);
        imageViewLogo.setVisibility(View.VISIBLE);
        editTextField.setHint(R.string.email_hint);
        buttonAction.setText(R.string.action_sign_up);
        if (getArguments() != null &&
                getArguments().getString(Constants.EXTRA_EMAIL) != null) {
            model.setEmail(getArguments().getString(Constants.EXTRA_EMAIL));
            getArguments().clear();
        }
        editTextField.setText(model.getEmail());
    }

    public void initFullNameState() {
        clearReadyModel();
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL, this, validationEditText));
        editTextField.setText(model.getFullName());
        editTextField.setHint(R.string.fullname_hint);
        setUpClearEnter();
    }

    public void initMobileState() {
        clearReadyModel();
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_PHONE);
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL, this, validationEditText));
        editTextField.setText(model.getMobile());
        editTextField.setHint(R.string.mobile_hint);
        setUpClearEnter();
    }

    public void initFirstPasswordState() {
        clearReadyModel();
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextField.addTextChangedListener(new SimpleTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD
                , this, validationEditText));
        editTextField.setText(model.getPasswordFirst());
        editTextField.setHint(R.string.first_password_hint);
        setUpClearEnter();
    }

    public void initSecondPasswordState() {
        clearReadyModel();
        editTextField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editTextField.addTextChangedListener( new PasswordTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this,
                model.getPasswordFirst(), validationEditText));
        editTextField.setText(model.getSecondPassword());
        editTextField.setHint(R.string.seond_password_hint);
        setUpClearEnter();
    }

    private void sendRegistrationRequest(){
        dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getActivity().getString(
                R.string.waiting));
        dialog.setCancelable(false);
        if (model.isReady()) {
            dialog.show();
            buttonAction.setEnabled(false);
            RegistrationDataCallback callback = new RegistrationDataCallback(progressBar, this, getActivity());
            Call<RegistrationData> call = LykkeApplication_.getInstance().getRestApi().registration(model);
            call.enqueue(callback);
        }

    }


    @Click(R.id.buttonAction)
    public void clickAction() {
        if (model.isReady()) {
            switch (controller.getCurrentState()) {
                case EmailSignInScreen:
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EXTRA_EMAIL, editTextField.getText().toString());
                    ((FieldActivity)getActivity()).initFragment(new AuthFragment_(), bundle);
                    break;
                case EmailScreen:
                    model.setEmail(editTextField.getText().toString());
                    controller.fire(FieldTrigger.FullNameScreen);
                    ((FieldActivity) getActivity()).initFragment(new FieldFragment_(),
                            null, controller, model);
                    break;
                case FullNameScreen:
                    model.setFullName(editTextField.getText().toString());
                    controller.fire(FieldTrigger.MobileScreen);
                    ((FieldActivity) getActivity()).initFragment(new FieldFragment_(),
                            null, controller, model);
                    break;
                case MobileScreen:
                    model.setMobile(editTextField.getText().toString());
                    controller.fire(FieldTrigger.FirstPasswordScreen);
                    ((FieldActivity) getActivity()).initFragment(new FieldFragment_(),
                            null, controller, model);
                    break;
                case FirstPasswordScreen:
                    model.setPasswordFirst(editTextField.getText().toString());
                    controller.fire(FieldTrigger.SecondPasswordScreen);
                    ((FieldActivity) getActivity()).initFragment(new FieldFragment_(),
                            null, controller, model);
                    break;
                case SecondPasswordScreen:
                    controller.fire(FieldTrigger.SendRegistrationRequst);
                    sendRegistrationRequest();
                    break;
            }
        }
    }

    @Click(R.id.relBackground)
    public void clickClearArea(){
        if (controller.getCurrentState() == FieldState.EmailScreen
                || controller.getCurrentState() == FieldState.EmailSignInScreen) {
            if (editTextField != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextField.getWindowToken(), 0);
            }
            editTextField.clearFocus();
        }
    }


    public void initOnBackPressed(){
        switch (controller.getCurrentState()) {
            case Idle:

            case EmailScreen:

            case EmailSignInScreen:
                getActivity().finish();
                break;
            case FullNameScreen:
                model.setFullName(editTextField.getText().toString());
                controller.fire(FieldTrigger.EmailScreen);
                ((FieldActivity) getActivity()).initFragment(new FieldFragment_(),
                        null, controller, model);
                break;
            case MobileScreen:
                model.setMobile(editTextField.getText().toString());
                controller.fire(FieldTrigger.FullNameScreen);
                ((FieldActivity) getActivity()).initFragment(new FieldFragment_(),
                        null, controller, model);
                break;
            case FirstPasswordScreen:
                model.setPasswordFirst(editTextField.getText().toString());
                controller.fire(FieldTrigger.MobileScreen);
                ((FieldActivity) getActivity()).initFragment(new FieldFragment_(),
                        null, controller, model);
                break;
            case SecondPasswordScreen:
                model.setSecondPassword(editTextField.getText().toString());
                controller.fire(FieldTrigger.FirstPasswordScreen);
                ((FieldActivity) getActivity()).initFragment(new FieldFragment_(),
                        null, controller, model);
                break;
        }
    }

    private void setUpReady(){
        buttonAction.setEnabled(true);
        validationEditText.setReady(true);
        model.setIsReady(true);
    }

    @Override
    public void onSuccess(Object result) {
        switch (controller.getCurrentState()) {
            case EmailSignInScreen:
                if (result != null) {
                    model.setIsReady(!((AcountExistResult) result).isEmailRegistered());
                    if (!model.isReady()) {
                        buttonAction.setText(R.string.action_sing_in);
                        controller.fire(FieldTrigger.EmailSignInScreen);
                        model.setIsReady(true);
                    } else {
                        buttonAction.setText(R.string.action_sign_up);
                        controller.fire(FieldTrigger.EmailScreen);
                    }
                    setUpReady();
                }
                break;
            case EmailScreen:
                if (result != null) {
                    model.setIsReady(!((AcountExistResult) result).isEmailRegistered());
                    if (!model.isReady()) {
                        buttonAction.setText(R.string.action_sing_in);
                        controller.fire(FieldTrigger.EmailSignInScreen);
                        model.setIsReady(true);
                    }
                    setUpReady();
                }
                break;
            case FullNameScreen:
                setUpReady();
                break;
            case MobileScreen:
                setUpReady();
                break;
            case FirstPasswordScreen:
                setUpReady();
                break;
            case SecondPasswordScreen:
                setUpReady();
                break;
            case SendRegistrationRequst:
                dialog.dismiss();
                Intent intent = new Intent();
                intent.setClass(getActivity(),  CameraActivity_.class);
                new UserPref_(getActivity()).authToken().put(((RegistrationData) result).getResult().getToken());
                new UserPref_(getActivity()).fullName().put(((RegistrationData) result).getResult().getPersonalData().getFullName());
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }


    @Override
    public void onFail(Error error) {

    }

    @Override
    public void onConsume(FieldState state) {

    }
}
