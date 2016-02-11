package com.lykkex.LykkeWallet.gui.fragments.controllers;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.FieldStateMachine;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EBean
public class FieldController {

    @Bean FieldStateMachine stateMachine;

    public void init(FieldFragment fragment, FieldState state){
        stateMachine.init(state, fragment);
    }

    public void fire(FieldTrigger trigger){
        stateMachine.fire(trigger);
    }
    public void fire(){
        switch (stateMachine.getState()){
            case Idle:
                stateMachine.fire(FieldTrigger.EmailScreen);
                break;
            case EmailScreen:
                stateMachine.fire(FieldTrigger.FullNameScreen);
                break;
            case FullNameScreen:
                stateMachine.fire(FieldTrigger.MobileScreen);
                break;
            case MobileScreen:
                stateMachine.fire(FieldTrigger.FirstPasswordScreen);
                break;
            case FirstPasswordScreen:
                stateMachine.fire(FieldTrigger.SecondPasswordScreen);
                break;
            case SecondPasswordScreen:
                stateMachine.fire(FieldTrigger.SendRegistrationRequst);
                break;
            case EmailSignInScreen:
                stateMachine.fire(FieldTrigger.PasswordSignInScreen);
                break;
            case PasswordSignInScreen:
                stateMachine.fire(FieldTrigger.SendAuthRequest);
                break;
            case FullNameScreenBack:
                stateMachine.fire(FieldTrigger.FullNameScreen);
                break;
            case MobileScreenBack:
                stateMachine.fire(FieldTrigger.MobileScreen);
                break;
            case FirstPasswordScreenBack:
                stateMachine.fire(FieldTrigger.FirstPasswordScreen);
                break;
            case SecondPasswordScreenBack:
                stateMachine.fire(FieldTrigger.SecondPasswordScreen);
                break;
        }
    }

    public void fireBackPressed(){
        switch (stateMachine.getState()){
            case EmailScreen:
                stateMachine.fire(FieldTrigger.EmailScreenBack);
            case FullNameScreen:
                stateMachine.fire(FieldTrigger.FullNameScreenBack);
                break;
            case MobileScreen:
                stateMachine.fire(FieldTrigger.MobileScreenBack);
                break;
            case FirstPasswordScreen:
                stateMachine.fire(FieldTrigger.FirstPasswordScreenBack);
                break;
            case SecondPasswordScreen:
                stateMachine.fire(FieldTrigger.SecondPasswordScreenBack);
                break;
            case SecondPasswordScreenBack:
                stateMachine.fire(FieldTrigger.FirstPasswordScreenBack);
                break;
            case FirstPasswordScreenBack:
                stateMachine.fire(FieldTrigger.MobileScreenBack);
                break;
            case MobileScreenBack:
                stateMachine.fire(FieldTrigger.FullNameScreenBack);
                break;
            case FullNameScreenBack:
                stateMachine.fire(FieldTrigger.EmailScreenBack);
                break;
        }
    }

    public FieldState getCurrentState(){
        return stateMachine.getState();
    }
}
