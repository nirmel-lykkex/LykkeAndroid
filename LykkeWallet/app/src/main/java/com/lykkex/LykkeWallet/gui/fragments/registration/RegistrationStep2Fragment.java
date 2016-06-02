package com.lykkex.LykkeWallet.gui.fragments.registration;

import android.app.Fragment;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.customviews.StepsIndicator;
import com.lykkex.LykkeWallet.gui.fragments.models.RegistrationModelGUI;
import com.lykkex.LykkeWallet.gui.managers.UserManager;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Murtic on 31/05/16.
 */
@EFragment(R.layout.registration_step_2_fragment)
public class RegistrationStep2Fragment extends Fragment {

    @ViewById
    StepsIndicator stepsIndicator;

    @ViewById
    EditText hintEditText;

    @ViewById
    Button buttonAction;

    @Bean
    UserManager userManager;

    @Click(R.id.buttonAction)
    public void clickButtonAction(){

    }

    @AfterViews
    void afterViews() {
        stepsIndicator.setCurrentStep(1);
    }

    @AfterTextChange(R.id.hintEditText)
    void onHintChange(Editable text) {
        userManager.getRegistrationModel().setHint(text.toString());

        buttonAction.setEnabled(text.toString().length() > 0);
    }
}
