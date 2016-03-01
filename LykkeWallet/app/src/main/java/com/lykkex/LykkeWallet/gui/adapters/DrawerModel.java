package com.lykkex.LykkeWallet.gui.adapters;

/**
 * Created by LIZA on 29.02.2016.
 */
public class DrawerModel {

    private String name;
    private int res;
    private int resActive;

    public DrawerModel(String name, int res, int resActive){
        this.name = name;
        this.res = res;
        this.resActive = resActive;
    }

    public int getResActive() {
        return resActive;
    }

    public void setResActive(int resActive) {
        this.resActive = resActive;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
