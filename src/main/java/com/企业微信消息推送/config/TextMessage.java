package com.企业微信消息推送.config;

import lombok.Data;

@Data
public class TextMessage {
    // 文本
    private Text text;
    // 否 表示是否是保密消息，0表示否，1表示是，默认0
    private int safe;
}
