package com.lykkex.LykkeWallet.rest;



import com.lykkex.LykkeWallet.rest.base.models.Error;
import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraData;
import com.lykkex.LykkeWallet.rest.camera.response.models.DocumentAnswerData;
import com.lykkex.LykkeWallet.rest.camera.response.models.PersonData;
import com.lykkex.LykkeWallet.rest.internal.request.model.IdBaseAsset;
import com.lykkex.LykkeWallet.rest.trading.response.model.AssetPairData;
import com.lykkex.LykkeWallet.rest.internal.response.model.BaseAssetData;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingData;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingSignOrder;
import com.lykkex.LykkeWallet.rest.internal.response.model.SettingSignOrderData;
import com.lykkex.LykkeWallet.rest.login.request.model.AuthRequest;
import com.lykkex.LykkeWallet.rest.login.response.model.AuthModelData;
import com.lykkex.LykkeWallet.rest.login.response.model.PersonalData;
import com.lykkex.LykkeWallet.rest.pin.request.model.PinRequest;
import com.lykkex.LykkeWallet.rest.pin.response.model.SecurityData;
import com.lykkex.LykkeWallet.rest.registration.request.models.RegistrationModel;
import com.lykkex.LykkeWallet.rest.registration.response.models.AccountExistData;
import com.lykkex.LykkeWallet.rest.registration.response.models.RegistrationData;
import com.lykkex.LykkeWallet.rest.trading.response.model.RateData;
import com.lykkex.LykkeWallet.rest.wallet.request.models.CardModel;
import com.lykkex.LykkeWallet.rest.wallet.response.models.BankCardsData;
import com.lykkex.LykkeWallet.rest.wallet.response.models.LykkeWallerData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @GET("/api/Registration")
    Call<AuthModelData> getRegistrationData(
            @Header("Authorization") String authorization);

    @POST("/api/Auth")
    Call<AuthModelData> getAuth(@Body AuthRequest request);

    @GET("/api/CheckDocumentsToUpload")
    Call<CameraData> checkDocuments(@Header("Authorization") String authorization);

    @POST("/api/KycDocuments")
    Call<PersonData> kysDocuments(@Header("Authorization") String authorization,
            @Body CameraModel cameraModel);

    @TIMEOUT(10000)
    @GET("/api/KycStatus")
    Call<DocumentAnswerData> getKycStatus(@Header("Authorization") String authorization);

    @TIMEOUT(10000)
    @POST("/api/KycStatus")
    Call<PersonalData> kysDocuments(@Header("Authorization") String authorization);

    @POST ("/api/PinSecurity")
    Call<Error> postPinSecurite(@Header("Authorization")String authorization,
                                @Body PinRequest pin);

    @GET ("/api/PinSecurity")
    Call<SecurityData> signInPinSecurite(@Header("Authorization")String authorization,
                                         @Query("Pin") String pin);

    @GET("/api/LykkeWallet")
    Call<LykkeWallerData> getLykkeWallet(@Header("Authorization")String authorization);

    @GET("/api/AppSettings")
    Call<SettingData> getAppSettings(@Header("Authorization")String authorization);

    @GET("/api/BaseAsset")
    Call<BaseAssetData> getBaseAsset(@Header("Authorization")String authorization);

    @GET("/api/BaseAssets")
    Call<BaseAssetData> getBaseAssets(@Header("Authorization")String authorization);

    @POST("/api/BaseAsset")
    Call<BaseAssetData> postBaseAsset(@Header("Authorization")String authorization,
                                      @Body IdBaseAsset id);

    @POST("/api/BankCards")
    Call<BankCardsData> postBankCards(@Header("Authorization")String authorization,
                                      @Body CardModel model);

    @GET("api/PersonalData")
    Call<PersonData> getPersonalData(@Header("Authorization")String authorization);

    @POST("api/SettingSignOrder")
    Call<SettingSignOrderData> postSettingSignOrder(@Header("Authorization")String authorization,
                                                    @Body SettingSignOrder order);

    @GET("/api/AssetPairs")
    Call<AssetPairData> getAssetPairs(@Header("Authorization")String authorization);

    @GET("/api/AssetPairRates")
    Call<RateData> getAssetPairsRates(@Header("Authorization")String authorization);
}
