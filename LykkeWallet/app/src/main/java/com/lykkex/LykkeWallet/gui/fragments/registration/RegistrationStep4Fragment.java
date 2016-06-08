package com.lykkex.LykkeWallet.gui.fragments.registration;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.customviews.ExtendedEditText;
import com.lykkex.LykkeWallet.gui.customviews.RichButton;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.startscreen.ConfirmEmailFragment_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.emailverify.request.model.VerifyEmailRequest;
import com.lykkex.LykkeWallet.rest.emailverify.response.model.VerifyEmailData;
import com.lykkex.LykkeWallet.rest.mobileverify.model.VerifyMobilePhoneData;
import com.lykkex.LykkeWallet.rest.mobileverify.request.VerifyMobilePhoneRequest;
import com.lykkex.LykkeWallet.rest.registration.request.models.SetFullNameModel;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodeData;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodesData;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodesResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.SetFullNameData;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.registration_step_4_fragment)
public class RegistrationStep4Fragment extends BaseFragment {

    @ViewById
    StepsIndicator stepsIndicator;

    @ViewById
    RichButton countryButton;

    @ViewById
    ExtendedEditText phoneEditText;

    @ViewById
    Button buttonAction;

    @Bean
    UserManager userManager;

    @App
    LykkeApplication lykkeApplication;

    @Click(R.id.buttonAction)
    public void clickButtonAction(){
        final RegistrationResult registrationResult = userManager.getRegistrationResult();

        Call<VerifyMobilePhoneData> call = lykkeApplication.getRestApi().verifyMobilePhoneNumber(new VerifyMobilePhoneRequest(registrationResult.getPersonalData().getPhone()));

        call.enqueue(new Callback<VerifyMobilePhoneData>() {
            @Override
            public void onResponse(Call<VerifyMobilePhoneData> call, Response<VerifyMobilePhoneData> response) {
                if(!response.isSuccess()) {
                    Log.e("ERROR", "Unexpected error while confirming mobile phone number: " +
                            registrationResult.getPersonalData().getPhone() + ", " + response.errorBody());

                    LykkeUtils.showError(getFragmentManager(), "Unexpected error while confirming mobile phone number.");

                    return;
                }

                if(response.body().getError() == null) {
                    ((BaseActivity) getActivity()).initFragment(new ConfirmMobilePhoneFragment_(), null);
                } else {
                    Log.e("ERROR", "Error while verifying mobile code number: " + registrationResult.getPersonalData().getPhone() + ", " + response.body().getError().getMessage());

                    LykkeUtils.showError(getFragmentManager(), response.body().getError().getMessage());
                }
            }

            @Override
            public void onFailure(Call<VerifyMobilePhoneData> call, Throwable t) {
                Log.e("ERROR", "Error while verifying mobile phone number: " + registrationResult.getPersonalData().getPhone(), t);

                LykkeUtils.showError(getFragmentManager(), "Unexpected error while confirming mobile phone number.");
            }
        });
    }

    @Click(R.id.countryButton)
    public void clickCountriesButton(){
        ((BaseActivity) getActivity()).initFragment(new CountryPhoneCodesFragment_(), null);
    }

    @AfterViews
    void afterViews() {
        stepsIndicator.setCurrentStep(1);
        Call<CountryPhoneCodesData> call = lykkeApplication.getRestApi().getCountryPhoneCodes();

        if(userManager.getCountryPhoneCodes() == null) {
            call.enqueue(new Callback<CountryPhoneCodesData>() {
                @Override
                public void onResponse(Call<CountryPhoneCodesData> call, Response<CountryPhoneCodesData> response) {
                    if (!response.isSuccess() || response.body().getResult() == null) {
                        Log.e(RegistrationStep4Fragment.class.getSimpleName(), "Error while loading list of countries");

                        return;
                    }

                    onCountryPhoneCodesLoaded(response.body().getResult());
                }

                @Override
                public void onFailure(Call<CountryPhoneCodesData> call, Throwable t) {
                    Log.e(RegistrationStep4Fragment.class.getSimpleName(), "Error while loading list of countries", t);
                }
            });
        } else {
            onCountryPhoneCodesLoaded(userManager.getCountryPhoneCodes());
        }
    }

    private void onCountryPhoneCodesLoaded(CountryPhoneCodesResult res) {
        final Map<String, CountryPhoneCodeData> phoneCodeDataMap = new HashMap<>();

        for (CountryPhoneCodeData countryPhoneCode : res.getCountriesList()) {
            phoneCodeDataMap.put(countryPhoneCode.getId(), countryPhoneCode);
        }

        CountryPhoneCodeData currentCode = phoneCodeDataMap.get(res.getCurrent());

        if (currentCode != null) {
            countryButton.setText(currentCode.getName());
            phoneEditText.setPrefix(currentCode.getPrefix() + " | ");

            phoneEditText.setText(userManager.getRegistrationResult().getPersonalData().getPhone().replace(currentCode.getPrefix(), ""));
        }

        userManager.setCountryPhoneCodes(res);
    }

    @AfterTextChange(R.id.phoneEditText)
    void onPhoneChange(Editable text) {
        userManager.getRegistrationResult().getPersonalData().setPhone(phoneEditText.getPrefix().replace(" | ", "") + text.toString());

        buttonAction.setEnabled(text.toString().length() > 0);
    }
}
