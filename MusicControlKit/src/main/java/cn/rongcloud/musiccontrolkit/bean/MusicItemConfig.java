package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttribute;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * @author gyn
 * @date 2021/12/10
 */
public class MusicItemConfig implements Serializable {
    @SerializedName("highlightColor")
    private RCColor highlightColor;
    @SerializedName("size")
    private RCSize size;
    @SerializedName("contentInsets")
    private RCInsets contentInsets;
    @SerializedName("coverSize")
    private RCSize coverSize;
    @SerializedName("titleAttribute")
    private RCAttribute titleAttribute;
    @SerializedName("contentAttribute")
    private RCAttribute contentAttribute;
    @SerializedName("sizeAttribute")
    private RCAttribute sizeAttribute;
    @SerializedName("separatorAttribute")
    private RCAttribute separatorAttribute;
    @SerializedName("addIconAttribute")
    private RCAttribute addIconAttribute;
    @SerializedName("topIconAttribute")
    private RCAttribute topIconAttribute;
    @SerializedName("deleteIconAttribute")
    private RCAttribute deleteIconAttribute;

    public RCColor getHighlightColor() {
        return highlightColor;
    }

    public void setHighlightColor(RCColor highlightColor) {
        this.highlightColor = highlightColor;
    }

    public RCSize getSize() {
        return size;
    }

    public void setSize(RCSize size) {
        this.size = size;
    }

    public RCInsets getContentInsets() {
        return contentInsets;
    }

    public void setContentInsets(RCInsets contentInsets) {
        this.contentInsets = contentInsets;
    }

    public RCSize getCoverSize() {
        return coverSize;
    }

    public void setCoverSize(RCSize coverSize) {
        this.coverSize = coverSize;
    }

    public RCAttribute getTitleAttribute() {
        return titleAttribute;
    }

    public void setTitleAttribute(RCAttribute titleAttribute) {
        this.titleAttribute = titleAttribute;
    }

    public RCAttribute getContentAttribute() {
        return contentAttribute;
    }

    public void setContentAttribute(RCAttribute contentAttribute) {
        this.contentAttribute = contentAttribute;
    }

    public RCAttribute getSizeAttribute() {
        return sizeAttribute;
    }

    public void setSizeAttribute(RCAttribute sizeAttribute) {
        this.sizeAttribute = sizeAttribute;
    }

    public RCAttribute getSeparatorAttribute() {
        return separatorAttribute;
    }

    public void setSeparatorAttribute(RCAttribute separatorAttribute) {
        this.separatorAttribute = separatorAttribute;
    }

    public RCAttribute getAddIconAttribute() {
        return addIconAttribute;
    }

    public void setAddIconAttribute(RCAttribute addIconAttribute) {
        this.addIconAttribute = addIconAttribute;
    }

    public RCAttribute getTopIconAttribute() {
        return topIconAttribute;
    }

    public void setTopIconAttribute(RCAttribute topIconAttribute) {
        this.topIconAttribute = topIconAttribute;
    }

    public RCAttribute getDeleteIconAttribute() {
        return deleteIconAttribute;
    }

    public void setDeleteIconAttribute(RCAttribute deleteIconAttribute) {
        this.deleteIconAttribute = deleteIconAttribute;
    }
}
