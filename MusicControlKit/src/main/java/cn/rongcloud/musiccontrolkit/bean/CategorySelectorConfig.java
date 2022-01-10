package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttributes;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCNode;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/12/7
 */
public class CategorySelectorConfig implements Serializable {
    @SerializedName("size")
    private RCNode<RCSize> size;
    @SerializedName("backgroundColor")
    private RCNode<RCColor> backgroundColor;
    @SerializedName("showIndicator")
    private RCNode<Boolean> showIndicator;
    @SerializedName("indicatorSize")
    private RCNode<RCSize> indicatorSize;
    @SerializedName("indicatorColor")
    private RCNode<RCColor> indicatorColor;
    @SerializedName("labelAttributes")
    private RCNode<RCAttributes> labelAttributes;

    public RCSize getSize() {
        return size.getValue();
    }

    public RCColor getBackgroundColor() {
        return backgroundColor.getValue();
    }


    public Boolean getShowIndicator() {
        return showIndicator.getValue();
    }

    public RCSize getIndicatorSize() {
        return indicatorSize.getValue();
    }

    public RCAttributes getLabelAttributes() {
        return labelAttributes.getValue();
    }


    public RCColor getIndicatorColor() {
        return indicatorColor.getValue();
    }
}
