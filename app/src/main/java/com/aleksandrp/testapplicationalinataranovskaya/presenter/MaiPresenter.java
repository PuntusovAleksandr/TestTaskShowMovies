package com.aleksandrp.testapplicationalinataranovskaya.presenter;

import com.aleksandrp.testapplicationalinataranovskaya.activity.MainActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.helper.ApiMoveHelper;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListGenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpView;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class MaiPresenter {

    private MvpView mvpView;
    private ApiMoveHelper apiHelper;

    @Inject
    public MaiPresenter(ApiMoveHelper mApiHelper, MvpView mMvpView) {
        this.mvpView = mMvpView;
        this.apiHelper = mApiHelper;
    }

    //================================================

    public void showMessageError(String mData) {
        try {
            ((MainActivity) mvpView).showMessageError(mData);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

    public void showListPopular(ListMoveModel mData) {
        try {
            ((MainActivity) mvpView).showListPopular(mData);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

    public void showGenres(ListGenresModel mData) {
        ((MainActivity) mvpView).showGenres(mData.genres);
    }
    //================================================

    public void getListGenres() {
        showDialog();
        apiHelper.getListGenres()
                .subscribe(new Subscriber<Response<ListGenresModel>>() {
                    private ListGenresModel body;

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessageError(e.getMessage());
                    }

                    @Override
                    public void onNext(Response<ListGenresModel> mResponse) {
                        if (mResponse.isSuccessful()) {
                            body = mResponse.body();
                            showGenres(body);
                        } else {
                            showMessageError(mResponse.message());
                        }
                    }
                });
    }

    public void getLostMoves(String genres) {
        getLostMoves(genres, 1);
    }

    public void getLostMoves(String genres, int current_page) {
        showDialog();
        apiHelper.getListPopular(current_page, genres)
                .subscribe(new Subscriber<Response<ListMoveModel>>() {
                    private ListMoveModel body;

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessageError(e.getMessage());
                    }

                    @Override
                    public void onNext(Response<ListMoveModel> mResponse) {
                        if (mResponse.isSuccessful()) {
                            body = mResponse.body();
                            showListPopular(body);
                        } else {
                            showMessageError(mResponse.message());
                        }
                    }
                });
    }

    private void showDialog() {
        try {
            ((MainActivity) mvpView).showProgress(true);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }

}
