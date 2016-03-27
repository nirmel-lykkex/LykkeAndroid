package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.ContentFrameLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Calculate;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.widgets.ConfirmDialog;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairRateCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateData;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;


/**
 * Created by e.kazimirova on 21.03.2016.
 */
@EFragment(R.layout.buy_asset)
public class BuyAsset  extends BaseFragment  implements View.OnFocusChangeListener, TextWatcher{

    @ViewById EditText etVolume;
    @ViewById TextView labelTotalCost;
    @ViewById View calc_keyboard;
    @ViewById TextView labelPrice;
    @ViewById Button button;
    @ViewById
    LinearLayout linearRoot;

    private Double rate = 0.0;
    private int volume = 0;

    private ArrayList<Call<RatesData>> listRates = new ArrayList<>();
    private boolean isShouldContinue = true;

    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            getRates();
            startHandler();
        }
    };

    @Pref UserPref_ userPref;
    private String id;
    private int accurancy;

    @AfterViews
    public void afterViews(){
        labelPrice.setText("0.0");
        labelTotalCost.setText("0.0");
        calc_keyboard.setVisibility(View.GONE);
        etVolume.setOnFocusChangeListener(this);
        etVolume.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                InputMethodManager imm = (InputMethodManager)
                        v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });

        etVolume.requestFocus();
        button.setEnabled(false);
        etVolume.addTextChangedListener(this);
        actionBar.setTitle(getString(R.string.buy) + " " +
                getArguments().getString(Constants.EXTRA_ASSETPAIR_NAME));
        accurancy = getArguments().getInt(Constants.EXTRA_ASSETPAIR_ACCURANCY);
        id = getArguments().getString(Constants.EXTRA_ASSETPAIR_ID);
        handler.post(run);
        if(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 575, getResources().getDisplayMetrics()) >
                ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight()){
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) linearRoot.getLayoutParams();
            lp.height= 170;
            linearRoot.setLayoutParams(lp);
        }

    }

    private void getRates(){
        if (isShouldContinue) {
            AssetPairRateCallBack callBack = new AssetPairRateCallBack(this, getActivity());
            Call<RateData> call = LykkeApplication_.getInstance().getRestApi().getAssetPairsRate
                    (Constants.PART_AUTHORIZATION + userPref.authToken().get(), id);
            call.enqueue(callBack);
        }
    }


    public void onStop(){
        super.onStop();
        stopHandler();
    }

    private void stopHandler(){
        for (Call<RatesData> call : listRates) {
            call.cancel();
        }

        isShouldContinue = false;
        handler.removeCallbacks(run);
    }

    private void startHandler(){
        handler.postDelayed(run, SettingSinglenton.getInstance().getRefreshTimer());
    }

    @Override
    public void initOnBackPressed() {

    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof RateResult) {
            if (((RateResult) result).getRate() != null &&
                    ((RateResult) result).getRate().getBid() != null) {
                rate = Double.parseDouble(((RateResult) result).getRate().getBid());
                setUpTotalCost();
            }
        }
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }

    @Click(R.id.button)
    public void clickTryToConfirm(){
        ConfirmDialog dialog = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_RATE, labelPrice.getText().toString());
        args.putString(Constants.EXTRA_VOLUME, etVolume.getText().toString());
        args.putString(Constants.EXTRA_TOTAL_COST, labelTotalCost.getText().toString());
        args.putString(Constants.EXTRA_ASSETPAIR_NAME, getArguments().getString(Constants.EXTRA_ASSETPAIR_NAME));
        args.putInt(Constants.EXTRA_ASSETPAIR_ACCURANCY, accurancy);
        args.putString(Constants.EXTRA_ASSETPAIR_ID, id);

        dialog.setArguments(args);
        dialog.show(getActivity().getFragmentManager(),
                "dlg1" +new Random((int)Constants.DELAY_5000));
    }

    @Click(R.id.rel100)
    public void click100(){
        etVolume.setText("100");
    }

    @Click(R.id.rel1000)
    public void click1000(){
        etVolume.setText("1000");
    }

    @Click(R.id.rel10000)
    public void click10000(){
        etVolume.setText("10000");
    }

    @Click(R.id.rel1)
    public void click1(){
        setUpText(etVolume.getText().toString() + "1");
    }

    @Click(R.id.rel2)
    public void click2(){
        setUpText(etVolume.getText().toString() + "2");
    }

    @Click(R.id.rel3)
    public void click3(){
        setUpText(etVolume.getText().toString() + "3");
    }

    @Click(R.id.rel4)
    public void click4(){
        setUpText(etVolume.getText().toString() + "4");
    }

    @Click(R.id.rel5)
    public void click5(){
        setUpText(etVolume.getText().toString() + "5");
    }

    @Click(R.id.rel6)
    public void click6(){
        setUpText(etVolume.getText().toString() + "6");
    }

    @Click(R.id.rel7)
    public void click7(){
        setUpText(etVolume.getText().toString() + "7");
    }

    @Click(R.id.rel8)
    public void click8(){
        setUpText(etVolume.getText().toString() + "8");
    }

    @Click(R.id.rel9)
    public void click9(){
        setUpText(etVolume.getText().toString() + "9");
    }

    @Click(R.id.relDot)
    public void clickDot(){
        setUpText(etVolume.getText().toString() + ".");
    }

    @Click(R.id.rel0)
    public void click0(){
        setUpText(etVolume.getText().toString() + "0");
    }

    @Click(R.id.relRemove)
    public void clickRemove(){
        if (etVolume.getText().toString().length() > 0) {
            setUpText(etVolume.getText().toString().substring(0, etVolume.getText().toString().length() - 1));
        }
    }

    @Click(R.id.relDivider)
    public void clickDivider(){
        setUpText(etVolume.getText().toString() + "/");
    }

    private void setUpText(String text) {
        etVolume.setText(text);
        etVolume.setSelection(etVolume.getText().toString().length());
    }

    @Click(R.id.relMultiply)
    public void clickMultiply(){
        setUpText(etVolume.getText().toString() + "*");
    }

    @Click(R.id.relMinus)
    public void clickMinus(){
        setUpText(etVolume.getText().toString() + "-");
    }

    @Click(R.id.relPlus)
    public void clickPlus(){
        setUpText(etVolume.getText().toString() + "+");
    }

    @Click(R.id.relEqual)
    public void clickEqual(){
        setUpText(String.valueOf(BigDecimal.valueOf(Calculate.eval(etVolume.getText().toString())).
                setScale(0, RoundingMode.HALF_EVEN).intValue()));
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if (b){
            calc_keyboard.setVisibility(View.VISIBLE);
        } else {
            calc_keyboard.setVisibility(View.GONE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        setUpTotalCost();
        if (etVolume.getText().toString().isEmpty()) {
            labelTotalCost.setText("0");
        }
        if (!etVolume.getText().toString().contains("/") &&
                !etVolume.getText().toString().contains("*") &&
                !etVolume.getText().toString().contains("-") &&
                !etVolume.getText().toString().contains("+") &&
                !labelTotalCost.getText().toString().isEmpty() &&
                !labelPrice.getText().toString().isEmpty()
                && !etVolume.getText().toString().isEmpty()
                && volume != 0 &&
                new BigDecimal(labelTotalCost.getText().toString()).compareTo(BigDecimal.ZERO) != 0 &&
                new BigDecimal(labelPrice.getText().toString()).compareTo(BigDecimal.ZERO) != 0) {
            button.setEnabled(true);
        } else {
            button.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    private void setUpTotalCost(){
        try {
            volume = Integer.parseInt(etVolume.getText().toString());
        } catch (NumberFormatException ex){}

        if (!etVolume.getText().toString().isEmpty() && volume != 0) {
            labelTotalCost.setText(String.valueOf(BigDecimal.valueOf(volume*rate).setScale(accurancy,
                    RoundingMode.HALF_EVEN)));
        }

        if ( BigDecimal.valueOf
                (rate).compareTo(BigDecimal.ZERO) != 0) {
            labelPrice.setText(String.valueOf(BigDecimal.valueOf
                    (rate).setScale(accurancy, RoundingMode.HALF_EVEN)));
        }
    }
}
