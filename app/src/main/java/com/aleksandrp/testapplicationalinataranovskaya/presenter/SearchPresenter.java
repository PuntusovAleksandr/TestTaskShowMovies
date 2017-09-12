package com.aleksandrp.testapplicationalinataranovskaya.presenter;

import android.support.v4.app.FragmentManager;

import com.aleksandrp.testapplicationalinataranovskaya.activity.SearchActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.bus.BusProvider;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkFailEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkRequestEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.UpdateUiEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.PresenterEventListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.RESPONSE_SEARCH_MOVE;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.SEARCH_MOVE;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class SearchPresenter extends BasePresenter implements PresenterEventListener {


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
                if (mO instanceof UpdateUiEvent) {
                    UpdateUiEvent event = (UpdateUiEvent) mO;
                    Object data = event.getData();
                    if (event.getId() == RESPONSE_SEARCH_MOVE) {
                        showListMovies((ListMoveModel) data);
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
            ((SearchActivity) mvpView).showMessageError(mData);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

    public void showListMovies(ListMoveModel mData) {
        try {
            ((SearchActivity) mvpView).showListFilter(mData);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

    //================================================

    public void searchMovies(String search) {
        final NetworkRequestEvent mEvent = new NetworkRequestEvent();
        mEvent.setId(SEARCH_MOVE);
        ((SearchActivity) mvpView).makeRequest(mEvent, search);
    }
}
