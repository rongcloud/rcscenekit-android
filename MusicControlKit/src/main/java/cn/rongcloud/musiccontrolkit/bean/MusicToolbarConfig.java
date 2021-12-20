package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCImageSelector;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCNode;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/12/6
 */
public class MusicToolbarConfig implements Serializable {
    @SerializedName("blurEnable")
    private RCNode<Boolean> blurEnable;
    @SerializedName("contentInsets")
    private RCNode<RCInsets> contentInsets;
    @SerializedName("backgroundColor")
    private RCNode<RCColor> backgroundColor;
    @SerializedName("size")
    private RCNode<RCSize> size;
    @SerializedName("spacing")
    private RCNode<Integer> spacing;
    @SerializedName("musicControlEnable")
    private RCNode<Boolean> musicControlEnable;
    @SerializedName("soundEffectEnable")
    private RCNode<Boolean> soundEffectEnable;
    @SerializedName("tabItems")
    private RCNode<List<RCImageSelector>> tabItems;
    @SerializedName("tabSize")
    private RCNode<RCSize> tabSize;

    public boolean isBlurEnable() {
        return blurEnable.getValue();
    }

    public RCInsets getContentInsets() {
        return contentInsets.getValue();
    }

    public RCColor getBackgroundColor() {
        return backgroundColor.getValue();
    }

    public RCSize getSize() {
        return size.getValue();
    }

    public Integer getSpacing() {
        return spacing.getValue();
    }

    public Boolean getMusicControlEnable() {
        return musicControlEnable.getValue();
    }

    public Boolean getSoundEffectEnable() {
        return soundEffectEnable.getValue();
    }

    public List<RCImageSelector> getTabItems() {
        return tabItems.getValue();
    }

    public RCSize getTabSize() {
        return tabSize.getValue();
    }

    public RCImageSelector getTabItem(int index) {
        if (tabItems == null || tabItems.getValue() == null) {
            return null;
        }
        if (index < tabItems.getValue().size()) {
            return tabItems.getValue().get(index);
        }
        return null;
    }

    public RCImageSelector getLastTab() {
        if (tabItems == null || tabItems.getValue() == null || tabItems.getValue().size() == 0) {
            return null;
        }
        return tabItems.getValue().get(tabItems.getValue().size() - 1);
    }
}
