package com.aleksandrp.testapplicationalinataranovskaya.presenter;

import com.aleksandrp.testapplicationalinataranovskaya.activity.DetailsMoveActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.bus.BusProvider;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkFailEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkRequestEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.UpdateUiEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.FullInfoMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.PresenterEventListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.GET_MOVE_INFO;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.RESPONSE_GET_MOVE_INFO;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class DetailsPresenter extends BasePresenter implements PresenterEventListener {


    private Subscriber subscriber;

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
                if (mO instanceof UpdateUiEvent) {
                    UpdateUiEvent event = (UpdateUiEvent) mO;
                    Object data = event.getData();
                    if (event.getId() == RESPONSE_GET_MOVE_INFO) {
                        showDetails((FullInfoMoveModel) data);
                    }
                } else if (mO instanceof NetworkFailEvent) {
                    NetworkFailEvent event = (NetworkFailEvent) mO;
                    showMessageError(event.getMessage());
                }
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

    //================================================

    public void showMessageError(String mData) {
        try {
            ((DetailsMoveActivity) mvpView).showMessageError(mData);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

    public void showDetails(FullInfoMoveModel mData) {
        try {
            ((DetailsMoveActivity) mvpView).showDetails(mData);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

    //================================================

    public void getDetailsMove(long id) {
        final NetworkRequestEvent mEvent = new NetworkRequestEvent();
        mEvent.setId(GET_MOVE_INFO);
        ((DetailsMoveActivity) mvpView).makeRequest(mEvent, id);
    }
}
