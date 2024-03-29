package com.企业微信消息推送.controller;


/**
 * 企业微信推送消息专用token
 *
 * @author shihy
 *
 */

public class AccessToken {
    //获取到的access_token字符串
    private String access_token;
    //有效时间（2h，7200s）
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int valid_time) {
        this.expires_in = valid_time;
    }


}