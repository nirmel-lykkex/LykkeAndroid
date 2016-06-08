package com.lykkex.LykkeWallet.gui.fragments.registration;

import android.app.Fragment;
import android.support.v7.widget.ListViewCompat;
import android.text.Editable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.adapters.CountryPhoneCodesAdapter;
import com.lykkex.LykkeWallet.gui.customviews.RichButton;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.managers.UserManager;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodeData;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodesData;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodesResult;

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
@EFragment(R.layout.country_phone_codes_list_fragment)
public class CountryPhoneCodesFragment extends BaseFragment {

    @ViewById
    ListViewCompat countryPhoneCodesList;

    @Bean
    UserManager userManager;

    @AfterViews
    void afterViews() {
        CountryPhoneCodesResult countryPhoneCodes = userManager.getCountryPhoneCodes();

        CountryPhoneCodesAdapter adapter = new CountryPhoneCodesAdapter(getActivity(), countryPhoneCodes, new CountryPhoneCodesAdapter.OnInteractionListener() {
            @Override
            public void onSelected(CountryPhoneCodeData item, int position) {
                userManager.getCountryPhoneCodes().setCurrent(item.getId());

                getFragmentManager().popBackStack();
            }
        });

        countryPhoneCodesList.setAdapter(adapter);
    }
}
