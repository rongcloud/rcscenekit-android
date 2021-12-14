package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttribute;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/12/7
 */
public class CategorySelectorConfig implements Serializable {
    @SerializedName("size")
    private RCSize size;
    @SerializedName("backgroundColor")
    private RCColor backgroundColor;
    @SerializedName("showIndicator")
    private Boolean showIndicator;
    @SerializedName("indicatorSize")
    private RCSize indicatorSize;
    @SerializedName("indicatorColor")
    private RCColor indicatorColor;
    @SerializedName("labelAttributes")
    private RCAttribute labelAttributes;

    public RCSize getSize() {
        return size;
    }

    public void setSize(RCSize size) {
        this.size = size;
    }

    public RCColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(RCColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Boolean getShowIndicator() {
        return showIndicator;
    }

    public void setShowIndicator(Boolean showIndicator) {
        this.showIndicator = showIndicator;
    }

    public RCSize getIndicatorSize() {
        return indicatorSize;
    }

    public void setIndicatorSize(RCSize indicatorSize) {
        this.indicatorSize = indicatorSize;
    }

    public RCAttribute getLabelAttributes() {
        return labelAttributes;
    }

    public void setLabelAttributes(RCAttribute labelAttributes) {
        this.labelAttributes = labelAttributes;
    }

    public RCColor getIndicatorColor() {
        return indicatorColor;
    }

    public void setIndicatorColor(RCColor indicatorColor) {
        this.indicatorColor = indicatorColor;
    }
}
