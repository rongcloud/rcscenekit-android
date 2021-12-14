package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttribute;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCCorner;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/12/7
 */
public class SoundEffectConfig implements Serializable {
    @SerializedName("blurEnable")
    private boolean blurEnable;
    @SerializedName("size")
    private RCSize size;
    @SerializedName("backgroundColor")
    private RCColor backgroundColor;
    @SerializedName("effectAttribute")
    private RCAttribute effectAttribute;
    @SerializedName("itemSpace")
    private int itemSpace;
    @SerializedName("contentInsets")
    private RCInsets contentInsets;
    @SerializedName("corner")
    private RCCorner corner;

    public boolean isBlurEnable() {
        return blurEnable;
    }

    public void setBlurEnable(boolean blurEnable) {
        this.blurEnable = blurEnable;
    }

    public RCColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(RCColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public RCAttribute getEffectAttribute() {
        return effectAttribute;
    }

    public void setEffectAttribute(RCAttribute effectAttribute) {
        this.effectAttribute = effectAttribute;
    }

    public RCSize getSize() {
        return size;
    }

    public void setSize(RCSize size) {
        this.size = size;
    }

    public int getItemSpace() {
        return itemSpace;
    }

    public void setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
    }

    public RCInsets getContentInsets() {
        return contentInsets;
    }

    public void setContentInsets(RCInsets contentInsets) {
        this.contentInsets = contentInsets;
    }

    public RCCorner getCorner() {
        return corner;
    }

    public void setCorner(RCCorner corner) {
        this.corner = corner;
    }
}
