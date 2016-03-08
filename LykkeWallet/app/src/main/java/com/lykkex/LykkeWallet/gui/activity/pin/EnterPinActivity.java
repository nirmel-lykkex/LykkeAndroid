package com.lykkex.LykkeWallet.gui.activity.pin;

import android.content.Intent;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.MainActivity_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.pin.callback.CallBackPinSetUp;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import retrofit2.Call;

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
                dialog.show();
                CallBackPinSetUp callback = new CallBackPinSetUp(this, this);
                Call<Error> call  = LykkeApplication_.getInstance().getRestApi().
                        signInPinSecurite(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                                pin);
                call.enqueue(callback);
                break;
        }
    }



    @Override
    public void onSuccess(Object result) {
        dialog.dismiss();
        Intent intent = new Intent();
        intent.setClass(this, MainActivity_.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFail(Object error) {
        dialog.dismiss();
        imgFirst.setImageResource(R.drawable.pin_un_setup);
        imgSecond.setImageResource(R.drawable.pin_un_setup);
        imgThird.setImageResource(R.drawable.pin_un_setup);
        imgFour.setImageResource(R.drawable.pin_un_setup);
        Toast.makeText(this, R.string.wrong_pin, Toast.LENGTH_LONG).show();
    }
}
