package com.turing.live.model;

/**
 *  直播间用户数据
 * Created by YSL on 2017/6/29.
 */

public class RoomUser {
    /**
     * rid : 46459556
     * level : 12
     * head : http://i5.pdim.gs/a0a65a06a82c4e0d094f6864d25fa546.jpeg
     * nick : 鱼刺o
     * room_order : 3961225577
     * level_icon : user12
     * section_icon : usersection12
     * special :
     * sitelevel : 12
     * role_val : 10
     */

    private int rid;
    private int level;
    private String head;
    private String nick;
    private long room_order;
    private String level_icon;
    private String section_icon;
    private String special;
    private String sitelevel;
    private int role_val;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getRoom_order() {
        return room_order;
    }

    public void setRoom_order(long room_order) {
        this.room_order = room_order;
    }

    public String getLevel_icon() {
        return level_icon;
    }

    public void setLevel_icon(String level_icon) {
        this.level_icon = level_icon;
    }

    public String getSection_icon() {
        return section_icon;
    }

    public void setSection_icon(String section_icon) {
        this.section_icon = section_icon;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getSitelevel() {
        return sitelevel;
    }

    public void setSitelevel(String sitelevel) {
        this.sitelevel = sitelevel;
    }

    public int getRole_val() {
        return role_val;
    }

    public void setRole_val(int role_val) {
        this.role_val = role_val;
    }
}
