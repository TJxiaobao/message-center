package com.iqilu.message.transfer.pojo;

import java.io.Serializable;

/**
 * 消息推送途径（选择短信推送方式，还是微信消息推送）
 *
 * @author 卢斌
 */
public enum SendWayEnum implements Serializable {


    /**
     * 微信
     */
    WECHAT,

    /**
     * 应用推送
     */
    APP_PUSH,

    /**
     * 短信推送
     */
    SMS,

    /**
     * socket推送
     */
    SOCKET;

}
