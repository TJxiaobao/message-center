package com.iqilu.message.transfer.service.sms.management;

import cn.hutool.core.util.ArrayUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendBatchSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendBatchSmsResponse;
import com.iqilu.message.transfer.exception.CustomException;
import darabonba.core.client.ClientOverrideConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author 卢斌
 */
@Slf4j
@Component
public class SmsExecutor {


    public static void sendBatch(LinkedList<Long> phoneNumbers, String signName, String templateCode, JSONObject contentBody) {
        if (CollectionUtils.isEmpty(phoneNumbers)) {
            return;
        }
        JSONArray jsonArray = new JSONArray();
        List<String> signList = new ArrayList<>(phoneNumbers.size());
        for (Long eachPhone : phoneNumbers) {
            JSONObject oneContent = new JSONObject(contentBody);
            signList.add(signName);
            jsonArray.add(oneContent);
        }
        SendBatchSmsRequest sendBatchSmsRequest = SendBatchSmsRequest.builder()
                .phoneNumberJson(JSON.toJSONString(phoneNumbers))
                .signNameJson(JSON.toJSONString(signList))
                .templateCode(templateCode).templateParamJson(jsonArray.toJSONString())
                .build();
        AsyncClient asyncClient = getAsyncClient();
        CompletableFuture<SendBatchSmsResponse> response = asyncClient.sendBatchSms(sendBatchSmsRequest);
        try {
            SendBatchSmsResponse resp = response.get();
            log.info("发送信息：{}", JSON.toJSONString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            asyncClient.close();
        }
    }

    public static void sendBatchBulk(LinkedList<Long> phoneNumbers, List<String> signName, String templateCode, JSONArray contentBody) {
        if (CollectionUtils.isEmpty(phoneNumbers) || CollectionUtils.isEmpty(signName) || CollectionUtils.isEmpty(contentBody)) {
            return;
        }
        int length = phoneNumbers.size();
        if (signName.size() != length || contentBody.size() != length) {
            throw new CustomException("短信发送参数长度不匹配");
        }
        SendBatchSmsRequest sendBatchSmsRequest = SendBatchSmsRequest.builder()
                .phoneNumberJson(JSON.toJSONString(phoneNumbers))
                .signNameJson(JSON.toJSONString(signName))
                .templateCode(templateCode).templateParamJson(contentBody.toJSONString())
                .build();
        AsyncClient asyncClient = getAsyncClient();
        CompletableFuture<SendBatchSmsResponse> response = asyncClient.sendBatchSms(sendBatchSmsRequest);
        try {
            SendBatchSmsResponse resp = response.get();
            log.info("发送信息：{}", JSON.toJSONString(resp));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            asyncClient.close();
        }
    }


    private static AsyncClient getAsyncClient() {
        // Configure the Client
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId("LTAI4FeaYJWLbtH9JtwCJZgS")
                .accessKeySecret("vU0i9egyhVVlqUqkVZs3QYyZB7M5E8")
                .build());
        return AsyncClient.builder()
                .region("cn-qingdao")
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                .setEndpointOverride("dysmsapi.aliyuncs.com")
                )
                .build();
    }

}
