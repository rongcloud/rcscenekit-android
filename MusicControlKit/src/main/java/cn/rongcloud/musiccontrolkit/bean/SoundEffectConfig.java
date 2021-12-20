package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttributes;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCCorner;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCNode;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/12/7
 */
public class SoundEffectConfig implements Serializable {
    @SerializedName("blurEnable")
    private RCNode<Boolean> blurEnable;
    @SerializedName("size")
    private RCNode<RCSize> size;
    @SerializedName("backgroundColor")
    private RCNode<RCColor> backgroundColor;
    @SerializedName("effectAttributes")
    private RCNode<RCAttributes> effectAttributes;
    @SerializedName("itemSpace")
    private RCNode<Integer> itemSpace;
    @SerializedName("contentInsets")
    private RCNode<RCInsets> contentInsets;
    @SerializedName("corner")
    private RCNode<RCCorner> corner;

    public boolean isBlurEnable() {
        return blurEnable.getValue();
    }

    public RCColor getBackgroundColor() {
        return backgroundColor.getValue();
    }

    public RCAttributes getEffectAttributes() {
        return effectAttributes.getValue();
    }

    public RCSize getSize() {
        return size.getValue();
    }

    public int getItemSpace() {
        return itemSpace.getValue();
    }

    public RCInsets getContentInsets() {
        return contentInsets.getValue();
    }

    public RCCorner getCorner() {
        return corner.getValue();
    }

}
