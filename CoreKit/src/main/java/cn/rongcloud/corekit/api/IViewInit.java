package cn.rongcloud.corekit.api;

import androidx.annotation.LayoutRes;

/**
 * Created by gyn on 2021/12/8
 */
public interface IViewInit<T> {
    @LayoutRes
    int setLayoutId();

    T getKitConfig();

    void initView();

    void initConfig(T t);
}
