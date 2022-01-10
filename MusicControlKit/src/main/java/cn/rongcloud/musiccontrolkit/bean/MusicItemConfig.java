package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttributes;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCNode;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * @author gyn
 * @date 2021/12/10
 */
public class MusicItemConfig implements Serializable {
    @SerializedName("highlightColor")
    private RCNode<RCColor> highlightColor;
    @SerializedName("size")
    private RCNode<RCSize> size;
    @SerializedName("contentInsets")
    private RCNode<RCInsets> contentInsets;
    @SerializedName("coverSize")
    private RCNode<RCSize> coverSize;
    @SerializedName("titleAttributes")
    private RCNode<RCAttributes> titleAttributes;
    @SerializedName("contentAttributes")
    private RCNode<RCAttributes> contentAttributes;
    @SerializedName("sizeAttributes")
    private RCNode<RCAttributes> sizeAttributes;
    @SerializedName("separatorAttributes")
    private RCNode<RCAttributes> separatorAttributes;
    @SerializedName("addIconAttributes")
    private RCNode<RCAttributes> addIconAttributes;
    @SerializedName("topIconAttributes")
    private RCNode<RCAttributes> topIconAttributes;
    @SerializedName("deleteIconAttributes")
    private RCNode<RCAttributes> deleteIconAttributes;

    public RCColor getHighlightColor() {
        return highlightColor.getValue();
    }

    public RCSize getSize() {
        return size.getValue();
    }

    public RCInsets getContentInsets() {
        return contentInsets.getValue();
    }

    public RCSize getCoverSize() {
        return coverSize.getValue();
    }

    public RCAttributes getTitleAttributes() {
        return titleAttributes.getValue();
    }

    public RCAttributes getContentAttributes() {
        return contentAttributes.getValue();
    }

    public RCAttributes getSizeAttributes() {
        return sizeAttributes.getValue();
    }

    public RCAttributes getSeparatorAttributes() {
        return separatorAttributes.getValue();
    }

    public RCAttributes getAddIconAttributes() {
        return addIconAttributes.getValue();
    }

    public RCAttributes getTopIconAttributes() {
        return topIconAttributes.getValue();
    }

    public RCAttributes getDeleteIconAttributes() {
        return deleteIconAttributes.getValue();
    }
}
