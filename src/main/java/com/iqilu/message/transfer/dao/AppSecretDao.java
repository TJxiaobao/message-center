package com.iqilu.message.transfer.dao;

import com.iqilu.message.transfer.pojo.AppSecret;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 卢斌
 */
@Mapper
public interface AppSecretDao {


    /**
     * 获取所有的APP-SECRET
     */
    List<AppSecret> listAllAppSecrets();

}
