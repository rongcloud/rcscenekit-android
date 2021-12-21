package cn.rongcloud.musiccontrolkit.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
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
import cn.rongcloud.corekit.base.RCFragment;
import cn.rongcloud.corekit.utils.GlideUtil;
import cn.rongcloud.corekit.utils.ListUtil;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.corekit.utils.VMLog;
import cn.rongcloud.musiccontrolkit.R;
import cn.rongcloud.musiccontrolkit.RCMusicControlEngine;
import cn.rongcloud.musiccontrolkit.RCMusicControlKit;
import cn.rongcloud.musiccontrolkit.bean.Music;
import cn.rongcloud.musiccontrolkit.bean.MusicControlKitConfig;
import cn.rongcloud.musiccontrolkit.bean.MusicItemConfig;
import cn.rongcloud.musiccontrolkit.bean.MusicListConfig;
import cn.rongcloud.musiccontrolkit.iinterface.OnSwitchPageListener;
import cn.rongcloud.musiccontrolkit.widget.MusicPlayingView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gyn on 2021/11/24
 */
public class MusicListFragment extends RCFragment<MusicControlKitConfig> {
    private static final String TAG = MusicListFragment.class.getSimpleName();
    private final OnSwitchPageListener onSwitchPageListener;
    private FrameLayout flRoot;
    private RecyclerView rvMusicList;
    private LinearLayout llEmpty;
    private TextView tvAddMusic;
    private MusicListAdapter adapter;
    private List<Music> musicList = new ArrayList<>();
    private SmartRefreshLayout srlRefresh;
    private MusicListConfig musicListConfig;
    private MusicItemConfig itemConfig;

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
    public MusicControlKitConfig getKitConfig() {
        return RCMusicControlKit.getInstance().getKitConfig();
    }

    @Override
    public void initView() {
        flRoot = getLayout().findViewById(R.id.fl_root);
        rvMusicList = getLayout().findViewById(R.id.rv_music_list);
        srlRefresh = getLayout().findViewById(R.id.srl_refresh);
        llEmpty = getLayout().findViewById(R.id.ll_empty);
        tvAddMusic = getLayout().findViewById(R.id.tv_add_music);

        adapter = new MusicListAdapter();
        rvMusicList.setAdapter(adapter);

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
    }

    @Override
    public void initConfig(MusicControlKitConfig musicControlKitConfig) {
        musicListConfig = musicControlKitConfig.getMusicList();
        if (musicListConfig != null) {
            srlRefresh.setEnableLoadMore(musicListConfig.isLoadMoreEnable());
            srlRefresh.setEnableRefresh(musicListConfig.isRefreshEnable());
            flRoot.setBackgroundColor(musicListConfig.getBackgroundColor().getColor());
            itemConfig = musicListConfig.getMusicItem();
        }
        refreshData();
        listenMusicListChanged();
    }

    private void refreshData() {
        RCMusicControlEngine.getInstance().onLoadMusicList(new DataCallback<List<Music>>() {
            @Override
            public void onResult(List<Music> musicList) {
                RCMusicControlEngine.getInstance().setMusicList(musicList);
            }
        });
    }

    private void loadMoreData() {
        RCMusicControlEngine.getInstance().onLoadMoreMusicList(new DataCallback<List<Music>>() {
            @Override
            public void onResult(List<Music> musicList) {
                RCMusicControlEngine.getInstance().addMusicList(musicList);
            }
        });
    }

    /**
     * 监听音乐列表变化刷新UI
     */
    private void listenMusicListChanged() {
        RCMusicControlEngine.getInstance().musicListObserve().observe(this, new Observer<List<Music>>() {
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
        RCMusicControlEngine.getInstance().playingObserve().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                VMLog.e(TAG, "music playing changed :" + aBoolean);
                adapter.notifyDataSetChanged();
            }
        });
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
        private View separator;
        private Guideline glGuideStart;
        private Guideline glGuideEnd;

        private int highlightColor = 0;

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
            separator = itemView.findViewById(R.id.v_separator);
            glGuideStart = itemView.findViewById(R.id.gl_guide_start);
            glGuideEnd = itemView.findViewById(R.id.gl_guide_end);
            initConfig();
        }

        private void initConfig() {
            if (itemConfig != null) {
                UiUtils.setViewSize(itemView, itemConfig.getSize());

                glGuideStart.setGuidelineBegin(itemConfig.getContentInsets().getLeftPx());
                glGuideEnd.setGuidelineEnd(itemConfig.getContentInsets().getRightPx());

                UiUtils.setViewSize(civTheme, itemConfig.getCoverSize());
                UiUtils.setTextAttributes(tvMusicName, itemConfig.getTitleAttributes());
                UiUtils.setTextAttributes(tvAuthor, itemConfig.getContentAttributes());
                UiUtils.setTextAttributes(tvSize, itemConfig.getSizeAttributes());
                if (itemConfig.getSeparatorAttributes() != null) {
                    UiUtils.setViewSize(separator, itemConfig.getSeparatorAttributes().getSize());
                    separator.setBackgroundColor(itemConfig.getSeparatorAttributes().getBackground().getColor());
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) separator.getLayoutParams();
                    params.leftMargin = itemConfig.getSeparatorAttributes().getInsets().getLeftPx();
                    params.rightMargin = itemConfig.getSeparatorAttributes().getInsets().getRightPx();
                    separator.setLayoutParams(params);
                }
                UiUtils.setImageAttribute(ivTop, itemConfig.getTopIconAttributes(), R.drawable.rckit_ic_music_top, RCMusicControlKit.getInstance().getAssetsPath());
                UiUtils.setImageAttribute(ivDel, itemConfig.getDeleteIconAttributes(), R.drawable.rckit_ic_music_del, RCMusicControlKit.getInstance().getAssetsPath());
                highlightColor = itemConfig.getHighlightColor().getColor();
                mpvPlaying.setLineColor(highlightColor);
            }
        }

        public void initView(Music music) {
            GlideUtil.loadImage(civTheme, music.getCoverUrl());
            tvMusicName.setText(music.getMusicName());
            tvAuthor.setText(music.getAuthor());
            tvSize.setText(getSizeString(music.getSize()));
            boolean isPlayingMusic = RCMusicControlEngine.getInstance().isPlayingMusic(music);
            if (isPlayingMusic && RCMusicControlEngine.getInstance().isPlaying()) {
                ivTop.setVisibility(View.GONE);
                ivDel.setVisibility(View.GONE);
                mpvPlaying.setVisibility(View.VISIBLE);
                ivPlay.setImageResource(R.drawable.rckit_ic_music_pause_cover);
                mpvPlaying.start();
                tvMusicName.setTextColor(highlightColor);
                tvAuthor.setTextColor(highlightColor);
                tvSize.setTextColor(highlightColor);
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
                RCMusicControlEngine.getInstance().toggleMusic(music);
            });
            ivTop.setOnClickListener(v -> {
                RCMusicControlEngine.getInstance().topMusic(music);
            });
            ivDel.setOnClickListener(v -> {
                RCMusicControlEngine.getInstance().deleteMusic(music);
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
