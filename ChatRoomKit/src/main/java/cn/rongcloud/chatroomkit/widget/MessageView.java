package cn.rongcloud.chatroomkit.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vanniktech.emoji.EmojiTextView;

import java.util.List;

import cn.rongcloud.chatroomkit.R;
import cn.rongcloud.chatroomkit.api.IRCChatroomMessage;
import cn.rongcloud.chatroomkit.api.IRCChatroomVoiceMessage;
import cn.rongcloud.chatroomkit.api.OnMessageContentClickListener;
import cn.rongcloud.chatroomkit.bean.MessageViewBean;
import cn.rongcloud.chatroomkit.cache.MessageList;
import cn.rongcloud.chatroomkit.manager.AudioPlayManager;
import cn.rongcloud.corekit.api.RCSceneKitEngine;
import cn.rongcloud.corekit.bean.RCColor;
import cn.rongcloud.corekit.bean.RCCorner;
import cn.rongcloud.corekit.utils.UiUtils;
import cn.rongcloud.corekit.utils.VMLog;
import cn.rongcloud.corekit.widget.SpaceItemDecoration;


/**
 * Created by hugo on 2021/11/12
 */
public class MessageView extends FrameLayout {
    private final static String TAG = MessageView.class.getSimpleName();
    MessageViewBean messageViewBean;
    MessageAdapter adapter;
    OnMessageContentClickListener onMessageContentClickListener;
    private RecyclerView rvMessage;

    public MessageView(@NonNull Context context) {
        this(context, null);
    }

