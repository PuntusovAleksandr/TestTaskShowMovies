package com.aleksandrp.testapplicationalinataranovskaya.api.interfaces;

import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public interface ServiceTask {

    @Multipart
    @POST("tasks")
    Observable<Response<Object>> createTask();

}
