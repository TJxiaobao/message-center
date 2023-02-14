package com.iqilu.message.transfer.pojo;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 消息发送参数
 *
 * @author lubin
 */
@Getter
@Setter
@NoArgsConstructor
public class MessageParam implements Serializable {

    /**
     * 发送人唯一标识
     */
    private String sender;

    /**
     * 发送人唯一标识类型
     */
    private UserKeyParamTypeEnum receiverParamType;

    /**
     * 消息收信人，消息内容
     */
    private List<MessageBody> messageList;

    /**
     * 消息发送途径
     */
    private SendWayEnum sendWay;

    /**
     * 模板编号（如果有）
     */
    private String templateCode;

    /**
     * 模板/签名名称
     */
    private String signName;

}
