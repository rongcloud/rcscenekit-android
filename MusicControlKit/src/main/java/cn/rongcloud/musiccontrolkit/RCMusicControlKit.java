package cn.rongcloud.musiccontrolkit;

import android.content.Context;

import androidx.annotation.NonNull;

import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

import cn.rongcloud.corekit.core.RCKitInit;
import cn.rongcloud.corekit.utils.VMLog;
import cn.rongcloud.musiccontrolkit.bean.MusicControlKitConfig;

/**
 * Created by gyn on 2021/11/23
 */
public class RCMusicControlKit extends RCKitInit<MusicControlKitConfig> {

    private static final String TAG = RCMusicControlKit.class.getSimpleName();

    private final static Holder holder = new Holder();

    public static RCMusicControlKit getInstance() {
        return holder.instance;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        // 设置加载更多等逻辑
        setSmartRefreshLayoutHeader(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsHeader(context);
            }
        });
        setSmartRefreshLayoutFooter(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context);
            }
        });
        VMLog.d(TAG, TAG + " has init");
    }

    @Override
    public String getKitConfigName() {
        return "MusicControlKit";
    }

    /**
     * 设置 SmartRefreshLayout 的刷新布局
     *
     * @param creator
     */
    public void setSmartRefreshLayoutHeader(DefaultRefreshHeaderCreator creator) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(creator);
    }

    /**
     * 设置 SmartRefreshLayout 的加载布局
     *
     * @param creator
     */
    public void setSmartRefreshLayoutFooter(DefaultRefreshFooterCreator creator) {
        SmartRefreshLayout.setDefaultRefreshFooterCreator(creator);
    }

    private static class Holder {
        private RCMusicControlKit instance = new RCMusicControlKit();
    }
}
