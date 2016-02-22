package com.lykkex.LykkeWallet.gui;

import android.content.Intent;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.rest.base.models.Error;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by LIZA on 18.02.2016.
 */
@EActivity(R.layout.pin_activity)
public class EnterPinActivity extends BasePinActivity{

    @AfterViews
    public void afterViews(){
        super.afterViews();

    }

    protected void setUpVisibility(){
        super.setUpVisibility();
        switch (pin.length()) {
            case 4:

                break;
        }
    }



    @Override
    public void onSuccess(Object result) {
        dialog.dismiss();
        Intent intent = new Intent();
        intent.setClass(this, MainActivity_.class);
        startActivity(intent);
    }

    @Override
    public void onFail(Error error) {

    }
}
