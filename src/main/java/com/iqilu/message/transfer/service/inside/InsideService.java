package com.iqilu.message.transfer.service.inside;


import com.iqilu.message.transfer.aop.StrParam;
import com.iqilu.message.transfer.pojo.MessageBody;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author 卢斌
 */
public interface InsideService {

    /**
     * 散装发送多条socket消息
     *
     * @param senderPrimaryKey  发送者电话号码
     * @param appId             应用Id
     * @param messageList       消息列表
     */
    void pushInsideMessage(@StrParam String senderPrimaryKey, @StrParam String appId, @Size(min = 1) List<MessageBody> messageList);


    /**
     * 刷新缓存的APP-SECRETE
     */
    void refreshInsideAppSecret();

}
