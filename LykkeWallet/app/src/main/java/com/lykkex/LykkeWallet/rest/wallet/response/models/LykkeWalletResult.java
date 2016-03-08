package com.lykkex.LykkeWallet.rest.wallet.response.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by LIZA on 01.03.2016.
 */
public class LykkeWalletResult {

    @SerializedName("Lykke")
    private LykkeWallet lykke;

    @SerializedName("BankCards")
    private BankCards[] bankCardses;

    public BankCards[] getBankCardses() {
        return bankCardses;
    }

    public void setBankCardses(BankCards[] bankCardses) {
        this.bankCardses = bankCardses;
    }

    public LykkeWallet getLykke() {
        return lykke;
    }

    public void setLykke(LykkeWallet lykke) {
        this.lykke = lykke;
    }
}