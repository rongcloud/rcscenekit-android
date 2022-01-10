package cn.rongcloud.musiccontrolkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gyn on 2021/11/25
 */
public class MusicCategory implements Serializable {
    @SerializedName("categoryId")
    private String categoryId;
    @SerializedName("categoryName")
    private String categoryName;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
