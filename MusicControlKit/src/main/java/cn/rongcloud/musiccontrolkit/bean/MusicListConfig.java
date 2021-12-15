package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCColor;

/**
 * Created by gyn on 2021/12/7
 */
public class MusicListConfig implements Serializable {
    @SerializedName("refreshEnable")
    private boolean refreshEnable;
    @SerializedName("loadMoreEnable")
    private boolean loadMoreEnable;
    @SerializedName("backgroundColor")
    private RCColor backgroundColor;
    @SerializedName("musicItem")
    private MusicItemConfig musicItem;

    public MusicItemConfig getMusicItem() {
        return musicItem;
    }

    public void setMusicItem(MusicItemConfig musicItem) {
        this.musicItem = musicItem;
    }

    public boolean isRefreshEnable() {
        return refreshEnable;
    }

    public void setRefreshEnable(boolean refreshEnable) {
        this.refreshEnable = refreshEnable;
    }

    public boolean isLoadMoreEnable() {
        return loadMoreEnable;
    }

    public void setLoadMoreEnable(boolean loadMoreEnable) {
        this.loadMoreEnable = loadMoreEnable;
    }

    public RCColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(RCColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
