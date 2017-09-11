package com.aleksandrp.testapplicationalinataranovskaya.api.event;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class NetworkRequestEvent<T> extends BaseEvent {


    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
