package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import cn.rongcloud.corekit.bean.RCColor;

/**
 * @author gyn
 * @date 2021/12/10
 */
public class MusicAddConfig {
    @SerializedName("refreshEnable")
    private boolean refreshEnable;
    @SerializedName("loadMoreEnable")
    private boolean loadMoreEnable;
    @SerializedName("backgroundColor")
    private RCColor backgroundColor;
    @SerializedName("musicSearch")
    private MusicSearchConfig musicSearch;
    @SerializedName("categorySelector")
    private CategorySelectorConfig categorySelector;
    @SerializedName("musicItem")
    private MusicItemConfig musicItem;
    @SerializedName("uploadMusicEnable")
    private boolean uploadMusicEnable;

    public MusicSearchConfig getMusicSearch() {
        return musicSearch;
    }

    public void setMusicSearch(MusicSearchConfig musicSearch) {
        this.musicSearch = musicSearch;
    }

    public CategorySelectorConfig getCategorySelector() {
        return categorySelector;
    }

    public void setCategorySelector(CategorySelectorConfig categorySelector) {
        this.categorySelector = categorySelector;
    }

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

    public boolean isUploadMusicEnable() {
        return uploadMusicEnable;
    }

    public void setUploadMusicEnable(boolean uploadMusicEnable) {
        this.uploadMusicEnable = uploadMusicEnable;
    }
}
