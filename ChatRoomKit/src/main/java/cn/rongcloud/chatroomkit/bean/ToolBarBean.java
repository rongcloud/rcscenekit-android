package cn.rongcloud.chatroomkit.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.rongcloud.corekit.annotation.KitBean;
import cn.rongcloud.corekit.bean.Argb;
import cn.rongcloud.corekit.bean.Insets;
import cn.rongcloud.corekit.bean.Size;

/**
 * Created by hugo on 2021/11/12
 */
@KitBean(parseKey = "ToolBar")
public class ToolBarBean implements Serializable {

    @SerializedName("backgroundColor")
    private Argb backgroundColor;
    @SerializedName("contentInsets")
    private Insets contentInsets;
    @SerializedName("chatButtonTitle")
    private String chatButtonTitle;
    @SerializedName("chatButtonSize")
    private Size chatButtonSize;
    @SerializedName("chatButtonInsets")
    private Insets chatButtonInsets;
    @SerializedName("chatButtonTextColor")
    private Argb chatButtonTextColor;
    @SerializedName("chatButtonTextSize")
    private Integer chatButtonTextSize;
    @SerializedName("chatButtonBackgroundColor")
    private Argb chatButtonBackgroundColor;
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

    public String getChatButtonTitle() {
        return chatButtonTitle;
    }

    public void setChatButtonTitle(String chatButtonTitle) {
        this.chatButtonTitle = chatButtonTitle;
    }

    public Size getChatButtonSize() {
        return chatButtonSize;
    }

    public void setChatButtonSize(Size chatButtonSize) {
        this.chatButtonSize = chatButtonSize;
    }

    public Insets getChatButtonInsets() {
        return chatButtonInsets;
    }

    public void setChatButtonInsets(Insets chatButtonInsets) {
        this.chatButtonInsets = chatButtonInsets;
    }

    public Argb getChatButtonTextColor() {
        return chatButtonTextColor;
    }

    public void setChatButtonTextColor(Argb chatButtonTextColor) {
        this.chatButtonTextColor = chatButtonTextColor;
    }

    public Integer getChatButtonTextSize() {
        return chatButtonTextSize;
    }

    public void setChatButtonTextSize(Integer chatButtonTextSize) {
        this.chatButtonTextSize = chatButtonTextSize;
    }

    public Argb getChatButtonBackgroundColor() {
        return chatButtonBackgroundColor;
    }

    public void setChatButtonBackgroundColor(Argb chatButtonBackgroundColor) {
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
