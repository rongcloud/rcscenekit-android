package cn.rongcloud.corekit.utils;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;

/**
 * Created by hugo on 2021/11/22
 */
public class GlideUtil {

    public static void loadImage(ImageView imageView, Object url) {
        loadImage(imageView, url, 0);
    }

    public static void loadImage(ImageView imageView, Object url, @DrawableRes int placeholder) {
        Glide.with(imageView).load(url).placeholder(placeholder).into(imageView);
    }
}
