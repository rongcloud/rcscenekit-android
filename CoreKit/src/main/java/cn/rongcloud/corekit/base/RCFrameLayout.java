package cn.rongcloud.corekit.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import cn.rongcloud.corekit.api.IViewInit;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by gyn on 2021/12/8
 */
public abstract class RCFrameLayout<T> extends FrameLayout implements IViewInit<T> {

    public RCFrameLayout(Context context) {
        this(context, null);
    }

    public RCFrameLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RCFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(setLayoutId(), this);
        initView();
        check();
    }

    @Override
    public void check() {
        if (getKitInstance() == null) {
            VMLog.e(getClass().getSimpleName(), "getKitInstance is null");
            return;
        }
        T t = getKitInstance().getKitConfig();
        if (t == null) {
            VMLog.e(getClass().getSimpleName(), "getKitConfig is null");
            return;
        }
        initConfig(t);
        getKitInstance().incrementUse();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (getKitInstance() != null) {
            getKitInstance().decrementUse();
        }
        super.onDetachedFromWindow();
    }
}
