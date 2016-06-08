package com.lykkex.LykkeWallet.gui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.pin.EnterPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.pin.SetUpPinActivity_;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity_;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
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

    @ViewById StepsIndicator stepsIndicator;

    private ArrayList<Call<DocumentAnswerData>> listCallDoc = new ArrayList<>();

    @AfterViews
    public void afterViews(){
        sendDocumentRel.setVisibility(View.GONE);
        if (getIntent() == null || getIntent().getExtras() ==null ||
                getIntent().getExtras().getSerializable(Constants.EXTRA_KYS_STATUS) == null
                || (KysStatusEnum)getIntent().getExtras().getSerializable(Constants.EXTRA_KYS_STATUS)==
                KysStatusEnum.Pending ||
                (KysStatusEnum)getIntent().getExtras().getSerializable(Constants.EXTRA_KYS_STATUS)==
                        KysStatusEnum.Rejected ) {
            progressBarsendDocument.setVisibility(View.VISIBLE);
            textViewsendDocument.setText(String.format(getString(R.string.dear_it_checked),
                    new UserPref_(this).fullName().get()));
            sendDocumentRel.setVisibility(View.VISIBLE);
            initHandler();
        } else {
            fireKysStatus(getIntent().getExtras().getSerializable(Constants.EXTRA_KYS_STATUS).
                    toString());
        }

        stepsIndicator.setCurrentStep(3);
    }

    @Click(R.id.btnUnderstandOops)
    public void clickUnderstandOops(){
        finish();
    }

    @Click(R.id.btnStart)
    public void clickGetStarted(){
        finish();
        Intent intent = new Intent();
        if (getIntent() == null || getIntent().getExtras() == null ||
                !getIntent().getExtras().getBoolean(Constants.EXTRA_PIN_STATUS)) {
            intent.setClass(this, SetUpPinActivity_.class);
        } else {
            intent.setClass(this, EnterPinActivity_.class);
        }
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
                getKycStatus();
        call.enqueue(callback);
        listCallDoc.add(call);


        if (isShouldContinue) {
            initHandler();
        }
    }

    private boolean isShouldContinue = true;

    public void stopHandler(){
        for (Call<DocumentAnswerData> call : listCallDoc) {
            call.cancel();
        }

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
        mHandler.postDelayed(run, Constants.DELAY_5000);
    }

    private void fireKysStatus(String kysStatusEnum){
        Log.e("STATUS COME", kysStatusEnum);
        switch (KysStatusEnum.valueOf(kysStatusEnum)){
            case Pending:
             //   sendDocumentForCheck();
               // progressBarsendDocument.setVisibility(View.VISIBLE);
                break;
            case NeedToFillData:
                oopsRel.setVisibility(View.GONE);
                sendDocumentRel.setVisibility(View.GONE);
                wasCreateRel.setVisibility(View.GONE);
                relUpdate.setVisibility(View.VISIBLE);
                tvUpdate.setText(String.format(getString(R.string.reupdate_doc),
                        userPref.fullName().get()));
                stopHandler();
                break;
            case Ok:
                oopsRel.setVisibility(View.GONE);
                wasCreateRel.setVisibility(View.VISIBLE);
                relUpdate.setVisibility(View.GONE);
                sendDocumentRel.setVisibility(View.GONE);
                textView3.setText(String.format(getString(R.string.succes_setup_document),
                        userPref.fullName().get()));
                stopHandler();
                break;
            case RestrictedArea:
                oopsRel.setVisibility(View.VISIBLE);
                wasCreateRel.setVisibility(View.GONE);
                relUpdate.setVisibility(View.GONE);
                sendDocumentRel.setVisibility(View.GONE);
                tvInfoOops.setText(String.format(getString(R.string.info_oops),
                        userPref.fullName().get()));
                stopHandler();
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

            fireKysStatus(((DocumentAnswerResult)result).getKysStatus().toString());

        }
    }

    @Override
    public void onFail(Object error) {
        if (error instanceof Error && ((Error)error).getCode() == Constants.ERROR_401) {
            stopHandler();
        }
    }
}
