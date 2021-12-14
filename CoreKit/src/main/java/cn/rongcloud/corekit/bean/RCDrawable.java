package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    public void setShape(int shape) {
        this.shape = shape;
    }

    public RCColor getColor() {
        return color;
    }

    public void setColor(RCColor color) {
        this.color = color;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public RCColor getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeColor(RCColor strokeColor) {
        this.strokeColor = strokeColor;
    }

    public RCCorner getCorner() {
        return corner;
    }

    public void setCorner(RCCorner corner) {
        this.corner = corner;
    }
}
