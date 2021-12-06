package cn.rongcloud.corekit.base;

import androidx.annotation.LayoutRes;

/**
 * Created by hugo on 2021/11/24
 */
public interface IBase {
    @LayoutRes
    int setLayoutId();

    void initView();

    void initListener();
}
