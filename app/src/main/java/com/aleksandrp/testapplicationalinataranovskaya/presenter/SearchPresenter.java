package com.aleksandrp.testapplicationalinataranovskaya.presenter;

import com.aleksandrp.testapplicationalinataranovskaya.activity.SearchActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.helper.ApiMoveHelper;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpView;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class SearchPresenter {

    private MvpView mvpView;
    private ApiMoveHelper apiHelper;

    @Inject
    public SearchPresenter(ApiMoveHelper mApiHelper, MvpView mMvpView) {
        this.mvpView = mMvpView;
        this.apiHelper = mApiHelper;
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
        showDialog();
        apiHelper.searchMovies(search)
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
                            showListMovies(body);
                        } else {
                            showMessageError(mResponse.message());
                        }
                    }
                });
    }

    private void showDialog() {
        try {
            ((SearchActivity) mvpView).showProgress(true);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }
}
