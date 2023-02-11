package com.iqilu.message.transfer.pojo.wechat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 卢斌
 */
@Getter
@Setter
@NoArgsConstructor
public class WeChatLoginResult {

    private String openId;

    private String sessionKey;
    
}
