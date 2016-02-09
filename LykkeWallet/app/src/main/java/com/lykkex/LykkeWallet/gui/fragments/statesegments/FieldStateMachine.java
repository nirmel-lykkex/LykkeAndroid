package com.lykkex.LykkeWallet.gui.fragments.statesegments;

import android.os.Handler;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;
import com.github.oxo42.stateless4j.triggers.TriggerWithParameters1;
import com.lykkex.LykkeWallet.gui.fragments.FieldFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;

import org.androidannotations.annotations.EBean;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EBean
public class FieldStateMachine  {


    private StateMachine<FieldState, FieldTrigger> mOverloadState;
    private FieldFragment fragment;

    protected StateMachine<FieldState, FieldTrigger> buildStateMachine(FieldState initialState) {
        return new StateMachine<>(initialState, getConfig());
    }

    public void init(FieldState startState, FieldFragment fragment) {
        this.fragment = fragment;
        this.mOverloadState = buildStateMachine(startState);
    }

    public FieldState getState() {
        return mOverloadState.getState();
    }

    public void fire(FieldTrigger trigger) {
        mOverloadState.fire(trigger);
    }

    public StateMachineConfig<FieldState, FieldTrigger> getConfig() {
        StateMachineConfig<FieldState, FieldTrigger> config = new StateMachineConfig<>();

        config.configure(FieldState.Idle)
                .permit(FieldTrigger.EmailScreen, FieldState.EmailScreen)
                .permit(FieldTrigger.FullNameScreen, FieldState.FullNameScreen)
                .permit(FieldTrigger.MobileScreen, FieldState.MobileScreen)
                .permit(FieldTrigger.FirstPasswordScreen, FieldState.FirstPasswordScreen)
                .permit(FieldTrigger.SecondPasswordScreen, FieldState.SecondPasswordScreen)
                .ignore(FieldTrigger.Idle);

        config.configure(FieldState.EmailScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initEmailState();
                    }
                });

        config.configure(FieldState.FullNameScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initFullNameState();
                    }
                });

        config.configure(FieldState.MobileScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initMobileState();
                    }
                });


        config.configure(FieldState.FirstPasswordScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initFirstPasswordState();
                    }
                });


        config.configure(FieldState.SecondPasswordScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initSecondPasswordState();
                    }
                });


        return config;
    }


}