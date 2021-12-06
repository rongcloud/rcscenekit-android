package cn.rongcloud.corekit.utils;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.bean.RCImageSelector;
import cn.rongcloud.corekit.bean.RCInsets;

/**
 * Created by hugo on 2021/11/15
 */
public class UiUtils {

    /**
     * @param context 上下文
     * @param dp      dp值
     * @return px
     */
    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * @param context 上下文
     * @param px      px值
     * @return dp
     */
    public static int px2dp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
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
        Context context = view.getContext();
        view.setPadding(dp2px(context, insets.getLeft()), dp2px(context, insets.getTop()), dp2px(context, insets.getRight()), dp2px(context, insets.getBottom()));
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
    public static void setSelectorBg(View view, RCImageSelector selector, @DrawableRes int defaultRes) {
        if (selector == null) {
            view.setBackgroundResource(defaultRes);
            return;
        }
        if (selector.getDrawable() != null) {
            view.setBackground(selector.getDrawable());
            return;
        }
        StateListDrawable drawable = new StateListDrawable();
        GlideUtil.loadBitmap(view.getContext(), selector.getSelect().getUrl(), new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                drawable.addState(new int[]{android.R.attr.state_selected}, resource);
                GlideUtil.loadBitmap(view.getContext(), selector.getNormal().getUrl(), new CustomTarget<Drawable>() {
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
    public static void setSelectorImage(ImageView view, RCImageSelector selector, @DrawableRes int defaultRes) {
        if (selector == null) {
            view.setImageResource(defaultRes);
            return;
        }
        if (selector.getDrawable() != null) {
            view.setImageDrawable(selector.getDrawable());
            return;
        }
        StateListDrawable drawable = new StateListDrawable();
        GlideUtil.loadBitmap(view.getContext(), selector.getSelect().getUrl(), new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                drawable.addState(new int[]{android.R.attr.state_selected}, resource);
                GlideUtil.loadBitmap(view.getContext(), selector.getNormal().getUrl(), new CustomTarget<Drawable>() {
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
}
