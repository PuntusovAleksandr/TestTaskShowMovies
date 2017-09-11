package com.aleksandrp.testapplicationalinataranovskaya.api.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.api.bus.BusProvider;
import com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkFailEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkResponseEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.UpdateUiEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.helper.ApiMoveHelper;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_GENRES_ID;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.EXTRA_PAGE;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.STATIC_PARAMS.SERVICE_JOB_ID_TITLE;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class ServiceApi extends Service {

    private Subscriber subscriber;
    private static final String LOGGER_TAG = "ServiceApi";
    private int startId;

    private ApiMoveHelper mMoveHelper;

    public ServiceApi() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("ID_SERVICE", "onCreate ID_SERVICE " + startId);
        subscriber = new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                if (o instanceof NetworkResponseEvent) {
                    NetworkResponseEvent event = (NetworkResponseEvent) o;
                    if (!event.isSucess()) {
                        requestFailed(event);
                    } else {
                        requestCallBack(event);
                    }
                }
            }
        };
        BusProvider.observe()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        Log.d(LOGGER_TAG, "subscribe");
        mMoveHelper = new ApiMoveHelper();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        subscriber.unsubscribe();
        subscriber = null;
        mMoveHelper = null;
        Log.d("ID_SERVICE", "onDestroy ID_SERVICE " + startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d("ID_SERVICE", "onBind ID_SERVICE " + startId);
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLowMemory() {
        Log.d("ID_SERVICE", "onLowMemory  stopSelf ID_SERVICE " + startId);
        stopSelf(startId);
        super.onLowMemory();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        int jobId = intent.getIntExtra(SERVICE_JOB_ID_TITLE, -1);
        int page = intent.getIntExtra(EXTRA_PAGE, 1);
        String genres = intent.getStringExtra(EXTRA_GENRES_ID);
        switch (jobId) {
            //
            case ApiConstants.LIST_POPULAR:
                mMoveHelper.getListPopular(page, genres);
                break;
        }
        return START_NOT_STICKY;
    }


    private void requestCallBack(NetworkResponseEvent event) {
        Log.d("ID_SERVICE", "requestCallBack ID_SERVICE " + startId);
        UpdateUiEvent updateUiEvent = new UpdateUiEvent();
        updateUiEvent.setSuccess(event.isSucess());
        updateUiEvent.setData(event.getData());
        switch (event.getId()) {
            //
            case ApiConstants.LIST_POPULAR:
                updateUiEvent.setId(ApiConstants.RESPONSE_LIST_POPULAR);
                break;

        }
        if (updateUiEvent != null) {
            BusProvider.send(updateUiEvent);
        }
        stopSelf(startId);
    }

    private void requestFailed(NetworkResponseEvent event) {

        NetworkFailEvent networkFailEvent = new NetworkFailEvent();
        if (event.getId() == ApiConstants.ERROR) {
            String data = (String) event.getData();
            if (data.isEmpty()) data = App.getContext().getString(R.string.bad_internet);
            networkFailEvent.setMessage(data);
        } else {
            networkFailEvent.setMessage("Error request");
        }
        BusProvider.send(networkFailEvent);
        Log.d("ID_SERVICE", "requestFailed ID_SERVICE " + startId);
        stopSelf(startId);
    }
}
