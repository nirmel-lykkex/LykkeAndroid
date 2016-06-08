package com.lykkex.LykkeWallet.gui.fragments.registration;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.registration.request.models.SetFullNameModel;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;
import com.lykkex.LykkeWallet.rest.registration.response.models.SetFullNameData;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.registration_step_3_fragment)
public class RegistrationStep3Fragment extends BaseFragment {

    @ViewById
    StepsIndicator stepsIndicator;

    @ViewById
    EditText fullNameEditText;

    @ViewById
    Button buttonAction;

    @Bean
    UserManager userManager;

    @App
    LykkeApplication lykkeApplication;

    @Click(R.id.buttonAction)
    public void clickButtonAction(){
        sendSetFullNameRequest();
    }

    @AfterViews
    void afterViews() {
        stepsIndicator.setCurrentStep(1);

        fullNameEditText.setText(userManager.getRegistrationResult().getPersonalData().getFullName());
    }

    @AfterTextChange(R.id.fullNameEditText)
    void onHintChange(Editable text) {
        userManager.getRegistrationResult().getPersonalData().setFullName(text.toString());

        buttonAction.setEnabled(text.toString().length() > 0);
    }

    private void sendSetFullNameRequest(){
        buttonAction.setEnabled(false);

        final SetFullNameModel model = new SetFullNameModel();
        model.setFullName(fullNameEditText.getText().toString());

        Call<SetFullNameData> call = lykkeApplication.getRestApi().setClientFullName(model);

        call.enqueue(new Callback<SetFullNameData>() {
            @Override
            public void onResponse(Call<SetFullNameData> call, Response<SetFullNameData> response) {
                if(!response.isSuccess() || response.body().getError() != null) {
                    onFailure(call, new RuntimeException("Unexpected error occured."));

                    return;
                }

                new UserPref_(getActivity()).fullName().put(model.getFullName());

                ((BaseActivity) getActivity()).initFragment(new RegistrationStep4Fragment_(), null);
            }

            @Override
            public void onFailure(Call<SetFullNameData> call, Throwable t) {
                Log.e(RegistrationStep3Fragment.class.getSimpleName(), "Error while registering user.", t);

                LykkeUtils.showError(getFragmentManager(), "Unexpected error while registering user.");
            }
        });
    }
}
