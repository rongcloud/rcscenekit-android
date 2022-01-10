package cn.rongcloud.corekit.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import cn.rongcloud.corekit.api.IViewInit;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by gyn on 2021/11/24
 */
public abstract class RCFragment<T> extends Fragment implements IViewInit<T> {

    private View layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(setLayoutId(), null);
        return layout;
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

    public View getLayout() {
        return layout;
    }

    public void initListener() {

    }

}
