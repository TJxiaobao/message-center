package com.iqilu.message.transfer.service.wechat.management;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iqilu.message.transfer.exception.CustomException;
import com.iqilu.message.transfer.pojo.MessageBody;
import com.iqilu.message.transfer.pojo.wechat.WeChatLoginResult;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 卢斌
 */
@Log4j2
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

    @Value("${chat.application.access-token-url}")
    private String wechatAccessTokenRequestUrl;

    @Value("${chat.push-message-url}")
    private String wechatPushMessageUrl;

    @Value("${chat.redis-key.access-token.value}")
    private String accessTokenRedisKey;

    @Value("${chat.push-message-url}")
    private String accessPushUrl;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;


    public WeChatLoginResult requestLogin(String code) {
        HttpEntity responseEntity = null;
        HttpGet httpGet = new HttpGet(weChatLoginRequestBaseUrl + "?appid=" + appId + "&secret=" + secret + "&grant_type=authorization_code&js_code=" + code);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response;
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

    public String requestRemoteAccessToken() {
        String accessTokenRequestUrl = wechatAccessTokenRequestUrl + "?grant_type=client_credential&appid=" + appId + "&secret=" + secret;
        log.debug("获取accessToken请求地址：{}", accessTokenRequestUrl);
        String responseString = HttpUtil.get(accessTokenRequestUrl);
        log.debug("accessToken获取到的返回值：{}", responseString);
        JSONObject responseJson = JSONObject.parseObject(responseString);
        String accessToken = responseJson.getString("access_token");
        if (StringUtils.hasLength(accessToken)) {
            return accessToken;
        } else {
            log.error(responseString);
            return null;
        }
    }


    public String getAccessToken() {
        Serializable accessToken = redisTemplate.opsForValue().get(accessTokenRedisKey);
        if (accessToken == null) {
            throw new CustomException("无法获取有效AccessToken");
        } else {
            return (String) accessToken;
        }
    }


    /**
     * 发送微信消息
     */
    public void sendWechatMessage(MessageBody messageBody) {
        List<String> sendFailedOpenId = new LinkedList<>();
        String requestUrl = wechatPushMessageUrl + "?access_token=" + getAccessToken();
        JSONObject requestParam = new JSONObject();
        requestParam.put("touser", messageBody.getReceiver());
        requestParam.put("template_id", messageBody.getTemplateCode());
        requestParam.put("url", "https://www.mrslu.top");
        requestParam.put("topcolor", "#FF0000");
        requestParam.put("data", messageBody.getTemplateParam());
        String sendResponse = HttpUtil.post(requestUrl, requestParam.toJSONString());
        log.info("消息发送结果：{}", sendResponse);
    }


}
