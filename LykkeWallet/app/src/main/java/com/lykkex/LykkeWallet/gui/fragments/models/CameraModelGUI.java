package com.lykkex.LykkeWallet.gui.fragments.models;

import com.lykkex.LykkeWallet.rest.camera.request.models.CameraModel;
import com.lykkex.LykkeWallet.rest.camera.response.models.CameraResult;

import java.io.Serializable;

/**
 * Created by e.kazimirova on 14.02.2016.
 */
public class CameraModelGUI extends CameraResult implements Serializable {

    private boolean isFront;
    private boolean isFile;
    private boolean isDone;
    private String pathSelfie = "";
    private String pathIdCard = "";
    private String pathProofAddress = "";
    private boolean isSelfieSend = false;
    private boolean isCardIdeSend = false;
    private boolean isProofAddressSend = false;
    private boolean isSelfieFront = true;
    private boolean isCardIdFront = false;
    private boolean isProofAddressFront = false;
    private boolean isSelfieFile = true;

    public boolean isCardIdFile() {
        return isCardIdFile;
    }

    public void setCardIdFile(boolean cardIdFile) {
        isCardIdFile = cardIdFile;
    }

    public boolean isSelfieFile() {
        return isSelfieFile;
    }

    public void setSelfieFile(boolean selfieFile) {
        isSelfieFile = selfieFile;
    }

    public boolean isProofAddressFront() {
        return isProofAddressFront;
    }

    public void setProofAddressFront(boolean proofAddressFront) {
        isProofAddressFront = proofAddressFront;
    }

    public boolean isCardIdFront() {
        return isCardIdFront;
    }

    public void setCardIdFront(boolean cardIdFront) {
        isCardIdFront = cardIdFront;
    }

    public boolean isSelfieFront() {
        return isSelfieFront;
    }

    public void setSelfieFront(boolean selfieFront) {
        isSelfieFront = selfieFront;
    }

    public boolean isProofAddressFile() {
        return isProofAddressFile;
    }

    public void setProofAddressFile(boolean proofAddressFile) {
        isProofAddressFile = proofAddressFile;
    }

    private boolean isCardIdFile = false;
    private boolean isProofAddressFile = false;

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
