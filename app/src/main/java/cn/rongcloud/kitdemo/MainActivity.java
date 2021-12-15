package cn.rongcloud.kitdemo;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import cn.rongcloud.kitdemo.chatroomkit.ChatRoomKitActivity;
import cn.rongcloud.kitdemo.musiccontrolkit.MusicControlKitActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showChatRoomKit(View view) {
        ChatRoomKitActivity.launch(this);
    }

    public void showMusicControlKit(View view) {
        MusicControlKitActivity.launch(this);
    }
}