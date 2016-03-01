package com.lykkex.LykkeWallet.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCards;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

import java.util.List;

/**
 * Created by LIZA on 01.03.2016.
 */
public class WalletAdapter extends BaseAdapter {

    private LykkeWalletResult lykkeWallet;
    private Context mContext;

    public WalletAdapter(LykkeWalletResult lykkeWallet, Context context){
        this.lykkeWallet = lykkeWallet;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        if (i == 1) {
            return lykkeWallet.getLykke();
        } else {
            return lykkeWallet.getBankCardses();
        }
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater lInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = lInflater.inflate(R.layout.waller_list_item, viewGroup, false);
        }
        final Holder holder;
        if (view.getTag() == null) {
            holder = new Holder();

            RelativeLayout relMain = (RelativeLayout) view.findViewById(R.id.relMain);
            TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            ImageView imgPlus = (ImageView) view.findViewById(R.id.imgPlus);

            LinearLayout relInfo = (LinearLayout) view.findViewById(R.id.relInfo);

            holder.relInfo = relInfo;
            holder.relMain = relMain;
            holder.imgPlus = imgPlus;
            holder.tvTitle = tvTitle;

        } else {
            holder = (Holder) view.getTag();
        }

        if (position == 0) {
            holder.tvTitle.setText(R.string.credit_card);
        } else {
            holder.tvTitle.setText(R.string.lykke_title);
        }

        holder.relInfo.setVisibility(View.GONE);

        holder.relMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpInfo(holder, position);
            }
        });

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpInfo(holder, position);
            }
        });

        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpInfo(holder, position);
            }
        });
        return view;
    }

    private void setUpInfo(Holder holder, int position){
        if (position == 1) {
            setUpLykkeInfo(holder);
        } else {
            setUpBankCardInfo(holder);
        }
    }

    private void setUpBankCardInfo(Holder holder){
        holder.relInfo.setVisibility(View.VISIBLE);
        for (BankCards cards : lykkeWallet.getBankCardses()) {
            InfoHolder holderInfo = getViewInfo(holder);
            holderInfo.tvTitleProp.setText(cards.getName());
            holderInfo.tvValue.setText("..."+cards.getLastDigits());
        }
    }

    private void setUpLykkeInfo(Holder holder){
        holder.relInfo.setVisibility(View.VISIBLE);
        if (lykkeWallet.getLykke().getAssets().length >0) {
            for (AssetsWallet assetsWallet : lykkeWallet.getLykke().getAssets()) {
                InfoHolder holderInfo = getViewInfo(holder);
                holderInfo.tvTitleProp.setText(assetsWallet.getName());
                holderInfo.tvValue.setText(assetsWallet.getBalance());
            }
        } else {
            getEmptyView(holder);
        }
    }

    private void getEmptyView(Holder holder){
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.empty_view, null, false);
        holder.relInfo.addView(view);
    }

    private InfoHolder getViewInfo(Holder holder){
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.info_item, null, false);
        holder.relInfo.addView(view);
        TextView tvValue = (TextView) view.findViewById(R.id.tvValue);
        TextView tvTitleProp = (TextView) view.findViewById(R.id.tvTitleProp);
        InfoHolder infoHolder = new InfoHolder();
        infoHolder.tvTitleProp = tvTitleProp;
        infoHolder.tvValue = tvValue;
        return infoHolder;
    }

    private class Holder {
        public RelativeLayout relMain;
        public TextView tvTitle;
        public ImageView imgPlus;
        public LinearLayout relInfo;

    }

    private class InfoHolder {
        public TextView tvValue;
        public TextView tvTitleProp;
    }
}
