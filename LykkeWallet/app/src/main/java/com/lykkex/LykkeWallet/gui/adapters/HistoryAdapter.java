package com.lykkex.LykkeWallet.gui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.BaseActivity;
import com.lykkex.LykkeWallet.gui.activity.HistoryActivity;
import com.lykkex.LykkeWallet.gui.activity.HistoryActivity_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.history.BlockChainHistoryFragment_;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.tradings.BlockchainFragment_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.CashInOut;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.ItemHistory;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.MarketOrder;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.Trading;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by e.kazimirova on 29.03.2016.
 */
public class HistoryAdapter extends BaseAdapter {

    private List<ItemHistory> list = new ArrayList<>();
    private  Context context;

    public HistoryAdapter(List<ItemHistory> list, Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup viewGroup) {
         View view = convertView;
        if (view == null) {
            LayoutInflater lInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.history_item, viewGroup, false);
        }

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvDateTime = (TextView) view.findViewById(R.id.tvDateTime);
        TextView tvAmount = (TextView) view.findViewById(R.id.tvAmount);
        ImageView imgLykke = (ImageView) view.findViewById(R.id.imgLykke);

        ItemHistory item = list.get(position);
        tvDateTime.setText(item.getDateTime());

        tvName.setText(item.getAsset());
        if (item instanceof CashInOut) {
            setUpColor(tvAmount, ((CashInOut) item).getAmount());
        } else {
            setUpColor(tvAmount, ((Trading) item).getVolume());
        }

        if (item.getIconId().equals("BTC")){
            imgLykke.setBackgroundResource(R.drawable.bitcoin);
        } else if (item.getIconId().equals("LKE")){
            imgLykke.setBackgroundResource(R.drawable.logo);
        }

        view.setClickable(true);
        view.setTag(item);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, HistoryActivity_.class);
                intent.putExtra(Constants.EXTRA_HISTORY_ITEM, (ItemHistory) view.getTag());
                ((BaseActivity)context).startActivity(intent);
            }
        });
        return view;

    }

    private void setUpColor(TextView tvAmount, String amount){
        if (new BigDecimal(amount).compareTo(BigDecimal.ZERO) == -1) {
            tvAmount.setTextColor(context.getResources().getColor(R.color.red_minus));
        } else {
            tvAmount.setTextColor(context.getResources().getColor(R.color.green_plus));
        }
        tvAmount.setText(amount);
    }
}
