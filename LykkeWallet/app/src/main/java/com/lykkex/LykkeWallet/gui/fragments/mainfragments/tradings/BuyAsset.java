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
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Calculate;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.OperationType;
import com.lykkex.LykkeWallet.gui.widgets.ConfirmDialog;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairRateCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPair;
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
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;


/**
 * Created by e.kazimirova on 21.03.2016.
 */
@EFragment(R.layout.buy_asset)
public class BuyAsset  extends BaseFragment  implements View.OnFocusChangeListener, TextWatcher{

    @ViewById TextView tvBaseAssetId;
    @ViewById TextView tvInformation;
    @ViewById TextView tvQautingId;
    @ViewById EditText etVolume;
    @ViewById EditText labelTotalCost;
    @ViewById View calc_keyboard;
    @ViewById TextView labelPrice;
    @ViewById Button button;
    @ViewById
    LinearLayout linearRoot;
    private AssetPair assetPair;

    private Double rate = 0.0;

    private ArrayList<Call<RatesData>> listRates = new ArrayList<>();
    private boolean isShouldContinue = true;
    private OperationType type = OperationType.buy;

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
        labelTotalCost.setOnFocusChangeListener(this);
        labelTotalCost.setOnTouchListener(new View.OnTouchListener() {

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
        labelTotalCost.addTextChangedListener(new TextWatcherTotal());
        type = (OperationType) getArguments().getSerializable(Constants.EXTRA_TYPE_OPERATION);
        assetPair = (AssetPair) getArguments().getSerializable(Constants.EXTRA_ASSET_PAIR);
        setTitle();
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

    private void setTitle(){
        if (type.equals(OperationType.buy)) {
            actionBar.setTitle(getString(R.string.buy) + " " +
                    getTitleBase());
        } else {
            actionBar.setTitle(getString(R.string.sell) + " " +
                    getTitleBase());
        }
        tvBaseAssetId.setText(getTitleBase());
        tvQautingId.setText(getTitleQuoting());
    }

    private String getTitleBase(){
        String name = assetPair.getBaseAssetId();
        if (SettingSinglenton.getInstance().getBaseAssetId().equals(assetPair.getBaseAssetId())) {
            name =  assetPair.getQuotingAssetId();
        } else {
            name = assetPair.getBaseAssetId();
        }
        return name;
    }

    private String getTitleQuoting(){
        String name = assetPair.getBaseAssetId();
        if (!SettingSinglenton.getInstance().getBaseAssetId().equals(assetPair.getBaseAssetId())) {
            name =  assetPair.getQuotingAssetId();
        } else {
            name = assetPair.getBaseAssetId();
        }
        return name;
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
        ((BaseActivity)getActivity()).initFragment(new TradingDescription_(), getArguments());
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof RateResult) {
            if (((RateResult) result).getRate() != null &&
                    ((RateResult) result).getRate().getAsk() != null) {
                rate = Double.parseDouble(((RateResult) result).getRate().getAsk());
                if (labelTotalCost.getText().toString().isEmpty() && !labelTotalCost.isFocused()) {
                    setUpTotalCost();
                }

                if (etVolume.getText().toString().isEmpty() && !etVolume.isFocused()) {
                    setUpVolume();
                }
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
        args.putString(Constants.EXTRA_VOLUME, etVolumeRes.toString());
        args.putString(Constants.EXTRA_TOTAL_COST, labelTotalCost.getText().toString());
        args.putString(Constants.EXTRA_ASSETPAIR_NAME, getArguments().getString(Constants.EXTRA_ASSETPAIR_NAME));
        args.putInt(Constants.EXTRA_ASSETPAIR_ACCURANCY, accurancy);
        args.putString(Constants.EXTRA_ASSETPAIR_ID, id);

        dialog.setArguments(args);
        dialog.show(getActivity().getFragmentManager(),
                "dlg1" + new Random((int) Constants.DELAY_5000));
    }

    private void getPrice(DecimalFormat format) throws ParseException{
        if (type.equals(OperationType.buy)) {
            etVolumeRes = ((BigDecimal) format.parse(etVolume.getText().toString()));
        } else {
            etVolumeRes = ((BigDecimal) format.parse(etVolume.getText().toString())).
                    subtract(((BigDecimal) format.parse(etVolume.getText().toString())).
                            multiply(BigDecimal.valueOf(2)));
        }

        String baseAssetId = SettingSinglenton.getInstance().getBaseAssetId();
        if (!baseAssetId.equals(assetPair.getBaseAssetId())) {
            try {
                etVolumeRes = BigDecimal.valueOf(1).divide(etVolumeRes, accurancy, RoundingMode.HALF_UP);
            } catch (ArithmeticException ex){
                etVolumeRes = BigDecimal.ZERO;
            }
        }
    }

    private void getPriceTotal(DecimalFormat format) throws ParseException{
        if (type.equals(OperationType.buy)) {
            etTotalRes = ((BigDecimal) format.parse(labelTotalCost.getText().toString()));
        } else {
            etTotalRes = ((BigDecimal) format.parse(labelTotalCost.getText().toString())).
                    subtract(((BigDecimal) format.parse(labelTotalCost.getText().toString())).
                            multiply(BigDecimal.valueOf(2)));
        }
    }

    @Click(R.id.rel100)
    public void click100(){
        if (etVolume.isFocused()) {
            etVolume.setText("100");
            etVolume.setSelection(etVolume.getText().toString().length());
        } else if (labelTotalCost.isFocused()) {
            labelTotalCost.setText("100");
            labelTotalCost.setSelection(labelTotalCost.getText().toString().length());
        }
    }

    @Click(R.id.rel1000)
    public void click1000(){
        if (etVolume.isFocused()) {
            etVolume.setText("1000");
            etVolume.setSelection(etVolume.getText().toString().length());
        } else if(labelTotalCost.isFocused()) {
            labelTotalCost.setText("1000");
            labelTotalCost.setSelection(labelTotalCost.getText().toString().length());
        }
    }

    @Click(R.id.rel10000)
    public void click10000(){
        if (etVolume.isFocused()) {
            etVolume.setText("10000");
            etVolume.setSelection(etVolume.getText().toString().length());
        } else if(labelTotalCost.isFocused()) {
            labelTotalCost.setText("10000");
            labelTotalCost.setSelection(labelTotalCost.getText().toString().length());
        }
    }

    @Click(R.id.rel1)
    public void click1(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "1");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "1");
        }
    }

