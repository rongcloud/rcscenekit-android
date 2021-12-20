package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCNode;

/**
 * @author gyn
 * @date 2021/12/10
 */
public class MusicAddConfig {
    @SerializedName("refreshEnable")
    private RCNode<Boolean> refreshEnable;
    @SerializedName("loadMoreEnable")
    private RCNode<Boolean> loadMoreEnable;
    @SerializedName("backgroundColor")
    private RCNode<RCColor> backgroundColor;
    @SerializedName("musicSearch")
    private RCNode<MusicSearchConfig> musicSearch;
    @SerializedName("categorySelector")
    private RCNode<CategorySelectorConfig> categorySelector;
    @SerializedName("musicItem")
    private RCNode<MusicItemConfig> musicItem;
    @SerializedName("uploadMusicEnable")
    private RCNode<Boolean> uploadMusicEnable;

    public MusicSearchConfig getMusicSearch() {
        return musicSearch.getValue();
    }

    public CategorySelectorConfig getCategorySelector() {
        return categorySelector.getValue();
    }

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

    public boolean isUploadMusicEnable() {
        return uploadMusicEnable.getValue();
    }
}
