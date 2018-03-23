package com.ea.card.trade.exception;

import com.lmtech.exceptions.ErrorCodeException;

/**
 * 订单不存在异常
 * @author huang.jb
 */
public class OrderNotExistException extends ErrorCodeException {
    public OrderNotExistException() {
        super("订单不存在", 5000000);
    }

    public OrderNotExistException(String message, long errorCode) {
        super(message, errorCode);
    }
}
