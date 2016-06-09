package com.lykkex.LykkeWallet.gui.managers;

import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAsset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LIZA on 03.03.2016.
 */
public class SettingManager {

    private static SettingManager instance;
    private int refreshTimer  = 15;
    private String  baseAssetId;
    private String baseAssetSymbol;
    private Map<String, BaseAsset> baseAssets;
    private String depositUrl;
    private boolean shouldSignOrder;
    private boolean debugMode;

    public static SettingManager getInstance(){
        if (instance == null) {
            instance = new SettingManager();
        }
        return instance;
    }

    private SettingManager() {
        this.baseAssets = new LinkedHashMap<>();
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

    public List<BaseAsset> getBaseAssets() {
        return new ArrayList(baseAssets.values());
    }

    public void setBaseAssets(BaseAsset[] baseAssets) {
        for(BaseAsset baseAsset : baseAssets) {
            this.baseAssets.put(baseAsset.getId(), baseAsset);
        }
    }

    public BaseAsset getBaseAsset(String id) {
        return baseAssets.get(id);
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
