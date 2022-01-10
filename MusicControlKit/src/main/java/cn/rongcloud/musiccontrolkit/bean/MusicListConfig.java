package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCNode;

/**
 * Created by gyn on 2021/12/7
 */
public class MusicListConfig implements Serializable {
    @SerializedName("refreshEnable")
    private RCNode<Boolean> refreshEnable;
    @SerializedName("loadMoreEnable")
    private RCNode<Boolean> loadMoreEnable;
    @SerializedName("backgroundColor")
    private RCNode<RCColor> backgroundColor;
    @SerializedName("musicItem")
    private RCNode<MusicItemConfig> musicItem;

    public MusicItemConfig getMusicItem() {
        return musicItem.getValue();
    }

    public boolean isRefreshEnable() {
        return refreshEnable.getValue();
    }

    public boolean isLoadMoreEnable() {
        return loadMoreEnable.getValue();
    }

    public RCColor getBackgroundColor() {
        return backgroundColor.getValue();
    }
}
