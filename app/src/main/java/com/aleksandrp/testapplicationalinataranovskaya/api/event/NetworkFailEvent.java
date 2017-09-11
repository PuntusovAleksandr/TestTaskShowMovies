package com.aleksandrp.testapplicationalinataranovskaya.api.event;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class NetworkFailEvent {

    private int errorCode;
    private String message;

    public NetworkFailEvent() {
    }

    public NetworkFailEvent(String mData) {
        this.message = mData;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
