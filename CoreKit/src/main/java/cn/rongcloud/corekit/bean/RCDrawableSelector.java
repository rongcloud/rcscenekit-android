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
    StateListDrawable drawable;
    @SerializedName("normal")
    private RCDrawable normal;
    @SerializedName("select")
    private RCDrawable select;

    public RCDrawable getNormal() {
        return normal;
    }

    public void setNormal(RCDrawable normal) {
        this.normal = normal;
    }

    public RCDrawable getSelect() {
        return select;
    }

    public void setSelect(RCDrawable select) {
        this.select = select;
    }

    public StateListDrawable getDrawable() {
        drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_selected}, UiUtils.createDrawable(select));
        drawable.addState(new int[]{}, UiUtils.createDrawable(normal));
        return drawable;
    }
}
