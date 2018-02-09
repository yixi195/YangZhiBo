package com.turing.live.model;

import cn.bmob.v3.BmobObject;

/**
 * 用户
 * Created by YSL on 2017/6/29.
 */

public class User extends BmobObject {
    private String userName;
    private String passWord;
    private String address;
    private String headUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", address='" + address + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }
}
