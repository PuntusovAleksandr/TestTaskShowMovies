package com.aleksandrp.testapplicationalinataranovskaya.presenter;

import com.aleksandrp.testapplicationalinataranovskaya.activity.DetailsMoveActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.helper.ApiMoveHelper;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.FullInfoMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpView;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class DetailsPresenter {

    private MvpView mvpView;
    private ApiMoveHelper apiHelper;

    @Inject
    public DetailsPresenter(ApiMoveHelper mApiHelper, MvpView mMvpView) {
        this.mvpView = mMvpView;
        this.apiHelper = mApiHelper;
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
        showDialog();
        apiHelper.getDetailsMove(id)
                .subscribe(new Subscriber<Response<FullInfoMoveModel>>() {
                    private FullInfoMoveModel body;

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showMessageError(e.getMessage());
                    }

                    @Override
                    public void onNext(Response<FullInfoMoveModel> mResponse) {
                        if (mResponse.isSuccessful()) {
                            body = mResponse.body();
                            showDetails(body);
                        } else {
                            showMessageError(mResponse.message());
                        }
                    }
                });
    }

    private void showDialog() {
        try {
            ((DetailsMoveActivity) mvpView).showProgress(true);
        } catch (Exception mE) {
            mE.printStackTrace();
        }
    }
}
