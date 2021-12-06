package cn.rongcloud.chatroomkit.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.rongcloud.corekit.annotation.KitBean;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by hugo on 2021/11/12
 */
@KitBean(parseKey = "ToolBar")
public class ToolBarBean implements Serializable {

    @SerializedName("backgroundColor")
    private RCColor backgroundColor;
    @SerializedName("contentInsets")
    private RCInsets contentInsets;
    @SerializedName("chatButtonTitle")
    private String chatButtonTitle;
    @SerializedName("chatButtonSize")
    private RCSize chatButtonSize;
    @SerializedName("chatButtonInsets")
    private RCInsets chatButtonInsets;
    @SerializedName("chatButtonTextColor")
    private RCColor chatButtonTextColor;
    @SerializedName("chatButtonTextSize")
    private Integer chatButtonTextSize;
    @SerializedName("chatButtonBackgroundColor")
    private RCColor chatButtonBackgroundColor;
    @SerializedName("chatButtonBackgroundCorner")
    private Integer chatButtonBackgroundCorner;
    @SerializedName("recordButtonEnable")
    private Boolean recordButtonEnable;
    @SerializedName("recordQuality")
    private Integer recordQuality;
    @SerializedName("recordPosition")
    private Integer recordPosition;
    @SerializedName("recordMaxDuration")
    private Integer recordMaxDuration;
    @SerializedName("buttonArray")
    private List<ActionButton> buttonArray;

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

    public String getChatButtonTitle() {
        return chatButtonTitle;
    }

    public void setChatButtonTitle(String chatButtonTitle) {
        this.chatButtonTitle = chatButtonTitle;
    }

    public RCSize getChatButtonSize() {
        return chatButtonSize;
    }

    public void setChatButtonSize(RCSize chatButtonSize) {
        this.chatButtonSize = chatButtonSize;
    }

    public RCInsets getChatButtonInsets() {
        return chatButtonInsets;
    }

    public void setChatButtonInsets(RCInsets chatButtonInsets) {
        this.chatButtonInsets = chatButtonInsets;
    }

    public RCColor getChatButtonTextColor() {
        return chatButtonTextColor;
    }

    public void setChatButtonTextColor(RCColor chatButtonTextColor) {
        this.chatButtonTextColor = chatButtonTextColor;
    }

    public Integer getChatButtonTextSize() {
        return chatButtonTextSize;
    }

    public void setChatButtonTextSize(Integer chatButtonTextSize) {
        this.chatButtonTextSize = chatButtonTextSize;
    }

    public RCColor getChatButtonBackgroundColor() {
        return chatButtonBackgroundColor;
    }

    public void setChatButtonBackgroundColor(RCColor chatButtonBackgroundColor) {
        this.chatButtonBackgroundColor = chatButtonBackgroundColor;
    }

    public Integer getChatButtonBackgroundCorner() {
        return chatButtonBackgroundCorner;
    }

    public void setChatButtonBackgroundCorner(Integer chatButtonBackgroundCorner) {
        this.chatButtonBackgroundCorner = chatButtonBackgroundCorner;
    }

    public Boolean getRecordButtonEnable() {
        return recordButtonEnable;
    }

    public void setRecordButtonEnable(Boolean recordButtonEnable) {
        this.recordButtonEnable = recordButtonEnable;
    }

    public Integer getRecordQuality() {
        return recordQuality;
    }

    public void setRecordQuality(Integer recordQuality) {
        this.recordQuality = recordQuality;
    }

    public Integer getRecordPosition() {
        return recordPosition;
    }

    public void setRecordPosition(Integer recordPosition) {
        this.recordPosition = recordPosition;
    }

    public Integer getRecordMaxDuration() {
        return recordMaxDuration;
    }

    public void setRecordMaxDuration(Integer recordMaxDuration) {
        this.recordMaxDuration = recordMaxDuration;
    }

    public List<ActionButton> getActionArray() {
        return buttonArray;
    }

    public void setActionArray(List<ActionButton> actionButtonArray) {
        this.buttonArray = actionButtonArray;
    }


}
