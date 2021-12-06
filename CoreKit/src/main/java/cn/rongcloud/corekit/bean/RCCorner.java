package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hugo on 2021/11/16
 */
public class RCCorner implements Serializable {

    @SerializedName("topLeft")
    private Integer topLeft;
    @SerializedName("topRight")
    private Integer topRight;
    @SerializedName("bottomLeft")
    private Integer bottomLeft;
    @SerializedName("bottomRight")
    private Integer bottomRight;

    public RCCorner() {
    }

    public RCCorner(Integer topLeft, Integer topRight, Integer bottomLeft, Integer bottomRight) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
    }

    public Integer getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Integer topLeft) {
        this.topLeft = topLeft;
    }

    public Integer getTopRight() {
        return topRight;
    }

    public void setTopRight(Integer topRight) {
        this.topRight = topRight;
    }

    public Integer getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(Integer bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public Integer getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Integer bottomRight) {
        this.bottomRight = bottomRight;
    }

    public float[] getRadius() {
        return new float[]{topLeft, topRight, bottomRight, bottomLeft};
    }
}
