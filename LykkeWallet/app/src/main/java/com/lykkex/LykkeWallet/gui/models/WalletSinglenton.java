package com.lykkex.LykkeWallet.gui.models;

import com.lykkex.LykkeWallet.rest.wallet.response.models.AssetsWallet;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWalletResult;

/**
 * Created by LIZA on 15.03.2016.
 */
public class WalletSinglenton {

    public LykkeWalletResult getResult() {
        return result;
    }

    public void setResult(LykkeWalletResult result) {
        this.result = result;
    }

    private LykkeWalletResult result;
    private static WalletSinglenton instance;

    public static WalletSinglenton getInstance(){
        if (instance == null) {
            instance = new WalletSinglenton();
        }
        return instance;
    }

    public int getAccurancy(String assetPairId){
        if (result != null && result.getLykke() != null &&
                result.getLykke().getAssets() != null)
        for (AssetsWallet wallet : result.getLykke().getAssets()) {
            if (wallet != null && wallet.getId() != null &&
                    wallet.getId().equals(assetPairId)){
                return wallet.getAccuracy();
            }
        }
        return 0;
    }
}
