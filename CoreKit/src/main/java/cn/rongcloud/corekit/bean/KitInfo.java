package cn.rongcloud.corekit.bean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by gyn on 2021/12/8
 */
public class KitInfo implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("kitId")
    private String kitId;
    @SerializedName("url")
    private String url;
    @SerializedName("md5")
    private String md5;
    @SerializedName("updateTime")
    private long updateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKitId() {
        return kitId;
    }

    public void setKitId(String kitId) {
        this.kitId = kitId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KitInfo kitInfo = (KitInfo) o;
        return Objects.equals(name, kitInfo.name) && Objects.equals(kitId, kitInfo.kitId) && Objects.equals(url, kitInfo.url) && Objects.equals(md5, kitInfo.md5) && Objects.equals(updateTime, kitInfo.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, kitId, url, md5, updateTime);
    }

    public boolean isValidate(String md5) {
        return TextUtils.equals(this.md5, md5);
    }
}
