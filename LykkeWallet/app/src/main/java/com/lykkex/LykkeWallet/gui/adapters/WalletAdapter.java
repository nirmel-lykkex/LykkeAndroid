package com.lykkex.LykkeWallet.gui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.AddCardActivity_;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.PaymentActivity_;
import com.lykkex.LykkeWallet.gui.activity.paymentflow.QrCodeActivity_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCards;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LIZA on 01.03.2016.
 */
public class WalletAdapter extends BaseAdapter {

    private LykkeWalletResult lykkeWallet;
    private Context mContext;
    private boolean isClickedLykke = false;
    private boolean isClickedBank = false;
    private boolean isItGet = false;


    public WalletAdapter(LykkeWalletResult lykkeWallet, Context context,boolean isItGet){
        this.lykkeWallet = lykkeWallet;
        this.isItGet = isItGet;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        if (i == 1) {
            List<AssetsWallet> assetWaller = new ArrayList<>();
            for(AssetsWallet wallet : lykkeWallet.getLykke().getAssets()){
                if (wallet.getIssuerId().equals(Constants.BTC)){
                    assetWaller.add(wallet);
                }
            }
            return assetWaller;
        } else {
            List<AssetsWallet> assetWaller = new ArrayList<>();

            for(AssetsWallet wallet : lykkeWallet.getLykke().getAssets()){
                if (wallet.getIssuerId().equals(Constants.LKE)){
                    assetWaller.add(wallet);
                }
            }
            return assetWaller;
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
            ImageView imgIcon = (ImageView) view.findViewById(R.id.imgIcon);
            View dividerView = (View) view.findViewById(R.id.dividerView);

            LinearLayout relInfo = (LinearLayout) view.findViewById(R.id.relInfo);

            holder.dividerView = dividerView;
            holder.relInfo = relInfo;
            holder.relMain = relMain;
            holder.tvTitle = tvTitle;
            holder.imgIcon = imgIcon;

        } else {
            holder = (Holder) view.getTag();
        }

        if (position == 0) {
            holder.tvTitle.setText(R.string.bitcoin);
            holder.imgIcon.setImageResource(R.drawable.bitcoin);
        } else {
            holder.tvTitle.setText(R.string.lykke_title);
            holder.imgIcon.setImageResource(R.drawable.lykke_wallet);
        }

        holder.relInfo.setVisibility(View.GONE);

        holder.relMain.setOnClickListener(new View.OnClickListener() {
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
        holder.relInfo.removeAllViews();
        if (position == 1) {
            if (isClickedLykke) {
                isClickedLykke = false;
            } else {
                setUpLykkeInfo(holder, Constants.LKE);
            }
        } else {
            if (isClickedBank) {
                isClickedBank = false;
            } else {
                setUpLykkeInfo(holder, Constants.BTC);
            }
        }
    }

   /* private void setUpBankCardInfo(Holder holder){
        isClickedBank = true;
        holder.dividerView.setVisibility(View.VISIBLE);
        holder.relInfo.setVisibility(View.VISIBLE);
        if (isItGet) {
            if (lykkeWallet.getBankCardses() != null &&
                    lykkeWallet.getBankCardses().length >0) {
                for (BankCards cards : lykkeWallet.getBankCardses()) {
                    InfoHolder holderInfo = getViewInfo(holder);
                    holderInfo.relMain.setTag(cards.getId());
                    holderInfo.relMain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startPaymentActivity((String) view.getTag());
                        }
                    });

                    holderInfo.tvTitleProp.setTag(cards.getId());
                    holderInfo.tvTitleProp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startPaymentActivity((String) view.getTag());
                        }
                    });
                    holderInfo.tvTitleProp.setText(cards.getName());
                    holderInfo.tvValue.setText(".... " + cards.getLastDigits());
                    holderInfo.tvValue.setTag(cards.getId());
                    holderInfo.tvValue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startPaymentActivity((String) view.getTag());
                        }
                    });
                }
            }else {
                getEmptyViewCards(getEmptyView(), holder);
            }
        } else {
            holder.relInfo.addView(new ProgressBar(mContext));
        }
    }*/

    private void setUpLykkeInfo(Holder holder, String parseName){
        holder.dividerView.setVisibility(View.GONE);
        isClickedLykke = true;
        holder.relInfo.setVisibility(View.VISIBLE);
        if (isItGet) {
            List<AssetsWallet> list= new ArrayList<>();
            if (lykkeWallet != null &&
                    lykkeWallet.getLykke() != null &&
                    lykkeWallet.getLykke().getAssets() != null) {
                for (AssetsWallet assetsWallet : lykkeWallet.getLykke().getAssets()) {
                    if (assetsWallet.getIssuerId().equals(parseName)) {
                        if (Double.valueOf(assetsWallet.getBalance()) < 0 &&
                                !assetsWallet.isHideIfZero()) {
                            list.add(assetsWallet);
                        } else if (Double.valueOf(assetsWallet.getBalance()) > 0) {
                            list.add(assetsWallet);
                        }
                    }
                }
            }
            if (list != null &&
                    list.size() > 0) {
                for (AssetsWallet assetsWallet : list) {
                    InfoHolder holderInfo = getViewInfo(holder);
                    holderInfo.tvTitleProp.setText(assetsWallet.getName());
                    holderInfo.tvValue.setText(assetsWallet.getBalance());
                    holderInfo.relMain.setTag(assetsWallet);
                    holderInfo.relMain.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startPaymentActivity((AssetsWallet) view.getTag());
                        }
                    });

                    holderInfo.tvTitleProp.setTag(assetsWallet);
                    holderInfo.tvTitleProp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startPaymentActivity((AssetsWallet) view.getTag());
                        }
                    });

