package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.widget.ImageView;

import com.lykkex.LykkeWallet.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LIZA on 18.02.2016.
 */
@EActivity(R.layout.pin_activity)
public class PinActivity extends Activity{

    private String pin = "";
    @ViewById ImageView imgFirst;
    @ViewById ImageView imgSecond;
    @ViewById ImageView imgThird;
    @ViewById ImageView imgFour;

    @AfterViews
    public void afterViews(){

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
        pin = pin.substring(0, pin.length()-2);
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
                imgFour.setImageResource(R.drawable.pin_un_setup);
                break;
        }
    }
}
