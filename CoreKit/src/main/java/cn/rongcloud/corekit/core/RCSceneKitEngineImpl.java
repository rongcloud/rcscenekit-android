package cn.rongcloud.corekit.core;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.rongcloud.corekit.annotation.KitBean;
import cn.rongcloud.corekit.api.ICoreKitInit;
import cn.rongcloud.corekit.api.IRCSceneKitEngine;
import cn.rongcloud.corekit.utils.FileUtils;
import cn.rongcloud.corekit.utils.GsonUtil;
import cn.rongcloud.corekit.utils.VMLog;

/**
 * Created by hugo on 2021/11/15
 */
public class RCSceneKitEngineImpl implements IRCSceneKitEngine {

    private final static String TAG = VMLog.getTag(RCSceneKitEngineImpl.class);

    private final static Holder holder = new Holder();
    private final Map<String, Object> configMap = new HashMap<>();
    private final List<ICoreKitInit> coreKitInitList = new ArrayList<>();
    private JSONObject configObject = null;

    public static IRCSceneKitEngine getInstance() {
        return holder.instance;
    }

    /**
     * 注册Kit模块
     *
     * @param kit 要注册的kit
     */
    @Override
    public void installKit(ICoreKitInit... kit) {
        if (kit == null) {
            return;
        }
        for (ICoreKitInit iCoreKitInit : kit) {
            if (!coreKitInitList.contains(iCoreKitInit)) {
                coreKitInitList.add(iCoreKitInit);
            }
        }
    }

    /**
     * 初始化SDK
     *
     * @param context 上下文
     * @param appKey  appKey
     */
    @Override
    public void initWithAppKey(Context context, String appKey) {
        Context mContext = context.getApplicationContext();
        initSubKit(mContext);
        loadJson(mContext, appKey);
    }

    /**
     * 获取对应Kit的UI参数
     *
     * @param c   kit对应的数据类
     * @param <T> 泛型
     * @return 返回kit对应的UI数据
     */
    @Override
    public <T> T getKitConfig(Class<T> c) {
        KitBean kitBean = (KitBean) c.getAnnotation(KitBean.class);
        if (kitBean == null) {
            VMLog.e(TAG, "Please add @KitBean annotation in your class");
            return null;
        }

        String parseKey = kitBean.parseKey();

        if (configMap.containsKey(parseKey)) {
            return (T) configMap.get(parseKey);
        }

        if (null == configObject) {
            VMLog.e(TAG, "Please initWithAppKey at your application");
            return null;
        }

        JSONObject object = parseObject(configObject, parseKey);
        if (null == object) {
            VMLog.d(TAG, "Config file is not contains filed " + parseKey);
            return null;
        }

        T t = GsonUtil.json2Obj(object.toString(), c);
        configMap.put(parseKey, t);

        return t;
    }

    private JSONObject parseObject(JSONObject object, String key) {
        if (object.has(key)) {
            return object.optJSONObject(key);
        }
        Iterator<String> keys = object.keys();
        String k;
        JSONObject o;
        while (keys.hasNext()) {
            k = keys.next();
            o = object.optJSONObject(k);
            if (o != null) {
                return parseObject(o, key);
            }
        }
        return null;
    }

    /**
     * 加载config文件或从网络下载
     *
     * @param context 上下文
     * @param appKey  appKey
     */
    private void loadJson(Context context, String appKey) {
        // TODO 加载网络配置
        // 加载默认配置
        String configJson = FileUtils.getStringFromAssets(context, "KitConfig.json");
        try {
            configObject = new JSONObject(configJson);
        } catch (JSONException e) {
            VMLog.e(TAG, e.getLocalizedMessage());
        }
    }

    /**
     * 初始化子模块Kit
     */
    private void initSubKit(Context context) {
        for (ICoreKitInit k : coreKitInitList) {
            k.init(context);
        }
    }

    private static class Holder {
        private final IRCSceneKitEngine instance = new RCSceneKitEngineImpl();
    }

}
