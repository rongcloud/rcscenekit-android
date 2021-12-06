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
import cn.rongcloud.corekit.widget.SpaceItemDecoration;
import cn.rongcloud.musiccontrolkit.MusicEngine;
import cn.rongcloud.musiccontrolkit.R;
import cn.rongcloud.musiccontrolkit.bean.Effect;

/**
 * Created by hugo on 2021/11/29
 */
public class EffectSnackBar extends BaseTransientBottomBar<EffectSnackBar> {

    private RecyclerView rvEffect;
    private List<Effect> effectList = new ArrayList<>();
    private Effect currentEffect;
    private EffectAdapter adapter;

    public EffectSnackBar(@NonNull ViewGroup parent, @NonNull View content,
                          @NonNull com.google.android.material.snackbar.ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
        initView();
    }

    public static EffectSnackBar make(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rckit_effect_snackbar, parent, false);
        EffectSnackBar effectSnackBar = new EffectSnackBar(parent, view, new EffectCallback());
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
        rvEffect = (RecyclerView) getView().findViewById(R.id.rv_effect);
        adapter = new EffectAdapter();
        rvEffect.setAdapter(adapter);
        int space = UiUtils.dp2px(getContext(), 7);
        rvEffect.addItemDecoration(new SpaceItemDecoration(space, 0, space * 2, space * 2));
        MusicEngine.getInstance().onLoadEffectList(new DataCallback<List<Effect>>() {
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
        }

        public void setEffect(Effect effect) {
            tvEffect.setText(effect.getEffectName());
            tvEffect.setSelected(effect.equals(currentEffect));
            tvEffect.setOnClickListener(v -> {
                currentEffect = effect;
                MusicEngine.getInstance().onPlayEffect(effect);
                adapter.notifyDataSetChanged();
            });
        }
    }

}
