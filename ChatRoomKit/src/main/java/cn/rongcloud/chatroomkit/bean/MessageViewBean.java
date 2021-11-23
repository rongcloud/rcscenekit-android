package cn.rongcloud.chatroomkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.annotation.KitBean;
import cn.rongcloud.corekit.bean.Argb;
import cn.rongcloud.corekit.bean.Corner;
import cn.rongcloud.corekit.bean.Insets;

/**
 * Created by hugo on 2021/11/16
 */
@KitBean(parseKey = "MessageView")
public class MessageViewBean implements Serializable {

    @SerializedName("contentInsets")
    private Insets contentInsets;
    @SerializedName("maxVisibleCount")
    private Integer maxVisibleCount;
    @SerializedName("defaultBubbleColor")
    private Argb defaultBubbleColor;
    @SerializedName("bubbleInsets")
    private Insets bubbleInsets;
    @SerializedName("defaultBubbleCorner")
    private Corner defaultBubbleCorner;
    @SerializedName("bubbleSpace")
    private Integer bubbleSpace;
    @SerializedName("bubbleTextMaxLength")
    private Integer bubbleTextMaxLength;
    @SerializedName("defaultBubbleTextColor")
    private Argb defaultBubbleTextColor;
    @SerializedName("voiceIconColor")
    private Argb voiceIconColor;

    public Insets getContentInsets() {
        return contentInsets;
    }

    public void setContentInsets(Insets contentInsets) {
        this.contentInsets = contentInsets;
    }

    public Integer getMaxVisibleCount() {
        return maxVisibleCount;
    }

    public void setMaxVisibleCount(Integer maxVisibleCount) {
        this.maxVisibleCount = maxVisibleCount;
    }

    public Argb getDefaultBubbleColor() {
        return defaultBubbleColor;
    }

    public void setDefaultBubbleColor(Argb defaultBubbleColor) {
        this.defaultBubbleColor = defaultBubbleColor;
    }

    public Insets getBubbleInsets() {
        return bubbleInsets;
    }

    public void setBubbleInsets(Insets bubbleInsets) {
        this.bubbleInsets = bubbleInsets;
    }

    public Corner getDefaultBubbleCorner() {
        return defaultBubbleCorner;
    }

    public void setDefaultBubbleCorner(Corner defaultBubbleCorner) {
        this.defaultBubbleCorner = defaultBubbleCorner;
    }

    public Integer getBubbleSpace() {
        return bubbleSpace;
    }

    public void setBubbleSpace(Integer bubbleSpace) {
        this.bubbleSpace = bubbleSpace;
    }

    public Integer getBubbleTextMaxLength() {
        return bubbleTextMaxLength;
    }

    public void setBubbleTextMaxLength(Integer bubbleTextMaxLength) {
        this.bubbleTextMaxLength = bubbleTextMaxLength;
    }

    public Argb getDefaultBubbleTextColor() {
        return defaultBubbleTextColor;
    }

    public void setDefaultBubbleTextColor(Argb defaultBubbleTextColor) {
        this.defaultBubbleTextColor = defaultBubbleTextColor;
    }

    public Argb getVoiceIconColor() {
        return voiceIconColor;
    }

    public void setVoiceIconColor(Argb voiceIconColor) {
        this.voiceIconColor = voiceIconColor;
    }
}
