package cn.rongcloud.musiccontrolkit.fragment;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.corekit.base.RCFragment;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.musiccontrolkit.R;
import cn.rongcloud.musiccontrolkit.RCMusicControlEngine;
import cn.rongcloud.musiccontrolkit.RCMusicControlKit;
import cn.rongcloud.musiccontrolkit.bean.MusicControl;
import cn.rongcloud.musiccontrolkit.bean.MusicControlConfig;
import cn.rongcloud.musiccontrolkit.bean.MusicControlKitConfig;

/**
 * Created by gyn on 2021/11/24
 */
public class MusicControlFragment extends RCFragment<MusicControlKitConfig> {

    private RecyclerView rvControl;
    private List<ControlItem> controlItemList = new ArrayList<>();
    private ControlAdapter adapter;
    private MusicControlConfig controlConfig;

    public static MusicControlFragment getInstance() {
        return new MusicControlFragment();
    }

    @Override
    public int setLayoutId() {
        return R.layout.rckit_fragment_music_control;
    }

    @Override
    public MusicControlKitConfig getKitConfig() {
        return RCMusicControlKit.getInstance().getKitConfig();
    }

    @Override
    public void initView() {
        rvControl = (RecyclerView) getLayout().findViewById(R.id.rv_control);
        adapter = new ControlAdapter();
        rvControl.setAdapter(adapter);

        RCMusicControlEngine.getInstance().onLoadMusicControl(new DataCallback<MusicControl>() {
            @Override
            public void onResult(MusicControl musicControl) {
                if (musicControl != null) {
                    controlItemList.clear();
                    controlItemList.add(new ControlItem("本端音量", musicControl.getLocalVolume(), false, false));
                    controlItemList.add(new ControlItem("远端音量", musicControl.getRemoteVolume(), false, false));
                    controlItemList.add(new ControlItem("麦克音量", musicControl.getMicVolume(), false, false));
                    controlItemList.add(new ControlItem("开启耳返", 0, true, musicControl.isEarsBackEnable()));
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void initConfig(MusicControlKitConfig musicControlKitConfig) {
        controlConfig = musicControlKitConfig.getMusicControl();
        if (controlConfig != null) {
            rvControl.setBackgroundColor(controlConfig.getBackgroundColor().getColor());
        }
    }

    abstract class MySeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    class ControlAdapter extends RecyclerView.Adapter<ControlViewHolder> {


        @NonNull
        @Override
        public ControlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ControlViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.rckit_item_music_control, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ControlViewHolder holder, int position) {
            holder.initView(controlItemList.get(position), position);
        }

        @Override
        public int getItemCount() {
            return controlItemList.size();
        }

    }


    class ControlViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvVolume;
        private AppCompatSeekBar sbProgress;
        private SwitchCompat scSwitch;

        public ControlViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvVolume = (TextView) itemView.findViewById(R.id.tv_volume);
            sbProgress = (AppCompatSeekBar) itemView.findViewById(R.id.sb_progress);
            scSwitch = (SwitchCompat) itemView.findViewById(R.id.sc_switch);
            sbProgress.setMax(100);
            initConfig();
        }

        public void initConfig() {
            if (controlConfig != null) {
                tvName.setTextColor(controlConfig.getTextColor().getColor());
                tvVolume.setTextColor(controlConfig.getTextColor().getColor());
                UiUtils.setTextFont(tvName, controlConfig.getFont());
                UiUtils.setTextFont(tvVolume, controlConfig.getFont());

                LayerDrawable layerDrawable = (LayerDrawable) sbProgress.getProgressDrawable();
                Drawable dra1 = layerDrawable.getDrawable(0);
                dra1.setColorFilter(controlConfig.getNormalColor().getColor(), PorterDuff.Mode.SRC);

                Drawable dra2 = layerDrawable.getDrawable(1);
                dra2.setColorFilter(controlConfig.getTintColor().getColor(), PorterDuff.Mode.SRC);

                Drawable thumb = sbProgress.getThumb();
                thumb.setColorFilter(controlConfig.getThumbColor().getColor(), PorterDuff.Mode.SRC);
                sbProgress.invalidate();

                UiUtils.setSwitchColor(scSwitch, controlConfig.getThumbColor().getColor(), controlConfig.getNormalColor().getColor(), controlConfig.getTintColor().getColor());
            }
        }

        public void initView(ControlItem controlItem, int position) {
            tvName.setText(controlItem.name);
            tvVolume.setText(controlItem.volume + "");
            if (controlItem.isSwitch) {
                scSwitch.setVisibility(View.VISIBLE);
                tvVolume.setVisibility(View.GONE);
                sbProgress.setVisibility(View.GONE);
                scSwitch.setChecked(controlItem.isOpen);
                scSwitch.setOnClickListener(v -> {
                    boolean isOpen = !controlItem.isOpen;
                    controlItem.setOpen(isOpen);
                    adapter.notifyDataSetChanged();
                    RCMusicControlEngine.getInstance().onEarsBackEnableChanged(isOpen);

                });
            } else {
                scSwitch.setVisibility(View.GONE);
                tvVolume.setVisibility(View.VISIBLE);
                sbProgress.setVisibility(View.VISIBLE);
                sbProgress.setProgress(controlItem.getVolume());
                sbProgress.setOnSeekBarChangeListener(new MySeekBarListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        controlItem.setVolume(progress);
                        tvVolume.setText(controlItem.volume + "");
                        switch (position) {
                            case 0:
                                RCMusicControlEngine.getInstance().onLocalVolumeChanged(progress);
                                break;
                            case 1:
                                RCMusicControlEngine.getInstance().onRemoteVolumeChanged(progress);
                                break;
                            case 2:
                                RCMusicControlEngine.getInstance().onMicVolumeChanged(progress);
                                break;
                        }
                    }
                });
            }
        }
    }

    class ControlItem implements Serializable {
        private String name;
        private int volume;
        private boolean isSwitch;
        private boolean isOpen;

        public ControlItem(String name, int volume, boolean isSwitch, boolean isOpen) {
            this.name = name;
            this.volume = volume;
            this.isSwitch = isSwitch;
            this.isOpen = isOpen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getVolume() {
            return volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

        public boolean isSwitch() {
            return isSwitch;
        }

        public void setSwitch(boolean aSwitch) {
            isSwitch = aSwitch;
        }

        public boolean isOpen() {
            return isOpen;
        }

        public void setOpen(boolean open) {
            isOpen = open;
        }
    }
}
