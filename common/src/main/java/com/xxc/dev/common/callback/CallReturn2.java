package com.xxc.dev.common.callback;

/**
 * 带两个参数，含返回值的回调
 *
 * @param <P1>     参数1
 * @param <P2>     参数2
 * @param <Result> 返回值
 */
public interface CallReturn2<P1, P2, Result> {


    Result onCallReturn(P1 p1, P2 p2);
}
