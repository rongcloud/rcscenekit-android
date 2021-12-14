package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCFont;

/**
 * Created by gyn on 2021/12/7
 */
public class MusicControlConfig implements Serializable {

    @SerializedName("backgroundColor")
    private RCColor backgroundColor;
    @SerializedName("normalColor")
    private RCColor normalColor;
    @SerializedName("tintColor")
    private RCColor tintColor;
    @SerializedName("thumbColor")
    private RCColor thumbColor;
    @SerializedName("textColor")
    private RCColor textColor;
    @SerializedName("font")
    private RCFont font;

    public RCColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(RCColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public RCColor getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(RCColor normalColor) {
        this.normalColor = normalColor;
    }

    public RCColor getTintColor() {
        return tintColor;
    }

    public void setTintColor(RCColor tintColor) {
        this.tintColor = tintColor;
    }

    public RCColor getTextColor() {
        return textColor;
    }

    public void setTextColor(RCColor textColor) {
        this.textColor = textColor;
    }

    public RCFont getFont() {
        return font;
    }

    public void setFont(RCFont font) {
        this.font = font;
    }

    public RCColor getThumbColor() {
        return thumbColor;
    }

    public void setThumbColor(RCColor thumbColor) {
        this.thumbColor = thumbColor;
    }
}
