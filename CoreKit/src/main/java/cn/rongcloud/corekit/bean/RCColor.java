package cn.rongcloud.corekit.bean;

import android.graphics.Color;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hugo on 2021/11/16
 */
public class RCColor implements Serializable {

    @SerializedName("red")
    private Integer red;
    @SerializedName("green")
    private Integer green;
    @SerializedName("blue")
    private Integer blue;
    @SerializedName("alpha")
    private float alpha;

    public RCColor() {
    }

    public RCColor(@FloatRange(from = 0f, to = 1f) float alpha,
                   @IntRange(from = 0, to = 255) Integer red,
                   @IntRange(from = 0, to = 255) Integer green,
                   @IntRange(from = 0, to = 255) Integer blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public int getColor() {
        return Color.argb((int) (alpha * 255.0f + 0.5f), red, green, blue);
    }
}
