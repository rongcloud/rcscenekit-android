package cn.rongcloud.corekit.bean;

import android.graphics.drawable.StateListDrawable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gyn on 2021/12/3
 * example:
 * UiUtils.setSelectorImage(imageView, new RCImageSelector(new RCImage("", ""), new RCImage("", "")), R.drawable.rckit_ic_music_add_selector);
 */
public class RCImageSelector implements Serializable {
    @SerializedName("normal")
    private RCImage normal;
    @SerializedName("select")
    private RCImage select;

    private StateListDrawable drawable;

    public RCImageSelector() {
    }

    public RCImageSelector(RCImage normal, RCImage select) {
        this.normal = normal;
        this.select = select;
    }

    public RCImage getNormal() {
        return normal;
    }

    public void setNormal(RCImage normal) {
        this.normal = normal;
    }

    public RCImage getSelect() {
        return select;
    }

    public void setSelect(RCImage select) {
        this.select = select;
    }

    public StateListDrawable getDrawable() {
        return drawable;
    }

    public void setDrawable(StateListDrawable drawable) {
        this.drawable = drawable;
    }
}
