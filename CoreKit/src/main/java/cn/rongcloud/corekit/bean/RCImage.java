package cn.rongcloud.corekit.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hugo on 2021/12/3
 */
public class RCImage implements Serializable {
    @SerializedName("local")
    private String local;
    @SerializedName("remote")
    private String remote;

    public RCImage() {
    }

    public RCImage(String local, String remote) {
        this.local = local;
        this.remote = remote;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getRemote() {
        return remote;
    }

    public void setRemote(String remote) {
        this.remote = remote;
    }

    public String getUrl() {
        if (!TextUtils.isEmpty(remote)) {
            return remote;
        }
        return local;
    }
}
