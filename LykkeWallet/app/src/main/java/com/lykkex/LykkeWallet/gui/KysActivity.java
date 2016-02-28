package com.lykkex.LykkeWallet.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.fragments.models.KysStatusEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.callback.CheckSecurityDocumentCallBack;
import com.lykkex.LykkeWallet.rest.camera.response.models.DocumentAnswerData;
import com.lykkex.LykkeWallet.rest.camera.response.models.DocumentAnswerResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 15.02.2016.
 */
@EActivity(R.layout.kys_activity)
public class KysActivity extends Activity implements CallBackListener {

    @ViewById RelativeLayout oopsRel;
    @ViewById RelativeLayout wasCreateRel;
    @ViewById RelativeLayout relUpdate;

    @ViewById TextView tvUpdate;
    @ViewById TextView textView3;
    @ViewById TextView tvInfoOops;
    @ViewById TextView textViewsendDocument;

    @Pref UserPref_ userPref;

    @ViewById RelativeLayout sendDocumentRel;
    @ViewById ProgressBar progressBarsendDocument;
    private ArrayList<Call<DocumentAnswerData>> listCallDoc = new ArrayList<>();

    @AfterViews
    public void afterViews(){
        sendDocumentRel.setVisibility(View.GONE);
        if (getIntent() == null || getIntent().getExtras() ==null ||
                getIntent().getExtras().getString(Constants.EXTRA_KYS_STATUS) == null) {
            progressBarsendDocument.setVisibility(View.VISIBLE);
            sendDocumentForCheck();
        } else {
            fireKysStatus(getIntent().getExtras().getString(Constants.EXTRA_KYS_STATUS));
        }
    }

    @Click(R.id.btnUnderstandOops)
    public void clickUnderstandOops(){
        finish();
    }

    @Click(R.id.btnStart)
    public void clickGetStarted(){
        finish();
        Intent intent = new Intent();
        intent.setClass(this, SetUpPinActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.btnUpdate)
    public void clickUpdate(){
        finish();
        Intent intent = new Intent();
        intent.setClass(this, CameraActivity_.class);
        startActivity(intent);
    }

    private Handler mHandler = new Handler();

    public void sendDocumentForCheck(){
        textViewsendDocument.setText(String.format(getString(R.string.dear_it_checked),
                new UserPref_(this).fullName().get()));
        sendDocumentRel.setVisibility(View.VISIBLE);
        CheckSecurityDocumentCallBack callback = new CheckSecurityDocumentCallBack(this, this);
        Call<DocumentAnswerData> call  = LykkeApplication_.getInstance().getRestApi().
                getKycStatus(Constants.PART_AUTHORIZATION + userPref.authToken().get());
        call.enqueue(callback);
        listCallDoc.add(call);


        if (isShouldContinue) {
            initHandler();
        }
    }

    private boolean isShouldContinue = true;

    public void stopHandler(){
        isShouldContinue = false;
        mHandler.removeCallbacks(run);
    }

    private Runnable run = new Runnable() {
        @Override
        public void run() {
            sendDocumentForCheck();
        }
    };

    private void initHandler(){
        mHandler.postDelayed(run, Constants.DELAY_15000);
    }

    private void fireKysStatus(String kysStatusEnum){
        Log.e("STATUS COME", kysStatusEnum);
        switch (KysStatusEnum.valueOf(kysStatusEnum)){
            case Pending:
                sendDocumentForCheck();
               // progressBarsendDocument.setVisibility(View.VISIBLE);
                break;
            case NeedToFillData:
                oopsRel.setVisibility(View.GONE);
                sendDocumentRel.setVisibility(View.GONE);
                wasCreateRel.setVisibility(View.GONE);
                relUpdate.setVisibility(View.VISIBLE);
                tvUpdate.setText(String.format(getString(R.string.reupdate_doc),
                        userPref.fullName().get()));
                break;
            case Ok:
                oopsRel.setVisibility(View.GONE);
                wasCreateRel.setVisibility(View.VISIBLE);
                relUpdate.setVisibility(View.GONE);
                sendDocumentRel.setVisibility(View.GONE);
                textView3.setText(String.format(getString(R.string.succes_setup_document),
                        userPref.fullName().get()));
                break;
            case RestrictedArea:
                oopsRel.setVisibility(View.VISIBLE);
                wasCreateRel.setVisibility(View.GONE);
                relUpdate.setVisibility(View.GONE);
                sendDocumentRel.setVisibility(View.GONE);
                tvInfoOops.setText(String.format(getString(R.string.info_oops),
                        userPref.fullName().get()));
                break;
            case Rejected:
                sendDocumentForCheck();
               // progressBarsendDocument.setVisibility(View.VISIBLE);
                break;
        }

    }
    @Override
    public void onSuccess(Object result) {
        if (listCallDoc.size() >3) {
            for (Call<DocumentAnswerData> call : listCallDoc) {
                call.cancel();
            }
        }
        if (result != null && result instanceof DocumentAnswerResult
                && ((DocumentAnswerResult)result).getKysStatus() != null) {
            //progressBarsendDocument.setVisibility(View.GONE);
            for (Call<DocumentAnswerData> call : listCallDoc) {
                call.cancel();
            }
            stopHandler();
            fireKysStatus(((DocumentAnswerResult)result).getKysStatus().toString());

        }
    }

    @Override
    public void onFail(Error error) {

    }
}
