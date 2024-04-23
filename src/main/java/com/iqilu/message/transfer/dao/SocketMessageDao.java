package com.iqilu.message.transfer.dao;

import com.iqilu.message.transfer.pojo.MessageBody;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 卢斌
 */
@Mapper
public interface SocketMessageDao {


    /**
     * 保存未能及时发送成功的socket消息
     *
     * @param messageBodyList 消息内容
     */
    void saveMessage(@Param("messageBodyList") List<MessageBody> messageBodyList);


    /**
     * 删除某一接收者未接收的所有消息
     *
     * @param receiver  接收者唯一标识
     * @param appId     消息所属应用
     * @param messageId 缓存的消息id
     */
    void deleteMessageByReceiver(@Param("receiver") String receiver, @Param("appId") String appId, @Param("messageId") Long messageId);


    /**
     * 获取所有保存的尚未接收的socket消息
     *
     * @param receiver 接收者唯一标识
     * @param appId    消息所属应用Id
     * @return 保存的尚未接收的socket消息
     */
    List<MessageBody> listAllNeedReceiveMessage(@Param("receiver") String receiver, @Param("appId") String appId);


    /**
     * Id列表查询appId相同的消息内容
     *
     * @param appId         appId
     * @param messageIdList 消息Id列表
     * @return 消息列表
     */
    List<MessageBody> listMessageBodyInIdList(@Param("appId") String appId, @Param("messageIdList") List<Long> messageIdList);


    /**
     * 删除已经发送完成的缓存的站内消息
     *
     * @param appId         appId
     * @param messageIdList 消息Id列表
     */
    void deleteBufferedMessage(@Param("appId") String appId, @Param("messageIdList") List<Long> messageIdList);


}
