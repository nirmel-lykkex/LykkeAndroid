package com.lykkex.LykkeWallet.gui.activity.paymentflow;

import android.os.Bundle;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.SettingEnum;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.BaseAssetFragment_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.PersonalDataFragment_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting.PushFragment_;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 13.03.2016.
 */
@EActivity(R.layout.activity_main)
public class SettingActivity extends BaseActivity {

    @AfterViews
    public void afterViews(){
        SettingEnum status = (SettingEnum) getIntent().getExtras().getSerializable(Constants.EXTRA_FRAGMENT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Bundle args = new Bundle();
        args.putBoolean(Constants.SKIP_BACKSTACK, true);

        switch (status) {
            case personalData:
                initFragment(new PersonalDataFragment_(), args);
                break;
            case pushnotifications:
                initFragment(new PushFragment_(), args);
                break;
            case baseasset:
                initFragment(new BaseAssetFragment_(), args);
                break;
        }
    }
}