    public MessageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.rckit_message_view, this);
        initView();
    }

    private void initView() {
        // init view
        rvMessage = (RecyclerView) findViewById(R.id.rv_message);

        messageViewBean = RCSceneKitEngine.getInstance().getKitConfig(MessageViewBean.class);
        if (messageViewBean == null) {
            VMLog.e(TAG, "initView failed : messageViewBean is null");
            return;
        }

        UiUtils.setPadding(this, messageViewBean.getContentInsets());
        adapter = new MessageAdapter(messageViewBean.getMaxVisibleCount());
        SpaceItemDecoration decoration = new SpaceItemDecoration(0, dp2px(messageViewBean.getBubbleSpace() / 2));
        rvMessage.addItemDecoration(decoration);
        rvMessage.setAdapter(adapter);

    }

    public void setMessages(List<IRCChatroomMessage> messageList) {
        if (adapter != null) {
            MessageList<IRCChatroomMessage> newList = new MessageList<>(messageViewBean.getMaxVisibleCount());
            if (messageList != null) {
                newList.addAll(messageList);
            }
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(adapter.messageList, newList));
            adapter.messageList = newList;
            diffResult.dispatchUpdatesTo(adapter);
            scrollBottom();
        }
    }

    public void addMessage(IRCChatroomMessage message) {
        if (adapter != null) {
            MessageList<IRCChatroomMessage> newList = new MessageList<>(messageViewBean.getMaxVisibleCount());
            newList.addAll(adapter.messageList);
            if (message != null) {
                newList.add(message);
            }
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(adapter.messageList, newList));
            adapter.messageList = newList;
            diffResult.dispatchUpdatesTo(adapter);
            scrollBottom();
        }
    }

    public void addMessages(List<IRCChatroomMessage> messageList) {
        if (adapter != null) {
            MessageList<IRCChatroomMessage> newList = new MessageList<>(messageViewBean.getMaxVisibleCount());
            newList.addAll(adapter.messageList);
            if (messageList != null) {
                newList.addAll(messageList);
            }
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffCallback(adapter.messageList, newList));
            adapter.messageList = newList;
            diffResult.dispatchUpdatesTo(adapter);
            scrollBottom();
        }
    }

    public void setOnMessageContentClickListener(OnMessageContentClickListener onMessageContentClickListener) {
        this.onMessageContentClickListener = onMessageContentClickListener;
    }

    private void scrollBottom() {
        int count = adapter.getItemCount();
        if (count > 0) {
            rvMessage.smoothScrollToPosition(count - 1);
        }
    }

    private int dp2px(int dp) {
        return UiUtils.dp2px(getContext(), dp);
    }

    private class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_NORMAL = 0;
        private static final int TYPE_VOICE = 1;
        private MessageList<IRCChatroomMessage> messageList;

        public MessageAdapter(int messageMaxCount) {
            messageList = new MessageList<>(messageMaxCount);
        }

        public void addMessage(IRCChatroomMessage message) {
            if (message != null) {
                this.messageList.add(message);
                notifyItemInserted(this.messageList.size() - 1);
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TYPE_VOICE) {
                return new MessageVoiceViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.rckit_item_message_voice, parent, false));
            } else {
                return new MessageViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.rckit_item_message, parent, false));
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            IRCChatroomMessage message = messageList.get(position);
            switch (getItemViewType(position)) {
                case TYPE_NORMAL:
                    if (holder instanceof MessageViewHolder) {
                        ((MessageViewHolder) holder).setMessage(message);
                    }
                    break;
                case TYPE_VOICE:
                    if (holder instanceof MessageVoiceViewHolder) {
                        ((MessageVoiceViewHolder) holder).setVoiceMessage((IRCChatroomVoiceMessage) message);
                    }
                    break;
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull List<Object> payloads) {
            super.onBindViewHolder(holder, position, payloads);
            onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return messageList.size();
        }

        @Override
        public int getItemViewType(int position) {
            Object o = messageList.get(position);
            if (o instanceof IRCChatroomVoiceMessage) {
                return TYPE_VOICE;
            } else {
                return TYPE_NORMAL;
            }
        }
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        private final EmojiTextView tvMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            if (messageViewBean != null) {
                UiUtils.setPadding(tvMessage, messageViewBean.getBubbleInsets());
                setBackground(messageViewBean.getDefaultBubbleColor(), messageViewBean.getDefaultBubbleCorner());
                setMessageTextColor(messageViewBean.getDefaultBubbleTextColor());
            }
        }

        public void setMessage(IRCChatroomMessage chatroomMessage) {
            setBackground(chatroomMessage.bubbleColor(), chatroomMessage.bubbleCorner());
            setMessageTextColor(chatroomMessage.bubbleTextColor());
            SpannableStringBuilder builder = chatroomMessage.buildMessage(onMessageContentClickListener);
            if (builder.length() > messageViewBean.getBubbleTextMaxLength()) {
                tvMessage.setText(builder.subSequence(0, messageViewBean.getBubbleTextMaxLength()));
            } else {
                tvMessage.setText(builder);
            }
            tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
        }

        private void setBackground(RCColor argb, RCCorner corner) {
            if (argb == null) {
                argb = messageViewBean.getDefaultBubbleColor();
            }
            if (corner == null) {
                corner = messageViewBean.getDefaultBubbleCorner();
            }
            if (argb == null || corner == null) {
                return;
            }
            tvMessage.setBackground(UiUtils.createRectangleDrawable(argb.getColor(), 0, 0, corner.getRadius()));
        }

        private void setMessageTextColor(RCColor argb) {
            if (argb == null) {
                argb = messageViewBean.getDefaultBubbleTextColor();
            }
            if (argb == null) {
                return;
            }
            tvMessage.setTextColor(argb.getColor());
            tvMessage.setLinkTextColor(argb.getColor());
        }
    }

    private class MessageVoiceViewHolder extends RecyclerView.ViewHolder {
        private final LinearLayout llMessage;
        private final EmojiTextView tvMessage;
        private final ImageView ivVoice;
        private final TextView tvDuration;

        public MessageVoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            llMessage = itemView.findViewById(R.id.ll_message);
            tvMessage = itemView.findViewById(R.id.tv_message);
            ivVoice = itemView.findViewById(R.id.iv_voice);
            tvDuration = itemView.findViewById(R.id.tv_duration);

            if (messageViewBean != null) {
                UiUtils.setPadding(llMessage, messageViewBean.getBubbleInsets());
                setBackground(messageViewBean.getDefaultBubbleColor(), messageViewBean.getDefaultBubbleCorner());
                setMessageTextColor(messageViewBean.getDefaultBubbleTextColor());
                ivVoice.setImageTintList(ColorStateList.valueOf(messageViewBean.getVoiceIconColor().getColor()));
                tvDuration.setTextColor(messageViewBean.getVoiceIconColor().getColor());
            }
        }

        public void setVoiceMessage(IRCChatroomVoiceMessage voiceMessage) {
            setBackground(voiceMessage.bubbleColor(), voiceMessage.bubbleCorner());
            setMessageTextColor(voiceMessage.bubbleTextColor());
            SpannableStringBuilder builder = voiceMessage.buildMessage(onMessageContentClickListener);
            if (builder.length() > messageViewBean.getBubbleTextMaxLength()) {
                tvMessage.setText(builder.subSequence(0, messageViewBean.getBubbleTextMaxLength()));
            } else {
                tvMessage.setText(builder);
            }
            tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
            tvDuration.setText(String.format("%d''", voiceMessage.voiceDuration()));
            AnimationDrawable animationDrawable = (AnimationDrawable) ivVoice.getDrawable();
            String path = voiceMessage.voicePath();
            if (isPlaying(path)) {
                animationDrawable.start();
            } else {
                animationDrawable.stop();
                animationDrawable.selectDrawable(0);
            }
            llMessage.setOnClickListener(v -> {

                if (isPlaying(path)) {
                    //当前正在播放,并且点击的就是当前的
                    AudioPlayManager.getInstance().stopPlay();
                    return;
                }
                AudioPlayManager.getInstance().startPlay(getContext(), Uri.parse(path), new AudioPlayManager.OnAudioPlayListener() {

                    @Override
                    public void onStart(Uri uri) {
                        //开始动画
                        animationDrawable.start();
                    }

                    @Override
                    public void onStop(Uri uri) {
                        //音频被停止,停止动画
                        animationDrawable.stop();
                        animationDrawable.selectDrawable(0);
                    }

                    @Override
                    public void onComplete(Uri uri) {
                        //停止动画
                        animationDrawable.stop();
                        animationDrawable.selectDrawable(0);
                    }
                });
            });
        }

        private boolean isPlaying(String path) {
            return AudioPlayManager.getInstance().isPlaying() && TextUtils.equals(path, AudioPlayManager.getInstance().getPlayingUri().toString());
        }

        private void setBackground(RCColor argb, RCCorner corner) {
            if (argb == null) {
                argb = messageViewBean.getDefaultBubbleColor();
            }
            if (corner == null) {
                corner = messageViewBean.getDefaultBubbleCorner();
            }
            if (argb == null || corner == null) {
                return;
            }
            llMessage.setBackground(UiUtils.createRectangleDrawable(argb.getColor(), 0, 0, corner.getRadius()));
        }

        private void setMessageTextColor(RCColor argb) {
            if (argb == null) {
                argb = messageViewBean.getDefaultBubbleTextColor();
            }
            if (argb == null) {
                return;
            }
            tvMessage.setTextColor(argb.getColor());
            tvMessage.setLinkTextColor(argb.getColor());
        }
    }

    class DiffCallback extends DiffUtil.Callback {
        private MessageList<IRCChatroomMessage> oldList;
        private MessageList<IRCChatroomMessage> newList;

        public DiffCallback(MessageList<IRCChatroomMessage> oldList, MessageList<IRCChatroomMessage> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldItemPosition == newItemPosition;
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return TextUtils.equals(oldList.get(oldItemPosition).buildMessage(onMessageContentClickListener), newList.get(newItemPosition).buildMessage(onMessageContentClickListener));
        }

        @Nullable
        @Override
        public Object getChangePayload(int oldItemPosition, int newItemPosition) {
            VMLog.e(TAG, oldItemPosition + "---" + newItemPosition);
            IRCChatroomMessage oldBuilder = oldList.get(oldItemPosition);
            IRCChatroomMessage newBuilder = newList.get(newItemPosition);
            Bundle payload = new Bundle();
            if (!TextUtils.equals(oldBuilder.buildMessage(onMessageContentClickListener), newBuilder.buildMessage(onMessageContentClickListener))) {
                payload.putString("KEY", newBuilder.toString());
            }
            if (payload.size() == 0) {
                return null;
            }
            return payload;
        }
    }
}
