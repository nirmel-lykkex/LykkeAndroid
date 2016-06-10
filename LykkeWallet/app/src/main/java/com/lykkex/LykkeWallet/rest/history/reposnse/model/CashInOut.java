package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class CashInOut extends ItemHistory {

    @SerializedName("Amount")
    private BigDecimal amount;

    @SerializedName("IsRefund")
    private Boolean isRefund;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Boolean getRefund() {
        return isRefund;
    }

    public void setRefund(Boolean refund) {
        isRefund = refund;
    }
}
