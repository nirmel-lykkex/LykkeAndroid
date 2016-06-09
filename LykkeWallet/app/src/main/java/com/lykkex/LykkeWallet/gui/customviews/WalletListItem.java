package com.lykkex.LykkeWallet.gui.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import java.util.List;

/**
 * Created by Murtic on 08/06/16.
 */
@EViewGroup(R.layout.wallet_list_item)
public class WalletListItem extends RelativeLayout {

    @ViewById
    RelativeLayout relMain;

    @ViewById
    TextView tvTitle;

    @ViewById
    ImageView imgIcon;

    @ViewById
    View dividerView;

    @ViewById
    LinearLayout relInfo;

    private UserPref_ userPref;

    private int position = 0;

    private boolean isItGet = false;

    private List<AssetsWallet> assets;

    private LykkeWalletResult lykkeWallet;

    @Click({R.id.relMain, R.id.tvTitle})
    public void onClick() {
        setUpInfo(true);
    }

    @AfterViews
    public void afterViews() {
        userPref = new UserPref_(getContext());
    }

    public WalletListItem(Context context) {
        super(context);
    }

    public WalletListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPosition(Integer position) {
        this.position = position;

        if (position == 0) {
            tvTitle.setText(R.string.bitcoin);
            imgIcon.setImageResource(R.drawable.bitcoin);
        } else {
            tvTitle.setText(R.string.lykke_title);
            imgIcon.setImageResource(R.drawable.lykke_wallet);
        }
    }

    public void setUpInfo(){
        setUpInfo(false);
    }

    public void setUpInfo(boolean toggle){
        relInfo.removeAllViews();

        if (position == 1) {
            if(!toggle && userPref.isOpenLykke().getOr(false)) {
                setUpLykkeInfo();
            } else if (userPref.isOpenLykke().getOr(false)) {
                userPref.isOpenLykke().put(false);
            } else {
                userPref.isOpenLykke().put(true);
                setUpLykkeInfo();
            }
        } else {
            if(!toggle && userPref.isOpenBank().getOr(false)) {
                setUpLykkeInfo();
            } else if (userPref.isOpenBank().getOr(false)) {
                userPref.isOpenBank().put(false);
            } else {
                userPref.isOpenBank().put(true);
                setUpLykkeInfo();
            }
        }
    }

    public void setAssets(List<AssetsWallet> assets) {
        this.assets = assets;
    }

    public void setLykkeWallet(LykkeWalletResult lykkeWallet) {
        this.lykkeWallet = lykkeWallet;
    }

    private void setUpLykkeInfo(){
        dividerView.setVisibility(View.GONE);
        relInfo.setVisibility(View.VISIBLE);

        if (isItGet) {
            if (assets != null && assets.size() > 0) {
                for (AssetsWallet assetsWallet : assets) {
                    WalletInfoItem infoItem = WalletInfoItem_.build(getContext());
                    infoItem.render(lykkeWallet, assetsWallet);

                    relInfo.addView(infoItem);
                }
            } else {
                getEmptyViewCoins(getEmptyView());
            }
        } else {
            relInfo.addView(new ProgressBar(getContext()));
        }
    }

    public void setItGet(boolean itGet) {
        isItGet = itGet;
    }

    private void getEmptyViewCoins(View view){
        ((TextView) view.findViewById(R.id.tvEmpty)).setText(R.string.no_info_coins);
        relInfo.addView(view);
    }

    private View getEmptyView() {
        LayoutInflater lInflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        return lInflater.inflate(R.layout.empty_view, null, false);
    }
}
