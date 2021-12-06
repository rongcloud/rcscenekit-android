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

import cn.rongcloud.corekit.api.ICoreKitInit;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by hugo on 2021/11/23
 */
public class MusicControlKitInit implements ICoreKitInit {

    private static final String TAG = MusicControlKitInit.class.getSimpleName();

    private static Holder holder = new Holder();

    public static MusicControlKitInit getInstance() {
        return holder.instance;
    }

    private boolean enableRefreshAndLoadMore;

    @Override
    public void init(Context context) {
        setEnableRefreshAndLoadMore(true);
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

    public boolean isEnableRefreshAndLoadMore() {
        return enableRefreshAndLoadMore;
    }

    public void setEnableRefreshAndLoadMore(boolean enableRefreshAndLoadMore) {
        this.enableRefreshAndLoadMore = enableRefreshAndLoadMore;
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
        private MusicControlKitInit instance = new MusicControlKitInit();
    }
}
