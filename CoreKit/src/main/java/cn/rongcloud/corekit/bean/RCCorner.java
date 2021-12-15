package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.utils.UiUtils;

/**
 * Created by gyn on 2021/11/16
 */
public class RCCorner implements Serializable {

    @SerializedName("topLeft")
    private int topLeft;
    @SerializedName("topRight")
    private int topRight;
    @SerializedName("bottomLeft")
    private int bottomLeft;
    @SerializedName("bottomRight")
    private int bottomRight;
    @SerializedName("radius")
    private int radius;

    public RCCorner() {
    }

    public RCCorner(int topLeft, int topRight, int bottomLeft, int bottomRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public int getTopLeft() {
        if (topLeft == 0) {
            return radius;
        }
        return topLeft;
    }

    public void setTopLeft(int topLeft) {
        this.topLeft = topLeft;
    }

    public int getTopRight() {
        if (topRight == 0) {
            return radius;
        }
        return topRight;
    }

    public void setTopRight(int topRight) {
        this.topRight = topRight;
    }

    public int getBottomLeft() {
        if (bottomLeft == 0) {
            return radius;
        }
        return bottomLeft;
    }

    public void setBottomLeft(int bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public int getBottomRight() {
        if (bottomRight == 0) {
            return radius;
        }
        return bottomRight;
    }

    public void setBottomRight(int bottomRight) {
        this.bottomRight = bottomRight;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float[] getRadiusArray() {
        return new float[]{UiUtils.dp2px(getTopLeft()), UiUtils.dp2px(getTopRight()), UiUtils.dp2px(getBottomLeft()), UiUtils.dp2px(getBottomRight())};
    }
}
