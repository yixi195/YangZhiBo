package com.turing.live.model;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * 直播间基础信息
 * Created by YSL on 2017/6/27.
 */

public class BaseRoomInfo extends BmobObject {


    /**
     * roominfo : {"rid":"79326582","xid":"6457143","name":"我去闹海","notice":"","photo":"https://image.xingyan.panda.tv/10e9eeed4a300ac04a48ed7b9c79ec06.jpeg","picture":"","playstatus":"1","status":"0","lock_reason":[],"personnum":"5435","shareurl":"http://m.xingyan.panda.tv/live.html?xid=6457143","shareimg":"http://i5.pdim.gs/dmfd/100_100_70/b32cd688b637dbf1db61b4fe31bba315.jpg","userlevel":"6","userlevelicon":"user6","level":"10","levelicon":"anchor10","exp":"2518996","levelmin":"1500000","levelmax":"4249999"}
     * videoinfo : {"streamurl":"http://flv-live-ws.xingyan.panda.tv/panda-xingyan/caa8b6eb9373eaf95a5d65de60021153.flv","hlsurl":"http://hls-live-ws.xingyan.panda.tv/panda-xingyan/caa8b6eb9373eaf95a5d65de60021153.m3u8","streaminfo":{"width":"480","height":"848","decode_type":2}}
     * hostinfo : {"rid":"79326582","nickName":"护食的艺文baby","avatar":"http://i5.pdim.gs/dmfd/100_100_70/b32cd688b637dbf1db61b4fe31bba315.jpg","xid":"6457143","gender":"2","signature":"631747719QQ群 微博名俄罗斯大妞儿","is_anchor":"1","starval":"2427742"}
     */

    private RoominfoBean roominfo;
    private VideoinfoBean videoinfo;
    private HostinfoBean hostinfo;
    private String userName; //关注人
    private boolean isFollow; //是否关注
    private String xid; //房间id


    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public boolean isFollow() {
        return isFollow;
    }
    public RoominfoBean getRoominfo() {
        return roominfo;
    }

    public void setRoominfo(RoominfoBean roominfo) {
        this.roominfo = roominfo;
    }

    public VideoinfoBean getVideoinfo() {
        return videoinfo;
    }

    public void setVideoinfo(VideoinfoBean videoinfo) {
        this.videoinfo = videoinfo;
    }

    public HostinfoBean getHostinfo() {
        return hostinfo;
    }

    public void setHostinfo(HostinfoBean hostinfo) {
        this.hostinfo = hostinfo;
    }

    public static class RoominfoBean {
        /**
         * rid : 79326582
         * xid : 6457143
         * name : 我去闹海
         * notice :
         * photo : https://image.xingyan.panda.tv/10e9eeed4a300ac04a48ed7b9c79ec06.jpeg
         * picture :
         * playstatus : 1
         * status : 0
         * lock_reason : []
         * personnum : 5435
         * shareurl : http://m.xingyan.panda.tv/live.html?xid=6457143
         * shareimg : http://i5.pdim.gs/dmfd/100_100_70/b32cd688b637dbf1db61b4fe31bba315.jpg
         * userlevel : 6
         * userlevelicon : user6
         * level : 10
         * levelicon : anchor10
         * exp : 2518996
         * levelmin : 1500000
         * levelmax : 4249999
         */

        private String rid;
        private String xid;
        private String name;
        private String notice;
        private String photo;
        private String picture;
        private String playstatus;
        private String status;
        private String personnum;
        private String shareurl;
        private String shareimg;
        private String userlevel;
        private String userlevelicon;
        private String level;
        private String levelicon;
        private String exp;
        private String levelmin;
        private String levelmax;
        private List<?> lock_reason;

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getXid() {
            return xid;
        }

