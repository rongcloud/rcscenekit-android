package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hugo on 2021/11/15
 */
public class RCInsets implements Serializable {

    @SerializedName("left")
    private Integer left;
    @SerializedName("top")
    private Integer top;
    @SerializedName("right")
    private Integer right;
    @SerializedName("bottom")
    private Integer bottom;

    public Integer getLeft() {
        return left;
    }

    public void setLeft(Integer left) {
        this.left = left;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Integer getRight() {
        return right;
    }

    public void setRight(Integer right) {
        this.right = right;
    }

    public Integer getBottom() {
        return bottom;
    }

    public void setBottom(Integer bottom) {
        this.bottom = bottom;
    }

    public void reverseH() {
        int l = left;
        left = right;
        right = l;
    }

    public void reverseV() {
        int t = top;
        top = bottom;
        bottom = t;
    }
}
