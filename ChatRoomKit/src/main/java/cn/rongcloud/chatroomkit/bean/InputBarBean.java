package cn.rongcloud.chatroomkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.annotation.KitBean;
import cn.rongcloud.corekit.bean.Argb;
import cn.rongcloud.corekit.bean.Insets;

/**
 * Created by hugo on 2021/11/12
 */
@KitBean(parseKey = "InputBar")
public class InputBarBean implements Serializable {
    @SerializedName("backgroundColor")
    private Argb backgroundColor;
    @SerializedName("contentInsets")
    private Insets contentInsets;
    @SerializedName("inputBackgroundColor")
    private Argb inputBackgroundColor;
    @SerializedName("inputCorner")
    private Integer inputCorner;
    @SerializedName("inputMinHeight")
    private Integer inputMinHeight;
    @SerializedName("inputMaxHeight")
    private Integer inputMaxHeight;
    @SerializedName("inputTextSize")
    private Integer inputTextSize;
    @SerializedName("inputTextColor")
    private Argb inputTextColor;
    @SerializedName("inputHint")
    private String inputHint;
    @SerializedName("inputHintColor")
    private Argb inputHintColor;
    @SerializedName("inputInsets")
    private Insets inputInsets;
    @SerializedName("emojiEnable")
    private Boolean emojiEnable;

    public Argb getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Argb backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Insets getContentInsets() {
        return contentInsets;
    }

    public void setContentInsets(Insets contentInsets) {
        this.contentInsets = contentInsets;
    }

    public Argb getInputBackgroundColor() {
        return inputBackgroundColor;
    }

    public void setInputBackgroundColor(Argb inputBackgroundColor) {
        this.inputBackgroundColor = inputBackgroundColor;
    }

    public Integer getInputCorner() {
        return inputCorner;
    }

    public void setInputCorner(Integer inputCorner) {
        this.inputCorner = inputCorner;
    }

    public Integer getInputMinHeight() {
        return inputMinHeight;
    }

    public void setInputMinHeight(Integer inputMinHeight) {
        this.inputMinHeight = inputMinHeight;
    }

    public Integer getInputMaxHeight() {
        return inputMaxHeight;
    }

    public void setInputMaxHeight(Integer inputMaxHeight) {
        this.inputMaxHeight = inputMaxHeight;
    }

    public Integer getInputTextSize() {
        return inputTextSize;
    }

    public void setInputTextSize(Integer inputTextSize) {
        this.inputTextSize = inputTextSize;
    }

    public Argb getInputTextColor() {
        return inputTextColor;
    }

    public void setInputTextColor(Argb inputTextColor) {
        this.inputTextColor = inputTextColor;
    }

    public String getInputHint() {
        return inputHint;
    }

    public void setInputHint(String inputHint) {
        this.inputHint = inputHint;
    }

    public Argb getInputHintColor() {
        return inputHintColor;
    }

    public void setInputHintColor(Argb inputHintColor) {
        this.inputHintColor = inputHintColor;
    }

    public Insets getInputInsets() {
        return inputInsets;
    }

    public void setInputInsets(Insets inputInsets) {
        this.inputInsets = inputInsets;
    }

    public Boolean getEmojiEnable() {
        return emojiEnable;
    }

    public void setEmojiEnable(Boolean emojiEnable) {
        this.emojiEnable = emojiEnable;
    }

}
