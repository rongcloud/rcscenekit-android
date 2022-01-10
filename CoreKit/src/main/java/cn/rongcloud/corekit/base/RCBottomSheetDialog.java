package cn.rongcloud.corekit.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import cn.rongcloud.corekit.R;
import cn.rongcloud.corekit.api.IViewInit;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by gyn on 2021/11/23
 */
public abstract class RCBottomSheetDialog<T> extends BottomSheetDialogFragment implements IViewInit<T> {

    private static final String TAG = RCBottomSheetDialog.class.getSimpleName();

    private @LayoutRes
    int layoutId;

    private BottomSheetBehavior<View> mBehavior;

    public RCBottomSheetDialog() {
        layoutId = setLayoutId();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = View.inflate(getContext(), layoutId, null);
        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setHideable(isHideable());
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.RCKitBottomSheetDialogTheme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(layoutId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
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
    public void onDetach() {
        if (getKitInstance() != null) {
            getKitInstance().decrementUse();
        }
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        if (isFullScreen()) {
            mBehavior.setPeekHeight(UiUtils.getFullScreenHeight(getContext()));
        }
    }

    @Override
    public void show(@NonNull FragmentManager manager, @Nullable String tag) {
        try {
            manager.beginTransaction().remove(this).commit();
            super.show(manager, tag);
        } catch (Exception e) {
            VMLog.e(TAG, e.getLocalizedMessage());
        }

    }

    /**
     * 是否可以拖动关闭
     *
     * @return
     */
    protected boolean isHideable() {
        return false;
    }

    /**
     * 是否全屏
     *
     * @return
     */
    protected boolean isFullScreen() {
        return false;
    }

    /**
     * 初始化监听
     */
    protected void initListener() {

    }
}
