package com.lykkex.LykkeWallet.gui.models;

import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;

import java.util.List;

/**
 * Created by LIZA on 03.03.2016.
 */
public class SettingSinglenton {

    private static SettingSinglenton instance;
    private int refreshTimer  = 15;
    private String  baseAssetId;
    private String baseAssetSymbol;
    private BaseAsset[] baseAssets;
    private String depositUrl;
    private boolean shouldSignOrder;
    private boolean debugMode;

    public static SettingSinglenton getInstance(){
        if (instance == null) {
            instance = new SettingSinglenton();
        }
        return instance;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public int getRefreshTimer() {
        return refreshTimer;
    }

    public void setRefreshTimer(int refreshTimer) {
        this.refreshTimer = refreshTimer;
    }

    public String getBaseAssetId() {
        return baseAssetId;
    }

    public void setBaseAssetId(String baseAssetId) {
        this.baseAssetId = baseAssetId;
    }

    public String getBaseAssetSymbol() {
        return baseAssetSymbol;
    }

    public void setBaseAssetSymbol(String baseAssetSymbol) {
        this.baseAssetSymbol = baseAssetSymbol;
    }

    public BaseAsset[] getBaseAssets() {
        return baseAssets;
    }

    public void setBaseAssets(BaseAsset[] baseAssets) {
        this.baseAssets = baseAssets;
    }

    public String getDepositUrl() {
        return depositUrl;
    }

    public void setDepositUrl(String depositUrl) {
        this.depositUrl = depositUrl;
    }

    public boolean isShouldSignOrder() {
        return shouldSignOrder;
    }

    public void setShouldSignOrder(boolean shouldSignOrder) {
        this.shouldSignOrder = shouldSignOrder;
    }

}
