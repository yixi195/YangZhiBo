package com.turing.live.model;

/**
 * Created by YSL on 2017/6/26.
 */

public class Category {
    /**
     * id : 0
     * tag : 热门
     */

    private String id;
    private String tag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
