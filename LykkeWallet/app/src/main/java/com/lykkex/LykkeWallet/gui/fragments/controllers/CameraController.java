package com.lykkex.LykkeWallet.gui.fragments.controllers;

import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.BaseCameraFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.CameraStateMachine;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EBean
public class CameraController {

    @Bean  CameraStateMachine stateMachine;

    public void init(BaseCameraFragment fragment, CameraState state){
        stateMachine.init(state, fragment);
    }

    public void fire(CameraTrigger trigger){
        stateMachine.fire(trigger);
    }

    public CameraState getCurrentState(){
        return stateMachine.getState();
    }
}
