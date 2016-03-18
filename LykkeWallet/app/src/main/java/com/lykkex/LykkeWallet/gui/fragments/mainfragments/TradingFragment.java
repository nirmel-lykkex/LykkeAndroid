package com.lykkex.LykkeWallet.gui.fragments.mainfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.TradingActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.TradingEnum;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.models.AssetPairSinglenton;
import com.lykkex.LykkeWallet.gui.models.SettingSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.gui.widgets.DrawLine;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairCallBack;
import com.lykkex.LykkeWallet.rest.trading.callback.AssetPairRatesCallBack;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPair;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairData;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairsResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateData;
import com.lykkex.LykkeWallet.rest.trading.response.model.Rates;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;

import retrofit2.Call;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.trading_fragment)
public class TradingFragment extends Fragment implements CallBackListener {

    @Pref  UserPref_ pref;
    @ViewById ProgressBar progressBar;
    @ViewById LinearLayout linearEntity;


    private Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            getRates();
            startHandler();
        }
    };

    @AfterViews
    public void afterViews(){
        tryToSetUpView();
        if (AssetPairSinglenton.getInstance().getResult() == null ||
                AssetPairSinglenton.getInstance().getResult().getAssetPairs() != null ||
                AssetPairSinglenton.getInstance().getResult().getAssetPairs().length == 0){
            getAssetPairs();
        }
    }

    @Click(R.id.lykke_section)
    public void clickLykke(){
        if (AssetPairSinglenton.getInstance().isCollapsed()) {
            linearEntity.removeAllViews();
            AssetPairSinglenton.getInstance().setIsCollapsed(false);
        } else {
            AssetPairSinglenton.getInstance().setIsCollapsed(true);
            tryToSetUpView();
        }
    }

    public void onStop(){
        super.onStop();
        stopHandler();
    }

    public void onStart(){
        super.onStart();
        handler.post(run);
    }

    private void tryToSetUpView(){
        AssetPairsResult assetPairs = AssetPairSinglenton.getInstance().getResult();
        if (assetPairs != null &&
                assetPairs.getAssetPairs() != null && assetPairs.getAssetPairs().length > 0) {
            setUpView(assetPairs);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void getAssetPairs(){
        AssetPairCallBack callBack = new AssetPairCallBack(this, getActivity());
        Call<AssetPairData> call = LykkeApplication_.getInstance().getRestApi().getAssetPairs
                (Constants.PART_AUTHORIZATION + pref.authToken().get());
        call.enqueue(callBack);
    }

    private void setUpView(AssetPairsResult result){
        linearEntity.removeAllViews();
        progressBar.setVisibility(View.GONE);
        if (AssetPairSinglenton.getInstance().isCollapsed()) {
            for (AssetPair pair : result.getAssetPairs()) {
                LayoutInflater lInflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = lInflater.inflate(R.layout.trading_item, null, false);

                LinearLayout rootLinear = (LinearLayout) view.findViewById(R.id.rootLinear);
                RelativeLayout relInfo = (RelativeLayout) view.findViewById(R.id.relInfo);
                RelativeLayout relPrice = (RelativeLayout) view.findViewById(R.id.relPrice);
                TextView tvAssetName = (TextView)view.findViewById(R.id.tvAssetName);
                DrawLine graphic = (DrawLine) view.findViewById(R.id.graphic);
                TextView tvPrice = (TextView)view.findViewById(R.id.tvPrice);

                Rates rate = foundViaName(pair.getId());
                if (AssetPairSinglenton.getInstance().getRates() != null &&
                        AssetPairSinglenton.getInstance().getRates().getRates() != null &&
                        AssetPairSinglenton.getInstance().getRates().getRates().length != 0
                        && rate != null) {
                    tvPrice.setBackgroundResource(R.drawable.active_price);
                    tvPrice.setText(String.valueOf(BigDecimal.valueOf
                            (rate.getBid()).setScale(pair.getAccurancy(), RoundingMode.HALF_EVEN)));
                    graphic.setUpRates(rate, getResources().getColor(R.color.light_blue));
                } else {
                    tvPrice.setBackgroundResource(R.drawable.price_not_come);
                }
                tvAssetName.setText(pair.getName());

                setUpClickItem(pair, view);
                setUpClickItem(pair, rootLinear);
                setUpClickItem(pair, relInfo);
                setUpClickItem(pair, relPrice);
                setUpClickItem(pair, tvAssetName);
                setUpClickItem(pair, graphic);
                setUpClickItem(pair, tvPrice);
                linearEntity.addView(view);
            }
        }
    }

    private void setUpClickItem(AssetPair pair, View tvPrice) {
        tvPrice.setTag(pair.getId());
        tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem((String) view.getTag());
            }
        });
    }

    private void stopHandler(){
        handler.removeCallbacks(run);
    }

    private void clickItem(String id){
        Intent intent = new Intent();
        intent.setClass(getActivity(), TradingActivity_.class);
        intent.putExtra(Constants.EXTRA_FRAGMENT, TradingEnum.description);
        intent.putExtra(Constants.EXTRA_ASSETPAIR_ID, id);
        startActivity(intent);
    }

    private Rates foundViaName(String name){
        if (AssetPairSinglenton.getInstance().getRates() != null &&
                AssetPairSinglenton.getInstance().getRates().getRates() != null) {
            for (Rates rate : AssetPairSinglenton.getInstance().getRates().getRates()) {
                if (rate.getId().equals(name)) {
                    return rate;
                }
            }
        }
        return null;
    }

    private void getRates(){
        AssetPairRatesCallBack callBack = new AssetPairRatesCallBack(this, getActivity());
        Call<RateData> call = LykkeApplication_.getInstance().getRestApi().getAssetPairsRates
                (Constants.PART_AUTHORIZATION + pref.authToken().get());
        call.enqueue(callBack);
    }

    private void startHandler(){
        handler.postDelayed(run, SettingSinglenton.getInstance().getRefreshTimer());
    }

    @Override
    public void onSuccess(Object result) {
        if (result instanceof AssetPairsResult) {
            AssetPairSinglenton.getInstance().setResult((AssetPairsResult) result);
            setUpView((AssetPairsResult) result);
        } else if (result instanceof RatesResult){
            AssetPairSinglenton.getInstance().setRates((RatesResult) result);
            if (AssetPairSinglenton.getInstance().getResult() != null){
                setUpView(AssetPairSinglenton.getInstance().getResult());
            }
        }
    }

    @Override
    public void onFail(Object error) {

    }
}
