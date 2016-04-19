package com.lykkex.LykkeWallet.gui.activity.paymentflow;

import android.view.MenuItem;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet.TradingWalletFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 15.04.2016.
 */
@EActivity(R.layout.activity_main)
public class QrCodeActivity extends BaseActivity {

    @AfterViews
    public void afterViews() {
        initFragment(new TradingWalletFragment_(), getIntent().getExtras());
    }

    @Override
    public void onBackPressed(){
        ((BaseFragment)currentFragment).initOnBackPressed();
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
