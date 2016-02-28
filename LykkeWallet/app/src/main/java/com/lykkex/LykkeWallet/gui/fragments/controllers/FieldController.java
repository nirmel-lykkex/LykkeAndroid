package com.lykkex.LykkeWallet.gui.fragments.controllers;

import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.FieldStateMachine;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EBean
public class FieldController {

    @Bean FieldStateMachine stateMachine;

    public void init(BaseFragment fragment, FieldState state){
        stateMachine.init(state, fragment);
    }

    public void fire(FieldTrigger trigger){
        stateMachine.fire(trigger);
    }

    public FieldState getCurrentState(){
        return stateMachine.getState();
    }
}
