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

    private transient float[] radiusArray;

    public RCCorner() {
    }

    public RCCorner(int topLeft, int topRight, int bottomLeft, int bottomRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public int getTopLeftPx() {
        return UiUtils.dp2px(topLeft);
    }

    public int getTopRightPx() {
        return UiUtils.dp2px(topRight);
    }

    public int getBottomLeftPx() {
        return UiUtils.dp2px(bottomLeft);
    }

    public int getBottomRightPx() {
        return UiUtils.dp2px(bottomRight);
    }

    public int getTopLeft() {
        return topLeft;
    }

    public int getTopRight() {
        return topRight;
    }

    public int getBottomLeft() {
        return bottomLeft;
    }

    public int getBottomRight() {
        return bottomRight;
    }

    public float[] getRadiusArray() {
        if (radiusArray == null) {
            radiusArray = new float[]{getTopLeftPx(), getTopRightPx(), getBottomLeftPx(), getBottomRightPx()};
        }
        return radiusArray;
    }
}
