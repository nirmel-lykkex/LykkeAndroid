package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.storage.SetUpPref_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.pin.callback.CallBackPin;
import com.lykkex.LykkeWallet.rest.pin.request.model.PinRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import retrofit2.Call;

/**
 * Created by LIZA on 18.02.2016.
 */
@EActivity(R.layout.pin_activity)
public class PinActivity extends Activity implements CallBackListener{

    private String pin = "";
    private boolean isSetupFirstTime = false;
    private int countRetry = 0;
    @ViewById ImageView imgFirst;
    @ViewById ImageView imgSecond;
    @ViewById ImageView imgThird;
    @ViewById ImageView imgFour;
    @ViewById
    TextView textView4;
    @ViewById RelativeLayout relPin;
    @ViewById RelativeLayout relResult;
    @Pref UserPref_ userPref;
    @Pref
    SetUpPref_ setUpPref;

    ProgressDialog dialog;

    @AfterViews
    public void afterViews(){
        textView4.setText("");
        setUpPref.isInteredPin().put(true);
        userPref.pin().put("");
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage(getString(R.string.waiting));
    }

    @Click(R.id.btnOne)
    public void clickBtnOne(){
        pin +="1";
        setUpVisibility();
    }

    @Click(R.id.btnTwo)
    public void clickBtnTwo(){
        pin +="2";
        setUpVisibility();
    }

    @Click(R.id.btnThree)
    public void clickBtnThree(){
        pin +="3";
        setUpVisibility();
    }

    @Click(R.id.btnFour)
    public void clickBtnFour(){
        pin +="4";
        setUpVisibility();
    }

    @Click(R.id.btnFive)
    public void clickBtnFive(){
        pin +="5";
        setUpVisibility();
    }

    @Click(R.id.btnSix)
    public void clickBtnSix(){
        pin +="6";
        setUpVisibility();
    }

    @Click(R.id.btnSeven)
    public void clickBtnSeven(){
        pin +="7";
        setUpVisibility();
    }

    @Click(R.id.btnEight)
    public void clickBtnEight(){
        pin +="8";
        setUpVisibility();
    }

    @Click(R.id.btnNine)
    public void clickBtnNine(){
        pin +="9";
        setUpVisibility();
    }

    @Click(R.id.btnZero)
    public void clickBtnZero(){
        pin +="0";
        setUpVisibility();
    }

    @Click(R.id.btnRemove)
    public void clickBtnRemove(){
        pin = pin.substring(0, pin.length()-1);
        setUpVisibility();
    }

    private void setUpVisibility(){
        switch (pin.length()) {
            case 1:
                imgFirst.setImageResource(R.drawable.pin_setup);
                imgSecond.setImageResource(R.drawable.pin_un_setup);
                imgThird.setImageResource(R.drawable.pin_un_setup);
                imgFour.setImageResource(R.drawable.pin_un_setup);
                break;
            case 2:
                imgFirst.setImageResource(R.drawable.pin_setup);
                imgSecond.setImageResource(R.drawable.pin_setup);
                imgThird.setImageResource(R.drawable.pin_un_setup);
                imgFour.setImageResource(R.drawable.pin_un_setup);
                break;
            case 3:
                imgFirst.setImageResource(R.drawable.pin_setup);
                imgSecond.setImageResource(R.drawable.pin_setup);
                imgThird.setImageResource(R.drawable.pin_setup);
                imgFour.setImageResource(R.drawable.pin_un_setup);
                break;
            case 4:
                imgFirst.setImageResource(R.drawable.pin_setup);
                imgSecond.setImageResource(R.drawable.pin_setup);
                imgThird.setImageResource(R.drawable.pin_setup);
                imgFour.setImageResource(R.drawable.pin_setup);
                if (userPref.pin().get().isEmpty()){
                    userPref.pin().put(pin);
                    resetUp();
                    textView4.setText(R.string.re_enter_pin);
                } else if (userPref.pin().get().equals(pin)){
                    dialog.show();
                    CallBackPin callback = new CallBackPin(this, this);
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

    @Override
    public void onFail(Error error) {

    }
}
