package com.企业微信消息推送.config;

import lombok.Data;

/**
 * @desc  : 微信通用接口凭证
 *
 */
@Data
public class AccessToken {
    // 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private int expiresIn;

    public String getToken() {
        return token;
    }

}