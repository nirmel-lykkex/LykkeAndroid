package com.lykkex.LykkeWallet.gui.fragments.statesegments;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.states.FieldState;
import com.lykkex.LykkeWallet.gui.fragments.statesegments.triggers.FieldTrigger;

import org.androidannotations.annotations.EBean;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
@EBean
public class FieldStateMachine  {


    private StateMachine<FieldState, FieldTrigger> mOverloadState;
    private BaseFragment fragment;

    protected StateMachine<FieldState, FieldTrigger> buildStateMachine(FieldState initialState) {
        return new StateMachine<>(initialState, getConfig());
    }

    public void init(FieldState startState, BaseFragment fragment) {
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
                .permit(FieldTrigger.EmailSignInScreen, FieldState.EmailSignInScreen)
                .ignore(FieldTrigger.Idle);

        config.configure(FieldState.EmailScreen)
                .permit(FieldTrigger.FullNameScreen, FieldState.FullNameScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.EmailSignInScreen, FieldState.EmailSignInScreen)
                .ignore(FieldTrigger.MobileScreen)
                .ignore(FieldTrigger.FirstPasswordScreen)
                .ignore(FieldTrigger.SecondPasswordScreen);

        config.configure(FieldState.FullNameScreen)
                .permit(FieldTrigger.EmailScreen, FieldState.EmailScreen)
                .permit(FieldTrigger.MobileScreen, FieldState.MobileScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .ignore(FieldTrigger.FirstPasswordScreen)
                .ignore(FieldTrigger.SecondPasswordScreen)
                .ignore(FieldTrigger.EmailSignInScreen);

        config.configure(FieldState.MobileScreen)
                .permit(FieldTrigger.FullNameScreen, FieldState.FullNameScreen)
                .permit(FieldTrigger.FirstPasswordScreen, FieldState.FirstPasswordScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .ignore(FieldTrigger.EmailScreen)
                .ignore(FieldTrigger.EmailSignInScreen)
                .ignore(FieldTrigger.SecondPasswordScreen);


        config.configure(FieldState.FirstPasswordScreen)
                .permit(FieldTrigger.MobileScreen, FieldState.MobileScreen)
                .permit(FieldTrigger.SecondPasswordScreen, FieldState.SecondPasswordScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .ignore(FieldTrigger.EmailScreen)
                .ignore(FieldTrigger.EmailSignInScreen)
                .ignore(FieldTrigger.FullNameScreen);


        config.configure(FieldState.SecondPasswordScreen)
                .permit(FieldTrigger.FirstPasswordScreen, FieldState.FirstPasswordScreen)
                .permit(FieldTrigger.SendRegistrationRequst, FieldState.SendRegistrationRequst)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .ignore(FieldTrigger.EmailScreen)
                .ignore(FieldTrigger.EmailSignInScreen)
                .ignore(FieldTrigger.MobileScreen)
                .ignore(FieldTrigger.FullNameScreen);


        config.configure(FieldState.EmailSignInScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.EmailScreen, FieldState.EmailScreen)
                .ignore(FieldTrigger.EmailSignInScreen);

        return config;
    }


}