package com.lykkex.LykkeWallet.gui.activity.pin;

import android.content.Intent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.MainActivity_;
import com.lykkex.LykkeWallet.gui.activity.authentication.SignInActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.SettingEnum;
import com.lykkex.LykkeWallet.gui.managers.SettingManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.LykkeUtils;
import com.lykkex.LykkeWallet.rest.internal.callback.SignSettingOrderCallBack;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingSignOrder;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingSignOrderData;
import com.lykkex.LykkeWallet.rest.pin.callback.CallBackPinSignIn;
import com.lykkex.LykkeWallet.rest.pin.response.model.SecurityData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;

/**
 * Created by LIZA on 18.02.2016.
 */
@EActivity(R.layout.pin_activity)
public class EnterPinActivity extends BasePinActivity{

    private int countFail = 0;

    @ViewById
    RelativeLayout pinIndicator;

    @AfterViews
    public void afterViews(){
        super.afterViews();

        tvEnterPin.setText(getString(R.string.enter_pin));
    }

    protected void setUpVisibility(){
        super.setUpVisibility();
        switch (pin.length()) {
            case 4:
                dialog.show();
                CallBackPinSignIn callback = new CallBackPinSignIn(this, this);
                Call<SecurityData> call  = LykkeApplication_.getInstance().getRestApi().
                        signInPinSecurite(pin);
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
            } else if (settingEnum == null){
                signOut();

                dialog.dismiss();

            }
        }
        if (settingEnum != null){
            if (result instanceof SettingSignOrder) {
                dialog.dismiss();

                SettingManager.getInstance().setShouldSignOrder
                        (!SettingManager.getInstance().isShouldSignOrder());
                Toast.makeText(this, R.string.sign_orders_was_changed, Toast.LENGTH_LONG).show();
                finish();
            } else {
                if (((SecurityData)result).getResult().isPassed()) {
                    SettingSignOrder order = new SettingSignOrder();
                    order.setSignOrderBeforeGo(!SettingManager.getInstance().isShouldSignOrder());
                    SignSettingOrderCallBack callBack = new SignSettingOrderCallBack(this, this);
                    Call<SettingSignOrderData> call = LykkeApplication_.getInstance().getRestApi().
                            postSettingSignOrder(order);
                    call.enqueue(callBack);
                } else if (countFail < 2) {
                    dialog.dismiss();
                    onFail(null);
                } else {
                    finish();

                    dialog.dismiss();
                }
            }
        }
    }

    private void signOut() {
        userPref.clear();
        Intent intent = new Intent();
        intent.setClass(getBaseContext(), SignInActivity_.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFail(Object error) {
        countFail +=1;

        tvEnterPin.setText(getString(R.string.num_attempts_left, 3 - countFail));

        dialog.dismiss();
        pin = "";
        imgFirst.setImageResource(R.drawable.pin_un_setup);
        imgSecond.setImageResource(R.drawable.pin_un_setup);
        imgThird.setImageResource(R.drawable.pin_un_setup);
        imgFour.setImageResource(R.drawable.pin_un_setup);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
        pinIndicator.startAnimation(animation);
    }
}
