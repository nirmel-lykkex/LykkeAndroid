package com.lykkex.LykkeWallet.gui.managers;

import com.lykkex.LykkeWallet.gui.fragments.models.CameraModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.registration.request.models.RegistrationModel;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodesResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationResult;

import org.androidannotations.annotations.EBean;

/**
 * Created by Murtic on 01/06/16.
 */
@EBean(scope = EBean.Scope.Singleton)
public class UserManager {

    private RegistrationModelGUI model = new RegistrationModelGUI();

    private CountryPhoneCodesResult countryPhoneCodes;

    private boolean isUserRegistered = false;

    private RegistrationResult registrationResult;

    private CameraModel cameraModel;

    private CameraResult cameraResult;

    public UserManager() {
    }

    public RegistrationModelGUI getRegistrationModel() {
        return model;
    }

    public boolean isUserRegistered() {
        return isUserRegistered;
    }

    public void setUserRegistered(boolean userRegistered) {
        isUserRegistered = userRegistered;
    }

    public CountryPhoneCodesResult getCountryPhoneCodes() {
        return countryPhoneCodes;
    }

    public void setCountryPhoneCodes(CountryPhoneCodesResult countryPhoneCodes) {
        this.countryPhoneCodes = countryPhoneCodes;
    }

    public RegistrationResult getRegistrationResult() {
        return registrationResult;
    }

    public void setRegistrationResult(RegistrationResult registrationResult) {
        this.registrationResult = registrationResult;
    }

    public CameraModel getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(CameraModel cameraModel) {
        this.cameraModel = cameraModel;
    }

    public CameraResult getCameraResult() {
        return cameraResult;
    }

    public void setCameraResult(CameraResult cameraResult) {
        this.cameraResult = cameraResult;
    }
}
