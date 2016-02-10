package com.lykkex.LykkeWallet.rest.registration;

import com.lykkex.LykkeWallet.rest.registration.models.AccountExisData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public interface RegistrationApi {

    @GET("/api/AccountExist")
    Call<AccountExisData> accountExis(
            @Query("email") String email);
}
