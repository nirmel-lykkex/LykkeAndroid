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
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet.HistoryItem;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet.HistoryItem_;
import com.lykkex.LykkeWallet.gui.models.WalletSinglenton;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.CashInOut;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.ItemHistory;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.MarketOrder;
import com.lykkex.LykkeWallet.rest.history.reposnse.model.Trading;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by e.kazimirova on 29.03.2016.
 */
public class HistoryAdapter extends BaseAdapter {

    private List<ItemHistory> list = new ArrayList<>();
    private Context context;

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
        HistoryItem view = (HistoryItem) convertView;

        if (view == null) {
            view = HistoryItem_.build(context);
        }

        view.render(list.get(position));

        return view;

    }
}
