package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
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
        SettingEnum status = (SettingEnum) getIntent().getExtras().getSerializable(Constants.EXTRA_FRAGMENT_SETTING);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        switch (status) {
            case personalData:
                initFragment(new PersonalDataFragment_(), null);
                break;
        }
    }
}
