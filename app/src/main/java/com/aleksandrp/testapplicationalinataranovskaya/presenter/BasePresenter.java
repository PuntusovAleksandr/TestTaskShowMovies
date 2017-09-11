package com.aleksandrp.testapplicationalinataranovskaya.presenter;

import com.aleksandrp.testapplicationalinataranovskaya.presenter.interfaces.MvpView;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public abstract class BasePresenter {

    protected MvpView mvpView;

    public abstract void init();

    public void destroy(){
        mvpView = null;
    };

    public MvpView getMvpView() {
        return mvpView;
    }

    public void setMvpView(MvpView mvpView) {
        this.mvpView = mvpView;
    }

}
