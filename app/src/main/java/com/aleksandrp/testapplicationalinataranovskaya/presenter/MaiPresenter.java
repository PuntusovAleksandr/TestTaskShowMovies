package com.aleksandrp.testapplicationalinataranovskaya.presenter;

import android.support.v4.app.FragmentManager;

import com.aleksandrp.testapplicationalinataranovskaya.activity.MainActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.bus.BusProvider;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkFailEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkRequestEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.UpdateUiEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListGenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.PresenterEventListener;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.LIST_OFFICIAL;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.LIST_POPULAR;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.RESPONSE_LIST_OFFICIAL;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.RESPONSE_LIST_POPULAR;
import static com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants.SEARCH_MOVE;

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
                if (mO instanceof UpdateUiEvent) {
                    UpdateUiEvent event = (UpdateUiEvent) mO;
                    Object data = event.getData();
                    if (event.getId() == RESPONSE_LIST_POPULAR) {
                        showListPopular((ListMoveModel) data);
                    } else if (event.getId() == RESPONSE_LIST_OFFICIAL) {
                        showGenres((ListGenresModel) data);
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
        ((MainActivity) mvpView).showMessageError(mData);
    }

    public void showListPopular(ListMoveModel mData) {
        ((MainActivity) mvpView).showListPopular(mData);
    }

    public void showGenres(ListGenresModel mData) {
        ((MainActivity) mvpView).showGenres(mData.genres);
    }
    //================================================

    public void getListGenres() {
        final NetworkRequestEvent mEvent = new NetworkRequestEvent();
        mEvent.setId(LIST_OFFICIAL);
        ((MainActivity) mvpView).makeRequest(mEvent, null, null);
    }

    public void getLostMoves(String genres) {
        final NetworkRequestEvent mEvent = new NetworkRequestEvent();
        mEvent.setId(LIST_POPULAR);
        ((MainActivity) mvpView).makeRequest(mEvent, null, genres);
    }

    public void searchMovies(String search) {
        final NetworkRequestEvent mEvent = new NetworkRequestEvent();
        mEvent.setId(SEARCH_MOVE);
        ((MainActivity) mvpView).makeRequest(mEvent, search, null);
    }
}
