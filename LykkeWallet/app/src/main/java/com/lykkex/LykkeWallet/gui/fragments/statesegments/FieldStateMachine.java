package com.lykkex.LykkeWallet.gui.fragments.statesegments;

import android.os.Handler;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;
import com.github.oxo42.stateless4j.triggers.TriggerWithParameters1;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
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
                .permit(FieldTrigger.EmailSignInScreen, FieldState.EmailSignInScreen)
                .permit(FieldTrigger.PasswordSignInScreen, FieldState.PasswordSignInScreen)
                .ignore(FieldTrigger.Idle);

        config.configure(FieldState.EmailScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initEmailState();
                    }
                })
                .permit(FieldTrigger.FullNameScreen, FieldState.FullNameScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.EmailSignInScreen, FieldState.EmailSignInScreen);

        config.configure(FieldState.FullNameScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initFullNameState();
                    }
                })
                .permit(FieldTrigger.MobileScreen, FieldState.MobileScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.EmailScreenBack,FieldState.EmailScreenBack );

        config.configure(FieldState.MobileScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initMobileState();
                    }
                })
                .permit(FieldTrigger.FirstPasswordScreen, FieldState.FirstPasswordScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.FullNameScreenBack,FieldState.FullNameScreenBack );


        config.configure(FieldState.FirstPasswordScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initFirstPasswordState();
                    }
                })
                .permit(FieldTrigger.SecondPasswordScreen, FieldState.SecondPasswordScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.MobileScreenBack,FieldState.MobileScreenBack );


        config.configure(FieldState.SecondPasswordScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initSecondPasswordState();
                    }
                })
                .permit(FieldTrigger.SendRegistrationRequst, FieldState.SendRegistrationRequst)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.FirstPasswordScreenBack,FieldState.FirstPasswordScreenBack );

        config.configure(FieldState.SendRegistrationRequst)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.sendRegistrationRequest();
                    }
                });

        config.configure(FieldState.EmailSignInScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initEmailSignInScreen();
                    }
                })
                .permit(FieldTrigger.PasswordSignInScreen, FieldState.PasswordSignInScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle);

        config.configure(FieldState.PasswordSignInScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.initPasswordSignInScreen();
                    }
                })
                .permit(FieldTrigger.EmailSignInScreen, FieldState.EmailSignInScreen)
                .permit(FieldTrigger.SendAuthRequest, FieldState.SendAuthRequest)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.EmailSignInScreenBack,FieldState.EmailSignInScreenBack );

        config.configure(FieldState.SendAuthRequest)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.sendAuthRequest();
                    }
                });

        return config;
    }


}