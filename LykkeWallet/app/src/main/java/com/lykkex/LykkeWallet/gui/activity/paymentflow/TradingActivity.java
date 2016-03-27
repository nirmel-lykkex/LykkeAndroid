package com.lykkex.LykkeWallet.gui.activity.paymentflow;

import android.view.MenuItem;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.TradingEnum;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.TradingDescription;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.TradingDescription_;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by e.kazimirova on 18.03.2016.
 */
@EActivity(R.layout.activity_main)
public class TradingActivity extends BaseActivity {

    @AfterViews
    public void afterViews(){
        TradingEnum status = (TradingEnum) getIntent().getExtras().getSerializable(Constants.EXTRA_FRAGMENT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        switch (status) {
            case description:
                initFragment(new TradingDescription_(), getIntent().getExtras());
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

    @Override
    public void onBackPressed(){
        ((BaseFragment)currentFragment).initOnBackPressed();
    }


}

