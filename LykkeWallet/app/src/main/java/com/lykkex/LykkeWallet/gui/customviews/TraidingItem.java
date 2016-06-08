package com.lykkex.LykkeWallet.gui.customviews;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.TradingActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.enums.TradingEnum;
import com.lykkex.LykkeWallet.gui.managers.AssetPairManager;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.widgets.DrawLine;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPair;
import com.lykkex.LykkeWallet.rest.trading.response.model.Rate;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Murtic on 08/06/16.
 */
@EViewGroup(R.layout.trading_item)
public class TraidingItem extends RelativeLayout {

    @ViewById
    TextView tvAssetName;

    @ViewById
    DrawLine graphic;

    @ViewById
    TextView tvPrice;

    @Bean
    AssetPairManager assetPairManager;

    private AssetPair assetPair;

    @Click({R.id.tvAssetName, R.id.tvPrice})
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(getContext(), TradingActivity_.class);
        intent.putExtra(Constants.EXTRA_ASSET_PAIR, assetPair);
        intent.putExtra(Constants.EXTRA_FRAGMENT, TradingEnum.description);
        intent.putExtra(Constants.EXTRA_ASSETPAIR_NAME, assetPair.getName());
        intent.putExtra(Constants.EXTRA_ASSETPAIR_ID, assetPair.getId());
        intent.putExtra(Constants.EXTRA_ASSETPAIR_ACCURANCY, assetPair.getAccurancy());

        getContext().startActivity(intent);
    }

    @AfterViews
    public void afterViews() {
        if(assetPair == null) return;

        tvAssetName.setText(assetPair.getName());

        Rate rate = foundViaName(assetPair.getId());

        if(rate != null) {
            tvPrice.setText(String.valueOf(BigDecimal.valueOf
                    (rate.getAsk()).setScale(assetPair.getAccurancy(), RoundingMode.HALF_EVEN)));
            tvPrice.setBackgroundResource(R.drawable.active_price);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                graphic.setUpRates(rate, getResources().getColor(R.color.light_blue, null));
            } else {
                graphic.setUpRates(rate, getResources().getColor(R.color.light_blue));
            }
        } else {
            tvPrice.setBackgroundResource(R.drawable.price_not_come);
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
