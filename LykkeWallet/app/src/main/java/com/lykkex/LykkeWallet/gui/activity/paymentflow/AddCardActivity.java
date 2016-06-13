package com.lykkex.LykkeWallet.gui.activity.paymentflow;


import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.addcard.AddCard_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 06.03.2016.
 */
@EActivity(R.layout.activity_main)
public class AddCardActivity extends BaseActivity {

    @AfterViews
    public void afterViews() {
        initFragment(new AddCard_(), getIntent().getExtras());
    }
}
