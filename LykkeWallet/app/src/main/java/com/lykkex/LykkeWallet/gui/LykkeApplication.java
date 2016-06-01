package com.lykkex.LykkeWallet.gui;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.lykkex.LykkeWallet.gui.fragments.storage.UserPref_;
import com.lykkex.LykkeWallet.gui.utils.Constants;
import com.lykkex.LykkeWallet.rest.RestApi;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EApplication;

import java.io.IOException;

import io.fabric.sdk.android.Fabric;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by e.kazimirova on 08.02.2016.
 */
@EApplication
public class LykkeApplication extends Application {

    private RestApi restApi;
    private Retrofit retrofit;
    private UserPref_ pref;

    @AfterInject
    public void init() {
        pref = new UserPref_(this);
        Fabric.with(this, new Crashlytics());
        setUpServer();
    }

    public void setUpServer(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .method(original.method(), original.body());

                        if(pref.authToken() != null && pref.authToken().get() != null) {
                            requestBuilder.header("Authorization", Constants.PART_AUTHORIZATION + pref.authToken().get());
                        }

                        return chain.proceed(requestBuilder.build());
                    }
        }).build();

        String apiUrl = null;

        switch (pref.idServer().get()) {
            case 0:
                apiUrl = "https://lykke-api-dev.azurewebsites.net";
                break;
            case 1:
                apiUrl = "https://lykke-api-test.azurewebsites.net";
                break;
            case 2:
                apiUrl = "https://lykke-api-demo.azurewebsites.net";
                break;
        }

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(apiUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        restApi = retrofit.create(RestApi.class);
    }

    public RestApi getRestApi(){
        return restApi;
    }
}
