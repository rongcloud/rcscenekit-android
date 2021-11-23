package cn.rongcloud.corekit.utils;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.bean.Insets;

/**
 * Created by hugo on 2021/11/15
 */
public class UiUtils {

    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

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

    public static void setPadding(View view, Insets insets) {
        Context context = view.getContext();
        view.setPadding(dp2px(context, insets.getLeft()), dp2px(context, insets.getTop()), dp2px(context, insets.getRight()), dp2px(context, insets.getBottom()));
    }

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
}
