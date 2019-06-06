package com.xxc.dev.common.callback;

/**
 * 带一个参数，含有返回值的回调
 *
 * @param <Param>  参数
 * @param <Result> 返回值
 */
public interface CallReturn<Param, Result> {

    Result onCallReturn(Param param);
}