    @Click(R.id.rel2)
    public void click2(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "2");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "2");
        }
    }

    @Click(R.id.rel3)
    public void click3(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "3");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "3");
        }
    }

    @Click(R.id.rel4)
    public void click4(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "4");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "4");
        }
    }

    @Click(R.id.rel5)
    public void click5(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "5");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "5");
        }
    }

    @Click(R.id.rel6)
    public void click6(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "6");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "6");
        }
    }

    @Click(R.id.rel7)
    public void click7(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "7");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "7");
        }
    }

    @Click(R.id.rel8)
    public void click8(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "8");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "8");
        }
    }

    @Click(R.id.rel9)
    public void click9(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "9");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "9");
        }
    }

    @Click(R.id.relDot)
    public void clickDot(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + ".");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + ".");
        }
    }

    @Click(R.id.rel0)
    public void click0(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "0");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "0");
        }
    }

    @Click(R.id.relRemove)
    public void clickRemove(){
        if (etVolume.isFocused()) {
            if (etVolume.getText().toString().length() > 0) {
                setUpTextVolume(etVolume.getText().toString().substring(0, etVolume.getText().toString().length() - 1));
            }
        }else if(labelTotalCost.isFocused()) {
            if (labelTotalCost.getText().toString().length() > 0) {
                setUpTextTotal(labelTotalCost.getText().toString().substring(0, labelTotalCost.getText().toString().length() - 1));
            }
        }

    }

    @Click(R.id.relDivider)
    public void clickDivider(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "/");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "/");
        }
    }

    private void setUpTextVolume(String text) {
        etVolume.setText(text);
        etVolume.setSelection(etVolume.getText().toString().length());
    }

    private void setUpTextTotal(String text) {
        labelTotalCost.setText(text);
        labelTotalCost.setSelection(labelTotalCost.getText().toString().length());
    }

    @Click(R.id.relMultiply)
    public void clickMultiply(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "*");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "*");
        }
    }

    @Click(R.id.relMinus)
    public void clickMinus(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "-");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "-");
        }
    }

    @Click(R.id.relPlus)
    public void clickPlus(){
        if (etVolume.isFocused()) {
            setUpTextVolume(etVolume.getText().toString() + "+");
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(labelTotalCost.getText().toString() + "+");
        }
    }

    @Click(R.id.relEqual)
    public void clickEqual(){
        if (etVolume.isFocused()) {
            setUpTextVolume(String.valueOf((Calculate.eval(
                    etVolume.getText().toString(), accurancy))));
            setUpTotalCost();
        }else if(labelTotalCost.isFocused()) {
            setUpTextTotal(String.valueOf((Calculate.eval(
                    labelTotalCost.getText().toString(), accurancy))));
            setUpVolume();
        }
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

    private  BigDecimal etVolumeRes = BigDecimal.ZERO;
    private  BigDecimal etTotalRes = BigDecimal.ZERO;

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (etVolume.isFocused() || (!etVolume.isFocused() && !labelTotalCost.isFocused())) {
            try {
                if (etVolume.getText().toString().isEmpty()) {
                    labelTotalCost.setText("0");
                }
                DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
                format.setParseBigDecimal(true);
                DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
                symbols.setDecimalSeparator('.');
                format.setDecimalFormatSymbols(symbols);

                getPrice(format);
                setUpTotalCost();

                int charCount = labelTotalCost.getText().toString().replaceAll("[^.]", "").length();
                int charCountVolume = etVolume.getText().toString().replaceAll("[^.]", "").length();
                if ((charCount <= 1
                        && !labelTotalCost.getText().toString().startsWith(".")
                        && !labelTotalCost.getText().toString().contains("/") &&
                        !labelTotalCost.getText().toString().contains("*") &&
                        !labelTotalCost.getText().toString().contains("+") &&
                        !labelPrice.getText().toString().isEmpty()
                        && !labelTotalCost.getText().toString().isEmpty()
                        &&
                        !labelTotalCost.getText().toString().isEmpty() &&
                        new BigDecimal(labelTotalCost.getText().toString()).compareTo(BigDecimal.ZERO) != 0 &&
                        new BigDecimal(labelPrice.getText().toString()).compareTo(BigDecimal.ZERO) != 0)

                        && (charCountVolume <= 1
                        && !etVolume.getText().toString().startsWith(".")
                        && !etVolume.getText().toString().contains("/") &&
                        !etVolume.getText().toString().contains("*") &&
                        !etVolume.getText().toString().contains("+") &&
                        !labelPrice.getText().toString().isEmpty()
                        && !etVolume.getText().toString().isEmpty()&&
                        new BigDecimal(labelPrice.getText().toString()).compareTo(BigDecimal.ZERO) != 0)){
                    if ((etTotalRes.compareTo(BigDecimal.ZERO) < 0 && type.equals(OperationType.buy))
                            || (etVolumeRes.compareTo(BigDecimal.ZERO) < 0 && type.equals(OperationType.buy))) {
                        button.setEnabled(false);
                    } else {
                        button.setEnabled(true);
                    }
                } else {
                    button.setEnabled(false);
                    tvInformation.setVisibility(View.GONE);
                }
            } catch (NumberFormatException ex) {
                button.setEnabled(false);
                tvInformation.setVisibility(View.GONE);
            } catch (ParseException e) {
                button.setEnabled(false);
                tvInformation.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    private void setUpTotalCost(){
        if (!etVolume.getText().toString().isEmpty() && etVolumeRes.compareTo(BigDecimal.ZERO) != 0) {
            labelTotalCost.setText(String.valueOf(etVolumeRes.multiply(BigDecimal.valueOf(rate).setScale(accurancy,
                    RoundingMode.HALF_EVEN))));
            etTotalRes =etVolumeRes.multiply(BigDecimal.valueOf(rate).setScale(accurancy,
                    RoundingMode.HALF_EVEN));
            tvInformation.setVisibility(View.VISIBLE);
            if (type.equals(OperationType.buy)) {
                tvInformation.setText(String.format(getString(R.string.info_sell), etVolume.getText().toString()
                                +" " +getTitleBase(),
                        labelTotalCost.getText().toString()+" " +getTitleQuoting()));
            } else {
                tvInformation.setText(String.format(getString(R.string.info_buy),  etVolume.getText().toString()
                                +" " +getTitleBase(),
                        labelTotalCost.getText().toString()+" " +getTitleQuoting()));
            }
        }

        if ( BigDecimal.valueOf
                (rate).compareTo(BigDecimal.ZERO) != 0) {
            labelPrice.setText(String.valueOf(BigDecimal.valueOf
                    (rate).setScale(accurancy, RoundingMode.HALF_EVEN)));
        }
    }

    private void setUpVolume(){
        if (!labelTotalCost.getText().toString().isEmpty() && etTotalRes.compareTo(BigDecimal.ZERO) != 0) {
            etVolume.setText(String.valueOf(etTotalRes.divide(BigDecimal.valueOf(rate),accurancy,
                    RoundingMode.HALF_EVEN)));
            etVolumeRes = etTotalRes.divide(BigDecimal.valueOf(rate),accurancy,
                    RoundingMode.HALF_EVEN);
            tvInformation.setVisibility(View.VISIBLE);
            if (type.equals(OperationType.buy)) {
                tvInformation.setText(String.format(getString(R.string.info_sell), etVolume.getText().toString()
                                +" " +getTitleBase(),
                        labelTotalCost.getText().toString()+" " +getTitleQuoting()));
            } else {
                tvInformation.setText(String.format(getString(R.string.info_buy),  etVolume.getText().toString()
                                +" " +getTitleBase(),
                        labelTotalCost.getText().toString()+" " +getTitleQuoting()));
            }
        }

        if ( BigDecimal.valueOf
                (rate).compareTo(BigDecimal.ZERO) != 0) {
            labelPrice.setText(String.valueOf(BigDecimal.valueOf
                    (rate).setScale(accurancy, RoundingMode.HALF_EVEN)));
        }
    }

    private class TextWatcherTotal implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (labelTotalCost.isFocused()) {
                try {
                    if (labelTotalCost.getText().toString().isEmpty()) {
                        etVolume.setText("0");
                    }
                    DecimalFormat format = (DecimalFormat) DecimalFormat.getInstance();
                    format.setParseBigDecimal(true);
                    DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
                    symbols.setDecimalSeparator('.');
                    format.setDecimalFormatSymbols(symbols);

                    getPriceTotal(format);
                    setUpVolume();

                    int charCount = labelTotalCost.getText().toString().replaceAll("[^.]", "").length();
                    int charCountVolume = etVolume.getText().toString().replaceAll("[^.]", "").length();
                    if ((charCount <= 1
                            && !labelTotalCost.getText().toString().startsWith(".")
                            && !labelTotalCost.getText().toString().contains("/") &&
                            !labelTotalCost.getText().toString().contains("*") &&
                            !labelTotalCost.getText().toString().contains("-") &&
                            !labelTotalCost.getText().toString().contains("+") &&
                            !labelPrice.getText().toString().isEmpty()
                            && !labelTotalCost.getText().toString().isEmpty()
                            && ((type.equals(OperationType.buy) &&
                            !labelTotalCost.getText().toString().isEmpty()
                            && etTotalRes.compareTo(BigDecimal.ZERO) > 0)
                            || (type.equals(OperationType.sell) &&
                            new BigDecimal(labelTotalCost.getText().toString()).compareTo(BigDecimal.ZERO) != 0 &&
                            etTotalRes.compareTo(BigDecimal.ZERO) < 0)) &&
                            new BigDecimal(labelPrice.getText().toString()).compareTo(BigDecimal.ZERO) != 0)

                            && (charCountVolume <= 1
                            && !etVolume.getText().toString().startsWith(".")
                            && !etVolume.getText().toString().contains("/") &&
                            !etVolume.getText().toString().contains("*") &&
                            !etVolume.getText().toString().contains("-") &&
                            !etVolume.getText().toString().contains("+") &&
                            !labelPrice.getText().toString().isEmpty()
                            && !etVolume.getText().toString().isEmpty()
                            && ((type.equals(OperationType.buy) &&
                            etVolumeRes.compareTo(BigDecimal.ZERO) > 0)
                            || (type.equals(OperationType.sell) &&
                            etVolumeRes.compareTo(BigDecimal.ZERO) < 0)) &&
                            new BigDecimal(labelPrice.getText().toString()).compareTo(BigDecimal.ZERO) != 0)){
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                        tvInformation.setVisibility(View.GONE);
                    }
                } catch (NumberFormatException ex) {
                    button.setEnabled(false);
                    tvInformation.setVisibility(View.GONE);
                } catch (ParseException e) {
                    button.setEnabled(false);
                    tvInformation.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
