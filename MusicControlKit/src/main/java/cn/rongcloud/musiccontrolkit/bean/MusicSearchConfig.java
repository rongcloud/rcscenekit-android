package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.bean.RCAttributes;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCNode;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/12/7
 */
public class MusicSearchConfig implements Serializable {
    @SerializedName("contentInsets")
    private RCNode<RCInsets> contentInsets;
    @SerializedName("searchSize")
    private RCNode<RCSize> searchSize;
    @SerializedName("textAttributes")
    private RCNode<RCAttributes> textAttributes;

    public RCInsets getContentInsets() {
        return contentInsets.getValue();
    }

    public RCSize getSearchSize() {
        return searchSize.getValue();
    }

    public RCAttributes getTextAttributes() {
        return textAttributes.getValue();
    }
}
