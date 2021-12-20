package cn.rongcloud.musiccontrolkit.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.corekit.api.DataCallback;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.corekit.widget.RealtimeBlurView;
import cn.rongcloud.corekit.widget.SpaceItemDecoration;
import cn.rongcloud.musiccontrolkit.R;
import cn.rongcloud.musiccontrolkit.RCMusicControlEngine;
import cn.rongcloud.musiccontrolkit.bean.Effect;
import cn.rongcloud.musiccontrolkit.bean.SoundEffectConfig;

/**
 * Created by gyn on 2021/11/29
 */
public class EffectSnackBar extends BaseTransientBottomBar<EffectSnackBar> {

    private RecyclerView rvEffect;
    private RealtimeBlurView rbvEffect;
    private List<Effect> effectList = new ArrayList<>();
    private Effect currentEffect;
    private EffectAdapter adapter;
    private SoundEffectConfig soundEffectConfig;

    public EffectSnackBar(@NonNull ViewGroup parent, @NonNull View content,
                          @NonNull com.google.android.material.snackbar.ContentViewCallback contentViewCallback, SoundEffectConfig soundEffectConfig) {
        super(parent, content, contentViewCallback);
        initView();
        initConfig(soundEffectConfig);
        this.soundEffectConfig = soundEffectConfig;
    }

    public static EffectSnackBar make(ViewGroup parent, SoundEffectConfig soundEffectConfig) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rckit_effect_snackbar, parent, false);
        EffectSnackBar effectSnackBar = new EffectSnackBar(parent, view, new EffectCallback(), soundEffectConfig);
        effectSnackBar.getView().setPadding(0, 0, 0, 0);
        effectSnackBar.getView().setBackgroundResource(R.color.transparent);
        effectSnackBar.setDuration(LENGTH_INDEFINITE);
        effectSnackBar.setAnimationMode(ANIMATION_MODE_FADE);
        effectSnackBar.addCallback(new BaseCallback<EffectSnackBar>() {
            @Override
            public void onDismissed(EffectSnackBar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
            }

            @Override
            public void onShown(EffectSnackBar transientBottomBar) {
                super.onShown(transientBottomBar);
            }
        });
        return effectSnackBar;
    }

    private void initView() {
        rbvEffect = getView().findViewById(R.id.rbv_effect);
        rvEffect = (RecyclerView) getView().findViewById(R.id.rv_effect);
        adapter = new EffectAdapter();
        rvEffect.setAdapter(adapter);
        RCMusicControlEngine.getInstance().onLoadEffectList(new DataCallback<List<Effect>>() {
            @Override
            public void onResult(List<Effect> effects) {
                if (effects != null && effects.size() > 0) {
                    effectList.clear();
                    effectList.addAll(effects);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initConfig(SoundEffectConfig effectConfig) {
        if (effectConfig == null) {
            return;
        }
        UiUtils.setViewSize(getView(), effectConfig.getSize());
        rbvEffect.setOverlayColor(effectConfig.getBackgroundColor().getColor());
        rbvEffect.setBlurRadius(effectConfig.isBlurEnable() ? UiUtils.dp2px(14) : 0);
        rbvEffect.setCorner(effectConfig.getCorner().getTopLeft(), effectConfig.getCorner().getTopRight(), effectConfig.getCorner().getBottomLeft(), effectConfig.getCorner().getBottomRight());
        UiUtils.setPadding(rvEffect, effectConfig.getContentInsets());
        int space = UiUtils.dp2px(effectConfig.getItemSpace());
        rvEffect.addItemDecoration(new SpaceItemDecoration(space / 2, 0, -space / 2, -space / 2));
    }

    static class EffectCallback implements com.google.android.material.snackbar.ContentViewCallback {

        @Override
        public void animateContentIn(int delay, int duration) {
        }

        @Override
        public void animateContentOut(int delay, int duration) {
        }
    }

    class EffectAdapter extends RecyclerView.Adapter<EffectViewHolder> {


        @NonNull
        @Override
        public EffectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EffectViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.rckit_item_effect, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull EffectViewHolder holder, int position) {
            holder.setEffect(effectList.get(position));
        }

        @Override
        public int getItemCount() {
            return effectList.size();
        }
    }

    class EffectViewHolder extends RecyclerView.ViewHolder {
        private TextView tvEffect;

        public EffectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEffect = (TextView) itemView.findViewById(R.id.tv_effect);
            initConfig();
        }

        private void initConfig() {
            if (soundEffectConfig != null && soundEffectConfig.getEffectAttributes() != null) {
                UiUtils.setTextAttributes(tvEffect, soundEffectConfig.getEffectAttributes());
            }
        }

        public void setEffect(Effect effect) {
            tvEffect.setText(effect.getEffectName());
            tvEffect.setSelected(effect.equals(currentEffect));
            tvEffect.setOnClickListener(v -> {
                currentEffect = effect;
                RCMusicControlEngine.getInstance().onPlayEffect(effect);
                adapter.notifyDataSetChanged();
            });
        }
    }

}
