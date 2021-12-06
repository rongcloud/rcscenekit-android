package cn.rongcloud.musiccontrolkit.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.corekit.base.BaseFragment;
import cn.rongcloud.corekit.utils.GlideUtil;
import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.corekit.utils.VMLog;
import cn.rongcloud.musiccontrolkit.MusicControlKitInit;
import cn.rongcloud.musiccontrolkit.MusicEngine;
import cn.rongcloud.musiccontrolkit.R;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.iinterface.OnSwitchPageListener;
import cn.rongcloud.musiccontrolkit.widget.MusicPlayingView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hugo on 2021/11/24
 */
public class MusicListFragment extends BaseFragment {
    private static final String TAG = MusicListFragment.class.getSimpleName();
    private final OnSwitchPageListener onSwitchPageListener;
    private RecyclerView rvMusicList;
    private LinearLayout llEmpty;
    private TextView tvAddMusic;
    private MusicListAdapter adapter;
    private List<Music> musicList = new ArrayList<>();
    private SmartRefreshLayout srlRefresh;

    public MusicListFragment(OnSwitchPageListener onSwitchPageListener) {
        this.onSwitchPageListener = onSwitchPageListener;
    }

    public static MusicListFragment getInstance(OnSwitchPageListener onSwitchPageListener) {
        return new MusicListFragment(onSwitchPageListener);
    }

    @Override
    public int setLayoutId() {
        return R.layout.rckit_fragment_music_list;
    }

    @Override
    public void initView() {
        rvMusicList = getLayout().findViewById(R.id.rv_music_list);
        srlRefresh = getLayout().findViewById(R.id.srl_refresh);
        llEmpty = getLayout().findViewById(R.id.ll_empty);
        tvAddMusic = getLayout().findViewById(R.id.tv_add_music);

        adapter = new MusicListAdapter();
        rvMusicList.setAdapter(adapter);

        boolean enableRefreshAndLoadMore = MusicControlKitInit.getInstance().isEnableRefreshAndLoadMore();
        srlRefresh.setEnableLoadMore(enableRefreshAndLoadMore);
        srlRefresh.setEnableRefresh(enableRefreshAndLoadMore);

        srlRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMoreData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
            }
        });

        tvAddMusic.setOnClickListener(v -> {
            if (onSwitchPageListener != null) {
                onSwitchPageListener.onSwitch(1);
            }
        });

        refreshData();
        listenMusicListChanged();
    }

    private void refreshData() {
        MusicEngine.getInstance().onLoadMusicList(new DataCallback<List<Music>>() {
            @Override
            public void onResult(List<Music> musicList) {
                MusicEngine.getInstance().setMusicList(musicList);
            }
        });
    }

    private void loadMoreData() {
        MusicEngine.getInstance().onLoadMoreMusicList(new DataCallback<List<Music>>() {
            @Override
            public void onResult(List<Music> musicList) {
                MusicEngine.getInstance().addMusicList(musicList);
            }
        });
    }

    /**
     * 监听音乐列表变化刷新UI
     */
    private void listenMusicListChanged() {
        MusicEngine.getInstance().musicListObserve().observe(this, new Observer<List<Music>>() {
            @Override
            public void onChanged(List<Music> list) {
                VMLog.e(TAG, "music list changed :" + list.size() + Thread.currentThread());
                List<Music> newMusicList = new ArrayList<>();
                if (ListUtil.isEmpty(list)) {
                    llEmpty.setVisibility(View.VISIBLE);
                } else {
                    llEmpty.setVisibility(View.GONE);
                    newMusicList.addAll(list);
                }
                DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MusicDiffCallback(musicList, newMusicList));
                musicList = newMusicList;
                diffResult.dispatchUpdatesTo(adapter);

                srlRefresh.finishRefresh();
                srlRefresh.finishLoadMore();
            }
        });
        MusicEngine.getInstance().playingObserve().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                VMLog.e(TAG, "music playing changed :" + aBoolean);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        MusicListManager.getInstance().refresh();
    }

    class MusicListAdapter extends RecyclerView.Adapter<MusicItemViewHolder> {

        @NonNull
        @Override
        public MusicItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MusicItemViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.rckit_item_music_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull MusicItemViewHolder holder, int position) {
            holder.initView(musicList.get(position));
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }
    }

    class MusicItemViewHolder extends RecyclerView.ViewHolder {
        DecimalFormat df = new DecimalFormat("#0.00");
        private CircleImageView civTheme;
        private ImageView ivPlay;
        private TextView tvMusicName;
        private TextView tvAuthor;
        private TextView tvSize;
        private ImageView ivTop;
        private ImageView ivDel;
        private MusicPlayingView mpvPlaying;

        public MusicItemViewHolder(@NonNull View itemView) {
            super(itemView);
            civTheme = (CircleImageView) itemView.findViewById(R.id.civ_theme);
            ivPlay = (ImageView) itemView.findViewById(R.id.iv_play);
            tvMusicName = (TextView) itemView.findViewById(R.id.tv_music_name);
            tvAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            tvSize = (TextView) itemView.findViewById(R.id.tv_size);
            ivTop = (ImageView) itemView.findViewById(R.id.iv_top);
            ivDel = (ImageView) itemView.findViewById(R.id.iv_del);
            mpvPlaying = (MusicPlayingView) itemView.findViewById(R.id.mpv_playing);
        }

        public void initView(Music music) {
            GlideUtil.loadImage(civTheme, music.getCoverUrl());
            tvMusicName.setText(music.getMusicName());
            tvAuthor.setText(music.getAuthor());
            tvSize.setText(getSizeString(music.getSize()));
            boolean isPlayingMusic = MusicEngine.getInstance().isPlayingMusic(music);
            if (isPlayingMusic && MusicEngine.getInstance().isPlaying()) {
                ivTop.setVisibility(View.GONE);
                ivDel.setVisibility(View.GONE);
                mpvPlaying.setVisibility(View.VISIBLE);
                ivPlay.setImageResource(R.drawable.rckit_ic_music_pause_cover);
                mpvPlaying.start();
                int color = getResources().getColor(R.color.rckit_color_primary);
                tvMusicName.setTextColor(color);
                tvAuthor.setTextColor(color);
                tvSize.setTextColor(color);
            } else {
                ivTop.setVisibility(View.VISIBLE);
                ivDel.setVisibility(View.VISIBLE);
                mpvPlaying.setVisibility(View.GONE);
                mpvPlaying.stop();
                ivPlay.setImageResource(R.drawable.rckit_ic_music_play_cover);
                int color = Color.WHITE;
                tvMusicName.setTextColor(color);
                tvAuthor.setTextColor(color);
                tvSize.setTextColor(color);
            }
            itemView.setOnClickListener(v -> {
                MusicEngine.getInstance().toggleMusic(music);
            });
            ivTop.setOnClickListener(v -> {
                MusicEngine.getInstance().topMusic(music);
            });
            ivDel.setOnClickListener(v -> {
                MusicEngine.getInstance().deleteMusic(music);
            });
        }

        private String getSizeString(long size) {
            if (size > 1024 * 1024) {
                return df.format(size / 1024.0 / 1024.0) + "M";
            } else {
                return df.format(size / 1024.0) + "KB";
            }
        }
    }


}
