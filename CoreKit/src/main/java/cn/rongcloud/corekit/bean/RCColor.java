package cn.rongcloud.corekit.bean;

import android.graphics.Color;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gyn on 2021/11/16
 */
public class RCColor implements Serializable {

    @SerializedName("red")
    private int red;
    @SerializedName("green")
    private int green;
    @SerializedName("blue")
    private int blue;
    @SerializedName("alpha")
    private float alpha;

    private int color = -1;

    public RCColor() {
    }

    public RCColor(@FloatRange(from = 0f, to = 1f) float alpha,
                   @IntRange(from = 0, to = 255) int red,
                   @IntRange(from = 0, to = 255) int green,
                   @IntRange(from = 0, to = 255) int blue) {
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getColor() {
        if (color == -1) {
            color = Color.argb((int) (alpha * 255.0f + 0.5f), red, green, blue);
        }
        return color;
    }
}
