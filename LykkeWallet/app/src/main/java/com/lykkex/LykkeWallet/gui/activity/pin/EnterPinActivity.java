package com.lykkex.LykkeWallet.gui.activity.pin;

import android.content.Intent;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.MainActivity_;
import com.lykkex.LykkeWallet.gui.activity.authentication.FieldActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.SettingEnum;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.internal.callback.SignSettingOrderCallBack;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingSignOrder;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingSignOrderData;
import com.lykkex.LykkeWallet.rest.pin.callback.CallBackPinSignIn;
import com.lykkex.LykkeWallet.rest.pin.response.model.SecurityData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import retrofit2.Call;

/**
 * Created by LIZA on 18.02.2016.
 */
@EActivity(R.layout.pin_activity)
public class EnterPinActivity extends BasePinActivity{

    private int countFail = 0;
    @AfterViews
    public void afterViews(){
        super.afterViews();

    }

    protected void setUpVisibility(){
        super.setUpVisibility();
        switch (pin.length()) {
            case 4:
                dialog.show();
                CallBackPinSignIn callback = new CallBackPinSignIn(this, this);
                Call<SecurityData> call  = LykkeApplication_.getInstance().getRestApi().
                        signInPinSecurite(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                                pin);
                call.enqueue(callback);
                break;

        }
    }



    @Override
    public void onSuccess(Object result) {
        SettingEnum settingEnum = null;
        if (getIntent().getExtras() != null &&
                getIntent().getExtras().getSerializable(Constants.EXTRA_FRAGMENT) != null) {
            settingEnum =
                    (SettingEnum) getIntent().getExtras().getSerializable(Constants.EXTRA_FRAGMENT);
        }

        if (result instanceof SecurityData) {
            if (((SecurityData)result).getResult().isPassed() && settingEnum == null) {
                    dialog.dismiss();

                    Intent intent = new Intent();
                    intent.setClass(this, MainActivity_.class);
                    startActivity(intent);
                    finish();
            } else if (countFail < 2 && settingEnum == null) {
                dialog.dismiss();
                onFail(null);
                countFail +=1;
            } else if (settingEnum == null){
                Toast.makeText(this, getString(R.string.not_authorized), Toast.LENGTH_LONG).show();
                userPref.clear();
                Intent intent = new Intent();
                intent.setClass(LykkeApplication_.getInstance(), FieldActivity_.class);
                startActivity(intent);
                finish();

                dialog.dismiss();

            }
        }
        if (settingEnum != null){
            if (result instanceof SettingSignOrder) {
                dialog.dismiss();

                SettingSinglenton.getInstance().setShouldSignOrder
                        (!SettingSinglenton.getInstance().isShouldSignOrder());
                Toast.makeText(this, R.string.sign_orders_was_changed, Toast.LENGTH_LONG).show();
                finish();
            } else {
                if (((SecurityData)result).getResult().isPassed()) {
                    SettingSignOrder order = new SettingSignOrder();
                    order.setSignOrderBeforeGo(!SettingSinglenton.getInstance().isShouldSignOrder());
                    SignSettingOrderCallBack callBack = new SignSettingOrderCallBack(this, this);
                    Call<SettingSignOrderData> call = LykkeApplication_.getInstance().getRestApi().
                            postSettingSignOrder(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                                    order);
                    call.enqueue(callBack);
                } else if (countFail < 2) {
                    dialog.dismiss();
                    onFail(null);
                    countFail +=1;
                } else {
                    Toast.makeText(this, getString(R.string.not_authorized), Toast.LENGTH_LONG).show();
                    userPref.clear();
                    Intent intent = new Intent();
                    intent.setClass(LykkeApplication_.getInstance(), FieldActivity_.class);
                    startActivity(intent);
                    finish();

                    dialog.dismiss();
                }
            }
        }
    }

    @Override
    public void onFail(Object error) {
        dialog.dismiss();
        pin = "";
        imgFirst.setImageResource(R.drawable.pin_un_setup);
        imgSecond.setImageResource(R.drawable.pin_un_setup);
        imgThird.setImageResource(R.drawable.pin_un_setup);
        imgFour.setImageResource(R.drawable.pin_un_setup);
        Toast.makeText(this, R.string.wrong_pin, Toast.LENGTH_LONG).show();
    }
}
