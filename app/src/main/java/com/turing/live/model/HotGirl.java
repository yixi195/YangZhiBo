package com.turing.live.model;

/**
 * 首页热门主播
 * Created by YSL on 2017/6/26.
 */

public class HotGirl {

    /**
     * xid : 7247757
     * rid : 84203920
     * style_type : 301
     * display_type : 2
     * playstatus : 1
     * name : ^_^
     * photo : https://image.xingyan.panda.tv/9725296799c78859f8440669f1eb568b.jpeg?x-oss-process=image/quality,q_70
     * s_photo : https://image.xingyan.panda.tv/9725296799c78859f8440669f1eb568b.jpeg?x-oss-process=image/resize,w_480/quality,q_70
     * i_photo :
     * personnum : 5200
     * province :
     * city :
     * streamurl : http://flv-live-ws.xingyan.panda.tv/panda-xingyan/c7a3c7e9aad3ba24cdfbd2734b152425.flv
     * tag : {"text":"","color":"","icon":""}
     * streaminfo : {"width":"480","height":"848","decode_type":2}
     * sc : 1
     * nickName : 黑黑啊Black
     * avatar : http://i7.pdim.gs/1404be405182416c45543a193036c2a2.jpg
     * level : 7
     * levelicon : anchor7
     * distance : 身边
     */

    public int height; //用于记录瀑布流高度
    private String xid;
    private String rid;
    private String style_type;
    private String display_type;
    private String playstatus;
    private String name;
    private String photo;
    private String s_photo;
    private String i_photo;
    private String personnum;
    private String province;
    private String city;
    private String streamurl;
    private TagBean tag;
    private StreaminfoBean streaminfo;
    private String sc;
    private String nickName;
    private String avatar;
    private String level;
    private String levelicon;
    private String distance;


    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getStyle_type() {
        return style_type;
    }

    public void setStyle_type(String style_type) {
        this.style_type = style_type;
    }

    public String getDisplay_type() {
        return display_type;
    }

    public void setDisplay_type(String display_type) {
        this.display_type = display_type;
    }

    public String getPlaystatus() {
        return playstatus;
    }

    public void setPlaystatus(String playstatus) {
        this.playstatus = playstatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getS_photo() {
        return s_photo;
    }

    public void setS_photo(String s_photo) {
        this.s_photo = s_photo;
    }

    public String getI_photo() {
        return i_photo;
    }

    public void setI_photo(String i_photo) {
        this.i_photo = i_photo;
    }

    public String getPersonnum() {
        return personnum;
    }

    public void setPersonnum(String personnum) {
        this.personnum = personnum;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreamurl() {
        return streamurl;
    }

    public void setStreamurl(String streamurl) {
        this.streamurl = streamurl;
    }

    public TagBean getTag() {
        return tag;
    }

    public void setTag(TagBean tag) {
        this.tag = tag;
    }

    public StreaminfoBean getStreaminfo() {
        return streaminfo;
    }

    public void setStreaminfo(StreaminfoBean streaminfo) {
        this.streaminfo = streaminfo;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public static class TagBean {
        /**
         * text :
         * color :
         * icon :
         */

        private String text;
        private String color;
        private String icon;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
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
