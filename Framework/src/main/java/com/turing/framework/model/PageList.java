package com.turing.framework.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * 分页泛型
 * Created by huang on 2017/4/19.
 */

public class PageList<T> {


    private int total; //总数量
    private int pageSize; //总页数

    @SerializedName("items")
    private ArrayList<T> rows;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ArrayList<T> getRows() {
        return rows;
    }

    public void setRows(ArrayList<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageList{" +
                "total=" + total +
                ", pageSize=" + pageSize +
                ", rows=" + rows +
                '}';
    }
}
