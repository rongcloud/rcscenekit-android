package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.rongcloud.corekit.annotation.KitBean;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCImageSelector;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/12/6
 */
@KitBean(parseKey = "musicToolBar")
public class MusicToolbarConfig implements Serializable {
    @SerializedName("blurEnable")
    private boolean blurEnable;
    @SerializedName("contentInsets")
    private RCInsets contentInsets;
    @SerializedName("backgroundColor")
    private RCColor backgroundColor;
    @SerializedName("size")
    private RCSize size;
    @SerializedName("spacing")
    private Integer spacing;
    @SerializedName("musicControlEnable")
    private Boolean musicControlEnable;
    @SerializedName("soundEffectEnable")
    private Boolean soundEffectEnable;
    @SerializedName("tabItems")
    private List<RCImageSelector> tabItems;
    @SerializedName("tabSize")
    private RCSize tabSize;

    public boolean isBlurEnable() {
        return blurEnable;
    }

    public void setBlurEnable(boolean blurEnable) {
        this.blurEnable = blurEnable;
    }

    public RCInsets getContentInsets() {
        return contentInsets;
    }

    public void setContentInsets(RCInsets contentInsets) {
        this.contentInsets = contentInsets;
    }

    public RCColor getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(RCColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public RCSize getSize() {
        return size;
    }

    public void setSize(RCSize size) {
        this.size = size;
    }

    public Integer getSpacing() {
        return spacing;
    }

    public void setSpacing(Integer spacing) {
        this.spacing = spacing;
    }

    public Boolean getMusicControlEnable() {
        return musicControlEnable;
    }

    public void setMusicControlEnable(Boolean musicControlEnable) {
        this.musicControlEnable = musicControlEnable;
    }

    public Boolean getSoundEffectEnable() {
        return soundEffectEnable;
    }

    public void setSoundEffectEnable(Boolean soundEffectEnable) {
        this.soundEffectEnable = soundEffectEnable;
    }

    public List<RCImageSelector> getTabItems() {
        return tabItems;
    }

    public void setTabItems(List<RCImageSelector> tabItems) {
        this.tabItems = tabItems;
    }

    public RCSize getTabSize() {
        return tabSize;
    }

    public void setTabSize(RCSize tabSize) {
        this.tabSize = tabSize;
    }

    public RCImageSelector getTabItem(int index) {
        if (tabItems == null) {
            return null;
        }
        if (index < tabItems.size()) {
            return tabItems.get(index);
        }
        return null;
    }

    public RCImageSelector getLastTab() {
        if (tabItems == null || tabItems.size() == 0) {
            return null;
        }
        return tabItems.get(tabItems.size() - 1);
    }
}
