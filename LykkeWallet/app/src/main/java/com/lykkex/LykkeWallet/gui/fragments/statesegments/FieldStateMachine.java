package com.lykkex.LykkeWallet.gui.fragments.statesegments;

import android.os.Handler;
import android.support.v4.app.Fragment;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;
import com.github.oxo42.stateless4j.triggers.TriggerWithParameters1;
import com.lykkex.LykkeWallet.gui.LykkeApplication_;
import com.lykkex.LykkeWallet.gui.fragments.BaseFragment;
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
                .permit(FieldTrigger.PasswordSignInScreen, FieldState.PasswordSignInScreen)
                .ignore(FieldTrigger.Idle);

        config.configure(FieldState.EmailScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initEmailState();
                    }
                })
                .permit(FieldTrigger.FullNameScreen, FieldState.FullNameScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.EmailSignInScreen, FieldState.EmailSignInScreen);

        config.configure(FieldState.FullNameScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initFullNameState();
                    }
                })
                .permit(FieldTrigger.MobileScreen, FieldState.MobileScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.FullNameScreenBack, FieldState.FullNameScreenBack);

        config.configure(FieldState.MobileScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initMobileState();
                    }
                })
                .permit(FieldTrigger.FirstPasswordScreen, FieldState.FirstPasswordScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.MobileScreenBack, FieldState.MobileScreenBack);;


        config.configure(FieldState.FirstPasswordScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initFirstPasswordState();
                    }
                })
                .permit(FieldTrigger.SecondPasswordScreen, FieldState.SecondPasswordScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.FirstPasswordScreenBack,FieldState.FirstPasswordScreenBack );


        config.configure(FieldState.SecondPasswordScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initSecondPasswordState();
                    }
                })
                .permit(FieldTrigger.SendRegistrationRequst, FieldState.SendRegistrationRequst)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.SecondPasswordScreenBack, FieldState.SecondPasswordScreenBack);

        config.configure(FieldState.SendRegistrationRequst)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).sendRegistrationRequest();
                    }
                });

        config.configure(FieldState.EmailSignInScreen)
                .permit(FieldTrigger.PasswordSignInScreen, FieldState.PasswordSignInScreen)
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.EmailScreen, FieldState.EmailScreen);

        config.configure(FieldState.PasswordSignInScreen)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        fragment.onConsume(FieldState.PasswordSignInScreen);
                    }
                })
                .permit(FieldTrigger.Idle, FieldState.Idle)
                .permit(FieldTrigger.EmailScreen, FieldState.EmailScreen);

        config.configure(FieldState.EmailScreenBack)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initBackPressed();
                    }
                });

        config.configure(FieldState.FullNameScreenBack)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initBackPressedFullName();
                    }
                })
                .permit(FieldTrigger.FullNameScreen, FieldState.FullNameScreen)
                .permit(FieldTrigger.EmailSignInScreen, FieldState.EmailSignInScreen)
                .permit(FieldTrigger.EmailScreenBack, FieldState.EmailScreenBack);

        config.configure(FieldState.MobileScreenBack)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initBackPressedMobile();
                    }
                })
                .permit(FieldTrigger.MobileScreen, FieldState.MobileScreen)
                .permit(FieldTrigger.FullNameScreenBack, FieldState.FullNameScreenBack);

        config.configure(FieldState.FirstPasswordScreenBack)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initBackPressedFirstPasswordScreen();
                    }
                })
                .permit(FieldTrigger.FirstPasswordScreen, FieldState.FirstPasswordScreen)
                .permit(FieldTrigger.MobileScreenBack, FieldState.MobileScreenBack);

        config.configure(FieldState.SecondPasswordScreenBack)
                .onEntry(new Action() {
                    @Override
                    public void doIt() {
                        ((FieldFragment)fragment).initBackPressedSecondPasswordScreen();
                    }
                })
                .permit(FieldTrigger.SecondPasswordScreen, FieldState.SecondPasswordScreen)
                .permit(FieldTrigger.FirstPasswordScreenBack, FieldState.FirstPasswordScreenBack);

        return config;
    }


}