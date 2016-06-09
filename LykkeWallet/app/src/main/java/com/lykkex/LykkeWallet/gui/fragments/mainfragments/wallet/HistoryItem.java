package com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.HistoryActivity_;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.QrCodeActivity_;
import com.lykkex.LykkeWallet.gui.managers.SettingManager;
import com.lykkex.LykkeWallet.gui.models.WalletSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.CashInOut;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.ItemHistory;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.Trading;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Murtic on 08/06/16.
 */
@EViewGroup(R.layout.history_item)
public class HistoryItem extends RelativeLayout {

    @ViewById
    TextView tvName;

    @ViewById
    TextView tvDateTime;

    @ViewById
    TextView tvAmount;

    @ViewById
    ImageView imgLykke;

    @AfterViews
    public void afterViews() {
    }

    public HistoryItem(Context context) {
        super(context);
    }

    public HistoryItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void render(final ItemHistory item) {
        tvDateTime.setText(item.getFormatedDateTime());

        if (item instanceof CashInOut) {
            if (((CashInOut) item).getAmount().compareTo(BigDecimal.ZERO) > 0 ) {
                tvName.setText(item.getAsset() + " " + getContext().getResources().getString(R.string.cash_in_name));
            } else {
                tvName.setText(item.getAsset() + " " + getContext().getResources().getString(R.string.cash_out_name));
            }
            setUpColor(tvAmount, (((CashInOut) item).getAmount()).
                    setScale(WalletSinglenton.getInstance().getAccurancy(item.getAsset()),  RoundingMode.HALF_EVEN).
                    stripTrailingZeros().toPlainString());
        } else {
            if (((Trading) item).getVolume().compareTo(BigDecimal.ZERO) > 0 ) {
                tvName.setText(item.getAsset() + " " + getContext().getResources().getString(R.string.exchange_in_name));
            } else {
                tvName.setText(item.getAsset() + " " + getContext().getResources().getString(R.string.exchange_out_name));
            }
            setUpColor(tvAmount, ((Trading) item).getVolume().
                    setScale(WalletSinglenton.getInstance().getAccurancy(item.getAsset()),
                            RoundingMode.HALF_EVEN).stripTrailingZeros().toPlainString());
        }

        if (item.getIconId().equals("BTC")){
            imgLykke.setBackgroundResource(R.drawable.bitcoin);
        } else if (item.getIconId().equals("LKE")){
            imgLykke.setBackgroundResource(R.drawable.logo);
        }

        setClickable(true);

        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getContext(), HistoryActivity_.class);
                intent.putExtra(Constants.EXTRA_HISTORY_ITEM, item);
                (getContext()).startActivity(intent);
            }
        });

        if(item.getBlockChainHash() == null || item.getBlockChainHash().isEmpty()) {
            setAlpha(0.5f);
        }
    }

    private void setUpColor(TextView tvAmount, String amount){
        if (new BigDecimal(amount).compareTo(BigDecimal.ZERO) == -1) {
            tvAmount.setText(amount);
            tvAmount.setTextColor(getContext().getResources().getColor(R.color.red_minus));
        } else {
            tvAmount.setText("+" + amount);
            tvAmount.setTextColor(getContext().getResources().getColor(R.color.green_plus));
        }
    }
}
