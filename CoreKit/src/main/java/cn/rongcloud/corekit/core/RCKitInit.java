package cn.rongcloud.corekit.core;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;

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
    private Context context;
    private String assetsPath;

    /**
     * 初始化 kit
     *
     * @param context
     */
    public void init(Context context) {
        this.context = context;
        getKitConfig();
    }

    public abstract String getKitConfigName();

    /**
     * @return 返回 Kit 配置信息
     */
    public T getKitConfig() {
        if (context == null) {
            throw new RuntimeException("Please add super.init(context) in " + getClass().getSimpleName());
        }
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
        assetsPath = null;
        kitConfig = getLocalKitConfig();
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
        if (context == null) {
            throw new RuntimeException("Please add super.init(context) in " + getClass().getSimpleName());
        }
        String kitConfigName = getKitConfigName();
        if (TextUtils.isEmpty(kitConfigName)) {
            throw new RuntimeException("Please return KitConfigName in " + getClass().getSimpleName());
        }
        String json = FileUtils.getStringFromAssets(context, getKitConfigName() + ".json");
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
}
