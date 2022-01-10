package cn.rongcloud.corekit.utils;

import java.util.List;

/**
 * Created by gyn on 2021/11/30
 */
public class ListUtil {
    public static boolean isNotEmpty(List list) {
        return list != null && !list.isEmpty();
    }

    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }
}
