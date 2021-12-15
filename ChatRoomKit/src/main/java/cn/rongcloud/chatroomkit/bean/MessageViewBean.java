package cn.rongcloud.chatroomkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.annotation.KitBean;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCCorner;
import cn.rongcloud.corekit.bean.RCInsets;

/**
 * Created by gyn on 2021/11/16
 */
@KitBean(parseKey = "MessageView")
public class MessageViewBean implements Serializable {

    @SerializedName("contentInsets")
    private RCInsets contentInsets;
    @SerializedName("maxVisibleCount")
    private Integer maxVisibleCount;
    @SerializedName("defaultBubbleColor")
    private RCColor defaultBubbleColor;
    @SerializedName("bubbleInsets")
    private RCInsets bubbleInsets;
    @SerializedName("defaultBubbleCorner")
    private RCCorner defaultBubbleCorner;
    @SerializedName("bubbleSpace")
    private Integer bubbleSpace;
    @SerializedName("bubbleTextMaxLength")
    private Integer bubbleTextMaxLength;
    @SerializedName("defaultBubbleTextColor")
    private RCColor defaultBubbleTextColor;
    @SerializedName("voiceIconColor")
    private RCColor voiceIconColor;

    public RCInsets getContentInsets() {
        return contentInsets;
    }

    public void setContentInsets(RCInsets contentInsets) {
        this.contentInsets = contentInsets;
    }

    public Integer getMaxVisibleCount() {
        return maxVisibleCount;
    }

    public void setMaxVisibleCount(Integer maxVisibleCount) {
        this.maxVisibleCount = maxVisibleCount;
    }

    public RCColor getDefaultBubbleColor() {
        return defaultBubbleColor;
    }

    public void setDefaultBubbleColor(RCColor defaultBubbleColor) {
        this.defaultBubbleColor = defaultBubbleColor;
    }

    public RCInsets getBubbleInsets() {
        return bubbleInsets;
    }

    public void setBubbleInsets(RCInsets bubbleInsets) {
        this.bubbleInsets = bubbleInsets;
    }

    public RCCorner getDefaultBubbleCorner() {
        return defaultBubbleCorner;
    }

    public void setDefaultBubbleCorner(RCCorner defaultBubbleCorner) {
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

    public RCColor getDefaultBubbleTextColor() {
        return defaultBubbleTextColor;
    }

    public void setDefaultBubbleTextColor(RCColor defaultBubbleTextColor) {
        this.defaultBubbleTextColor = defaultBubbleTextColor;
    }

    public RCColor getVoiceIconColor() {
        return voiceIconColor;
    }

    public void setVoiceIconColor(RCColor voiceIconColor) {
        this.voiceIconColor = voiceIconColor;
    }
}
