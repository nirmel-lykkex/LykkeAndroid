package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by e.kazimirova on 29.03.2016.
 */
public class ItemHistory implements Serializable{

    @SerializedName("Id")
    private String id;

    @SerializedName("DateTime")
    private String dateTime;

    @SerializedName("Asset")
    private String asset;

    @SerializedName("IconId")
    private String iconId;

    @SerializedName("Volume")
    private BigDecimal volume;

    @SerializedName("BlockChainHash")
    private String blockChainHash;

    public String getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getFormatedDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newFormat = new SimpleDateFormat("M/d/yy, h:mm a");

        try {
            Date date = format.parse(dateTime);

            return newFormat.format(date);
        } catch (ParseException e) {
            return dateTime;
        }
    }

    public String getAsset() {
        return asset;
    }

    public String getIconId() {
        return iconId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getBlockChainHash() {
        return blockChainHash;
    }

    public void setBlockChainHash(String blockChainHash) {
        this.blockChainHash = blockChainHash;
    }
}
