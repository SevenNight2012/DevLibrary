package com.xxc.dev.network.okgo.exception;

/**
 * 泛型解析异常
 */
public class GenericException extends RuntimeException {

    public GenericException() {
        super("Can not parse the result of INetworkResult<Result>");
    }
}
