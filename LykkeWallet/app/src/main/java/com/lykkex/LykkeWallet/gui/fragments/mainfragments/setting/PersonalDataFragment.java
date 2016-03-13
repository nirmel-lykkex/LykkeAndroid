package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.personal_data_fragment)
public class PersonalDataFragment extends BaseFragment {

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


    @AfterViews
    public void afterViews(){
        actionBar.setTitle(R.string.personal_data_title);
        if (userPref.phone().get().isEmpty()) {
            relContactPhone.setVisibility(View.GONE);
        } else {
            tvPhone.setText(userPref.phone().get());
        }

        if (userPref.zip().get().isEmpty()) {
            relZip.setVisibility(View.GONE);
        } else {
            tvZip.setText(userPref.zip().get());
        }

        if (userPref.fullName().get().isEmpty()) {
            relName.setVisibility(View.GONE);
        } else {
            tvName.setText(userPref.fullName().get());
        }

        if (userPref.email().get().isEmpty()) {
            relEmail.setVisibility(View.GONE);
        } else {
            tvEmail.setText(userPref.email().get());
        }

        if (userPref.address().get().isEmpty()) {
            relAddress.setVisibility(View.GONE);
        } else {
            tvAddress.setText(userPref.address().get());
        }

        if (userPref.city().get().isEmpty()) {
            relCity.setVisibility(View.GONE);
        } else {
            tvCity.setText(userPref.city().get());
        }

        if (userPref.country().get().isEmpty()) {
            relCountry.setVisibility(View.GONE);
        } else {
            tvCountry.setText(userPref.country().get());
        }
    }

    @Override
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {

    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}
