package com.lykkex.LykkeWallet.gui.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.guisegments.LoginGuiSegment;
import com.lykkex.LykkeWallet.gui.fragments.guisegments.RegistrationGuiSegment;
import com.lykkex.LykkeWallet.gui.fragments.models.AuthModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.gui.utils.validation.EmailTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.PasswordTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.SimpleTextWatcher;
import com.lykkex.LykkeWallet.gui.utils.validation.ValidationListener;
import com.lykkex.LykkeWallet.gui.widgets.ValidationEditText;
import com.lykkex.LykkeWallet.rest.login.callback.LoginDataCallback;
import com.lykkex.LykkeWallet.rest.login.request.model.AuthRequest;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.registration.callback.RegistrationDataCallback;
import com.lykkex.LykkeWallet.rest.registration.response.models.AcountExistResult;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.field_fragment)
public class FieldFragment extends Fragment {

    @Bean FieldController controller;
    @Bean RegistrationGuiSegment registrationGuiSegment;
    @Bean LoginGuiSegment loginGuiSegment;

    @ViewById Button buttonAction;
    @ViewById RelativeLayout validationField;
    @ViewById EditText editTextField;
    @ViewById ImageView imageWell;
    @ViewById Button buttonClear;


    @AfterViews
    public void afterViews() {
        controller.init(this, FieldState.EmailScreen);
        registrationGuiSegment.init(editTextField, imageWell,buttonClear,
               buttonAction,  controller);
        loginGuiSegment.init(editTextField,  buttonAction, imageWell,buttonClear,controller);
        initEmailState();
    }

    public void initEmailState() {
        registrationGuiSegment.initEmailState();
    }

    public void initFullNameState() {
        registrationGuiSegment.initFullNameState();
    }

    public void initMobileState() {
        registrationGuiSegment.initMobileState();
    }

    public void initFirstPasswordState() {
        registrationGuiSegment.initFirstPasswordState();
    }

    public void initSecondPasswordState() {
        registrationGuiSegment.initSecondPasswordState();
    }

    public void initPasswordSignInScreen(){
        loginGuiSegment.initPasswordSignInScreen();
    }

    public void sendRegistrationRequest(){
        registrationGuiSegment.sendRegistrationRequest();
    }

    public void sendAuthRequest(){
        loginGuiSegment.sendAuthRequest();
    }

    public void initEmailSignInScreen(){
        registrationGuiSegment.clearEditText();
        loginGuiSegment.initEmailSignIn();
    }

    @Click(R.id.buttonAction)
    public void clickAction() {
        registrationGuiSegment.clickAction();
        loginGuiSegment.clickAction();
    }


    public void initOnBackPressed(){
        if (controller.getCurrentState() == FieldState.Idle ||
                controller.getCurrentState() == FieldState.EmailScreen) {
            getActivity().finish();
        } else {
            controller.fireBackPressed();
        }
    }
}
