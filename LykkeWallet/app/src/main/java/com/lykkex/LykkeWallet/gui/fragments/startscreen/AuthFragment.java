package com.lykkex.LykkeWallet.gui.fragments.startscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.authentication.AuthenticationActivity_;
import com.lykkex.LykkeWallet.gui.activity.authentication.FieldActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.Consume;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.utils.validation.CountTextWatcher;
import com.lykkex.LykkeWallet.rest.base.models.Error;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.auth_fragment)
public class AuthFragment extends BaseFragment<FieldState> implements CallBackListener {

    @Bean FieldController controller;
    private AuthModelGUI authRequest;
    private ActionBar actionBar;
    @ViewById EditText editTextLogin;
    @ViewById EditText editTextPassword;
    @ViewById Button buttonLogin;
    @ViewById ProgressBar progressBar;
    CountTextWatcher passwordTextWatcher;

    public void setUpActionBar(ActionBar actionBar){
        this.actionBar = actionBar;
    }

    @AfterViews
    public void afterViews() {
        if (actionBar != null) {
            actionBar.setTitle(R.string.login);
            actionBar.show();
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        authRequest = new AuthModelGUI();
        authRequest.setEmail(getArguments().getString(Constants.EXTRA_EMAIL));
        editTextLogin.setText(authRequest.getEmail());
        passwordTextWatcher = new CountTextWatcher(Constants.MIN_COUNT_SYMBOL_PASSWORD, this);
        editTextPassword.addTextChangedListener(passwordTextWatcher);
        editTextPassword.requestFocus();
        buttonLogin.setEnabled(false);
    }


    @Click(R.id.buttonLogin)
    public void clickAction() {
        buttonLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        authRequest.setPassword(editTextPassword.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_AUTH_REQUEST, authRequest);
        intent.setClass(getActivity(), AuthenticationActivity_.class);
        startActivity(intent);
        getActivity().finish();
    }


    public void initOnBackPressed() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRA_EMAIL, editTextLogin.getText().toString());
        ((FieldActivity) getActivity()).initFragment(new FieldFragment_(), bundle);
    }

    @Override
    public void onSuccess(Object result) {
        buttonLogin.setEnabled(true);
    }

    @Override
    public void onFail(Error error) {

    }

    @Override
    public void onConsume(FieldState state) {
        switch (state){
            case EmailSignInScreen:
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EXTRA_EMAIL, editTextLogin.getText().toString());
                ((FieldActivity)getActivity()).initFragment(new FieldFragment_(), bundle);
                break;
        }
    }
}
