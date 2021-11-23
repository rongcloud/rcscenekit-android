package cn.rongcloud.corekit.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hugo on 2021/11/15
 */
public class FileUtils {

    private static final String TAG = VMLog.getTag(FileUtils.class);

    public static String getStringFromAssets(Context context, String name) {
        try {
            InputStream inputStream = context.getAssets().open(name);
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            VMLog.e(TAG, "open assets failed: " + e.getLocalizedMessage());
        }
        return "";
    }
}
