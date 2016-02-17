package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by e.kazimirova on 15.02.2016.
 */
@EActivity(R.layout.kys_activity)
public class KysActivity extends Activity{

    @ViewById RelativeLayout oopsRel;
    @ViewById RelativeLayout wasCreateRel;

    @AfterViews
    public void afterViews(){
        if (getIntent() != null && getIntent().getExtras() != null) {
            KysStatusEnum kysStatus = (KysStatusEnum)
                    getIntent().getExtras().get(Constants.EXTRA_KYS_STATUS);
            switch (kysStatus){
                case NeedToFillData:
                    oopsRel.setVisibility(View.GONE);
                    wasCreateRel.setVisibility(View.GONE);
                    break;
                case Ok:
                    oopsRel.setVisibility(View.GONE);
                    wasCreateRel.setVisibility(View.VISIBLE);
                    break;
                case RestrictedArea:
                    oopsRel.setVisibility(View.VISIBLE);
                    wasCreateRel.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Click(R.id.btnUnderstandOops)
    public void clickUnderstandOops(){
        finish();
    }

    @Click(R.id.btnStart)
    public void clickGetStarted(){

    }

    @Click(R.id.btnUpdate)
    public void clickUpdate(){
        finish();
        Intent intent = new Intent();
        intent.setClass(this, SelfieActivity_.class);
        startActivity(intent);
    }
}
