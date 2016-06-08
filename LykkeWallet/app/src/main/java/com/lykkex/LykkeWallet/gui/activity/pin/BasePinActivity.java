package com.lykkex.LykkeWallet.gui.activity.pin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by LIZA on 18.02.2016.
 */
@EActivity(R.layout.pin_activity)
public class BasePinActivity extends Activity implements CallBackListener{

    protected String pin = "";
    protected @ViewById ImageView imgFirst;
    protected @ViewById ImageView imgSecond;
    protected @ViewById ImageView imgThird;
    protected @ViewById ImageView imgFour;
    protected @ViewById TextView tvEnterPin;
    protected @ViewById RelativeLayout relPin;
    protected @ViewById RelativeLayout relResult;
    protected @Pref UserPref_ userPref;

    protected ProgressDialog dialog;

    @AfterViews
    public void afterViews(){
        tvEnterPin.setText("");
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
        if (pin.length() > 0 ) {
            pin = pin.substring(0, pin.length() - 1);

        }
        setUpVisibility();
    }

    protected void setUpVisibility(){
        switch (pin.length()) {
            case 0:
                imgFirst.setImageResource(R.drawable.pin_un_setup);
                imgSecond.setImageResource(R.drawable.pin_un_setup);
                imgThird.setImageResource(R.drawable.pin_un_setup);
                imgFour.setImageResource(R.drawable.pin_un_setup);
                break;
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
                break;
        }
    }

    @Override
    public void onSuccess(Object result) {
    }

    @Override
    public void onFail(Object error) {

    }
}
