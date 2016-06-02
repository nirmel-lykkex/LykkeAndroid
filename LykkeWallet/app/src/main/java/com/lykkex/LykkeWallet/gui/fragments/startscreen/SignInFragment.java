package com.lykkex.LykkeWallet.gui.fragments.startscreen;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.lykkex.LykkeWallet.BuildConfig;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.customviews.RichEditText;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.widgets.DialogChangeServer;
import com.lykkex.LykkeWallet.rest.appinfo.callback.AppInfoCallBack;
import com.lykkex.LykkeWallet.rest.appinfo.response.model.AppInfoData;
import com.lykkex.LykkeWallet.rest.emailverify.request.model.VerifyEmailRequest;
import com.lykkex.LykkeWallet.rest.emailverify.response.model.VerifyEmailData;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.sign_in_fragment)
public class SignInFragment extends Fragment {

    @ViewById
    RichEditText emailRichEditText;

    @ViewById
    Button buttonAction;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    ImageView whatShouldKnowImageView;

    @ViewById
    Button btnChangeServer;

    @Bean
    UserManager userManager;

    @App
    LykkeApplication lykkeApplication;

    @Click(R.id.btnChangeServer)
    public void clickBtnChangeServer(){
        DialogChangeServer dialogChangeServer = new DialogChangeServer();
        dialogChangeServer.show(getActivity().getFragmentManager(),
                "dlg1" +new Random((int) Constants.DELAY_5000));
    }

    @Click(R.id.buttonAction)
    public void clickButtonAction(){
        final RegistrationModelGUI registrationModel = userManager.getRegistrationModel();

        registrationModel.setEmail(emailRichEditText.getText().toString());

        if(getActivity() instanceof BaseActivity) {
            if(userManager.isUserRegistered()) {
                // TODO: LOG IN!
            } else {
                Call<VerifyEmailData> call = lykkeApplication.getRestApi().verifyEmail(new VerifyEmailRequest(registrationModel.getEmail()));

                call.enqueue(new Callback<VerifyEmailData>() {
                    @Override
                    public void onResponse(Call<VerifyEmailData> call, Response<VerifyEmailData> response) {
                        if(response.body() == null) {
                            Log.e("ERROR", "Unexpected error while confirming email: " +
                                    userManager.getRegistrationModel().getEmail() + ", " + response.errorBody());

                            LykkeUtils.showError(getFragmentManager(), "Unexpected error while confirming email.");

                            return;
                        }

                        if(response.body().getError() == null) {
                            ((BaseActivity) getActivity()).initFragment(new ConfirmRegistrationFragment_(), null);
                        } else {
                            Log.e("ERROR", "Error while verifying email address: " + registrationModel.getEmail() + ", " + response.body().getError().getMessage());

                            LykkeUtils.showError(getFragmentManager(), response.body().getError().getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<VerifyEmailData> call, Throwable t) {
                        Log.e("ERROR", "Error while verifying email address: " + registrationModel.getEmail(), t);

                        LykkeUtils.showError(getFragmentManager(), "Unexpected error while confirming email.");
                    }
                });
            }
        }
    }

    @AfterViews
    void afterViews() {
        if (BuildConfig.CHANGE_SERVER){
            btnChangeServer.setVisibility(View.VISIBLE);
        } else {
            btnChangeServer.setVisibility(View.GONE);
        }

        final RegistrationModelGUI registrationModel = userManager.getRegistrationModel();

        buttonAction.setText(R.string.action_sign_up);

        if (getArguments() != null && getArguments().containsKey(Constants.EXTRA_EMAIL)) {
            registrationModel.setEmail(getArguments().getString(Constants.EXTRA_EMAIL));

            getArguments().clear();
        }

        emailRichEditText.addTextWatcher(new EmailTextWatcher(new CallBackListener<AcountExistResult>() {
            @Override
            public void onSuccess(AcountExistResult result) {
                if (result != null) {
                    if ( emailRichEditText.getText().toString().equals(result.getEmail())) {
                        buttonAction.setText(result.isEmailRegistered() ?
                                R.string.action_sign_in : R.string.action_sign_up);

                        userManager.setUserRegistered(result.isEmailRegistered());

                        registrationModel.setIsReady(true);

                        buttonAction.setEnabled(true);

                        emailRichEditText.setReady(registrationModel.isReady());
                    } else {
                        buttonAction.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFail(Object error) {
                Log.e("ERROR", "Error while checking if user is registered: " + (error != null ? error.toString() : ""));
            }
        }, emailRichEditText, progressBar, buttonAction));

        emailRichEditText.setText(registrationModel.getEmail());
        emailRichEditText.setHint(R.string.email_hint);
    }
}
