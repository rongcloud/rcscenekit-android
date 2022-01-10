package cn.rongcloud.corekit.core;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import cn.rongcloud.corekit.api.RCSceneKitEngine;
import cn.rongcloud.corekit.bean.KitInfo;
import cn.rongcloud.corekit.utils.FileUtils;
import cn.rongcloud.corekit.utils.GsonUtil;
import cn.rongcloud.corekit.utils.ObjUtil;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by gyn on 2021/11/18
 */
public abstract class RCKitInit<T> {
    private static final String TAG = RCKitInit.class.getSimpleName();
    private T kitConfig;
    private String assetsPath;
    /**
     * 这个用来存储 KitConfig 被读取的次数，用来决定更新KitConfig的时机
     * 如果还没被使用useCount=0，从网络下载下来的数据可以直接刷新内存里的KitConfig
     * 如果已经被使用useCount>0，当view销毁useCount-1,直到没有view在使用当前KitConfig且有新配置待更新，则主动刷新内存
     * 每次初始化view后读取config时useCount+1
     */
    private final AtomicInteger useCount = new AtomicInteger(0);
    private final AtomicBoolean needRefresh = new AtomicBoolean(false);

    private boolean useLocal = false;

    /**
     * 初始化 kit
     *
     * @param context
     */
    public void init(Context context) {
        getKitConfig();
    }

    public abstract String getKitConfigName();

    /**
     * @return 返回 Kit 配置信息
     */
    public T getKitConfig() {
        if (kitConfig != null) {
            return kitConfig;
        } else {
            return refreshKitConfig();
        }
    }

    /**
     * 刷新 Kit 配置信息
     *
     * @return Kit 配置信息
     */
    public T refreshKitConfig() {
        if (useLocal) {
            assetsPath = null;
            kitConfig = getLocalKitConfig();
        }
        if (kitConfig == null) {
            kitConfig = getDefaultKitConfig();
        }
        return kitConfig;
    }

    /**
     * 从 kit 的 assets 中加载配置信息，kit 的 assets 文件夹下必须要有 ${kitName}.json 文件
     *
     * @return kit 配置信息
     */
    private T getDefaultKitConfig() {
        String kitConfigName = getKitConfigName();
        if (TextUtils.isEmpty(kitConfigName)) {
            throw new RuntimeException("Please return KitConfigName in " + getClass().getSimpleName());
        }
        String json = FileUtils.getStringFromAssets(RCSceneKitEngine.getInstance().getContext(), getKitConfigName() + ".json");
        if (TextUtils.isEmpty(json)) {
            throw new RuntimeException("Please add " + kitConfigName + ".json in your assets");
        }
        T t = parseJson(json);
        if (t == null) {
            VMLog.e(TAG, "Local KitConfig parse failed in " + getClass().getSimpleName());
        }
        return t;
    }

    /**
     * 从 kit 的 缓存目录中加载配置信息
     *
     * @return kit 配置信息
     */
    private T getLocalKitConfig() {
        File kitInfoFile = new File(CoreKitConstant.getKitInfoPath(getKitConfigName()));
        if (!kitInfoFile.exists()) {
            VMLog.d(TAG, String.format("%s.json is not exists in %s", getKitConfigName(), getKitConfigName()));
            return null;
        }
        String kitInfoJson = FileUtils.getStringFromFile(kitInfoFile);
        KitInfo kitInfo = GsonUtil.json2Obj(kitInfoJson, KitInfo.class);
        if (kitInfo == null) {
            VMLog.d(TAG, String.format("kitInfo is parse failed in %s", getKitConfigName()));
            return null;
        }
        File configFile = new File(CoreKitConstant.getKitConfigPath(kitInfo.getName(), kitInfo.getKitId()));
        if (!configFile.exists()) {
            VMLog.d(TAG, String.format("%s is not exists in %s", CoreKitConstant.KIT_CONFIG_NAME, getKitConfigName()));
            return null;
        }
        String json = FileUtils.getStringFromFile(configFile);
        if (TextUtils.isEmpty(json)) {
            VMLog.d(TAG, String.format("%s is empty in %s", CoreKitConstant.KIT_CONFIG_NAME, getKitConfigName()));
            return null;
        }
        T t = parseJson(json);
        if (t != null) {
            assetsPath = CoreKitConstant.getKitAssetsPath(kitInfo.getName(), kitInfo.getKitId());
            VMLog.d(TAG, String.format("Get %s success in local %s", CoreKitConstant.KIT_CONFIG_NAME, getKitConfigName()));
        }
        return t;
    }

    /**
     * @param json 要解析的json
     * @return T
     */
    private T parseJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        try {
            return GsonUtil.json2Obj(json, (Class<T>) (ObjUtil.getTType(getClass())[0]));
        } catch (Exception e) {
            VMLog.e(TAG, e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * @return 返回资源文件目录
     */
    public String getAssetsPath() {
        return assetsPath;
    }

    /**
     * 使用+1
     */
    public void incrementUse() {
        int count = useCount.incrementAndGet();
        VMLog.d(TAG, "increment use count is " + count);
    }

    /**
     * 使用-1
     */
    public void decrementUse() {
        int count = useCount.decrementAndGet();
        VMLog.d(TAG, "decrement use count is " + count);
        checkRefresh();
    }

    /**
     * 设置需要刷新
     */
    public void setNeedRefresh() {
        needRefresh.set(true);
        VMLog.d(TAG, "set need refresh");
        checkRefresh();
    }

    /**
     * 没有使用者且需要更新时，更新
     */
    private void checkRefresh() {
        if (useCount.get() == 0 && needRefresh.get()) {
            needRefresh.set(false);
            refreshKitConfig();
        }
    }

    /**
     * 是否使用本地sd卡上缓存的版本
     *
     * @param useLocal true使用，false使用默认配置
     */
    public void setUseLocal(boolean useLocal) {
        this.useLocal = useLocal;
    }
}
