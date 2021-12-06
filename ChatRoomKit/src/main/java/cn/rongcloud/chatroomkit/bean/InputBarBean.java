package cn.rongcloud.chatroomkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.annotation.KitBean;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCInsets;

/**
 * Created by hugo on 2021/11/12
 */
@KitBean(parseKey = "InputBar")
public class InputBarBean implements Serializable {
    @SerializedName("backgroundColor")
    private RCColor backgroundColor;
    @SerializedName("contentInsets")
    private RCInsets contentInsets;
    @SerializedName("inputBackgroundColor")
    private RCColor inputBackgroundColor;
    @SerializedName("inputCorner")
    private Integer inputCorner;
    @SerializedName("inputMinHeight")
    private Integer inputMinHeight;
    @SerializedName("inputMaxHeight")
    private Integer inputMaxHeight;
    @SerializedName("inputTextSize")
    private Integer inputTextSize;
    @SerializedName("inputTextColor")
    private RCColor inputTextColor;
    @SerializedName("inputHint")
    private String inputHint;
    @SerializedName("inputHintColor")
    private RCColor inputHintColor;
    @SerializedName("inputInsets")
    private RCInsets inputInsets;
    @SerializedName("emojiEnable")
    private Boolean emojiEnable;

    public RCColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(RCColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public RCInsets getContentInsets() {
        return contentInsets;
    }

    public void setContentInsets(RCInsets contentInsets) {
        this.contentInsets = contentInsets;
    }

    public RCColor getInputBackgroundColor() {
        return inputBackgroundColor;
    }

    public void setInputBackgroundColor(RCColor inputBackgroundColor) {
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

    public RCColor getInputTextColor() {
        return inputTextColor;
    }

    public void setInputTextColor(RCColor inputTextColor) {
        this.inputTextColor = inputTextColor;
    }

    public String getInputHint() {
        return inputHint;
    }

    public void setInputHint(String inputHint) {
        this.inputHint = inputHint;
    }

    public RCColor getInputHintColor() {
        return inputHintColor;
    }

    public void setInputHintColor(RCColor inputHintColor) {
        this.inputHintColor = inputHintColor;
    }

    public RCInsets getInputInsets() {
        return inputInsets;
    }

    public void setInputInsets(RCInsets inputInsets) {
        this.inputInsets = inputInsets;
    }

    public Boolean getEmojiEnable() {
        return emojiEnable;
    }

    public void setEmojiEnable(Boolean emojiEnable) {
        this.emojiEnable = emojiEnable;
    }

}
