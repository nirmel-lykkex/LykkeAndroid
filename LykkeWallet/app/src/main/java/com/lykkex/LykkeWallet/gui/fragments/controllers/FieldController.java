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

    public void init(FieldFragment fragment){
        stateMachine.init(FieldState.Idle, fragment);
    }

    public void fire(){
        switch (stateMachine.getState()){
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
                //TODO
                break;
        }
    }

    public FieldState getCurrentState(){
        return stateMachine.getState();
    }
}
