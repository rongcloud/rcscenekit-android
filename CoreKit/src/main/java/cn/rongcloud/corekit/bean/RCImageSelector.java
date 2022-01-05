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
    @SerializedName("selected")
    private RCImage selected;

    private transient StateListDrawable drawable;

    public RCImageSelector() {
    }

    public RCImageSelector(RCImage normal, RCImage selected) {
        this.normal = normal;
        this.selected = selected;
    }

    public RCImage getNormal() {
        return normal;
    }

    public RCImage getSelected() {
        return selected;
    }

    public StateListDrawable getDrawable() {
        return drawable;
    }

    public void setDrawable(StateListDrawable drawable) {
        this.drawable = drawable;
    }
}
