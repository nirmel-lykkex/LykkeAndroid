package com.lykkex.LykkeWallet.gui.utils;

/**
 * Created by e.kazimirova on 09.02.2016.
 */
public interface Constants {

    int ERROR_401 = 401;
    int ERROR_500 = 500;
    int MIN_COUNT_SYMBOL = 1;
    int CVV_COUNT = 3;
    int COUNT_MONTH = 5;
    int COUNT_CARD_NUMBER = 19;
    int MIN_COUNT_SYMBOL_PASSWORD = 6;
    int FILE_SELECT_CODE = 0;
    long DELAY_15000 = 15000L;
    long DELAY_5000 = 5000L;
    long DELAY_500 = 500L;

    String EXTRA_KYS_STATUS  = "extra_kys_status";
    String EXTRA_PIN_STATUS  = "extra_pin_status";
    String EXTRA_EMAIL = "extra_email";
    String EXTRA_AUTH_REQUEST = "extra_auth";
    String EXTRA_CAMERA_DATA = "extra_camera_data";
    String EXTRA_CAMERA_MODEL_GUI = "extra_camera_model_gui";
    String EXTRA_ASSET_NAME = "extra_asset_name";
    String EXTRA_ASSET_ID = "extra_asset_id";
    String PART_AUTHORIZATION = "Bearer ";
    String JPG = "jpg";

    String SETUP_URL_PAYMENT="?Email=%1$s&AssetId=%2$s";

}
