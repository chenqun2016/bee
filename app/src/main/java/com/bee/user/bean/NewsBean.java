package com.bee.user.bean;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/21  20：54
 * 描述：
 */
public class NewsBean {
    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public int getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(int isReaded) {
        this.isReaded = isReaded;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMessagePictureUrl() {
        return messagePictureUrl;
    }

    public void setMessagePictureUrl(String messagePictureUrl) {
        this.messagePictureUrl = messagePictureUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String messageTitle;
    private String messageType;
    private String messageBody;
    private int isReaded;
    private String createTime;
    private String messagePictureUrl;
    private int id;

    public String getMessageIcon() {
        return messageIcon;
    }

    public void setMessageIcon(String messageIcon) {
        this.messageIcon = messageIcon;
    }

    private String messageIcon;
}