                    holderInfo.tvValue.setTag(assetsWallet);
                    holderInfo.tvValue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startPaymentActivity((AssetsWallet) view.getTag());
                        }
                    });
                }
            } else {
                getEmptyViewCoins(getEmptyView(), holder);
            }
        } else {
            holder.relInfo.addView(new ProgressBar(mContext));
        }
    }

    private View getEmptyView() {
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return lInflater.inflate(R.layout.empty_view, null, false);
    }

    private void getEmptyViewCards(View view, Holder holder){
        ((TextView) view.findViewById(R.id.tvEmpty)).setText(R.string.no_info_cards);
        holder.relInfo.addView(view);
    }

    private void getEmptyViewCoins(View view, Holder holder){
        ((TextView) view.findViewById(R.id.tvEmpty)).setText(R.string.no_info_coins);
        holder.relInfo.addView(view);
    }

    private InfoHolder getViewInfo(Holder holder){
        LayoutInflater lInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = lInflater.inflate(R.layout.info_item, null, false);
        holder.relInfo.addView(view);
        TextView tvValue = (TextView) view.findViewById(R.id.tvValue);
        TextView tvTitleProp = (TextView) view.findViewById(R.id.tvTitleProp);
        RelativeLayout relMain = (RelativeLayout) view.findViewById(R.id.relMain);
        ImageView imgPlus = (ImageView) view.findViewById(R.id.imgPlus);

        InfoHolder infoHolder = new InfoHolder();
        infoHolder.relMain = relMain;
        infoHolder.tvTitleProp = tvTitleProp;
        infoHolder.tvValue = tvValue;
        infoHolder.imgPlus = imgPlus;
        return infoHolder;
    }

    private void startPaymentActivity(AssetsWallet assetsWallet){
        if ((lykkeWallet.getColoredMultiSig() != null &&
                assetsWallet.getIssuerId().equals(Constants.LKE)) ||
                (lykkeWallet.getMultiSig() != null &&
                assetsWallet.getIssuerId().equals(Constants.BTC))) {
            Intent intent = new Intent();
            intent.setClass(mContext, QrCodeActivity_.class);
            intent.putExtra(Constants.EXTRA_ASSET, assetsWallet);
            mContext.startActivity(intent);
        }
    }


    private void startAddCardActivity(){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_BANK_CARDS, lykkeWallet.getBankCardses());
        intent.setClass(mContext, AddCardActivity_.class);
        mContext.startActivity(intent);
    }

    private class Holder {
        public RelativeLayout relMain;
        public TextView tvTitle;
        public LinearLayout relInfo;
        public ImageView imgIcon;
        public View dividerView;
    }

    private class InfoHolder {
        public TextView tvValue;
        public TextView tvTitleProp;
        public RelativeLayout relMain;
        public ImageView imgPlus;
    }
}
