package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCFont;
import cn.rongcloud.corekit.bean.RCNode;

/**
 * Created by gyn on 2021/12/7
 */
public class MusicControlConfig implements Serializable {

    @SerializedName("backgroundColor")
    private RCNode<RCColor> backgroundColor;
    @SerializedName("normalColor")
    private RCNode<RCColor> normalColor;
    @SerializedName("tintColor")
    private RCNode<RCColor> tintColor;
    @SerializedName("thumbColor")
    private RCNode<RCColor> thumbColor;
    @SerializedName("textColor")
    private RCNode<RCColor> textColor;
    @SerializedName("font")
    private RCNode<RCFont> font;

    public RCColor getBackgroundColor() {
        return backgroundColor.getValue();
    }

    public RCColor getNormalColor() {
        return normalColor.getValue();
    }

    public RCColor getTintColor() {
        return tintColor.getValue();
    }

    public RCColor getTextColor() {
        return textColor.getValue();
    }

    public RCFont getFont() {
        return font.getValue();
    }

    public RCColor getThumbColor() {
        return thumbColor.getValue();
    }
}
