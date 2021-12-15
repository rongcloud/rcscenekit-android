package cn.rongcloud.chatroomkit.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by gyn on 2021/12/8
 */
public class ChatRoomKitBean implements Serializable {
    @SerializedName("messageView")
    private MessageViewBean messageView;
    @SerializedName("toolBar")
    private ToolBarBean toolBar;
    @SerializedName("inputBar")
    private InputBarBean inputBar;

    public MessageViewBean getMessageView() {
        return messageView;
    }

    public void setMessageView(MessageViewBean messageView) {
        this.messageView = messageView;
    }

    public ToolBarBean getToolBar() {
        return toolBar;
    }

    public void setToolBar(ToolBarBean toolBar) {
        this.toolBar = toolBar;
    }

    public InputBarBean getInputBar() {
        return inputBar;
    }

    public void setInputBar(InputBarBean inputBar) {
        this.inputBar = inputBar;
    }
}
