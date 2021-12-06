package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hugo on 2021/12/3
 */
class RCAttribute implements Serializable {

    @SerializedName("textColor")
    private RCColor textColor;
    @SerializedName("textSize")
    private Integer textSize;
    @SerializedName("text")
    private String text;
    @SerializedName("hintColor")
    private RCColor hintColor;
    @SerializedName("hintText")
    private String hintText;
    @SerializedName("background")
    private RCColor background;
    @SerializedName("corner")
    private RCCorner corner;
    @SerializedName("imageSelector")
    private RCImageSelector imageSelector;
    @SerializedName("colorSelector")
    private RCColorSelector colorSelector;
    @SerializedName("size")
    private RCSize size;
    @SerializedName("insets")
    private RCInsets insets;
    @SerializedName("image")
    private RCImage image;

    public RCColor getTextColor() {
        return textColor;
    }

    public void setTextColor(RCColor textColor) {
        this.textColor = textColor;
    }

    public Integer getTextSize() {
        return textSize;
    }

    public void setTextSize(Integer textSize) {
        this.textSize = textSize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RCColor getHintColor() {
        return hintColor;
    }

    public void setHintColor(RCColor hintColor) {
        this.hintColor = hintColor;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }

    public RCColor getBackground() {
        return background;
    }

    public void setBackground(RCColor background) {
        this.background = background;
    }

    public RCCorner getCorner() {
        return corner;
    }

    public void setCorner(RCCorner corner) {
        this.corner = corner;
    }

    public RCImageSelector getImageSelector() {
        return imageSelector;
    }

    public void setImageSelector(RCImageSelector imageSelector) {
        this.imageSelector = imageSelector;
    }

    public RCColorSelector getColorSelector() {
        return colorSelector;
    }

    public void setColorSelector(RCColorSelector colorSelector) {
        this.colorSelector = colorSelector;
    }

    public RCSize getSize() {
        return size;
    }

    public void setSize(RCSize size) {
        this.size = size;
    }

    public RCInsets getInsets() {
        return insets;
    }

    public void setInsets(RCInsets insets) {
        this.insets = insets;
    }

    public RCImage getImage() {
        return image;
    }

    public void setImage(RCImage image) {
        this.image = image;
    }
}
