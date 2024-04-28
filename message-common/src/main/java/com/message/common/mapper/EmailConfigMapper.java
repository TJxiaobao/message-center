package com.message.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.message.common.domin.EmailConfig;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EmailConfigMapper extends BaseMapper<EmailConfig> {
}
