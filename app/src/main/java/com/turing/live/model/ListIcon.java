package com.turing.live.model;

/**
 * Created by YSL on 2017/6/26.
 */

public class ListIcon {

    /**
     * img_url : null
     * target_url : null
     * info : null
     */

    private Object img_url;
    private Object target_url;
    private Object info;

    public Object getImg_url() {
        return img_url;
    }

    public void setImg_url(Object img_url) {
        this.img_url = img_url;
    }

    public Object getTarget_url() {
        return target_url;
    }

    public void setTarget_url(Object target_url) {
        this.target_url = target_url;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "ListIcon{" +
                "img_url=" + img_url +
                ", target_url=" + target_url +
                ", info=" + info +
                '}';
    }
}
