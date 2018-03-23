package com.ea.card.crm.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.lmtech.model.DbEntityBase;

@TableName("ea_wxactive_message")
public class WxActiveMessage extends DbEntityBase {
    @TableField("to_user_name")
    private String toUserName;
    @TableField("from_user_name")
    private String fromUserName;
    @TableField("applet_open_id")
    private String appletOpenId;
    @TableField("outer_str")
    private String outerStr;
    @TableField("is_give_by_friend")
    private boolean isGiveByFriend;
    @TableField("friend_user_name")
    private String friendUserName;
    @TableField("code")
    private String code;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getAppletOpenId() {
        return appletOpenId;
    }

    public void setAppletOpenId(String appletOpenId) {
        this.appletOpenId = appletOpenId;
    }

    public String getOuterStr() {
        return outerStr;
    }

    public void setOuterStr(String outerStr) {
        this.outerStr = outerStr;
    }

    public boolean isGiveByFriend() {
        return isGiveByFriend;
    }

    public void setGiveByFriend(boolean giveByFriend) {
        isGiveByFriend = giveByFriend;
    }

    public String getFriendUserName() {
        return friendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        this.friendUserName = friendUserName;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
    
    
}
