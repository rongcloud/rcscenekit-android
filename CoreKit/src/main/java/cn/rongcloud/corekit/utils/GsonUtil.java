package cn.rongcloud.corekit.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;


public class GsonUtil {

    private final static String TAG = GsonUtil.class.getSimpleName();
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    private final static Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();

    public static String obj2Json(Object obj) {
        return null == obj ? "" : gson.toJson(obj);
    }

    public static <R> R json2Obj(String json, Class<R> clazz) {
        if (null == clazz) {
            Log.e(TAG, "the clazz can not null!");
            return null;
        }
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    public static <R> R jsonElem2Obj(JsonElement json, Class<R> clazz) {
        if (null == clazz) {
            Log.e(TAG, "the clazz can not null!");
            return null;
        }
        try {
            return gson.fromJson(json, clazz);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * @param element 待解析的JsonElement
     * @param clazz   字节码文件
     * @param <R>     result的类型
     * @param <T>     解析实体的类 result是实体：R和T一样
     *                result是集合：R 是List<T>
     * @throws Exception
     */
    public static <R, T> R json2Obj(JsonElement element, Class<T> clazz) {
        if (null == clazz) {
            Log.e(TAG, "the clazz can not null!");
            return null;
        }
        if (element.isJsonArray()) {//list
            List<T> lst = new ArrayList<T>();
            JsonArray array = element.getAsJsonArray();
            for (JsonElement elem : array) {
                lst.add(gson.fromJson(elem, clazz));
            }
            return (R) lst;
        } else if (element.isJsonObject()) {//obj
            return (R) gson.fromJson(element, clazz);
        }
        return null;
    }

    public static <T> List<T> json2List(JsonElement element, Class<T> clazz) {
        if (null == clazz) {
            Log.e(TAG, "the clazz can not null!");
            return null;
        }
        List<T> lst = new ArrayList<T>();
        if (element instanceof JsonArray) {//兼融list
            JsonArray array = element.getAsJsonArray();
            for (JsonElement elem : array) {
                lst.add(gson.fromJson(elem, clazz));
            }
        } else if (element instanceof JsonObject) {//obj
            lst.add(gson.fromJson(element, clazz));
        }
        return lst;
    }

    public static <T> List<T> json2List(String json, Class<T> clazz) {
        if (null == clazz) {
            VMLog.e(TAG, "the clazz can not null!");
            return null;
        }
        List<T> lst = new ArrayList<T>();
        try {
//            JsonElement jsonElement = new JsonParser().parse(json);
            JsonElement jsonElement = JsonParser.parseString(json);
            if (jsonElement instanceof JsonArray) {//兼融list
                JsonArray array = jsonElement.getAsJsonArray();
                for (JsonElement elem : array) {
                    lst.add(gson.fromJson(elem, clazz));
                }
            } else if (jsonElement instanceof JsonObject) {//obj
                lst.add(gson.fromJson(json, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }
}
