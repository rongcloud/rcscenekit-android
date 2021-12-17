package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author gyn
 * @date 2021/12/17
 */
public class RCRadio<T> implements Serializable {
    @SerializedName("radios")
    private List<RCNode<T>> radios;
    @SerializedName("selected")
    private RCNode<T> selected;

    public List<RCNode<T>> getRadios() {
        return radios;
    }

    public RCNode<T> getSelected() {
        return selected;
    }
}
