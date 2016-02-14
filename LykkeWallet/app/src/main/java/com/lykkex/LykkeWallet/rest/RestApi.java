package com.lykkex.LykkeWallet.rest;



import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.login.request.model.AuthRequest;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;
import com.lykkex.LykkeWallet.rest.registration.request.models.RegistrationModel;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public interface RestApi {

    @GET("/api/AccountExist")
    Call<AccountExistData> accountExis(
            @Query("email") String email);

    @POST("/api/Registration")
    Call<RegistrationData> registration(
            @Body RegistrationModel registrationModel);

    @POST("/api/Auth")
    Call<AuthModelData> getAuth(@Body AuthRequest request);

    @GET("/api/CheckDocumentsToUpload")
    Call<CameraData> checkDocuments(@Body PersonalData request);
}