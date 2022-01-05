package cn.rongcloud.corekit.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.api.RCSceneKitEngine;
import cn.rongcloud.corekit.bean.RCAttributes;
import cn.rongcloud.corekit.bean.RCDrawable;
import cn.rongcloud.corekit.bean.RCDrawableSelector;
import cn.rongcloud.corekit.bean.RCFont;
import cn.rongcloud.corekit.bean.RCImage;
import cn.rongcloud.corekit.bean.RCImageSelector;
import cn.rongcloud.corekit.bean.RCInsets;
import cn.rongcloud.corekit.bean.RCSize;

/**
 * Created by gyn on 2021/11/15
 */
public class UiUtils {

    /**
     * @param dp dp值
     * @return px
     */
    public static int dp2px(int dp) {
        float density = RCSceneKitEngine.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * @param px px值
     * @return dp
     */
    public static int px2dp(int px) {
        float density = RCSceneKitEngine.getInstance().getContext().getResources().getDisplayMetrics().density;
        return (int) ((px / density + 0.5f));
    }

    /**
     * 创建背景颜色
     *
     * @param color       填充色
     * @param strokeColor 线条颜色
     * @param strokeWidth 线条宽度  单位px
     * @param radius      角度  px
     */
    public static GradientDrawable createRectangleDrawable(@ColorInt int color, @ColorInt int strokeColor, int strokeWidth, float radius) {
        try {
            GradientDrawable radiusBg = new GradientDrawable();
            //设置Shape类型
            radiusBg.setShape(GradientDrawable.RECTANGLE);
            //设置填充颜色
            radiusBg.setColor(color);
            //设置线条粗心和颜色,px
            radiusBg.setStroke(strokeWidth, strokeColor);
            //设置圆角角度,如果每个角度都一样,则使用此方法
            radiusBg.setCornerRadius(radius);
            return radiusBg;
        } catch (Exception e) {
            return new GradientDrawable();
        }
    }

    public static GradientDrawable createDrawable(RCDrawable drawable) {
        try {
            GradientDrawable radiusBg = new GradientDrawable();
            //设置Shape类型
            switch (drawable.getShape()) {
                case 0:
                    radiusBg.setShape(GradientDrawable.RECTANGLE);
                    break;
                case 1:
                    radiusBg.setShape(GradientDrawable.OVAL);
                    break;
                case 2:
                    radiusBg.setShape(GradientDrawable.LINE);
                    break;
                case 3:
                    radiusBg.setShape(GradientDrawable.RING);
                    break;
            }
            //设置填充颜色
            radiusBg.setColor(drawable.getColor().getColor());
            //设置线条粗心和颜色,px
            radiusBg.setStroke(drawable.getStrokeWidthPx(), drawable.getStrokeColor().getColor());
            //设置圆角角度,如果每个角度都一样,则使用此方法
            float[] radius = drawable.getCorner().getRadiusArray();
            radiusBg.setCornerRadii(new float[]{radius[0], radius[0], radius[1], radius[1], radius[2], radius[2], radius[3], radius[3]});
            return radiusBg;
        } catch (Exception e) {
            return new GradientDrawable();
        }
    }

    public static void setDrawable(View view, RCDrawable drawable) {
        view.setBackground(createDrawable(drawable));
    }

    public static void setDrawableSelector(View view, RCDrawableSelector drawableSelector) {
        view.setBackground(drawableSelector.getDrawable());
    }

    /**
     * 创建背景颜色
     *
     * @param color       填充色
     * @param strokeColor 线条颜色
     * @param strokeWidth 线条宽度  单位px
     * @param radius      角度  px,长度为4,分别表示左上,右上,右下,左下的角度
     */
    public static GradientDrawable createRectangleDrawable(@ColorInt int color, @ColorInt int strokeColor, int strokeWidth, float radius[]) {
        try {
            GradientDrawable radiusBg = new GradientDrawable();
            //设置Shape类型
            radiusBg.setShape(GradientDrawable.RECTANGLE);
            //设置填充颜色
            radiusBg.setColor(color);
            //设置线条粗心和颜色,px
            radiusBg.setStroke(strokeWidth, strokeColor);
            //每连续的两个数值表示是一个角度,四组:左上,右上,右下,左下
            if (radius != null && radius.length == 4) {
                radiusBg.setCornerRadii(new float[]{radius[0], radius[0], radius[1], radius[1], radius[2], radius[2], radius[3], radius[3]});
            }
            return radiusBg;
        } catch (Exception e) {
            return new GradientDrawable();
        }
    }

    /**
     * 给view设置padding
     *
     * @param view
     * @param insets
     */
    public static void setPadding(View view, RCInsets insets) {
        if (view == null || insets == null) {
            return;
        }
        view.setPadding(insets.getLeftPx(), insets.getTopPx(), insets.getRightPx(), insets.getBottomPx());
    }

    /**
     * 反转ViewGroup内的child
     *
     * @param viewGroup
     */
    public static void reverseChild(ViewGroup viewGroup) {
        List<View> views = new ArrayList<>();
        int count = viewGroup.getChildCount();
        for (int i = 0; i < count; i++) {
            views.add(viewGroup.getChildAt(i));
        }
        viewGroup.removeAllViews();
        for (int i = count - 1; i >= 0; i--) {
            viewGroup.addView(views.get(i));
        }
    }


    /**
     * 获取屏幕高度,包括状态栏
     */
    public static int getFullScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(dm);
        } else {
            display.getMetrics(dm);
        }
        return dm.heightPixels;
    }

    /**
     * 获取屏幕高度,不包括状态栏
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getRealMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕中位置
     */
    public static int[] getLocation(View view) {
        int[] location = new int[2];
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);
            location[0] = rect.left;
            location[1] = rect.top;
        } else {
            view.getLocationOnScreen(location);
        }
        return location;
    }

    /**
     * 加载背景选择器
     *
     * @param view       背景view
     * @param selector   RCSelector
     * @param defaultRes 失败后默认选择器
     */
    public static void setSelectorBg(View view, RCImageSelector selector, @DrawableRes int defaultRes, String assetsPath) {
        if (selector == null) {
            view.setBackgroundResource(defaultRes);
            return;
        }
        if (selector.getDrawable() != null) {
            view.setBackground(selector.getDrawable());
            return;
        }
        StateListDrawable drawable = new StateListDrawable();
        GlideUtil.loadBitmap(view.getContext(), selector.getSelected().getUrl(assetsPath), new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                drawable.addState(new int[]{android.R.attr.state_selected}, resource);
                GlideUtil.loadBitmap(view.getContext(), selector.getNormal().getUrl(assetsPath), new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        drawable.addState(new int[]{}, resource);
                        selector.setDrawable(drawable);
                        view.setBackground(drawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        view.setBackgroundResource(defaultRes);
                    }
                });
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                view.setBackgroundResource(defaultRes);
            }
        });
    }

    /**
     * 加载图片选择器
     *
     * @param view       图片view
     * @param selector   RCSelector
     * @param defaultRes 失败后默认选择器
     */
    public static void setSelectorImage(ImageView view, RCImageSelector selector, @DrawableRes int defaultRes, String assetsPath) {
        if (view == null) {
            return;
        }
        if (selector == null) {
            view.setImageResource(defaultRes);
            return;
        }
        if (TextUtils.isEmpty(assetsPath)) {
            view.setImageResource(defaultRes);
            return;
        }
        if (selector.getDrawable() != null) {
            view.setImageDrawable(selector.getDrawable());
            return;
        }
        StateListDrawable drawable = new StateListDrawable();
        GlideUtil.loadBitmap(view.getContext(), selector.getSelected().getUrl(assetsPath), new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                drawable.addState(new int[]{android.R.attr.state_selected}, resource);
                GlideUtil.loadBitmap(view.getContext(), selector.getNormal().getUrl(assetsPath), new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        drawable.addState(new int[]{}, resource);
                        selector.setDrawable(drawable);
                        view.setImageDrawable(drawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        view.setImageResource(defaultRes);
                    }
                });
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                view.setImageResource(defaultRes);
            }
        });
    }

    public static void setImage(ImageView view, RCImage image, @DrawableRes int defaultRes, String assetsPath) {
        if (view == null) {
            return;
        }
        if (image == null) {
            view.setImageResource(defaultRes);
            return;
        }
        if (TextUtils.isEmpty(assetsPath)) {
            view.setImageResource(defaultRes);
            return;
        }
        GlideUtil.loadImage(view, image.getUrl(assetsPath), defaultRes);
    }

    public static void setViewSize(View view, RCSize size) {
        if (view == null || size == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        int with;
        if (size.getWidthMode() == -1 || size.getWidthMode() == -2) {
            with = size.getWidthMode();
        } else {
            with = size.getWidthPx();
        }
        int height;
        if (size.getHeightMode() == -1 || size.getHeightMode() == -2) {
            height = size.getHeightMode();
        } else {
            height = size.getHeightPx();
        }
        if (params == null) {
            params = new ViewGroup.LayoutParams(with, height);
        } else {
            params.width = with;
            params.height = height;
        }
        view.setLayoutParams(params);
    }

    public static void setTextAttributes(TextView view, RCAttributes attributes) {
        if (view == null || attributes == null) {
            return;
        }
        if (!TextUtils.isEmpty(attributes.getText())) {
            view.setText(attributes.getText());
        }
        if (attributes.getInsets() != null) {
            setPadding(view, attributes.getInsets());
        }
        if (attributes.getSize() != null) {
            setViewSize(view, attributes.getSize());
        }
        if (attributes.getBackground() != null) {
            view.setBackgroundColor(attributes.getBackground().getColor());
        }
        if (attributes.getTextColor() != null) {
            view.setTextColor(attributes.getTextColor().getColor());
        }
        if (attributes.getFont() != null) {
            setTextFont(view, attributes.getFont());
        }
        if (attributes.getHintColor() != null) {
            view.setHintTextColor(attributes.getHintColor().getColor());
        }
        if (!TextUtils.isEmpty(attributes.getHintText())) {
            view.setHint(attributes.getHintText());
        }
        if (attributes.getColorSelector() != null) {
            view.setTextColor(attributes.getColorSelector().getColor());
        }
        if (attributes.getDrawableSelector() != null) {
            setDrawableSelector(view, attributes.getDrawableSelector());
        }
        if (attributes.getDrawable() != null) {
            setDrawable(view, attributes.getDrawable());
        }
    }

    public static void setTextFont(TextView view, RCFont rcFont) {
        if (view == null || rcFont == null) {
            return;
        }
        view.setTextSize(rcFont.getSize());
        view.getPaint().setFakeBoldText(rcFont.isBold());
    }

    public static void setImageAttribute(ImageView view, RCAttributes attribute, @DrawableRes int defaultRes, String assetsPath) {
        if (view == null || attribute == null) {
            return;
        }
        if (attribute.getSize() != null) {
            setViewSize(view, attribute.getSize());
        }
        if (attribute.getInsets() != null) {
            setPadding(view, attribute.getInsets());
        }
        if (attribute.getBackground() != null) {
            view.setBackgroundColor(attribute.getBackground().getColor());
        }
        if (attribute.getBackgroundSelector() != null) {
            view.setBackground(attribute.getBackgroundSelector().getDrawable());
        }
        if (attribute.getImageSelector() != null) {
            setSelectorImage(view, attribute.getImageSelector(), defaultRes, assetsPath);
        }
        if (attribute.getImage() != null) {
            setImage(view, attribute.getImage(), defaultRes, assetsPath);
        }
        if (attribute.getDrawableSelector() != null) {
            setDrawableSelector(view, attribute.getDrawableSelector());
        }
        if (attribute.getDrawable() != null) {
            setDrawable(view, attribute.getDrawable());
        }
    }

    public static void setMargin(View view, RCInsets insets) {
        if (view == null || insets == null) {
            return;
        }
        ViewGroup.LayoutParams params = view.getLayoutParams();
        if (params instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) params).leftMargin = insets.getLeftPx();
            ((ViewGroup.MarginLayoutParams) params).topMargin = insets.getTopPx();
            ((ViewGroup.MarginLayoutParams) params).rightMargin = insets.getRightPx();
            ((ViewGroup.MarginLayoutParams) params).bottomMargin = insets.getBottomPx();
            view.setLayoutParams(params);
        }
    }

    public static void setSwitchColor(SwitchCompat view, int thumbColor, int trackColor, int tintColor) {
        if (view == null) {
            return;
        }
        // set the thumb color
        DrawableCompat.setTintList(view.getThumbDrawable(), new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        tintColor,
                        thumbColor
                }));

        // set the track color
        DrawableCompat.setTintList(view.getTrackDrawable(), new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_checked},
                        new int[]{}
                },
                new int[]{
                        tintColor,
                        trackColor
                }));
    }
}
