package com.turing.live.model;

import java.io.Serializable;

/**
 * 直播间
 */
public class LiveRoom implements Serializable {

    /**
     * 直播id
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 地址
     */
    private String address;
    /**
     * 观看人数
     */
    private int audienceNum;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 封面
     */
    private String cover;
    /**
     * 聊天室id
     */
    private String chatroomId;
    /**
     * 主播id
     */
    private String anchorId;
    /**
     * 直播间URL
     */
    private String liveRoomUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAudienceNum() {
        return audienceNum;
    }

    public void setAudienceNum(int audienceNum) {
        this.audienceNum = audienceNum;
    }

    public String getCover() {
        return cover;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public void setChatroomId(String chatroomId) {
        this.chatroomId = chatroomId;
    }

    public String getAnchorId() {
        return anchorId;
    }

    public void setAnchorId(String anchorId) {
        this.anchorId = anchorId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLiveRoomUrl() {
        return liveRoomUrl;
    }

    public void setLiveRoomUrl(String liveRoomUrl) {
        this.liveRoomUrl = liveRoomUrl;
    }
}
