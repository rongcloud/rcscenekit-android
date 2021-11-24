package cn.rongcloud.chatroomkit.widget;

import android.Manifest;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.rongcloud.chatroomkit.R;
import cn.rongcloud.chatroomkit.bean.ActionButton;
import cn.rongcloud.chatroomkit.bean.ToolBarBean;
import cn.rongcloud.chatroomkit.manager.AudioPlayManager;
import cn.rongcloud.chatroomkit.manager.AudioRecordManager;
import cn.rongcloud.chatroomkit.utils.PermissionCheckUtil;
import cn.rongcloud.corekit.api.RCSceneKitEngine;
import cn.rongcloud.corekit.utils.GlideUtil;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.corekit.utils.VMLog;


/**
 * Created by hugo on 2021/11/12
 */
public class ToolBar extends ConstraintLayout {
    private final static String TAG = ToolBar.class.getSimpleName();
    private LinearLayout llChat;
    private TextView tvChat;
    private RecyclerView rvAction;
    private ImageView ivRecord;
    private AudioRecordManager audioRecordManager;
    private ToolBarBean toolBarBean;
    private OnActionClickListener onActionClickListener;
    private ActionAdapter actionAdapter;
    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            String[] permissions = {Manifest.permission.RECORD_AUDIO};
            if (!PermissionCheckUtil.checkPermissions(
                    v.getContext(),
                    permissions
            ) && event.getAction() == MotionEvent.ACTION_DOWN
            ) {
                PermissionCheckUtil.requestPermissions(
                        ((FragmentActivity) v.getContext()),
                        permissions,
                        PermissionCheckUtil.REQUEST_CODE_ASK_PERMISSIONS
                );
                return true;
            }
            int[] location = new int[2];
            ivRecord.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                //在这里拦截外部的滑动事件
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if (AudioPlayManager.getInstance().isPlaying()) {
                    AudioPlayManager.getInstance().stopPlay();
                }

                audioRecordManager.startRecord(v.getRootView(), toolBarBean.getRecordQuality() != 0);
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (event.getRawX() <= 0 || event.getRawX() > x + v.getWidth() || event.getRawY() < y) {
                    audioRecordManager.willCancelRecord();
                } else {
                    audioRecordManager.continueRecord();
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                v.getParent().requestDisallowInterceptTouchEvent(false);
                audioRecordManager.stopRecord();
            }
            return true;
        }
    };

    public ToolBar(@NonNull Context context) {
        this(context, null);
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.rckit_toolbar, this);
        initView();
    }

    private void initView() {
        // init view

        llChat = (LinearLayout) findViewById(R.id.ll_chat);
        tvChat = (TextView) findViewById(R.id.tv_chat);
        rvAction = (RecyclerView) findViewById(R.id.rv_action);
        ivRecord = (ImageView) findViewById(R.id.iv_record);

        toolBarBean = RCSceneKitEngine.getInstance().getKitConfig(ToolBarBean.class);
        if (toolBarBean == null) {
            VMLog.e(TAG, "initView failed : toolBarBean is null");
            return;
        }
        // content
        this.setBackgroundColor(toolBarBean.getBackgroundColor().getColor());
        UiUtils.setPadding(this, toolBarBean.getContentInsets());
        // chat button
        tvChat.setText(toolBarBean.getChatButtonTitle());
        llChat.setMinimumHeight(toolBarBean.getChatButtonSize().getHeight());
        tvChat.setTextColor(toolBarBean.getChatButtonTextColor().getColor());
        tvChat.setTextSize(toolBarBean.getChatButtonTextSize());
        llChat.setBackground(UiUtils.createRectangleDrawable(toolBarBean.getChatButtonBackgroundColor().getColor(), 0, 0, dp2px(toolBarBean.getChatButtonBackgroundCorner())));
        UiUtils.setPadding(llChat, toolBarBean.getChatButtonInsets());
        // record image
        if (toolBarBean.getRecordButtonEnable()) {
            ivRecord.setVisibility(VISIBLE);
        } else {
            ivRecord.setVisibility(GONE);
        }
        audioRecordManager = new AudioRecordManager();
        audioRecordManager.setMaxVoiceDuration(toolBarBean.getRecordMaxDuration());
        ivRecord.setOnTouchListener(onTouchListener);
        if (toolBarBean.getRecordPosition() != 0) {
            UiUtils.reverseChild(llChat);
        }
        // action recyclerview
        actionAdapter = new ActionAdapter();
        rvAction.setAdapter(actionAdapter);
        actionAdapter.setActionList(toolBarBean.getActionArray());

    }

    private int dp2px(int dp) {
        return UiUtils.dp2px(getContext(), dp);
    }

    public void setOnClickChatButton(View.OnClickListener l) {
        llChat.setOnClickListener(l);
    }

    public void setOnRecordVoiceListener(AudioRecordManager.OnAudioRecordListener onRecordVoiceListener) {
        if (audioRecordManager != null) {
            audioRecordManager.setOnAudioRecordListener(onRecordVoiceListener);
        }
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        this.onActionClickListener = onActionClickListener;
    }

    public List<ActionButton> getActionButtons() {
        if (toolBarBean != null) {
            return toolBarBean.getActionArray();
        } else {
            return null;
        }
    }

    public void setActionButtons(List<ActionButton> actionButtonList) {
        if (actionAdapter != null) {
            actionAdapter.setActionList(actionButtonList);
        }
    }

    public interface OnActionClickListener {
        void onClickAction(int index, String extra);
    }

    private static class ActionViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivAction;
        private final TextView tvBadge;

        public ActionViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAction = (ImageView) itemView.findViewById(R.id.iv_action);
            tvBadge = (TextView) itemView.findViewById(R.id.tv_badge);
        }

        public void setActionData(ActionButton actionButton) {
            if (actionButton.getLocalIcon() != 0) {
                ivAction.setImageResource(actionButton.getLocalIcon());
            } else {
                GlideUtil.loadImage(ivAction, actionButton.getActionIcon());
            }
            if (actionButton.hasBadge()) {
                tvBadge.setVisibility(VISIBLE);
                if (actionButton.getBadgeNum() > 0) {
                    tvBadge.setText(String.valueOf(actionButton.getBadgeNum()));
                } else {
                    tvBadge.setText("");
                }
            } else {
                tvBadge.setVisibility(GONE);
            }
        }
    }

    private class ActionAdapter extends RecyclerView.Adapter<ActionViewHolder> {
        private List<ActionButton> actionButtonList = new ArrayList<>();


        public void setActionList(List<ActionButton> actionButtonList) {
            this.actionButtonList = actionButtonList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ActionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.rckit_item_action, parent, false);
            return new ActionViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ActionViewHolder holder, int position) {
            holder.setActionData(actionButtonList.get(position));
            holder.itemView.setOnClickListener(v -> {
                if (onActionClickListener != null) {
                    onActionClickListener.onClickAction(position, actionButtonList.get(position).getExtra());
                }
            });
        }

        @Override
        public int getItemCount() {
            return actionButtonList.size();
        }
    }
}
