package com.lykkex.LykkeWallet.gui;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.lykkex.LykkeWallet.BuildConfig;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.rest.RestApi;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.sharedpreferences.Pref;

import io.fabric.sdk.android.Fabric;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by e.kazimirova on 08.02.2016.
 */
@EApplication
public class LykkeApplication extends Application {

    private RestApi restApi;
    private  Retrofit retrofit;
    private
    UserPref_ pref;

    @AfterInject
    public void init() {
        pref = new UserPref_(this);
        //Fabric.with(this, new Crashlytics());
        setUpServer();
    }

    public void setUpServer(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        switch (pref.idServer().get()) {
            case 0:
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("https://lykke-api-dev.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                break;
            case 1:
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("https://lykke-api-test.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                break;
            case 2:
                retrofit = new Retrofit.Builder()
                        .client(client)
                        .baseUrl("https://lykke-api-demo.azurewebsites.net")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                break;
        }


        restApi = retrofit.create(RestApi.class);
    }


    public RestApi getRestApi(){
        return restApi;
    }

}
