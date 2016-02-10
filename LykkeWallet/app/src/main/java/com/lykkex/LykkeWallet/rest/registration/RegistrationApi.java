package com.lykkex.LykkeWallet.rest.registration;



import com.lykkex.LykkeWallet.rest.registration.request.models.RegistrationModel;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExisData;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @POST("/api/Registration")
    Call<RegistrationData> registration(
            @Body RegistrationModel registrationModel);
}
