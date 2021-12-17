package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.utils.UiUtils;

/**
 * Created by gyn on 2021/11/15
 */
public class RCSize implements Serializable {

    @SerializedName("width")
    private int width;
    @SerializedName("height")
    private int height;
    @SerializedName("widthMode")
    private int widthMode;
    @SerializedName("heightMode")
    private int heightMode;

    public int getWidth() {
        return UiUtils.dp2px(width);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return UiUtils.dp2px(height);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidthMode() {
        return widthMode;
    }

    public void setWidthMode(int widthMode) {
        this.widthMode = widthMode;
    }

    public int getHeightMode() {
        return heightMode;
    }

    public void setHeightMode(int heightMode) {
        this.heightMode = heightMode;
    }
}
