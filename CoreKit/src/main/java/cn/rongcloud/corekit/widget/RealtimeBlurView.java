/*
 * Copyright © 2021 RongCloud. All rights reserved.
 */

package cn.rongcloud.corekit.widget;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;

import cn.rongcloud.corekit.R;
import cn.rongcloud.corekit.widget.blurImpl.AndroidStockBlurImpl;
import cn.rongcloud.corekit.widget.blurImpl.AndroidXBlurImpl;
import cn.rongcloud.corekit.widget.blurImpl.BlurImpl;
import cn.rongcloud.corekit.widget.blurImpl.EmptyBlurImpl;
import cn.rongcloud.corekit.widget.blurImpl.SupportLibraryBlurImpl;


/**
 * A realtime blurring overlay (like iOS UIVisualEffectView). Just put it above
 * the view you want to blur and it doesn't have to be in the same ViewGroup
 * <ul>
 * <li>realtimeBlurRadius (10dp)</li>
 * <li>realtimeDownsampleFactor (4)</li>
 * <li>realtimeOverlayColor (#aaffffff)</li>
 * </ul>
 */
public class RealtimeBlurView extends View {

    private static final StopException STOP_EXCEPTION = new StopException();
    private static int RENDERING_COUNT;
    private static int BLUR_IMPL;
    private final BlurImpl mBlurImpl;
    private final Paint mPaint;
    private final Rect mRectSrc = new Rect(), mRectDst = new Rect();
    private float mDownsampleFactor; // default 4
    private int mOverlayColor; // default #aaffffff
    private float mBlurRadius; // default 10dp (0 < r <= 25)
    private boolean mDirty;
    private Bitmap mBitmapToBlur, mBlurredBitmap;
    private Canvas mBlurringCanvas;
    private boolean mIsRendering;
    // mDecorView should be the root view of the activity (even if you are on a different window like a dialog)
    private View mDecorView;
    // If the view is on different root view (usually means we are on a PopupWindow),
    // we need to manually call invalidate() in onPreDraw(), otherwise we will not be able to see the changes
    private boolean mDifferentRoot;
    private final ViewTreeObserver.OnPreDrawListener preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        @Override
        public boolean onPreDraw() {
            final int[] locations = new int[2];
            Bitmap oldBmp = mBlurredBitmap;
            View decor = mDecorView;
            if (decor != null && isShown() && prepare()) {
                boolean redrawBitmap = mBlurredBitmap != oldBmp;
                oldBmp = null;
                decor.getLocationOnScreen(locations);
                int x = -locations[0];
                int y = -locations[1];

                getLocationOnScreen(locations);
                x += locations[0];
                y += locations[1];

                // just erase transparent
                mBitmapToBlur.eraseColor(mOverlayColor & 0xffffff);

                int rc = mBlurringCanvas.save();
                mIsRendering = true;
                RENDERING_COUNT++;
                try {
                    mBlurringCanvas.scale(1.f * mBitmapToBlur.getWidth() / getWidth(), 1.f * mBitmapToBlur.getHeight() / getHeight());
                    mBlurringCanvas.translate(-x, -y);
                    if (decor.getBackground() != null) {
                        decor.getBackground().draw(mBlurringCanvas);
                    }
                    decor.draw(mBlurringCanvas);
                } catch (StopException e) {
                } finally {
                    mIsRendering = false;
                    RENDERING_COUNT--;
                    mBlurringCanvas.restoreToCount(rc);
                }

                blur(mBitmapToBlur, mBlurredBitmap);

                if (redrawBitmap || mDifferentRoot) {
                    invalidate();
                }
            }

            return true;
        }
    };
    private float radius = 0;
    private float leftTopRadius = 0;
    private float rightTopRadius = 0;
    private float rightBottomRadius = 0;
    private float leftBottomRadius = 0;
    private float width = 0;
    private float height = 0;

    public RealtimeBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mBlurImpl = getBlurImpl(); // provide your own by override getBlurImpl()

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RCKitRealtimeBlurView);
        mBlurRadius = a.getDimension(R.styleable.RCKitRealtimeBlurView_realtimeBlurRadius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics()));
        mDownsampleFactor = a.getFloat(R.styleable.RCKitRealtimeBlurView_realtimeDownsampleFactor, 4);
        mOverlayColor = a.getColor(R.styleable.RCKitRealtimeBlurView_realtimeOverlayColor, 0xAAFFFFFF);

        radius = a.getDimensionPixelOffset(R.styleable.RCKitRealtimeBlurView_radius, 0);
        leftTopRadius = a.getDimensionPixelOffset(R.styleable.RCKitRealtimeBlurView_left_top_radius, 0);
        rightTopRadius = a.getDimensionPixelOffset(R.styleable.RCKitRealtimeBlurView_right_top_radius, 0);
        rightBottomRadius = a.getDimensionPixelOffset(R.styleable.RCKitRealtimeBlurView_right_bottom_radius, 0);
        leftBottomRadius = a.getDimensionPixelOffset(R.styleable.RCKitRealtimeBlurView_left_bottom_radius, 0);

        if (0 == leftTopRadius) {
            leftTopRadius = radius;
        }
        if (0 == rightTopRadius) {
            rightTopRadius = radius;
        }
        if (0 == rightBottomRadius) {
            rightBottomRadius = radius;
        }
        if (0 == leftBottomRadius) {
            leftBottomRadius = radius;
        }

        a.recycle();

        mPaint = new Paint();
    }

    protected BlurImpl getBlurImpl() {
        if (BLUR_IMPL == 0) {
            // try to use stock impl first
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                try {
                    AndroidStockBlurImpl impl = new AndroidStockBlurImpl();
                    Bitmap bmp = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                    impl.prepare(getContext(), bmp, 4);
                    impl.release();
                    bmp.recycle();
                    BLUR_IMPL = 3;
                } catch (Throwable e) {
                }
            }
        }
        if (BLUR_IMPL == 0) {
            try {
                getClass().getClassLoader().loadClass("androidx.renderscript.RenderScript");
                // initialize RenderScript to load jni impl
                // may throw unsatisfied link error
                AndroidXBlurImpl impl = new AndroidXBlurImpl();
                Bitmap bmp = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                impl.prepare(getContext(), bmp, 4);
                impl.release();
                bmp.recycle();
                BLUR_IMPL = 1;
            } catch (Throwable e) {
                // class not found or unsatisfied link
            }
        }
        if (BLUR_IMPL == 0) {
            try {
                getClass().getClassLoader().loadClass("android.support.v8.renderscript.RenderScript");
                // initialize RenderScript to load jni impl
                // may throw unsatisfied link error
                SupportLibraryBlurImpl impl = new SupportLibraryBlurImpl();
                Bitmap bmp = Bitmap.createBitmap(4, 4, Bitmap.Config.ARGB_8888);
                impl.prepare(getContext(), bmp, 4);
                impl.release();
                bmp.recycle();
                BLUR_IMPL = 2;
            } catch (Throwable e) {
                // class not found or unsatisfied link
            }
        }
        if (BLUR_IMPL == 0) {
            // fallback to empty impl, which doesn't have blur effect
            BLUR_IMPL = -1;
        }
        switch (BLUR_IMPL) {
            case 1:
                return new AndroidXBlurImpl();
            case 2:
                return new SupportLibraryBlurImpl();
            case 3:
                return new AndroidStockBlurImpl();
            default:
                return new EmptyBlurImpl();
        }
    }

    public void setBlurRadius(float radius) {
        if (mBlurRadius != radius) {
            mBlurRadius = radius;
            mDirty = true;
            invalidate();
        }
    }

    public void setDownsampleFactor(float factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("Downsample factor must be greater than 0.");
        }

        if (mDownsampleFactor != factor) {
            mDownsampleFactor = factor;
            mDirty = true; // may also change blur radius
            releaseBitmap();
            invalidate();
        }
    }

    public void setOverlayColor(int color) {
        if (mOverlayColor != color) {
            mOverlayColor = color;
            invalidate();
        }
    }

    private void releaseBitmap() {
        if (mBitmapToBlur != null) {
            mBitmapToBlur.recycle();
            mBitmapToBlur = null;
        }
        if (mBlurredBitmap != null) {
            mBlurredBitmap.recycle();
            mBlurredBitmap = null;
        }
    }

    protected void release() {
        releaseBitmap();
        mBlurImpl.release();
    }

    protected boolean prepare() {
        if (mBlurRadius == 0) {
            release();
            return false;
        }

        float downsampleFactor = mDownsampleFactor;
        float radius = mBlurRadius / downsampleFactor;
        if (radius > 25) {
            downsampleFactor = downsampleFactor * radius / 25;
            radius = 25;
        }

        final int width = getWidth();
        final int height = getHeight();

        int scaledWidth = Math.max(1, (int) (width / downsampleFactor));
        int scaledHeight = Math.max(1, (int) (height / downsampleFactor));

        boolean dirty = mDirty;

        if (mBlurringCanvas == null || mBlurredBitmap == null
                || mBlurredBitmap.getWidth() != scaledWidth
                || mBlurredBitmap.getHeight() != scaledHeight) {
            dirty = true;
            releaseBitmap();

            boolean r = false;
            try {
                mBitmapToBlur = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
                if (mBitmapToBlur == null) {
                    return false;
                }
                mBlurringCanvas = new Canvas(mBitmapToBlur);

                mBlurredBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
                if (mBlurredBitmap == null) {
                    return false;
                }

                r = true;
            } catch (OutOfMemoryError e) {
                // Bitmap.createBitmap() may cause OOM error
                // Simply ignore and fallback
            } finally {
                if (!r) {
                    release();
                    return false;
                }
            }
        }

        if (dirty) {
            if (mBlurImpl.prepare(getContext(), mBitmapToBlur, radius)) {
                mDirty = false;
            } else {
                return false;
            }
        }

        return true;
    }

    protected void blur(Bitmap bitmapToBlur, Bitmap blurredBitmap) {
        if (null != bitmapToBlur && null != blurredBitmap) {
            mBlurImpl.blur(bitmapToBlur, blurredBitmap);
        }
    }

    protected View getActivityDecorView() {
        Context ctx = getContext();
        for (int i = 0; i < 4 && ctx != null && !(ctx instanceof Activity) && ctx instanceof ContextWrapper; i++) {
            ctx = ((ContextWrapper) ctx).getBaseContext();
        }
        if (ctx instanceof Activity) {
            return ((Activity) ctx).getWindow().getDecorView();
        } else {
            return null;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mDecorView = getActivityDecorView();
        if (mDecorView != null) {
            mDecorView.getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            mDifferentRoot = mDecorView.getRootView() != getRootView();
            if (mDifferentRoot) {
                mDecorView.postInvalidate();
            }
        } else {
            mDifferentRoot = false;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        if (mDecorView != null) {
            mDecorView.getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
        }
        release();
        super.onDetachedFromWindow();
    }

    @Override
    public void draw(Canvas canvas) {
        if (mIsRendering) {
            // Quit here, don't draw views above me
//            throw STOP_EXCEPTION;
        } else if (RENDERING_COUNT > 0) {
            // Doesn't support blurview overlap on another blurview
        } else {
            super.draw(canvas);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBlurredBitmap(canvas, mBlurredBitmap, mOverlayColor);
    }

    /**
     * Custom draw the blurred bitmap and color to define your own shape
     *
     * @param canvas
     * @param blurredBitmap
     * @param overlayColor
     */
    protected void drawBlurredBitmap(Canvas canvas, Bitmap blurredBitmap, int overlayColor) {
        if (blurredBitmap != null) {
            mRectSrc.right = blurredBitmap.getWidth();
            mRectSrc.bottom = blurredBitmap.getHeight();
            mRectDst.right = getWidth();
            mRectDst.bottom = getHeight();

            float maxLeft = Math.max(leftTopRadius, leftBottomRadius);
            float maxRight = Math.max(rightBottomRadius, rightTopRadius);
            float minWidth = maxLeft + maxRight;
            float maxTop = Math.max(leftTopRadius, rightTopRadius);
            float maxBottom = Math.max(leftBottomRadius, rightBottomRadius);
            float minHeight = maxTop + maxBottom;

            if (width >= minWidth && height >= minHeight) {
                Path path = new Path();
                path.moveTo(leftTopRadius, 0f);
                path.lineTo(width - rightTopRadius, 0f);
                path.quadTo(width, 0f, width, rightTopRadius);
                path.lineTo(width, height - rightBottomRadius);
                path.quadTo(width, height, width - rightBottomRadius, height);
                path.lineTo(leftBottomRadius, height);
                path.quadTo(0f, height, 0f, height - leftBottomRadius);
                path.lineTo(0f, leftTopRadius);
                path.quadTo(0f, 0f, leftTopRadius, 0f);
                canvas.clipPath(path);
            }

            canvas.drawBitmap(blurredBitmap, mRectSrc, mRectDst, null);
        }
        mPaint.setColor(overlayColor);
        canvas.drawRect(mRectDst, mPaint);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    private static class StopException extends RuntimeException {
    }
}
