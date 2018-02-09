package com.turing.live.model;

import java.util.List;

/**
 *  轮播Banner
 * Created by YSL on 2017/4/18.
 */

public class Banner {


    private List<BannersBean> banners;
    private List<?> navs;

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public List<?> getNavs() {
        return navs;
    }

    public void setNavs(List<?> navs) {
        this.navs = navs;
    }

    public static class BannersBean {
        /**
         * title : 我去闹海
         * img : https://image.xingyan.panda.tv/01310f00f79f1079eb1d94d252612504.jpeg
         * type : 0
         * display_type : 2
         * style_type : 301
         * roomid : 6457143
         */

        private String title;
        private String img;
        private String type;
        private String display_type;
        private String style_type;
        private String roomid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDisplay_type() {
            return display_type;
        }

        public void setDisplay_type(String display_type) {
            this.display_type = display_type;
        }

        public String getStyle_type() {
            return style_type;
        }

        public void setStyle_type(String style_type) {
            this.style_type = style_type;
        }

        public String getRoomid() {
            return roomid;
        }

        public void setRoomid(String roomid) {
            this.roomid = roomid;
        }
    }
}
