package cn.rongcloud.corekit.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;

/**
 * Created by gyn on 2021/11/22
 */
public class GlideUtil {

    public static void loadImage(ImageView imageView, Object url) {
        loadImage(imageView, url, 0);
    }

    public static void loadImage(ImageView imageView, Object url, @DrawableRes int placeholder) {
        Glide.with(imageView).load(url).placeholder(placeholder).into(imageView);
    }

    public static void loadBitmap(Context context, Object url, CustomTarget<Drawable> target) {
        Glide.with(context).load(url).into(target);
    }
}
