package com.lykkex.LykkeWallet.rest.trading.response.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by e.kazimirova on 24.03.2016.
 */
public class OrderResult implements Serializable{

    @SerializedName("Order")
    private Order order;

    public Order getOrder() {
        return order;
    }
}
