package com.lykkex.LykkeWallet.gui.fragments.mainfragments.setting;

import android.view.MenuItem;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
