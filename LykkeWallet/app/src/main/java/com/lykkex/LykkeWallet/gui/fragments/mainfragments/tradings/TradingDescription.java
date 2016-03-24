package com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings;

import android.content.res.Resources;
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

import retrofit2.Call;

/**
 * Created by e.kazimirova on 18.03.2016.
 */
@EFragment(R.layout.trading_description_fragment)
public class TradingDescription  extends BaseFragment {

    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            getRates();
            startHandler();
        }
    };

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

    @AfterViews
    public void afterViews(){
        actionBar.setTitle(getArguments().getString(Constants.EXTRA_ASSETPAIR_NAME));
        accurancy = getArguments().getInt(Constants.EXTRA_ASSETPAIR_ACCURANCY);
        id = getArguments().getString(Constants.EXTRA_ASSETPAIR_ID);
        setUpVisibility(View.VISIBLE, View.GONE);
        getDescription();
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
        AssetPairRateCallBack callBack = new AssetPairRateCallBack(this, getActivity());
        Call<RateData> call = LykkeApplication_.getInstance().getRestApi().getAssetPairsRate
                (Constants.PART_AUTHORIZATION + userPref.authToken().get(), id);
        call.enqueue(callBack);
    }

    private void stopHandler(){
        handler.removeCallbacks(run);
    }

    private void startHandler(){
        handler.postDelayed(run, SettingSinglenton.getInstance().getRefreshTimer());
    }

    @Override
    public void initOnBackPressed() {
        getActivity().finish();
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof DescriptionResult) {
            setUpVisibility(View.GONE, View.VISIBLE);
            tvAssetClass.setText(((DescriptionResult) result).getAssetClass());
            tvDescription.setText(((DescriptionResult) result).getDescription());
            tvIssuerName.setText(((DescriptionResult) result).getIssuerName());
            tvNumberOfCoins.setText(((DescriptionResult) result).getNumberOfCoins());
            btnBuy.setText(getString(R.string.buy_rate) + " " + ((DescriptionResult) result).getMarketCapitalization());

            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, r.getDisplayMetrics());
            float pxMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, r.getDisplayMetrics());

            for (int i =0; i<Integer.parseInt(((DescriptionResult) result).getPopIndex()); i++){
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(R.drawable.star);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        Math.round(px),
                        Math.round(px));
                lp.setMargins( Math.round(pxMargin), 0,  Math.round(pxMargin), 0);
                imageView.setLayoutParams(lp);

                linearPop.addView(imageView);
            }
            handler.post(run);
        } else if (result instanceof RateResult) {
            if (((RateResult) result).getRate() != null && ((RateResult) result).getRate().getBid() != null) {
                btnBuy.setText(getString(R.string.buy_rate) + " " + String.valueOf(BigDecimal.valueOf
                        (Double.parseDouble(((RateResult) result).getRate().getBid())).setScale(accurancy, RoundingMode.HALF_EVEN)));
            }
        }
    }

    @Click(R.id.btnBuy)
    public void clickBtnBuy(){
        ((BaseActivity)getActivity()).initFragment(new BuyAsset_(), getArguments());
    }

    private void setUpVisibility(int gone, int goneLinear) {
        relProgress.setVisibility(gone);
        progressBar.setVisibility(gone);
        linearInfo.setVisibility(goneLinear);
    }

    @Override
    public void onFail(Object error) {

    }

    @Override
    public void onConsume(Object o) {

    }
}
