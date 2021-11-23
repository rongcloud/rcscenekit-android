package cn.rongcloud.chatroomkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ActionButton implements Serializable {
    @SerializedName("icon")
    private String actionIcon;
    @SerializedName("localIcon")
    private int localIcon;
    @SerializedName("hasBadge")
    private boolean hasBadge;
    @SerializedName("extra")
    private String extra;
    @SerializedName("badgeNum")
    private int badgeNum;

    public String getActionIcon() {
        return actionIcon;
    }

    public void setActionIcon(String actionIcon) {
        this.actionIcon = actionIcon;
    }

    public int getLocalIcon() {
        return localIcon;
    }

    public void setLocalIcon(int localIcon) {
        this.localIcon = localIcon;
    }

    public boolean hasBadge() {
        return hasBadge;
    }

    public void setHasBadge(boolean hasBadge) {
        this.hasBadge = hasBadge;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public int getBadgeNum() {
        return badgeNum;
    }

    public void setBadgeNum(int badgeNum) {
        this.badgeNum = badgeNum;
    }
}