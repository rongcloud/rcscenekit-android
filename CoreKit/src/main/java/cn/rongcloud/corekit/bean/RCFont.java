package cn.rongcloud.corekit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gyn on 2021/12/7
 */
public class RCFont implements Serializable {
    @SerializedName("size")
    private int size;
    @SerializedName("weight")
    private int weight;

    public int getSize() {
        return size;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isBold() {
        return weight >= 700;
    }
}
