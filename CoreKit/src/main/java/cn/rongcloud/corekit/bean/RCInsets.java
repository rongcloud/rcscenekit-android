package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gyn on 2021/11/15
 */
public class RCInsets implements Serializable {
    @SerializedName("insets")
    private int insets;
    @SerializedName("left")
    private int left;
    @SerializedName("top")
    private int top;
    @SerializedName("right")
    private int right;
    @SerializedName("bottom")
    private int bottom;

    public int getInsets() {
        return insets;
    }

    public void setInsets(int insets) {
        this.insets = insets;
    }

    public int getLeft() {
        if (left == 0) {
            return insets;
        }
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        if (top == 0) {
            return insets;
        }
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getRight() {
        if (right == 0) {
            return insets;
        }
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        if (bottom == 0) {
            return insets;
        }
        return bottom;
    }

    public void setBottom(int bottom) {
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
