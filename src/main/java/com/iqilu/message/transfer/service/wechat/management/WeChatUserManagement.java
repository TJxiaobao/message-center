package com.iqilu.message.transfer.service.wechat.management;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqilu.message.transfer.exception.CustomException;
import com.iqilu.message.transfer.pojo.wechat.WeChatLoginResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author 卢斌
 */
@Component
public class WeChatUserManagement {

    /**
     * 小程序的appId
     */
    @Value("${chat.application.app_id}")
    private String appId;

    @Value("${chat.application.secret}")
    private String secret;

    @Value("${chat.application.base_url}")
    private String weChatLoginRequestBaseUrl;

    public WeChatLoginResult requestLogin(String code) {
        HttpEntity responseEntity = null;
        HttpGet httpGet = new HttpGet(weChatLoginRequestBaseUrl + "?appid=" + appId + "&secret=" + secret + "&grant_type=authorization_code&js_code=" + code);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String openId = null;
        String sessionKey = null;
        try {
            response = client.execute(httpGet);
            responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String responseContent = EntityUtils.toString(responseEntity, "UTF-8");
                JSONObject jsonObject = new JSONObject(JSON.parseObject(responseContent));
                Integer errorCode = jsonObject.getInteger("errcode");
                if (errorCode != null) {
                    switch (errorCode) {
                        case -1:    throw new CustomException("系统繁忙，此时请稍候再试");
                        case 40029: throw new CustomException("登录识别码无效");
                        case 45011: throw new CustomException("请勿尝试攻击服务器");
                        case 40226: throw new CustomException("您存在违规操作记录，所以暂时无法使用本软件");
                        default: throw new CustomException("未知错误");
                    }
                }
                openId = jsonObject.getString("openid");
                sessionKey = jsonObject.getString("session_key");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        WeChatLoginResult result = new WeChatLoginResult();
        result.setOpenId(openId);
        result.setSessionKey(sessionKey);
        return result;
    }

}
