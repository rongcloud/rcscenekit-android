package cn.rongcloud.corekit.bean;

import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gyn on 2021/12/3
 * example:
 * imageView.setImageDrawable(new RCColorSelector(new RCColor(0.3f, 255, 255, 255), new RCColor(1f, 255, 255, 255)).getDrawable());
 */
public class RCColorSelector implements Serializable {
    @SerializedName("normal")
    private RCColor normal;
    @SerializedName("select")
    private RCColor select;

    private StateListDrawable drawable;

    private ColorStateList color;

    public RCColorSelector() {
    }

    public RCColorSelector(RCColor normal, RCColor select) {
        this.normal = normal;
        this.select = select;
    }

    public RCColor getNormal() {
        return normal;
    }

    public RCColor getSelect() {
        return select;
    }

    public StateListDrawable getDrawable() {
        if (drawable == null) {
            drawable = new StateListDrawable();
            drawable.addState(new int[]{android.R.attr.state_selected}, new ColorDrawable(select.getColor()));
            drawable.addState(new int[]{}, new ColorDrawable(normal.getColor()));
        }
        return drawable;
    }

    public ColorStateList getColor() {
        if (color == null) {
            color = new ColorStateList(new int[][]{new int[]{android.R.attr.state_selected}, new int[]{}}, new int[]{getSelect().getColor(), getNormal().getColor()});
        }
        return color;
    }

    public void setDrawable(StateListDrawable drawable) {
        this.drawable = drawable;
    }
}
