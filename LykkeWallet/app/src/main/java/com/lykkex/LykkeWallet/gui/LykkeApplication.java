package com.lykkex.LykkeWallet.gui;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.lykkex.LykkeWallet.rest.registration.RegistrationApi;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import io.fabric.sdk.android.Fabric;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by e.kazimirova on 08.02.2016.
 */
@EApplication
public class LykkeApplication extends Application {

    private  RegistrationApi registrationApi;
    private  Retrofit retrofit;

    @AfterInject
    public void init() {
       // Fabric.with(this, new Crashlytics());

        retrofit = new Retrofit.Builder()
                .baseUrl("https://lykke-api.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        registrationApi = retrofit.create(RegistrationApi.class);
    }



    public RegistrationApi getRegistrationApi(){
        return registrationApi;
    }

}
