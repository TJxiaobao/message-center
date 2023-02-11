package com.iqilu.message.transfer.controller;

import com.iqilu.message.transfer.controller.result.Result;
import com.iqilu.message.transfer.service.WeChatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @author 卢斌
 */
@Log4j2
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
    public void checkWeChatSignature(String signature, String timestamp, String nonce, String echoStr, HttpServletResponse response) throws IOException {
        Boolean checkResult = weChatService.acceptSignature(signature, timestamp, nonce);
        log.debug("验签结果：{}, echoStr:{}", checkResult, echoStr);
        if (Boolean.TRUE.equals(checkResult)) {
            PrintWriter writer = response.getWriter();
            writer.print(echoStr);
            writer.close();
        }
    }

    /**
     * 测试请求
     */
    @GetMapping(value = "/connection")
    public Result<?> test() {
        return Result.ok();
    }

}
