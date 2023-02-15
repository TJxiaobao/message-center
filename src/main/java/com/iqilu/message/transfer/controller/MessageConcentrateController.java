package com.iqilu.message.transfer.controller;

import com.iqilu.message.transfer.controller.result.Result;
import com.iqilu.message.transfer.exception.CustomException;
import com.iqilu.message.transfer.exception.CustomerErrorException;
import com.iqilu.message.transfer.pojo.MessageParam;
import com.iqilu.message.transfer.service.sms.SmsService;
import com.iqilu.message.transfer.service.wechat.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author lubin
 */
@Controller
@ResponseBody
@CrossOrigin
@RequestMapping(value = "/concentrate")
public class MessageConcentrateController {

    @Autowired
    private SmsService smsService;

    @Autowired
    private WeChatService weChatService;


    @PostMapping(value = "/message")
    public Result<?> sendMessage(@RequestBody MessageParam messageParam) {
        try {
            switch (messageParam.getSendWay()) {
                case SMS:
                    Long sender = Long.parseLong(messageParam.getSender());
                    smsService.sendSmsMessageBulk(sender, messageParam.getTemplateCode(), messageParam.getSignName(), messageParam.getMessageList());
                    break;
                case WECHAT:
                    weChatService.sendWechatMessageBulk(messageParam.getMessageList());
                    break;
                default:
                    throw new CustomException("暂不支持该类发送方式");
            }
        } catch (NumberFormatException e) {
            throw new CustomerErrorException("接口数据类型有误");
        }

        return Result.ok();
    }

}
