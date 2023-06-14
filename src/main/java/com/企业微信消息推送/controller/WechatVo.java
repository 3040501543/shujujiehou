package com.企业微信消息推送.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 企业微信消息推送实体类
 */
public class WechatVo {

    /** 接收消息的用户账号（可以是企业号成员的 UserID，也可以是部门 ID、标签 ID，或者是整个企业号） */
    private String toUser;

    /** 接收消息的部门 ID */
    private String toParty;

    /** 接收消息的标签 ID */
    private String toTag;

    /** 发送的内容 */
    private String content;

    /** 表示是否是保密消息，0 表示否，1 表示是 */
    private String safe;

    /** 企业 ID */
    private String corpId;

    /** 企业应用的 Secret */
    private String corpSecret;

    /** 企业应用的 ID */
    private String agentId;

    /** 消息类型，如 text 表示文本消息，image 表示图片消息等 */
    private String type;
}
