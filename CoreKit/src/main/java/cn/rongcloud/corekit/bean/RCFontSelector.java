package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author gyn
 * @date 2021/12/13
 */
public class RCFontSelector implements Serializable {
    @SerializedName("normal")
    private RCFont normal;
    @SerializedName("select")
    private RCFont select;

    public RCFont getNormal() {
        return normal;
    }

    public RCFont getSelect() {
        return select;
    }

}
