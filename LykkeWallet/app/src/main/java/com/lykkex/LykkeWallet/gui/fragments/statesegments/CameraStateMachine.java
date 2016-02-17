package com.lykkex.LykkeWallet.gui.fragments.statesegments;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.SelfieActivity;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.CameraState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.CameraTrigger;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;

import org.androidannotations.annotations.EBean;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EBean
public class CameraStateMachine {


    private StateMachine<CameraState, CameraTrigger> mOverloadState;
    private SelfieActivity activity;

    protected StateMachine<CameraState, CameraTrigger> buildStateMachine(CameraState initialState) {
        return new StateMachine<>(initialState, getConfig());
    }

    public void init(CameraState startState, SelfieActivity activity) {
        this.activity = activity;
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
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        activity.init();
                    }
                })
                .permit(CameraTrigger.IdCard, CameraState.IdCard)
                .permit(CameraTrigger.ProofOfAddress, CameraState.ProofOfAddress)
                .permit(CameraTrigger.Selfie, CameraState.Selfie)
                .permit(CameraTrigger.SelfieBack, CameraState.SelfieBack)
                .permit(CameraTrigger.CheckStatus, CameraState.CheckStatus)
                .permit(CameraTrigger.CheckingStatus, CameraState.CheckingStatus)
                .ignore(CameraTrigger.Idle);

        config.configure(CameraState.Selfie)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        activity.initSelfie();
                    }
                })
                .permit(CameraTrigger.IdCard, CameraState.IdCard)
                .permit(CameraTrigger.ProofOfAddress, CameraState.ProofOfAddress)
                .permit(CameraTrigger.CheckStatus, CameraState.CheckStatus)
                .permit(CameraTrigger.CheckingStatus, CameraState.CheckingStatus)
                .permit(CameraTrigger.Idle, CameraState.Idle);

        config.configure(CameraState.IdCard)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        activity.initIdCard();
                    }
                })
                .permit(CameraTrigger.Selfie, CameraState.Selfie)
                .permit(CameraTrigger.ProofOfAddress, CameraState.ProofOfAddress)
                .permit(CameraTrigger.SelfieBack, CameraState.SelfieBack)
                .permit(CameraTrigger.CheckStatus, CameraState.CheckStatus)
                .permit(CameraTrigger.CheckingStatus, CameraState.CheckingStatus)
                .permit(CameraTrigger.Idle, CameraState.Idle);

        config.configure(CameraState.ProofOfAddress)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        activity.initProofOfAddress();
                    }
                })
                .permit(CameraTrigger.Selfie, CameraState.Selfie)
                .permit(CameraTrigger.IdCard, CameraState.IdCard)
                .permit(CameraTrigger.SelfieBack, CameraState.SelfieBack)
                .permit(CameraTrigger.CheckingStatus, CameraState.CheckingStatus)
                .permit(CameraTrigger.CheckStatus, CameraState.CheckStatus)
                .permit(CameraTrigger.Idle, CameraState.Idle);

        config.configure(CameraState.SelfieBack)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        activity.initSelfieBack();
                    }
                })
                .permit(CameraTrigger.IdCard, CameraState.IdCard)
                .permit(CameraTrigger.ProofOfAddress, CameraState.ProofOfAddress)
                .permit(CameraTrigger.CheckingStatus, CameraState.CheckingStatus)
                .permit(CameraTrigger.CheckStatus, CameraState.CheckStatus)
                .permit(CameraTrigger.Idle, CameraState.Idle);

        config.configure(CameraState.CheckStatus)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        activity.checkStatus();
                    }
                })
                .permit(CameraTrigger.IdCard, CameraState.IdCard)
                .permit(CameraTrigger.ProofOfAddress, CameraState.ProofOfAddress)
                .permit(CameraTrigger.CheckingStatus, CameraState.CheckingStatus)
                .permit(CameraTrigger.Idle, CameraState.Idle);

        config.configure(CameraState.CheckingStatus)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        activity.sendRequestForCheck();
                    }
                })
                .permit(CameraTrigger.Idle, CameraState.Idle);

        return config;

    }


}