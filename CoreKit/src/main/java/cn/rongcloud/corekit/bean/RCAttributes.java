package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gyn on 2021/12/3
 */
public class RCAttributes implements Serializable {

    @SerializedName("textColor")
    private RCNode<RCColor> textColor;
    @SerializedName("font")
    private RCNode<RCFont> font;
    @SerializedName("text")
    private RCNode<String> text;
    @SerializedName("hintColor")
    private RCNode<RCColor> hintColor;
    @SerializedName("hintText")
    private RCNode<String> hintText;
    @SerializedName("background")
    private RCNode<RCColor> background;
    @SerializedName("corner")
    private RCNode<RCCorner> corner;
    @SerializedName("imageSelector")
    private RCNode<RCImageSelector> imageSelector;
    @SerializedName("colorSelector")
    private RCNode<RCColorSelector> colorSelector;
    @SerializedName("fontSelector")
    private RCNode<RCFontSelector> fontSelector;
    @SerializedName("backgroundSelector")
    private RCNode<RCColorSelector> backgroundSelector;
    @SerializedName("drawableSelector")
    private RCNode<RCDrawableSelector> drawableSelector;
    @SerializedName("size")
    private RCNode<RCSize> size;
    @SerializedName("insets")
    private RCNode<RCInsets> insets;
    @SerializedName("image")
    private RCNode<RCImage> image;
    @SerializedName("drawable")
    private RCNode<RCDrawable> drawable;

    public RCColor getTextColor() {
        return textColor == null ? null : textColor.getValue();
    }

    public String getText() {
        return text == null ? null : text.getValue();
    }

    public RCColor getHintColor() {
        return hintColor == null ? null : hintColor.getValue();
    }

    public String getHintText() {
        return hintText == null ? null : hintText.getValue();
    }

    public RCColor getBackground() {
        return background == null ? null : background.getValue();
    }

    public RCCorner getCorner() {
        return corner == null ? null : corner.getValue();
    }

    public RCImageSelector getImageSelector() {
        return imageSelector == null ? null : imageSelector.getValue();
    }

    public RCColorSelector getColorSelector() {
        return colorSelector == null ? null : colorSelector.getValue();
    }

    public RCSize getSize() {
        return size == null ? null : size.getValue();
    }

    public RCInsets getInsets() {
        return insets == null ? null : insets.getValue();
    }

    public RCImage getImage() {
        return image == null ? null : image.getValue();
    }

    public RCColorSelector getBackgroundSelector() {
        return backgroundSelector == null ? null : backgroundSelector.getValue();
    }

    public RCFontSelector getFontSelector() {
        return fontSelector == null ? null : fontSelector.getValue();
    }

    public RCFont getFont() {
        return font == null ? null : font.getValue();
    }

    public RCDrawableSelector getDrawableSelector() {
        return drawableSelector == null ? null : drawableSelector.getValue();
    }

    public RCDrawable getDrawable() {
        return drawable == null ? null : drawable.getValue();
    }

}
