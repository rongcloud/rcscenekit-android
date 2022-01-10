package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.utils.UiUtils;

/**
 * @author gyn
 * @date 2021/12/13
 */
public class RCDrawable implements Serializable {
    @SerializedName("shape")
    private int shape;
    @SerializedName("color")
    private RCColor color;
    @SerializedName("strokeWidth")
    private int strokeWidth;
    @SerializedName("strokeColor")
    private RCColor strokeColor;
    @SerializedName("corner")
    private RCCorner corner;

    public int getShape() {
        return shape;
    }

    public RCColor getColor() {
        return color;
    }

    public int getStrokeWidthPx() {
        return UiUtils.dp2px(strokeWidth);
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public RCColor getStrokeColor() {
        return strokeColor;
    }

    public RCCorner getCorner() {
        return corner;
    }
}
