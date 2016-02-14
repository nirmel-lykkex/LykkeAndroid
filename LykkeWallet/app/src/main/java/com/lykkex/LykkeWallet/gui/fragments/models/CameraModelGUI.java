package com.lykkex.LykkeWallet.gui.fragments.models;

import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;

/**
 * Created by e.kazimirova on 14.02.2016.
 */
public class CameraModelGUI extends CameraResult {

    private boolean isFront;
    private boolean isFile;
    private boolean isDone;
    private String pathSelfie;
    private String pathIdCard;
    private String pathProofAddress;

    public String getPathSelfie() {
        return pathSelfie;
    }

    public void setPathSeldie(String path) {
        this.pathSelfie = path;
    }

    public String getPathIdCard() {
        return pathIdCard;
    }

    public void setPathIdCard(String path) {
        this.pathIdCard = path;
    }

    public String getPathProofAddress() {
        return pathProofAddress;
    }

    public void setPathProofAddress(String path) {
        this.pathProofAddress = path;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public boolean isFile() {
        return isFile;
    }

    public void setIsFile(boolean isFile) {
        this.isFile = isFile;
    }

    public boolean isFront() {
        return isFront;
    }

    public void setIsFront(boolean isFront) {
        this.isFront = isFront;
    }

}
