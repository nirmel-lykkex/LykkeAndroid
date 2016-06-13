package com.lykkex.LykkeWallet.gui.fragments.mainfragments;

import android.app.Activity;
import android.graphics.Point;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.customviews.TraidingItem;
import com.lykkex.LykkeWallet.gui.customviews.TraidingItem_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.managers.AssetPairManager;
import com.lykkex.LykkeWallet.gui.managers.SettingManager;
import com.lykkex.LykkeWallet.gui.utils.validation.CallBackListener;
import com.lykkex.LykkeWallet.rest.internal.callback.BaseAssetCallback;
import com.lykkex.LykkeWallet.rest.internal.request.model.IdBaseAsset;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetData;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPair;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairData;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairsResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesData;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by LIZA on 29.02.2016.
 */
@EFragment(R.layout.trading_fragment)
public class TradingFragment extends BaseFragment {

    @Pref
    UserPref_ pref;

    @ViewById
    ProgressBar progressBar;

    @ViewById
    LinearLayout linearEntity;

    @ViewById
    LinearLayout assetsContainer;

    @App
    LykkeApplication lykkeApplication;

    @Bean
    AssetPairManager assetPairManager;

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

    @AfterViews
    public void afterViews(){
        tryToSetUpView();
        if (assetPairManager.getAssetPairsResult() == null ||
                assetPairManager.getAssetPairsResult().getAssetPairs() != null ||
                assetPairManager.getAssetPairsResult().getAssetPairs().length == 0){
            getAssetPairs();
        }
    }

    @Click(R.id.lykkeSection)
    public void clickLykke(){
        if (assetPairManager.isCollapsed()) {
            linearEntity.removeAllViews();
            assetPairManager.setCollapsed(false);
        } else {
            assetPairManager.setCollapsed(true);
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
        AssetPairsResult assetPairs = assetPairManager.getAssetPairsResult();
        if (assetPairs != null &&
                assetPairs.getAssetPairs() != null && assetPairs.getAssetPairs().length > 0) {
            setUpView(assetPairs);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void renderBaseAssets() {
        assetsContainer.removeAllViews();

        List<BaseAsset> baseAssets = SettingManager.getInstance().getBaseAssets();

        if (baseAssets != null) {
            for (final BaseAsset baseAsset : baseAssets) {
                TextView assetView = new TextView(getActivity());
                assetView.setText(baseAsset.getName());
                assetView.setTag(baseAsset.getId());

                Point size = new Point();

                getActivity().getWindowManager().getDefaultDisplay().getSize(size);
                assetView.setMinWidth(size.x / 5);

                assetView.setGravity(Gravity.CENTER_HORIZONTAL);

                if(baseAsset.getId().equals(SettingManager.getInstance().getBaseAssetId())) {
                    assetView.setTextColor(getResources().getColor(R.color.blue_color));
                }

                assetView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setBaseAsset(baseAsset);
                    }
                });

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = baseAssets.size();

                assetsContainer.addView(assetView, params);
            }
        }
    }

    private void setBaseAsset(final BaseAsset baseAsset){
        IdBaseAsset asset = new IdBaseAsset(baseAsset.getId());
        Call<BaseAssetData> call = LykkeApplication_.getInstance().getRestApi().
                postBaseAsset(asset);
        BaseAssetCallback baseAssetCallback = new BaseAssetCallback(new CallBackListener<BaseAssetResult>() {
            @Override
            public void onSuccess(BaseAssetResult result) {
                SettingManager.getInstance().setBaseAssetId(baseAsset.getId());

                renderBaseAssets();

                getAssetPairs();
                getRates();
            }

            @Override
            public void onFail(Object error) {
                Log.e(TradingFragment.class.getSimpleName(), "Error while setting base asset");
            }
        }, getActivity());

        call.enqueue(baseAssetCallback);
    }

    private void getAssetPairs(){
        Call<AssetPairData> call = lykkeApplication.getRestApi().getAssetPairs();

        call.enqueue(new Callback<AssetPairData>() {
            @Override
            public void onResponse(Call<AssetPairData> call, Response<AssetPairData> response) {
                if(response.isSuccess()) {
                    AssetPairsResult result = response.body().getResult();

                    assetPairManager.setAssetPairsResult(result);

                    setUpView(result);
                } else {
                    try {
                        Log.d(TradingFragment.class.getSimpleName(), "Error while loading asset pairs: " + response.errorBody().string());
                    } catch (IOException e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<AssetPairData> call, Throwable t) {
                Log.d(TradingFragment.class.getSimpleName(), "Error while loading asset pairs", t);
            }
        });
    }

    private void setUpView(AssetPairsResult result){
        if (getActivity() == null) return;

        renderBaseAssets();

        linearEntity.removeAllViews();
        progressBar.setVisibility(View.GONE);

        if (assetPairManager.isCollapsed()) {
            for (AssetPair pair : result.getAssetPairs()) {
                TraidingItem traidingItem = TraidingItem_.build(getActivity());
                traidingItem.setAssetPair(pair);

                linearEntity.addView(traidingItem);
            }
        }
    }

    private void stopHandler(){
        for (Call<RatesData> call : listRates) {
            call.cancel();
        }

        isShouldContinue = false;
        handler.removeCallbacks(run);
    }

    private void getRates(){
        if (isShouldContinue) {
            Call<RatesData> call = lykkeApplication.getRestApi().getAssetPairsRates();
            listRates.add(call);

            call.enqueue(new Callback<RatesData>() {
                @Override
                public void onResponse(Call<RatesData> call, Response<RatesData> response) {

                    if(response.isSuccess()) {
                        RatesResult result = response.body().getResult();

                        assetPairManager.setRatesResult(result);

                        if (assetPairManager.getAssetPairsResult() != null){
                            setUpView(assetPairManager.getAssetPairsResult());
                        }
                    } else {
                        try {
                            Log.d(TradingFragment.class.getSimpleName(), "Error while loading pair rates: " + response.errorBody().string());
                        } catch (IOException e) {
                        }
                    }
                }

                @Override
                public void onFailure(Call<RatesData> call, Throwable t) {
                    Log.d(TradingFragment.class.getSimpleName(), "Error while loading pair rates", t);
                }
            });
        }
    }

    private void startHandler(){
        handler.postDelayed(run, SettingManager.getInstance().getRefreshTimer());
    }
}
