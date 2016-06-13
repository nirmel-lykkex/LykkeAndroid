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

    @SerializedName("BlockChainHash")
    private String blockChainHash;

    public String getId() {
        return id;
    }

    public Date getDateTime() {
        if(dateTime != null) {
            dateTime += " GMT";

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS z");

            try {
                Date date = format.parse(dateTime);

                return date;
            } catch (ParseException e) {
            }
        }

        return null;
    }

    public String getFormatedDateTime() {
        SimpleDateFormat newFormat = new SimpleDateFormat("M/d/yy h:mm a");

        Date d = getDateTime();

        if(d == null) {
            return dateTime;
        } else {
            return newFormat.format(d);
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

    public String getBlockChainHash() {
        return blockChainHash;
    }

    public void setBlockChainHash(String blockChainHash) {
        this.blockChainHash = blockChainHash;
    }
}
