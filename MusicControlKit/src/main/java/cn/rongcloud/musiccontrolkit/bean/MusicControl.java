package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gyn on 2021/11/25
 */
public class MusicControl implements Serializable {
    @SerializedName("localVolume")
    private int localVolume = 100;
    @SerializedName("remoteVolume")
    private int remoteVolume = 100;
    @SerializedName("micVolume")
    private int micVolume = 100;
    @SerializedName("earsBackEnable")
    private boolean earsBackEnable = false;

    public int getLocalVolume() {
        return localVolume;
    }

    public void setLocalVolume(int localVolume) {
        this.localVolume = localVolume;
    }

    public int getRemoteVolume() {
        return remoteVolume;
    }

    public void setRemoteVolume(int remoteVolume) {
        this.remoteVolume = remoteVolume;
    }

    public int getMicVolume() {
        return micVolume;
    }

    public void setMicVolume(int micVolume) {
        this.micVolume = micVolume;
    }

    public boolean isEarsBackEnable() {
        return earsBackEnable;
    }

    public void setEarsBackEnable(boolean earsBackEnable) {
        this.earsBackEnable = earsBackEnable;
    }
}
