package cn.rongcloud.corekit.core;

import android.content.Context;
import android.text.TextUtils;

import org.apache.commons.codec.digest.DigestUtils;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.rongcloud.corekit.api.IRCSceneKitEngine;
import cn.rongcloud.corekit.bean.KitInfo;
import cn.rongcloud.corekit.net.oklib.OkApi;
import cn.rongcloud.corekit.net.oklib.WrapperCallBack;
import cn.rongcloud.corekit.net.oklib.api.callback.FileIOCallBack;
import cn.rongcloud.corekit.net.oklib.interceptor.RetryInterceptor;
import cn.rongcloud.corekit.net.oklib.wrapper.Wrapper;
import cn.rongcloud.corekit.utils.FileUtils;
import cn.rongcloud.corekit.utils.GsonUtil;
import cn.rongcloud.corekit.utils.HandlerUtils;
import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.corekit.utils.VMLog;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by gyn on 2021/11/15
 */
public class RCSceneKitEngineImpl implements IRCSceneKitEngine {

    private final static String TAG = RCSceneKitEngineImpl.class.getSimpleName();

    private final static Holder holder = new Holder();
    private final Map<String, RCKitInit<?>> kitInitMap = new HashMap<>();
    private Context mContext;
    private String mAppKey;

    public static IRCSceneKitEngine getInstance() {
        return holder.instance;
    }

    /**
     * 初始化SDK
     *
     * @param context 上下文
     * @param appKey  appKey
     */
    @Override
    public void initWithAppKey(Context context, String appKey) {
        mContext = context.getApplicationContext();
        mAppKey = appKey;
        createRootFolder();
        loadAllKitInfo();
    }

