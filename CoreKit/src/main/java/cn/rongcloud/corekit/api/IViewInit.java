package cn.rongcloud.corekit.api;

import androidx.annotation.LayoutRes;

import cn.rongcloud.corekit.core.RCKitInit;

/**
 * Created by gyn on 2021/12/8
 */
public interface IViewInit<T> {
    @LayoutRes
    int setLayoutId();

    RCKitInit<T> getKitInstance();

    void initView();

    void initConfig(T t);

    void check();
}
