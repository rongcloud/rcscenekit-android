package cn.rongcloud.corekit.bean;

import android.graphics.drawable.StateListDrawable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import cn.rongcloud.corekit.utils.UiUtils;

/**
 * @author gyn
 * @date 2021/12/13
 */
public class RCDrawableSelector implements Serializable {
    private transient StateListDrawable drawable;
    @SerializedName("normal")
    private RCDrawable normal;
    @SerializedName("selected")
    private RCDrawable selected;

    public RCDrawable getNormal() {
        return normal;
    }

    public RCDrawable getSelected() {
        return selected;
    }


    public StateListDrawable getDrawable() {
        drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_selected}, UiUtils.createDrawable(selected));
        drawable.addState(new int[]{}, UiUtils.createDrawable(normal));
        return drawable;
    }
}
