package com.iqilu.message.transfer.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lubin
 */
@Getter
@Setter
@NoArgsConstructor
public class MessageBody implements Serializable {

    private Long id;

    private String receiver;

    private String appId;

    private String sender;

    private String messageContent;

    private JSONObject templateParam;

    private String templateCode;

    private JSONObject attachConfig;

    private Date createTime;

}
