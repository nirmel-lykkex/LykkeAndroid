package com.lykkex.LykkeWallet.gui.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.guisegments.LoginGuiSegment;
import com.lykkex.LykkeWallet.gui.fragments.guisegments.RegistrationGuiSegment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.field_fragment)
public class FieldFragment extends Fragment  {

    @Bean FieldController controller;
    @Bean RegistrationGuiSegment registrationGuiSegment;
    @Bean LoginGuiSegment loginGuiSegment;

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

    public void setUpActionBar(ActionBar actionBar){
        this.actionBar = actionBar;
    }

    @AfterViews
    public void afterViews() {
        controller.init(this, FieldState.EmailScreen);
        registrationGuiSegment.init(editTextField, imageWell,buttonClear,
               buttonAction,  controller, progressBar, imageViewLogo, relProgress,
                tvInfo, actionBar, getActivity(), textView, imageView);
        loginGuiSegment.init(editTextField,  buttonAction, imageWell,buttonClear,controller,
                progressBar, tvInfo, relProgress, imageViewLogo, actionBar, getActivity(), editTextFieldLogin,
                validationFieldLogin,
                buttonClearLogin,
                imageWellLogin, textView, imageView);
        initEmailState();
    }

    public void initEmailState() {
        actionBar.show();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        registrationGuiSegment.initEmailState();
    }

    public void initFullNameState() {
        actionBar.show();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        registrationGuiSegment.initFullNameState();
    }

    public void initMobileState() {
        actionBar.show();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        registrationGuiSegment.initMobileState();
    }

    public void initFirstPasswordState() {
        actionBar.show();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        registrationGuiSegment.initFirstPasswordState();
    }

    public void initSecondPasswordState() {
        actionBar.show();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        registrationGuiSegment.initSecondPasswordState();
    }

    public void initPasswordSignInScreen(){
        actionBar.show();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        loginGuiSegment.initPasswordSignInScreen();
    }

    public void sendRegistrationRequest(){
        registrationGuiSegment.sendRegistrationRequest();
    }

    public void sendAuthRequest(){
        loginGuiSegment.sendAuthRequest();
    }

    public void initEmailSignInScreen(){
        actionBar.show();
        registrationGuiSegment.clearEditText();
        loginGuiSegment.initEmailSignIn();
    }

    @Click(R.id.buttonAction)
    public void clickAction() {
        registrationGuiSegment.clickAction();
        loginGuiSegment.clickAction();
    }

    @Click(R.id.relBackground)
    public void clickClearArea(){
        if (controller.getCurrentState() == FieldState.EmailScreen
                || controller.getCurrentState() == FieldState.EmailSignInScreen ||
                controller.getCurrentState() == FieldState.FullNameScreenBack) {
            if (editTextField != null) {
                InputMethodManager imm = (InputMethodManager)getActivity().
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(editTextField.getWindowToken(), 0);
            }
            editTextField.clearFocus();
        }
    }


    public void initBackPressed(){
        getActivity().finish();
    }

    public void initBackPressedFullName(){
        actionBar.show();
        registrationGuiSegment.initBackPressedFullName();
    }

    public void initBackPressedMobile(){
        actionBar.show();
        registrationGuiSegment.initBackPressedMobile();
    }

    public void initBackPressedFirstPasswordScreen(){
        actionBar.show();
        registrationGuiSegment.initBackPressedFirstPasswordScreen();
    }

    public void initBackPressedSecondPasswordScreen(){
        actionBar.show();
        registrationGuiSegment.initBackPressedSecondPasswordScreen();
    }

    public void initBackPressedPasswordSignIn(){
        actionBar.show();
        loginGuiSegment.initBackPressedPasswordSignIn();
    }

    public void initOnBackPressed(){
        if (controller.getCurrentState() == FieldState.Idle ||
                controller.getCurrentState() == FieldState.EmailScreenBack ||
                controller.getCurrentState() == FieldState.EmailScreen ||
                controller.getCurrentState() == FieldState.EmailSignInScreen) {
            getActivity().finish();
        } else {
            controller.fireBackPressed();
        }
    }
}
