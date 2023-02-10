package com.iqilu.message.transfer.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户锁执行期间发生的业务异常
 *
 * @author 卢斌
 */
@Getter
@Setter
public class UploadHeadPictureException extends RuntimeException{
    private Long userId;

    public UploadHeadPictureException(String message, Long userId) {
        super(message);
        this.userId = userId;
    }
}
