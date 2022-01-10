package cn.rongcloud.corekit.core;

import android.text.TextUtils;

import java.io.File;

import cn.rongcloud.corekit.BuildConfig;
import cn.rongcloud.corekit.api.RCSceneKitEngine;

/**
 * Created by gyn on 2021/12/8
 */
public class CoreKitConstant {
    public static final String ROOT_FOLDER = "RCSceneKit";
    public static final String ROOT_CONFIG_NAME = "RCSceneKit.json";
    public static final String KIT_CONFIG_NAME = "KitConfig.json";
    public static final String TEMP_FOLDER = "temp";
    public static final String ASSETS_FOLDER = "assets";

    private static String rootPath;

    /**
     * 获取kit根目录
     *
     * @return eg: /data/user/0/cn.rongcloud.scenekitdemo/files/RCSceneKit
     */
    public static String getRootPath() {
        if (TextUtils.isEmpty(rootPath)) {
            rootPath = RCSceneKitEngine.getInstance().getContext().getFilesDir().getAbsolutePath() +
                    File.separator +
                    ROOT_FOLDER;

        }
        return rootPath;
    }

    /**
     * 获取根目录下所有kit配置信息的文件路径
     *
     * @return eg: /data/user/0/cn.rongcloud.scenekitdemo/files/RCSceneKit/RCSceneKit.json
     */
    public static String getRootConfigName() {
        return rootPath + File.separator + ROOT_CONFIG_NAME;
    }

    /**
     * @return 临时文件目录
     */
    public static String getTempPath() {
        return getRootPath() + File.separator + TEMP_FOLDER;
    }

    /**
     * 获取 KitInfo.json 的路径
     *
     * @param kitName kit名称
     * @return eg: /data/user/0/cn.rongcloud.scenekitdemo/files/RCSceneKit/${kitName}/${kitName}.json
     */
    public static String getKitInfoPath(String kitName) {
        return getRootPath() + File.separator + kitName + File.separator + kitName + ".json";
    }

    /**
     * 获取 KitConfig.json 的路径
     *
     * @param kitName kit名称
     * @param kitId
     * @return eg: /data/user/0/cn.rongcloud.scenekitdemo/files/RCSceneKit/ChatRoomKit/${kitId}/KitConfig.json
     */
    public static String getKitConfigPath(String kitName, String kitId) {
        return getKitPath(kitName, kitId) + File.separator + KIT_CONFIG_NAME;
    }

    /**
     * 获取 kit文件夹 的路径
     *
     * @param kitName kit名称
     * @param kitId   kit的id
     * @return eg: /data/user/0/cn.rongcloud.scenekitdemo/files/RCSceneKit/ChatRoomKit/${kitId}
     */
    public static String getKitPath(String kitName, String kitId) {
        return getRootPath() + File.separator + kitName + File.separator + kitId;
    }

    /**
     * 获取 kit 的 assets 路径
     *
     * @param kitName kit名称
     * @param kitId   kit的id
     * @return eg: /data/user/0/cn.rongcloud.scenekitdemo/files/RCSceneKit/ChatRoomKit/${kitId}/assets
     */
    public static String getKitAssetsPath(String kitName, String kitId) {
        return getKitPath(kitName, kitId) + File.separator + ASSETS_FOLDER;
    }

    /**
     * 接口 api
     */
    public static class Api {
        public static final String KIT_INFO_LIST = BuildConfig.RC_SCENE_KIT_BASE_URL + "/kit/info_list";
    }
}
