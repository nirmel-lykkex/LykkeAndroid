package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by LIZA on 18.04.2016.
 */
@EFragment(R.layout.withdraw_fragment)
public class WithdrawFragment extends BaseFragment implements CallBackListener {

    @ViewById EditText etHashBitcoin;
    @ViewById Button btnProceed;

    @AfterViews
    public void afterViews(){
        actionBar.show();
        actionBar.setTitle(R.string.withdraw_funds);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        etHashBitcoin.addTextChangedListener(new SimpleTextWatcher(null,
                null, etHashBitcoin,
                Constants.MIN_COUNT_SYMBOL, this,
                null));
        if (getArguments() != null && getArguments().getString(Constants.EXTRA_QR_CODE_READ) != null){
            etHashBitcoin.setText(getArguments().getString(Constants.EXTRA_QR_CODE_READ));
        }
    }

    @Deprecated
    public void initOnBackPressed() {
        ((BaseActivity)getActivity()).initFragment(new TradingWalletFragment_(), getArguments());
    }

    @Click(R.id.tvPaste)
    public void clickPaste(){
        ClipboardManager  clipMan = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = clipMan.getPrimaryClip().getItemAt(0);
        etHashBitcoin.setText(item.getText());
    }

    @Click(R.id.btnScan)
    public void clickBtnScan(){
        Bundle bundle = getArguments();
        bundle.putString(Constants.EXTRA_QR_CODE_READ, etHashBitcoin.getText().toString());
       ((BaseActivity) getActivity()).initFragment(new QrCodeScanFragment_(), bundle);
    }

    @Click(R.id.btnProceed)
    public void clickBtnProceed(){
        Bundle bundle = getArguments();
        bundle.putString(Constants.EXTRA_QR_CODE_READ, etHashBitcoin.getText().toString());
        ((BaseActivity)getActivity()).initFragment(new WithdrawFundsFragment_(), bundle);
    }

    @Override
    public void onSuccess(Object result) {
        if (result == null) {
            btnProceed.setTextColor(Color.WHITE);
            btnProceed.setEnabled(true);
        }
    }

    @Override
    public void onFail(Object error) {
        if (error == null) {
            btnProceed.setTextColor(getResources().getColor(R.color.grey_text));
            btnProceed.setEnabled(false);
        }
    }
}
