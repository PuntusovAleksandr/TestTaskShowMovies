package com.aleksandrp.testapplicationalinataranovskaya.api.helper;

import com.aleksandrp.testapplicationalinataranovskaya.api.RestAdapter;
import com.aleksandrp.testapplicationalinataranovskaya.api.bus.BusProvider;
import com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkResponseEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.interfaces.ServiceTask;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class ApiMoveHelper {

    protected RestAdapter restAdapter;

    public ApiMoveHelper() {
        restAdapter = new RestAdapter();
    }

    public void createTask() {
        restAdapter.init(true, "createTask");
        ServiceTask serviceUser =
                restAdapter.getRetrofit().create(ServiceTask.class);
        Observable<Response<Object>> allSources =
                serviceUser.createTask();
        allSources.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Object>>() {
                    private NetworkResponseEvent event;
                    private NetworkResponseEvent<String> eventError;
                    private Object body;

                    @Override
                    public void onCompleted() {
                        if (event != null) {
                            event.setSucess(true);
                            event.setData(body);
                        } else {
                            event = new NetworkResponseEvent<>();
                            event.setId(ApiConstants.ERROR);
                            event.setSucess(false);
                        }
                        BusProvider.send(event);
                    }

                    @Override
                    public void onError(Throwable e) {
                        eventError = new NetworkResponseEvent<>();
                        eventError.setId(ApiConstants.ERROR);
                        eventError.setData("Error load Sources ::: " + e.getMessage());
                        eventError.setSucess(false);
                        BusProvider.send(eventError);
                    }

                    @Override
                    public void onNext(Response<Object> mResponse) {
                        if (mResponse.isSuccessful()) {
                            event = new NetworkResponseEvent<>();
                            event.setId(ApiConstants.ERROR);
                            body = mResponse.body();
                        }
                    }
                });
    }

}
