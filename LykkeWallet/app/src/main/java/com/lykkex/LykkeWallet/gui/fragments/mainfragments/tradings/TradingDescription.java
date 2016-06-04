package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.OperationType;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairRateCallBack;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairRatesCallBack;
import com.lykkex.LykkeWallet.rest.trading.callback.DescriptionCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.DescriptionData;
import com.lykkex.LykkeWallet.rest.trading.response.model.DescriptionResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.Rate;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateData;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesData;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by e.kazimirova on 18.03.2016.
 */
@EFragment(R.layout.trading_description_fragment)
public class TradingDescription  extends BaseFragment implements CallBackListener {

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

    @ViewById Button btnSell;
    @ViewById RelativeLayout relProgress;
    @ViewById LinearLayout linearInfo;
    @ViewById ProgressBar progressBar;
    @ViewById TextView tvAssetClass;
    @ViewById TextView tvDescription;
    @ViewById TextView tvIssuerName;
    @ViewById TextView tvNumberOfCoins;
    @ViewById LinearLayout linearPop;
    @ViewById Button btnBuy;
    @Pref UserPref_ userPref;
    private String id;
    private int accurancy;
    private String price;
    private String bid;

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(getArguments().getString(Constants.EXTRA_ASSETPAIR_NAME));
    }

    public void onStart(){
        super.onStart();
        price = getArguments().getString(Constants.EXTRA_RATE_PRICE);
        accurancy = getArguments().getInt(Constants.EXTRA_ASSETPAIR_ACCURANCY);
        if (getArguments().getSerializable(Constants.EXTRA_DESCRIPTION) != null) {
            btnBuy.setText(getString(R.string.buy_rate) + " " + String.valueOf(BigDecimal.valueOf
                    (Double.parseDouble(price)).setScale(accurancy, RoundingMode.HALF_EVEN)));
            btnSell.setText(getString(R.string.sell_at_price) + " " + String.valueOf(BigDecimal.valueOf
                    (Double.parseDouble(price)).setScale(accurancy, RoundingMode.HALF_EVEN)));
            onSuccess(getArguments().getSerializable(Constants.EXTRA_DESCRIPTION));
        } else {
            id = getArguments().getString(Constants.EXTRA_ASSETPAIR_ID);
            setUpVisibility(View.VISIBLE, View.GONE);
            btnBuy.setVisibility(View.GONE);
            btnSell.setVisibility(View.GONE);
            getDescription();
        }
    }

    public void onStop(){
        super.onStop();
        stopHandler();
    }

    private void getDescription(){
        DescriptionCallBack callBack = new DescriptionCallBack(this, getActivity());
        Call<DescriptionData> call = LykkeApplication_.getInstance().getRestApi().
                getDescription(Constants.PART_AUTHORIZATION + userPref.authToken().get(),
                        id);
        call.enqueue(callBack);
    }

    private void getRates(){
        if (isShouldContinue) {
            AssetPairRateCallBack callBack = new AssetPairRateCallBack(this, getActivity());
            Call<RateData> call = LykkeApplication_.getInstance().getRestApi().getAssetPairsRate
                    (Constants.PART_AUTHORIZATION + userPref.authToken().get(), id);
            call.enqueue(callBack);
        }
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

    @Deprecated
    public void initOnBackPressed() {
        getActivity().finish();
    }

    private DescriptionResult resultData = null;
    @Override
    public void onSuccess(Object result) {
        if (getActivity() != null) {
            if (result instanceof DescriptionResult) {
                resultData = (DescriptionResult) result;
                setUpVisibility(View.GONE, View.VISIBLE);
                btnBuy.setVisibility(View.VISIBLE);
                btnSell.setVisibility(View.VISIBLE);
                tvAssetClass.setText(((DescriptionResult) result).getAssetClass());
                tvDescription.setText(((DescriptionResult) result).getDescription());
                tvIssuerName.setText(((DescriptionResult) result).getIssuerName());
                tvNumberOfCoins.setText(((DescriptionResult) result).getNumberOfCoins());


                Resources r = getResources();
                float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
                float pxMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, r.getDisplayMetrics());

                for (int i = 0; i < Integer.parseInt(((DescriptionResult) result).getPopIndex()); i++) {
                    ImageView imageView = new ImageView(getActivity());
                    imageView.setImageResource(R.drawable.star);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            Math.round(px),
                            Math.round(px));
                    lp.setMargins(Math.round(pxMargin), 0, Math.round(pxMargin), 0);
                    imageView.setLayoutParams(lp);

                    linearPop.addView(imageView);
                }
                handler.post(run);
            } else if (result instanceof RateResult) {
                if (((RateResult) result).getRate() != null && ((RateResult) result).getRate().getAsk() != null
                        && getActivity() != null) {
                    price = ((RateResult) result).getRate().getAsk();
                    btnBuy.setText(getString(R.string.buy_rate) + " " + String.valueOf(BigDecimal.valueOf
                            (Double.parseDouble(((RateResult) result).getRate().getAsk())).setScale(accurancy, RoundingMode.HALF_EVEN)));
                }
                if (((RateResult) result).getRate() != null && ((RateResult) result).getRate().getBid() != null
                        && getActivity() != null) {
                    bid = ((RateResult) result).getRate().getBid();
                    btnSell.setText(getString(R.string.sell_at_price) + " " + String.valueOf(BigDecimal.valueOf
                            (Double.parseDouble(((RateResult) result).getRate().getBid())).setScale(accurancy, RoundingMode.HALF_EVEN)));
                }
            }
        }
    }

    @Click(R.id.btnBuy)
    public void clickBtnBuy(){
        Bundle bundle = getArguments();
        bundle.putSerializable(Constants.EXTRA_DESCRIPTION, resultData);
        bundle.putSerializable(Constants.EXTRA_RATE_PRICE, price);
        bundle.putSerializable(Constants.EXTRA_TYPE_OPERATION, OperationType.buy);
        ((BaseActivity) getActivity()).initFragment(new BuyAsset_(), getArguments());
    }

    @Click(R.id.btnSell)
    public void clickBtnSell(){
        Bundle bundle = getArguments();
        bundle.putSerializable(Constants.EXTRA_DESCRIPTION, resultData);
        bundle.putSerializable(Constants.EXTRA_RATE_PRICE, bid);
        bundle.putSerializable(Constants.EXTRA_TYPE_OPERATION, OperationType.sell);
        ((BaseActivity) getActivity()).initFragment(new BuyAsset_(), getArguments());
    }

    private void setUpVisibility(int gone, int goneLinear) {
        relProgress.setVisibility(gone);
        progressBar.setVisibility(gone);
        linearInfo.setVisibility(goneLinear);
    }

    @Override
    public void onFail(Object error) {

    }
}
