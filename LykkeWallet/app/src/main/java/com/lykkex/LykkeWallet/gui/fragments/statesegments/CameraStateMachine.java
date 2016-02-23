package com.lykkex.LykkeWallet.gui.fragments.statesegments;

import android.graphics.Camera;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;
import com.lykkex.LykkeWallet.gui.activity.selfie.CameraActivity;
import com.lykkex.LykkeWallet.gui.fragments.camerascreen.BaseCameraFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;

import org.androidannotations.annotations.EBean;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EBean
public class CameraStateMachine {


    private StateMachine<CameraState, CameraTrigger> mOverloadState;
    private BaseCameraFragment fragment;

    protected StateMachine<CameraState, CameraTrigger> buildStateMachine(CameraState initialState) {
        return new StateMachine<>(initialState, getConfig());
    }

    public void init(CameraState startState, BaseCameraFragment fragment) {
        this.fragment = fragment;
        this.mOverloadState = buildStateMachine(startState);
    }

    public CameraState getState() {
        return mOverloadState.getState();
    }

    public void fire(CameraTrigger trigger) {
        mOverloadState.fire(trigger);
    }

    public StateMachineConfig<CameraState, CameraTrigger> getConfig() {
        StateMachineConfig<CameraState, CameraTrigger> config = new StateMachineConfig<>();

        config.configure(CameraState.Idle)
                .permit(CameraTrigger.IdCard, CameraState.IdCard)
                .permit(CameraTrigger.ProofOfAddress, CameraState.ProofOfAddress)
                .permit(CameraTrigger.Selfie, CameraState.Selfie)
                .ignore(CameraTrigger.Idle);

        config.configure(CameraState.Selfie)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {

                        //activity.onConsume(C);
                    }
                })
                .permit(CameraTrigger.IdCard, CameraState.IdCard)
                .permit(CameraTrigger.ProofOfAddress, CameraState.ProofOfAddress)
                .permit(CameraTrigger.Idle, CameraState.Idle)
                .ignore(CameraTrigger.Selfie);

        config.configure(CameraState.IdCard)
                .permit(CameraTrigger.Selfie, CameraState.Selfie)
                .permit(CameraTrigger.ProofOfAddress, CameraState.ProofOfAddress)
                .permit(CameraTrigger.Idle, CameraState.Idle)
                .ignore(CameraTrigger.IdCard);

        config.configure(CameraState.ProofOfAddress)
                .permit(CameraTrigger.IdCard, CameraState.IdCard)
                .permit(CameraTrigger.Selfie, CameraState.Selfie)
                .permit(CameraTrigger.Idle, CameraState.Idle)
                .ignore(CameraTrigger.ProofOfAddress);

        return config;

    }


}