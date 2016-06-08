package com.lykkex.LykkeWallet.gui.managers;

import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodesResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairsResult;
import com.lykkex.LykkeWallet.rest.trading.response.model.RatesResult;

import org.androidannotations.annotations.EBean;

/**
 * Created by Murtic on 01/06/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class AssetPairManager {

    private AssetPairsResult assetPairsResult;

    private RatesResult ratesResult;

    private boolean collapsed = true;

    public AssetPairManager() {
    }

    public AssetPairsResult getAssetPairsResult() {
        return assetPairsResult;
    }

    public void setAssetPairsResult(AssetPairsResult assetPairsResult) {
        this.assetPairsResult = assetPairsResult;
    }

    public RatesResult getRatesResult() {
        return ratesResult;
    }

    public void setRatesResult(RatesResult ratesResult) {
        this.ratesResult = ratesResult;
    }

    public boolean isCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }
}
