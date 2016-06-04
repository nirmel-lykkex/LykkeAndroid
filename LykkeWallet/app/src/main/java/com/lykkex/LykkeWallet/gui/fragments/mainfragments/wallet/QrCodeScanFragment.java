package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import android.os.Bundle;
import android.util.Log;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.utils.Constants;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import javax.xml.transform.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by e.kazimirova on 19.04.2016.
 */
@EFragment(R.layout.qr_code_scan_fragment)
public class QrCodeScanFragment extends BaseFragment implements  ZXingScannerView.ResultHandler {

    @ViewById ZXingScannerView mScannerView;

    @AfterViews
    public void afterViews(){
        actionBar.hide();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(com.google.zxing.Result rawResult) {
        Bundle bundle = getArguments();
        bundle.putString(Constants.EXTRA_QR_CODE_READ, rawResult.getText());
        ((BaseActivity)getActivity()).initFragment(new WithdrawFragment_(), bundle);
    }

    @Deprecated
    public void initOnBackPressed() {
        ((BaseActivity)getActivity()).initFragment(new WithdrawFragment_(), getArguments());
    }
}
