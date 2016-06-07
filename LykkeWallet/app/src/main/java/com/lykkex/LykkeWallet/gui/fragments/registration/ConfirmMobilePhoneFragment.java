package com.lykkex.LykkeWallet.gui.fragments.registration;

import android.app.Fragment;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.mobileverify.model.VerifyCodeData;

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
@EFragment(R.layout.confirm_mobile_phone_fragment)
public class ConfirmMobilePhoneFragment extends Fragment {

    @ViewById
    StepsIndicator stepsIndicator;

    @ViewById
    EditText codeEditText;

    @ViewById
    Button buttonAction;

    @ViewById
    TextView confirmCodeMessage;

    @Bean
    UserManager userManager;

    @App
    LykkeApplication lykkeApplication;

    @Click(R.id.buttonAction)
    public void clickButtonAction(){
        Call<VerifyCodeData> call = lykkeApplication.getRestApi().verifyMobilePhoneCode(userManager.getRegistrationResult().getPersonalData().getPhone(), codeEditText.getText().toString());

        call.enqueue(new Callback<VerifyCodeData>() {
            @Override
            public void onResponse(Call<VerifyCodeData> call, Response<VerifyCodeData> response) {
                if(!response.isSuccess()) {
                    Log.e("ERROR", "Unexpected error while confirming code for phone number: " +
                            userManager.getRegistrationResult().getPersonalData().getPhone() + ", " + response.errorBody());

                    LykkeUtils.showError(getFragmentManager(), "Unexpected error while confirming code.");

                    return;
                }

                if(response.body().getError() != null) {
                    LykkeUtils.showError(getFragmentManager(), response.body().getError().getMessage());
                } else if(!response.body().getResult().getPassed()){
                    LykkeUtils.showError(getFragmentManager(), "Incorrect code.");
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), CameraActivity_.class);
                    intent.putExtra(Constants.EXTRA_CAMERA_DATA, new CameraResult());
                    startActivity(intent);

                    getActivity().finish();
                }
            }

            @Override
            public void onFailure(Call<VerifyCodeData> call, Throwable t) {
                LykkeUtils.showError(getFragmentManager(), "Unexpected error while confirming code.");

                Log.e("ERROR", "Unexpected error while confirming code for email: " +
                        userManager.getRegistrationResult().getPersonalData().getPhone(), t);
            }
        });
    }

    @Click(R.id.tvHaventReceivedCode)
    public void clickHaventReceivedCode() {
        getFragmentManager().popBackStack();
    }

    @AfterViews
    void afterViews() {
        stepsIndicator.setCurrentStep(1);

        confirmCodeMessage.setText(getResources().getString(R.string.confirm_mobile_phone_code_message, userManager.getRegistrationResult().getPersonalData().getPhone()));
    }

    @AfterTextChange(R.id.codeEditText)
    void onCodeTextChange(Editable text) {
        buttonAction.setEnabled(text.toString().length() > 0);
    }
}
