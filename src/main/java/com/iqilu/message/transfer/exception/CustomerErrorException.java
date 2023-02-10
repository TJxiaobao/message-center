package com.iqilu.message.transfer.exception;

import java.io.Serializable;

/**
 * @author 卢斌
 */
public class CustomerErrorException extends RuntimeException implements Serializable {
    public CustomerErrorException(String message) {
        super(message);
    }
}
