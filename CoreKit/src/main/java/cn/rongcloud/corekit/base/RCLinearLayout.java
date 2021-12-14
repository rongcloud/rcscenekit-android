package cn.rongcloud.corekit.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import cn.rongcloud.corekit.api.IViewInit;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by gyn on 2021/12/8
 */
public abstract class RCLinearLayout<T> extends LinearLayout implements IViewInit<T> {

    public RCLinearLayout(Context context) {
        this(context, null);
    }

    public RCLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RCLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RCLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LayoutInflater.from(context).inflate(setLayoutId(), this);
        initView();
        checkData(getKitConfig());
    }

    private void checkData(T t) {
        if (t == null) {
            VMLog.e("RCLinearLayout", "initData failed: t is null");
        }
        initConfig(t);
    }
}
