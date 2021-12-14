package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttribute;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/12/7
 */
public class MusicSearchConfig implements Serializable {
    @SerializedName("contentInsets")
    private RCInsets contentInsets;
    @SerializedName("searchSize")
    private RCSize searchSize;
    @SerializedName("textAttribute")
    private RCAttribute textAttribute;

    public RCInsets getContentInsets() {
        return contentInsets;
    }

    public void setContentInsets(RCInsets contentInsets) {
        this.contentInsets = contentInsets;
    }

    public RCSize getSearchSize() {
        return searchSize;
    }

    public void setSearchSize(RCSize searchSize) {
        this.searchSize = searchSize;
    }

    public RCAttribute getTextAttribute() {
        return textAttribute;
    }

    public void setTextAttribute(RCAttribute textAttribute) {
        this.textAttribute = textAttribute;
    }
}
