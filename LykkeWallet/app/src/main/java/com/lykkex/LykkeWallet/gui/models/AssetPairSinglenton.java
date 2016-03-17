package com.lykkex.LykkeWallet.gui.models;

import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairsResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesResult;

/**
 * Created by e.kazimirova on 17.03.2016.
 */
public class AssetPairSinglenton {

    private static AssetPairSinglenton instance;
    private RatesResult rates;
    private AssetPairsResult result;
    private boolean isCollapsed = true;

    public static AssetPairSinglenton getInstance(){
        if (instance == null) {
            instance = new AssetPairSinglenton();
        }
        return instance;
    }


    public AssetPairsResult getResult() {
        return result;
    }

    public void setResult(AssetPairsResult result) {
        this.result = result;
    }

    public boolean isCollapsed() {
        return isCollapsed;
    }

    public void setIsCollapsed(boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
    }


    public RatesResult getRates() {
        return rates;
    }

    public void setRates(RatesResult rates) {
        this.rates = rates;
    }

}
