package com.lykkex.LykkeWallet.gui.activity.paymentflow;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.addcard.AddCard;
import com.lykkex.LykkeWallet.gui.fragments.addcard.AddCard_;
import com.lykkex.LykkeWallet.rest.wallet.request.models.CardModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 06.03.2016.
 */
@EActivity(R.layout.activity_main)
public class AddCardActivity extends BaseActivity {

    @AfterViews
    public void afterViews() {
        initFragment(new AddCard_(), null);
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
