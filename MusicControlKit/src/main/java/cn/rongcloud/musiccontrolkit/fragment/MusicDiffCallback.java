package cn.rongcloud.musiccontrolkit.fragment;

import android.text.TextUtils;

import androidx.recyclerview.widget.DiffUtil;

import java.util.List;

import cn.rongcloud.musiccontrolkit.bean.Music;

/**
 * Created by gyn on 2021/11/24
 */
public class MusicDiffCallback extends DiffUtil.Callback {

    private List<Music> oldMusicList;
    private List<Music> newMusicList;

    public MusicDiffCallback(List<Music> oldMusicList, List<Music> newMusicList) {
        this.oldMusicList = oldMusicList;
        this.newMusicList = newMusicList;
    }

    @Override
    public int getOldListSize() {
        return oldMusicList.size();
    }

    @Override
    public int getNewListSize() {
        return newMusicList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return TextUtils.equals(oldMusicList.get(oldItemPosition).getMusicId(), newMusicList.get(newItemPosition).getMusicId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMusicList.get(oldItemPosition).equals(newMusicList.get(newItemPosition));
    }
}
