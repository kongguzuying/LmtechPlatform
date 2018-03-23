package com.ea.card.crm.service.vo;

/**
 * 微信领卡消息
 */
public class WxReceiveCardMessage extends WxMessageBase {

    /** 先领卡再激活 */
    public static final String SOURCE_SCENE_LANDING_PAGE = "SOURCE_SCENE_LANDING_PAGE";

    private String CardId;
    private boolean IsGiveByFriend;
    private String UserCardCode;
    private String FriendUserName;
    private String OuterId;
    private String OldUserCardCode;
    private String OuterStr;
    private int IsRestoreMemberCard;
    private boolean IsRecommendByFriend;
    private String SourceScene;

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public boolean isGiveByFriend() {
        return IsGiveByFriend;
    }

    public void setGiveByFriend(boolean giveByFriend) {
        IsGiveByFriend = giveByFriend;
    }

    public String getUserCardCode() {
        return UserCardCode;
    }

    public void setUserCardCode(String userCardCode) {
        UserCardCode = userCardCode;
    }

    public String getFriendUserName() {
        return FriendUserName;
    }

    public void setFriendUserName(String friendUserName) {
        FriendUserName = friendUserName;
    }

    public String getOuterId() {
        return OuterId;
    }

    public void setOuterId(String outerId) {
        OuterId = outerId;
    }

    public String getOldUserCardCode() {
        return OldUserCardCode;
    }

    public void setOldUserCardCode(String oldUserCardCode) {
        OldUserCardCode = oldUserCardCode;
    }

    public String getOuterStr() {
        return OuterStr;
    }

    public void setOuterStr(String outerStr) {
        OuterStr = outerStr;
    }

    public int isRestoreMemberCard() {
        return IsRestoreMemberCard;
    }

    public void setRestoreMemberCard(int restoreMemberCard) {
        IsRestoreMemberCard = restoreMemberCard;
    }

    public boolean isRecommendByFriend() {
        return IsRecommendByFriend;
    }

    public void setRecommendByFriend(boolean recommendByFriend) {
        IsRecommendByFriend = recommendByFriend;
    }

    public String getSourceScene() {
        return SourceScene;
    }

    public void setSourceScene(String sourceScene) {
        SourceScene = sourceScene;
    }
}
