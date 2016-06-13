package com.lykkex.LykkeWallet.gui.activity.paymentflow;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet.QrCodeFragment_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet.TradingWalletFragment_;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 15.04.2016.
 */
@EActivity(R.layout.activity_main)
public class QrCodeActivity extends BaseActivity {

    @AfterViews
    public void afterViews() {
        if(getIntent().getBooleanExtra(Constants.EXTRA_DEPOSIT, false)) {
            initFragment(new QrCodeFragment_(), getIntent().getExtras());
        } else {
            initFragment(new TradingWalletFragment_(), getIntent().getExtras());
        }
    }
}
