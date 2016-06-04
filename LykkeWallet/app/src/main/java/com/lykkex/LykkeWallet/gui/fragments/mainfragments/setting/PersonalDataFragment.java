package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.camera.callback.SendDocumentsDataCallback;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.personal_data_fragment)
public class PersonalDataFragment extends BaseFragment implements CallBackListener {

    @Pref UserPref_ userPref;
    @ViewById TextView tvName;
    @ViewById TextView tvEmail;
    @ViewById TextView tvPhone;
    @ViewById TextView tvCountry;
    @ViewById TextView tvCity;
    @ViewById TextView tvZip;
    @ViewById TextView tvAddress;
    @ViewById  LinearLayout relName;
    @ViewById  LinearLayout relEmail;
    @ViewById  LinearLayout relContactPhone;
    @ViewById  LinearLayout relCountry;
    @ViewById  LinearLayout relZip;
    @ViewById  LinearLayout relCity;
    @ViewById  LinearLayout relAddress;
    @ViewById  RelativeLayout relProgress;
    @ViewById  ProgressBar progressBar;
    @ViewById LinearLayout linearInfo;
    @ViewById View viewAddress;
    @ViewById View viewEmail;
    @ViewById View viewName;
    @ViewById View viewContactPhone;
    @ViewById View viewCountry;
    @ViewById View viewZip;
    @ViewById View viewCity;



    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.personal_data_title);
        relProgress.setVisibility(View.VISIBLE);
        getPersonalData();

    }

    private void getPersonalData(){
        Call<PersonData> call = LykkeApplication_.getInstance().getRestApi().getPersonalData
                (Constants.PART_AUTHORIZATION + userPref.authToken().get());
        SendDocumentsDataCallback callback = new SendDocumentsDataCallback(this, getActivity());
        call.enqueue(callback);
    }

    @Deprecated
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof PersonData) {
            PersonalData res = ((PersonData) result).getResult();
            if (res.getCity() != null) {
                userPref.city().put(res.getCity());
            }

            if (res.getCountry() != null) {
                userPref.country().put(res.getCountry());
            }

            if (res.getPhone() != null) {
                userPref.phone().put(res.getPhone());
            }

            if (res.getFullName() != null) {
                userPref.fullName().put(res.getFullName());
            }

            if (res.getZip() != null) {
                userPref.zip().put(res.getZip());
            }

            if (res.getAddress() != null){
                userPref.address().put(res.getAddress());
            }

            if (userPref.phone().get().isEmpty()) {
                relContactPhone.setVisibility(View.GONE);
            } else {
                tvPhone.setText(userPref.phone().get());
            }

            if (userPref.zip().get().isEmpty()) {
                relZip.setVisibility(View.GONE);
                viewZip.setVisibility(View.GONE);
            } else {
                tvZip.setText(userPref.zip().get());
            }

            if (userPref.fullName().get().isEmpty()) {
                relName.setVisibility(View.GONE);
                viewName.setVisibility(View.GONE);
            } else {
                tvName.setText(userPref.fullName().get());
            }

            if (userPref.email().get().isEmpty()) {
                relEmail.setVisibility(View.GONE);
                viewEmail.setVisibility(View.GONE);
            } else {
                tvEmail.setText(userPref.email().get());
            }

            if (userPref.address().get().isEmpty()) {
                relAddress.setVisibility(View.GONE);
                viewAddress.setVisibility(View.GONE);
            } else {
                tvAddress.setText(userPref.address().get());
            }

            if (userPref.city().get().isEmpty()) {
                relCity.setVisibility(View.GONE);
                viewCity.setVisibility(View.GONE);
            } else {
                tvCity.setText(userPref.city().get());
            }

            if (userPref.country().get().isEmpty()) {
                relCountry.setVisibility(View.GONE);
                viewCountry.setVisibility(View.GONE);
            } else {
                tvCountry.setText(userPref.country().get());
            }

            relProgress.setVisibility(View.GONE);
            linearInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFail(Object error) {

    }
}
