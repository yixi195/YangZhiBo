package com.turing.framework.rx;

import rx.functions.Func1;

/**
 * 服务器数据转换类.
 * <p/>
 */
public class ServerDataMap<T> implements Func1<ServerResult<T>, T> {

    private final int SUCCESS_CODE = 0; //成功

    @Override
    public T call(ServerResult<T> result) {
        if (result.getStatus() == SUCCESS_CODE) {
            return result.getData();
        } else {
            throw new ServerResult.ServerErrorException(result);
        }
    }
}
