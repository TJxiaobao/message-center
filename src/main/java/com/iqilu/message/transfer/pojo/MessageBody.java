package com.iqilu.message.transfer.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author lubin
 */
@Getter
@Setter
@NoArgsConstructor
public class MessageBody implements Serializable {

    private String receiver;

    private String messageContent;

    private JSONObject templateParam;

    private JSONObject attachConfig;

}
