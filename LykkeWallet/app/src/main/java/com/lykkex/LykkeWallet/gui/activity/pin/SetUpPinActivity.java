package com.lykkex.LykkeWallet.gui.activity.pin;

import android.content.Intent;
import android.view.View;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.MainActivity_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.pin.callback.CallBackPinSetUp;
import com.lykkex.LykkeWallet.rest.pin.request.model.PinRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

import retrofit2.Call;

/**
 * Created by LIZA on 18.02.2016.
 */
@EActivity(R.layout.pin_activity)
public class SetUpPinActivity extends BasePinActivity{

    @AfterViews
    public void afterViews(){
        userPref.pin().put("");
        super.afterViews();
        textView4.setText(R.string.enter_pin);
    }

    protected void setUpVisibility(){
        super.setUpVisibility();
        switch (pin.length()) {
            case 4:
                if (userPref.pin().get().isEmpty()){
                    userPref.pin().put(pin);
                    resetUp();
                    textView4.setText(R.string.re_enter_pin);
                } else if (userPref.pin().get().equals(pin)){
                    dialog.show();
                    CallBackPinSetUp callback = new CallBackPinSetUp(this, this);
                    Call<Error> call  = LykkeApplication_.getInstance().getRestApi().
                            postPinSecurite(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                                    new PinRequest((pin)));
                    call.enqueue(callback);
                } else {
                    resetUp();
                    textView4.setText(R.string.wrong_pin);
                    userPref.pin().put("");
                }
                break;
        }
    }

    private void resetUp(){
        pin = "";
        imgFirst.setImageResource(R.drawable.pin_un_setup);
        imgSecond.setImageResource(R.drawable.pin_un_setup);
        imgThird.setImageResource(R.drawable.pin_un_setup);
        imgFour.setImageResource(R.drawable.pin_un_setup);
    }

    @Override
    public void onSuccess(Object result) {
        dialog.dismiss();
        relPin.setVisibility(View.GONE);
        relResult.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnSubmit)
    public void clickStart(){
        Intent intent = new Intent(this, MainActivity_.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFail(Error error) {

    }
}
