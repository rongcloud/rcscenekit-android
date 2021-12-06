package cn.rongcloud.corekit.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by hugo on 2021/11/24
 */
public abstract class BaseFragment extends Fragment implements IBase {

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
    }

    public View getLayout() {
        return layout;
    }

    @Override
    public abstract int setLayoutId();

    @Override
    public abstract void initView();


    @Override
    public void initListener() {

    }

}
