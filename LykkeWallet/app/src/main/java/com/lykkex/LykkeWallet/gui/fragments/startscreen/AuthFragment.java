package com.lykkex.LykkeWallet.gui.fragments.startscreen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.authentication.AuthenticationActivity;
import com.lykkex.LykkeWallet.gui.activity.authentication.AuthenticationActivity_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextAuthWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.auth_fragment)
public class AuthFragment extends BaseFragment {

    private AuthModelGUI authRequest;
    private ActionBar actionBar;
    @ViewById EditText editTextLogin;
    @ViewById EditText editTextPassword;
    @ViewById Button buttonLogin;
    @ViewById ProgressBar progressBar;
    SimpleTextWatcher passwordTextWatcher;

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

        if(getArguments() != null) {
            authRequest.setEmail(getArguments().getString(Constants.EXTRA_EMAIL));
        }


        editTextLogin.setText(authRequest.getEmail());
        passwordTextWatcher = new SimpleTextAuthWatcher(null, null,
                editTextPassword, new CallBackListener() {
            @Override
            public void onSuccess(Object result) {
                buttonLogin.setEnabled(true);
            }

            @Override
            public void onFail(Object error) {
                // Skip
            }
        }, Constants.MIN_COUNT_SYMBOL_PASSWORD);

        editTextPassword.addTextChangedListener(passwordTextWatcher);
        editTextPassword.requestFocus();
        buttonLogin.setEnabled(false);
    }


    @Click(R.id.buttonLogin)
    public void clickAction() {
        authRequest.setPassword(editTextPassword.getText().toString());
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_AUTH_REQUEST, authRequest);
        intent.setClass(getActivity(), AuthenticationActivity_.class);

        startActivityForResult(intent, AuthenticationActivity.AUTHENTICATION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == AuthenticationActivity.AUTHENTICATION_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                getActivity().finish();
            }
        }
    }
}
