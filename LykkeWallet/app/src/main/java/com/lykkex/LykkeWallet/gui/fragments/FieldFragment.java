package com.lykkex.LykkeWallet.gui.fragments;

import android.support.v4.app.Fragment;
import android.widget.EditText;

import com.github.oxo42.stateless4j.delegates.Action;
import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.gui.fragments.controllers.FieldController;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.FieldStateMachine;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EFragment(R.layout.field_fragment)
public class FieldFragment extends Fragment{

    @Bean
    FieldController controller;
    @ViewById  EditText editTextField;

    @AfterViews
    public void afterViews(){
        controller.init(this);
        //TODO определить на каком этапе регистрации
    }

    public void initEmailState(){
        editTextField.setHint(R.string.email_hint);
    }

    public void initFullNameState(){
        editTextField.setHint(R.string.fullname_hint);
    }

    public void initMobileState(){
        editTextField.setHint(R.string.mobile_hint);
    }

    public void initFirstPasswordState(){
        editTextField.setHint(R.string.first_password_hint);
    }

    public void initSecondPasswordState(){
        editTextField.setHint(R.string.seond_password_hint);
    }

    @Click(R.id.buttonAction)
    public void clickAction(){
        controller.fire();
    }
}
