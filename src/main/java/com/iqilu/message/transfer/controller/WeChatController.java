package com.iqilu.message.transfer.controller;

import com.iqilu.message.transfer.controller.result.Result;
import com.iqilu.message.transfer.service.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 卢斌
 */
@Controller
@ResponseBody
@RequestMapping(value = "/wechat")
public class WeChatController {

    @Autowired
    private WeChatService weChatService;


    /**
     * 微信公众平台验签
     */
    @GetMapping(value = "/signature", params = {"signature", "timestamp", "nonce", "echostr"})
    public Result<?> checkWeChatSignature(String signature, String timestamp, String nonce, String echoStr) {
        Boolean checkResult = weChatService.acceptSignature(signature, timestamp, nonce, echoStr);
        return Result.ok(checkResult);
    }

    @GetMapping(value = "/connection")
    public Result<?> test() {
        return Result.ok();
    }

}