    /**
     * 创建根目录文件夹
     */
    public void createRootFolder() {
        File file = new File(CoreKitConstant.getRootPath());
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * 下载所有kit的版本信息
     */
    private void loadAllKitInfo() {
        if (TextUtils.isEmpty(mAppKey)) {
            return;
        }
        OkApi.config(new OkHttpClient.Builder().addInterceptor(new RetryInterceptor(2)));
        // TODO 替换成正式地址
        OkApi.get(CoreKitConstant.Api.KIT_INFO_LIST, null, new WrapperCallBack() {
            @Override
            public void onResult(Wrapper result) {
                VMLog.d(TAG, "kit info load success");
                if (result.ok()) {
                    List<KitInfo> kitInfoList = result.getList(KitInfo.class);
                    updateAllKitInfo(GsonUtil.obj2Json(kitInfoList));
                    if (ListUtil.isNotEmpty(kitInfoList)) {
                        VMLog.d(TAG, "your kit size :" + kitInfoList.size());
                        for (KitInfo kitInfo : kitInfoList) {
                            initSubKit(kitInfo);
                            checkKitNeedUpdate(kitInfo);
                        }
                    }
                } else {
                    loadLocalAllKitInfo();
                }
            }

            @Override
            public void onError(int code, String msg) {
                super.onError(code, msg);
                loadLocalAllKitInfo();
                VMLog.e(TAG, "kit info load failed :" + code + "->" + msg);
            }
        });
    }

    /**
     * 更新根目录下RCSceneKit.json文件
     *
     * @param allKitInfoJson json
     */
    private void updateAllKitInfo(String allKitInfoJson) {
        if (allKitInfoJson == null) {
            allKitInfoJson = "";
        }
        FileUtils.writeString(CoreKitConstant.getRootConfigName(), allKitInfoJson);
    }

    /**
     * 加载根目录下RCSceneKit.json文件
     */
    private void loadLocalAllKitInfo() {
        File file = new File(CoreKitConstant.getRootConfigName());
        if (file.exists() && file.isFile()) {
            String allKitInfo = FileUtils.getStringFromFile(file);
            if (!TextUtils.isEmpty(allKitInfo)) {
                List<KitInfo> kitInfoList = GsonUtil.json2List(allKitInfo, KitInfo.class);
                if (ListUtil.isNotEmpty(kitInfoList)) {
                    for (KitInfo kitInfo : kitInfoList) {
                        initSubKit(kitInfo);
                        checkKitNeedUpdate(kitInfo);
                    }
                }
            }
        }
    }

    /**
     * 初始化子模块
     *
     * @param kitInfo
     */
    private void initSubKit(KitInfo kitInfo) {
        try {
            if (kitInfo == null || TextUtils.isEmpty(kitInfo.getName())) {
                VMLog.e(TAG, "kitInfo is null or kitInfo.name is null");
                return;
            }
            Class<?> c = Class.forName(String.format("cn.rongcloud.%s.RC%s", kitInfo.getName().toLowerCase(Locale.ROOT), kitInfo.getName()));
            Method m = c.getDeclaredMethod("getInstance");
            Object o = m.invoke(null);
            if (o instanceof RCKitInit) {
                RCKitInit<?> kitInit = (RCKitInit<?>) o;
                kitInitMap.put(kitInfo.getName(), kitInit);
                kitInit.setUseLocal(true);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            VMLog.e(TAG, kitInfo.getName() + ": " + e.getLocalizedMessage());
        }
    }

    /**
     * 检查kit是否需要更新
     *
     * @param kitInfo KitInfo
     */
    private void checkKitNeedUpdate(KitInfo kitInfo) {
        if (kitInfo == null) {
            return;
        }
        String path = CoreKitConstant.getKitInfoPath(kitInfo.getName());
        File file = new File(path);
        if (file.exists()) {
            String json = FileUtils.getStringFromFile(file);
            KitInfo localKitInfo = GsonUtil.json2Obj(json, KitInfo.class);
            if (!kitInfo.equals(localKitInfo)) {
                loadKitZip(kitInfo);
            }
        } else {
            loadKitZip(kitInfo);
        }
    }

    /**
     * 下载压缩包
     *
     * @param kitInfo KitInfo
     */
    private void loadKitZip(KitInfo kitInfo) {
        if (kitInfo == null) {
            return;
        }
        String tempKitName = kitInfo.getKitId() + ".zip";
        OkApi.download(kitInfo.getUrl(), null, new FileIOCallBack(CoreKitConstant.getTempPath(), tempKitName) {
            @Override
            public File onParse(Response response) throws IOException {
                return super.onParse(response);
            }

            @Override
            public File saveFile(Response response) throws IOException {
                File file = super.saveFile(response);
                VMLog.d(TAG, "kit zip load success");
                // 对文件取MD5，对比完整性
                String md5 = DigestUtils.md5Hex(new FileInputStream(file));
                if (kitInfo.isValidate(md5)) {
                    // kit存储目录
                    File kitFile = new File(CoreKitConstant.getKitPath(kitInfo.getName(), kitInfo.getKitId()));
                    // 删除原有目录中的文件
                    if (kitFile.exists() && kitFile.isDirectory()) {
                        FileUtils.delAllFile(kitFile.getAbsolutePath());
                    } else {
                        kitFile.mkdirs();
                    }
                    // 解压缩zip到指定目录
                    ZipUtil.unpack(file, kitFile);
                    // 更新kitInfo信息
                    updateKitInfo(kitInfo);
                    // 刷新指定kit的kitConfig
                    refreshKitConfig(kitInfo);
                }
                return file;
            }

            @Override
            public void onResult(File result) {
                super.onResult(result);
                // 删除zip文件
                if (result != null && result.exists()) {
                    result.delete();
                }
            }

            @Override
            public void onError(int code, String msg) {
                super.onError(code, msg);
                VMLog.e(TAG, "kit zip load failed :" + code + "->" + msg);
            }
        });
    }

    /**
     * 更新KitInfo信息
     *
     * @param kitInfo KitInfo
     */
    private void updateKitInfo(KitInfo kitInfo) {
        FileUtils.writeString(CoreKitConstant.getKitInfoPath(kitInfo.getName()), GsonUtil.obj2Json(kitInfo));
    }

    /**
     * 刷新指定kit的配置信息
     */
    private void refreshKitConfig(KitInfo kitInfo) {
        HandlerUtils.mainThreadPost(() -> {
            RCKitInit<?> kitInit = kitInitMap.get(kitInfo.getName());
            if (kitInit != null) {
                kitInit.setNeedRefresh();
            }
        });
    }

    private static class Holder {
        private final IRCSceneKitEngine instance = new RCSceneKitEngineImpl();
    }

}
