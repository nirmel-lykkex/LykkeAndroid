package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.SetUpPref_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by e.kazimirova on 15.02.2016.
 */
@EActivity(R.layout.kys_activity)
public class KysActivity extends Activity{

    @ViewById RelativeLayout oopsRel;
    @ViewById RelativeLayout wasCreateRel;
    @ViewById RelativeLayout relUpdate;

    @ViewById TextView tvUpdate;
    @ViewById TextView textView3;
    @ViewById TextView tvInfoOops;

    @Pref UserPref_ userPref;
    @Pref SetUpPref_ setUpPref;

    @AfterViews
    public void afterViews(){
        KysStatusEnum kysStatus = KysStatusEnum.Ok;
        if (getIntent() != null && getIntent().getExtras() != null) {
            kysStatus = (KysStatusEnum)
                    getIntent().getExtras().get(Constants.EXTRA_KYS_STATUS);
            setUpPref.kysStatusStart().put(kysStatus.toString());
        } else {
            kysStatus = KysStatusEnum.valueOf(setUpPref.kysStatusStart().get());
        }
            switch (kysStatus){
                case NeedToFillData:
                    oopsRel.setVisibility(View.GONE);
                    wasCreateRel.setVisibility(View.GONE);
                    relUpdate.setVisibility(View.VISIBLE);
                    tvUpdate.setText(String.format(getString(R.string.reupdate_doc),
                            userPref.fullName().get()));
                    break;
                case Ok:
                    oopsRel.setVisibility(View.GONE);
                    wasCreateRel.setVisibility(View.VISIBLE);
                    relUpdate.setVisibility(View.GONE);
                    textView3.setText(String.format(getString(R.string.succes_setup_document),
                            userPref.fullName().get()));
                    break;
                case RestrictedArea:
                    oopsRel.setVisibility(View.VISIBLE);
                    wasCreateRel.setVisibility(View.GONE);
                    relUpdate.setVisibility(View.GONE);
                    tvInfoOops.setText(String.format(getString(R.string.info_oops),
                            userPref.fullName().get()));
                    break;
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
