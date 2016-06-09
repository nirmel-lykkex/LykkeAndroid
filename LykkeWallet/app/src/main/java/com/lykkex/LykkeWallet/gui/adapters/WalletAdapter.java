package com.lykkex.LykkeWallet.gui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet.WalletListItem;
import com.lykkex.LykkeWallet.gui.fragments.mainfragments.wallet.WalletListItem_;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LIZA on 01.03.2016.
 */
public class WalletAdapter extends BaseAdapter {

    private LykkeWalletResult lykkeWallet;
    private Context mContext;
    private boolean isItGet = false;
    private UserPref_ userPref;

    private List<List<AssetsWallet>> lykkeWalletItems = new ArrayList<>();

    public WalletAdapter(LykkeWalletResult lykkeWallet, Context context, boolean isItGet){
        this.lykkeWallet = lykkeWallet;
        this.isItGet = isItGet;
        this.mContext = context;
        userPref = new UserPref_(context);

        List<AssetsWallet> assetBTC = new ArrayList<>();
        List<AssetsWallet> assetLKE = new ArrayList<>();

        if(lykkeWallet != null) {
            for (AssetsWallet wallet : lykkeWallet.getLykke().getAssets()) {
                if (wallet.getBalance().doubleValue() <= 0 && wallet.isHideIfZero()) {
                    continue;
                }

                if (wallet.getIssuerId().equals(Constants.BTC)) {
                    assetBTC.add(wallet);
                } else if (wallet.getIssuerId().equals(Constants.LKE)) {
                    assetLKE.add(wallet);
                }
            }
        }

        lykkeWalletItems.add(assetBTC);
        lykkeWalletItems.add(assetLKE);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int i) {
        return lykkeWalletItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        WalletListItem view = (WalletListItem) convertView;

        if(view == null) {
            view = WalletListItem_.build(mContext);
        }

        view.setLykkeWallet(lykkeWallet);
        view.setAssets((List<AssetsWallet>) getItem(position));

        view.setPosition(position);
        view.setItGet(isItGet);

        if (position == 1 && isItGet && userPref.isOpenLykke().get()
                || position == 0 && isItGet && userPref.isOpenBank().get()) {
            view.setUpInfo();
        }

        return view;
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

    /*private void getEmptyViewCards(View view, Holder holder){
        ((TextView) view.findViewById(R.id.tvEmpty)).setText(R.string.no_info_cards);
        holder.relInfo.addView(view);
    }

    private void startAddCardActivity(){
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_BANK_CARDS, lykkeWallet.getBankCardses());
        intent.setClass(mContext, AddCardActivity_.class);
        mContext.startActivity(intent);
    }*/
}
