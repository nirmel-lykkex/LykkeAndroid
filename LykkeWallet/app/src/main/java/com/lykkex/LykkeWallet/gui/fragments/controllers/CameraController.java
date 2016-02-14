package com.lykkex.LykkeWallet.gui.fragments.controllers;

import com.lykkex.LykkeWallet.gui.SelfieActivity;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.CameraStateMachine;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.FieldStateMachine;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EBean
public class CameraController {

    @Bean  CameraStateMachine stateMachine;

    public void init(SelfieActivity activity, CameraState state){
        stateMachine.init(state, activity);
    }

    public void fire(CameraTrigger trigger){
        stateMachine.fire(trigger);
    }

    public void fire(){
        switch (stateMachine.getState()) {
            case Idle:
                break;
        }
    }

    public void fireBackPressed(){
        switch (stateMachine.getState()){
         }
    }

    public CameraState getCurrentState(){
        return stateMachine.getState();
    }
}
