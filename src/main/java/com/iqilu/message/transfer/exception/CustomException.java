package com.iqilu.message.transfer.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 卢斌
 */
@Slf4j
public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message);
    }
}
