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
    private boolean isSelfieSend = false;
    private boolean isCardIdeSend = false;
    private boolean isProofAddressSend = false;

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

    public boolean isProofAddressSend() {
        return isProofAddressSend;
    }

    public void setIsProofAddressSend(boolean isProofAddressSend) {
        this.isProofAddressSend = isProofAddressSend;
    }

    public void setPathSelfie(String pathSelfie) {
        this.pathSelfie = pathSelfie;
    }

    public boolean isSelfieSend() {
        return isSelfieSend;
    }

    public void setIsSelfieSend(boolean isSelfieSend) {
        this.isSelfieSend = isSelfieSend;
    }

    public boolean isCardIdeSend() {
        return isCardIdeSend;
    }

    public void setIsCardIdeSend(boolean isCardIdeSend) {
        this.isCardIdeSend = isCardIdeSend;
    }

}
