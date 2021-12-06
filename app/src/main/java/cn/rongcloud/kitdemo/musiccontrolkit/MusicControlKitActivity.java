package cn.rongcloud.kitdemo.musiccontrolkit;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hfopen.sdk.manager.HFOpenApi;

import cn.rongcloud.corekit.utils.VMLog;
import cn.rongcloud.kitdemo.MyApplication;
import cn.rongcloud.kitdemo.R;
import cn.rongcloud.kitdemo.utils.ToastUtil;
import cn.rongcloud.musiccontrolkit.MusicControlDialog;
import cn.rongcloud.musiccontrolkit.MusicEngine;
import cn.rongcloud.voiceroom.api.RCVoiceRoomEngine;
import cn.rongcloud.voiceroom.api.callback.RCVoiceRoomCallback;
import cn.rongcloud.voiceroom.model.RCVoiceRoomInfo;

/**
 * Created by hugo on 2021/11/25
 */
public class MusicControlKitActivity extends AppCompatActivity {

    private static final String TAG = MusicControlKitActivity.class.getSimpleName();
    // 假定用户id,房间名，房间id都是下面的值
    private Button btnCreate;
    private Button btnJoin;
    private Button btnShow;
    private Button btnExit;
    private MusicControlDialog dialog;

    public static void launch(Context context) {
        context.startActivity(new Intent(context, MusicControlKitActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musiccontrolkit);

        btnCreate = (Button) findViewById(R.id.btn_create);
        btnJoin = (Button) findViewById(R.id.btn_join);
        btnShow = (Button) findViewById(R.id.btn_show);
        btnExit = (Button) findViewById(R.id.btn_exit);

        btnShow.setEnabled(false);
        // 模拟房主创建一个语聊房，然后可以在房间内播放音乐
        btnCreate.setOnClickListener(v -> {
            // 权限判断
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, 0);
                } else {
                    connectUser1();
                }
            } else {
                connectUser1();
            }
        });
        // 模拟观众加入一个语聊房，可以听到房主播放的音乐
        btnJoin.setOnClickListener(v -> {
            connectUser2();
        });
        // 音乐列表的弹框
        btnShow.setOnClickListener(v -> {
            dialog = new MusicControlDialog(MusicControlManager.getInstance(), MusicControlManager.getInstance(), MusicControlManager.getInstance());
            dialog.show(getSupportFragmentManager(), TAG);
        });

        btnExit.setOnClickListener(v -> {
            leaveRoom();
        });

        initHiFive();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean granted = true;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                granted = false;
                break;
            }
        }
        if (granted) {
            connectUser1();
        }
    }

    private void connectUser1() {
        RCVoiceRoomEngine.getInstance().disconnect(false);
        RCVoiceRoomEngine.getInstance().connectWithToken(UserConstant.USER1.getToken(), new RCVoiceRoomCallback() {
            @Override
            public void onSuccess() {
                VMLog.d(TAG, "连接成功");
                createAndJoinRoom();
            }

            @Override
            public void onError(int i, String s) {
                VMLog.e(TAG, "连接失败 " + i + ":" + s);
            }
        });
    }

    private void connectUser2() {
        RCVoiceRoomEngine.getInstance().disconnect(false);
        RCVoiceRoomEngine.getInstance().connectWithToken(UserConstant.USER2.getToken(), new RCVoiceRoomCallback() {
            @Override
            public void onSuccess() {
                VMLog.d(TAG, "连接成功");
                joinRoom();
            }

            @Override
            public void onError(int i, String s) {
                VMLog.e(TAG, "连接失败 " + i + ":" + s);
            }
        });
    }

    /**
     * 使用融云语聊房SDk创建并加入一个房间
     * 创建并加入房间
     */
    private void createAndJoinRoom() {
        RCVoiceRoomInfo voiceRoomInfo = new RCVoiceRoomInfo();
        voiceRoomInfo.setMuteAll(false);
        voiceRoomInfo.setLockAll(false);
        voiceRoomInfo.setRoomName(UserConstant.USER1.getUserId());
        voiceRoomInfo.setFreeEnterSeat(true);
        voiceRoomInfo.setSeatCount(9);
        RCVoiceRoomEngine.getInstance().createAndJoinRoom(UserConstant.USER1.getUserId(), voiceRoomInfo, new RCVoiceRoomCallback() {
            @Override
            public void onSuccess() {
                VMLog.d(TAG, "创建加入成功");
                enterSeat();
            }

            @Override
            public void onError(int i, String s) {
                logError("创建加入失败", i, s);
            }
        });
    }

    private void joinRoom() {
        RCVoiceRoomEngine.getInstance().joinRoom(UserConstant.USER1.getUserId(), new RCVoiceRoomCallback() {
            @Override
            public void onSuccess() {
                VMLog.d(TAG, "加入成功");
                ToastUtil.show("加入房间成功");
                btnCreate.setEnabled(false);
                btnJoin.setEnabled(false);
            }

            @Override
            public void onError(int i, String s) {
                logError("加入失败", i, s);
            }
        });
    }

    /**
     * 上麦
     */
    private void enterSeat() {
        RCVoiceRoomEngine.getInstance().enterSeat(0, new RCVoiceRoomCallback() {
            @Override
            public void onSuccess() {
                VMLog.d(TAG, "上麦成功");
                ToastUtil.show("上麦成功");
                btnShow.setEnabled(true);
                btnJoin.setEnabled(false);
                btnCreate.setEnabled(false);
            }

            @Override
            public void onError(int i, String s) {
                logError("上麦失败", i, s);
                ToastUtil.show("上麦失败，请退出页面重试");
            }
        });
    }

    /**
     * 这里使用了HiFive提供的音乐能力
     * 这里做下初始化，您可以用自己的音乐服务
     */
    private void initHiFive() {
        HFOpenApi.registerApp(MyApplication.app, UserConstant.USER1.getUserId());
        HFOpenApi.setVersion("V4.1.2");
    }

    @Override
    protected void onDestroy() {
        /**
         * 页面销毁离开房间
         */
        leaveRoom();
        super.onDestroy();
    }

    /**
     * 离开房间
     */
    private void leaveRoom() {
        // 可以销毁
        MusicEngine.getInstance().release();
        RCVoiceRoomEngine.getInstance().leaveRoom(new RCVoiceRoomCallback() {
            @Override
            public void onSuccess() {
                VMLog.d(TAG, "离开成功");
                btnJoin.setEnabled(true);
                btnCreate.setEnabled(true);
                btnShow.setEnabled(false);
            }

            @Override
            public void onError(int i, String s) {
                logError("离开失败", i, s);
            }
        });
    }

    private void logError(String method, int i, String s) {
        VMLog.d(TAG, method + " " + i + ":" + s);
    }

}
