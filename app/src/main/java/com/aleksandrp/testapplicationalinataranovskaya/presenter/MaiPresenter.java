package com.aleksandrp.testapplicationalinataranovskaya.presenter;

import android.support.v4.app.FragmentManager;

import com.aleksandrp.testapplicationalinataranovskaya.api.bus.BusProvider;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.PresenterEventListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class MaiPresenter extends BasePresenter implements PresenterEventListener {


    private Subscriber subscriber;
    private FragmentManager mFragmentManager;
    private boolean isWork;

    public void initFragmentManager(FragmentManager mFragmentManager, boolean isWork) {
        this.mFragmentManager = mFragmentManager;
        this.isWork = isWork;
    }

    @Override
    public void init() {

    }

    @Override
    public void registerSubscriber() {
        subscriber = new Subscriber() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Object mO) {
//                if (mO instanceof UpdateUiEvent) {
//                    UpdateUiEvent event = (UpdateUiEvent) mO;
//                    Object data = event.getData();
//                    if (event.getId() == RESPONSE_GET_PLACES) {
//                        saveTasksDb((List<PlaceModel>) data);
//                    } else if (event.getId() == RESPONSE_GET_ID_TASK) {
//                        showDataTask((TaskModel) data);
//                    }
//                } else if (mO instanceof NetworkFailEvent) {
//                    NetworkFailEvent event = (NetworkFailEvent) mO;
//                    showMessageError(event.getMessage());
//                }
            }
        };
        BusProvider.observe().observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);
    }

    //================================================
    @Override
    public void unRegisterSubscriber() {
        if (subscriber != null && !subscriber.isUnsubscribed()) {
            subscriber.unsubscribe();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        subscriber = null;
    }

}
