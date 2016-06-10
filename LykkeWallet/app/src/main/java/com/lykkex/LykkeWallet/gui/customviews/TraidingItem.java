package com.lykkex.LykkeWallet.gui.customviews;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.TradingActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.TradingEnum;
import com.lykkex.LykkeWallet.gui.managers.AssetPairManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.widgets.DrawLine;
import com.lykkex.LykkeWallet.rest.RestApi;
import com.lykkex.LykkeWallet.rest.base.models.BaseModel;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.Trading;
import com.lykkex.LykkeWallet.rest.trading.request.model.InvertAssetPairRequest;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPair;
import com.lykkex.LykkeWallet.rest.trading.response.model.Rate;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murtic on 08/06/16.
 */
@EViewGroup(R.layout.trading_item)
public class TraidingItem extends RelativeLayout {

    @ViewById
    TextView quotingAssetId;

    @ViewById
    TextView baseAssetId;

    @ViewById
    DrawLine graphic;

    @ViewById
    TextView tvPrice;

    @ViewById
    ImageView exchangeSwitchAssets;

    @App
    LykkeApplication lykkeApplication;

    @Bean
    AssetPairManager assetPairManager;

    private AssetPair assetPair;

    @Click({R.id.itemContainer})
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(getContext(), TradingActivity_.class);
        intent.putExtra(Constants.EXTRA_ASSET_PAIR, assetPair);
        intent.putExtra(Constants.EXTRA_FRAGMENT, TradingEnum.description);
        intent.putExtra(Constants.EXTRA_ASSETPAIR_NAME, assetPair.getName());
        intent.putExtra(Constants.EXTRA_ASSETPAIR_ID, assetPair.getId());
        intent.putExtra(Constants.EXTRA_ASSETPAIR_ACCURANCY, assetPair.getAccuracy());

        getContext().startActivity(intent);
    }

    @Click({R.id.graphic})
    public void onGraphicClick() {

    }

    @Click(R.id.exchangeSwitchAssets)
    public void onSwitch() {
        InvertAssetPairRequest request = new InvertAssetPairRequest(assetPair.getId(), !assetPair.getInverted());

        Call<Void> call = lykkeApplication.getRestApi().invertAssetPairs(request);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccess()) {
                    assetPair.setInverted(!assetPair.getInverted());

                    Rate rate = foundViaName(assetPair.getId());

                    if(rate != null) {
                        rate.setInverted(!rate.getInverted());
                    }

                    refresh();
                } else {
                    try {
                        Log.e(TraidingItem.class.getSimpleName(), "Error while inverting asset" + assetPair.getId() + response.errorBody().string());
                    } catch (IOException e) {
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TraidingItem.class.getSimpleName(), "Error while inverting asset: " + assetPair.getId(), t);
            }
        });
    }

    @AfterViews
    public void afterViews() {
        refresh();
    }

    private void refresh() {
        if(assetPair == null) return;

        Rate rate = foundViaName(assetPair.getId());

        if(rate != null) {
            assetPair.setInverted(rate.getInverted());

            Double ask = rate.getAsk();

            if(rate.getInverted()) {
                ask = 1 / rate.getAsk();
            }

            tvPrice.setText(BigDecimal.valueOf(ask)
                    .setScale(assetPair.getInverted() ? assetPair.getInvertedAccuracy()
                            : assetPair.getAccuracy(), RoundingMode.HALF_EVEN)
                    .stripTrailingZeros().toPlainString());
            tvPrice.setBackgroundResource(R.drawable.active_price);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                graphic.setUpRates(rate, getResources().getColor(R.color.light_blue, null));
            } else {
                graphic.setUpRates(rate, getResources().getColor(R.color.light_blue));
            }
        } else {
            tvPrice.setBackgroundResource(R.drawable.price_not_come);
        }

        if(!assetPair.getInverted()) {
            baseAssetId.setText(assetPair.getBaseAssetId());
            quotingAssetId.setText(assetPair.getQuotingAssetId());
        } else {
            baseAssetId.setText(assetPair.getQuotingAssetId());
            quotingAssetId.setText(assetPair.getBaseAssetId());
        }
    }

    public TraidingItem(Context context) {
        super(context);
    }

    public TraidingItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setAssetPair(AssetPair assetPair) {
        this.assetPair = assetPair;

        afterViews();
    }

    // TODO: Improve performances by mapping assets
    private Rate foundViaName(String name){
        if (assetPairManager.getRatesResult() != null &&
                assetPairManager.getRatesResult().getRates() != null) {
            for (Rate rate : assetPairManager.getRatesResult().getRates()) {
                if (rate.getId().equals(name)) {
                    return rate;
                }
            }
        }
        return null;
    }
}