        public void setXid(String xid) {
            this.xid = xid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getPlaystatus() {
            return playstatus;
        }

        public void setPlaystatus(String playstatus) {
            this.playstatus = playstatus;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPersonnum() {
            return personnum;
        }

        public void setPersonnum(String personnum) {
            this.personnum = personnum;
        }

        public String getShareurl() {
            return shareurl;
        }

        public void setShareurl(String shareurl) {
            this.shareurl = shareurl;
        }

        public String getShareimg() {
            return shareimg;
        }

        public void setShareimg(String shareimg) {
            this.shareimg = shareimg;
        }

        public String getUserlevel() {
            return userlevel;
        }

        public void setUserlevel(String userlevel) {
            this.userlevel = userlevel;
        }

        public String getUserlevelicon() {
            return userlevelicon;
        }

        public void setUserlevelicon(String userlevelicon) {
            this.userlevelicon = userlevelicon;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLevelicon() {
            return levelicon;
        }

        public void setLevelicon(String levelicon) {
            this.levelicon = levelicon;
        }

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getLevelmin() {
            return levelmin;
        }

        public void setLevelmin(String levelmin) {
            this.levelmin = levelmin;
        }

        public String getLevelmax() {
            return levelmax;
        }

        public void setLevelmax(String levelmax) {
            this.levelmax = levelmax;
        }

        public List<?> getLock_reason() {
            return lock_reason;
        }

        public void setLock_reason(List<?> lock_reason) {
            this.lock_reason = lock_reason;
        }

        @Override
        public String toString() {
            return "RoominfoBean{" +
                    "rid='" + rid + '\'' +
                    ", xid='" + xid + '\'' +
                    ", name='" + name + '\'' +
                    ", notice='" + notice + '\'' +
                    ", photo='" + photo + '\'' +
                    ", picture='" + picture + '\'' +
                    ", playstatus='" + playstatus + '\'' +
                    ", status='" + status + '\'' +
                    ", personnum='" + personnum + '\'' +
                    ", shareurl='" + shareurl + '\'' +
                    ", shareimg='" + shareimg + '\'' +
                    ", userlevel='" + userlevel + '\'' +
                    ", userlevelicon='" + userlevelicon + '\'' +
                    ", level='" + level + '\'' +
                    ", levelicon='" + levelicon + '\'' +
                    ", exp='" + exp + '\'' +
                    ", levelmin='" + levelmin + '\'' +
                    ", levelmax='" + levelmax + '\'' +
                    ", lock_reason=" + lock_reason +
                    '}';
        }
    }

    public static class VideoinfoBean {
        /**
         * streamurl : http://flv-live-ws.xingyan.panda.tv/panda-xingyan/caa8b6eb9373eaf95a5d65de60021153.flv
         * hlsurl : http://hls-live-ws.xingyan.panda.tv/panda-xingyan/caa8b6eb9373eaf95a5d65de60021153.m3u8
         * streaminfo : {"width":"480","height":"848","decode_type":2}
         */

        private String streamurl;
        private String hlsurl;
        private StreaminfoBean streaminfo;

        public String getStreamurl() {
            return streamurl;
        }

        public void setStreamurl(String streamurl) {
            this.streamurl = streamurl;
        }

        public String getHlsurl() {
            return hlsurl;
        }

        public void setHlsurl(String hlsurl) {
            this.hlsurl = hlsurl;
        }

        public StreaminfoBean getStreaminfo() {
            return streaminfo;
        }

        public void setStreaminfo(StreaminfoBean streaminfo) {
            this.streaminfo = streaminfo;
        }

        public static class StreaminfoBean {
            /**
             * width : 480
             * height : 848
             * decode_type : 2
             */

            private String width;
            private String height;
            private int decode_type;

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public int getDecode_type() {
                return decode_type;
            }

            public void setDecode_type(int decode_type) {
                this.decode_type = decode_type;
            }
        }
    }

    public static class HostinfoBean {
        /**
         * rid : 79326582
         * nickName : 护食的艺文baby
         * avatar : http://i5.pdim.gs/dmfd/100_100_70/b32cd688b637dbf1db61b4fe31bba315.jpg
         * xid : 6457143
         * gender : 2
         * signature : 631747719QQ群 微博名俄罗斯大妞儿
         * is_anchor : 1
         * starval : 2427742
         */

        private String rid;
        private String nickName;
        private String avatar;
        private String xid;
        private String gender;
        private String signature;
        private String is_anchor;
        private String starval;

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getXid() {
            return xid;
        }

        public void setXid(String xid) {
            this.xid = xid;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getIs_anchor() {
            return is_anchor;
        }

        public void setIs_anchor(String is_anchor) {
            this.is_anchor = is_anchor;
        }

        public String getStarval() {
            return starval;
        }

        public void setStarval(String starval) {
            this.starval = starval;
        }
    }
}
