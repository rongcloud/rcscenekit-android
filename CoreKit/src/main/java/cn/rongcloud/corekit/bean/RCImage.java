package cn.rongcloud.corekit.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;

/**
 * Created by gyn on 2021/12/3
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

    public String getRemote() {
        return remote;
    }

    public String getUrl(String assetsPath) {
        if (!TextUtils.isEmpty(remote)) {
            return remote;
        }
        return assetsPath + File.separator + local;
    }
}
