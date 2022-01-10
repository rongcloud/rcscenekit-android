package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.utils.UiUtils;

/**
 * Created by gyn on 2021/11/15
 */
public class RCInsets implements Serializable {
    @SerializedName("left")
    private int left;
    @SerializedName("top")
    private int top;
    @SerializedName("right")
    private int right;
    @SerializedName("bottom")
    private int bottom;

    public int getLeftPx() {
        return UiUtils.dp2px(left);
    }

    public int getTopPx() {
        return UiUtils.dp2px(top);
    }

    public int getRightPx() {
        return UiUtils.dp2px(right);
    }

    public int getBottomPx() {
        return UiUtils.dp2px(bottom);
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setRight(int right) {
        this.right = right;
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
