package com.aleksandrp.testapplicationalinataranovskaya.di.modulies;

import com.aleksandrp.testapplicationalinataranovskaya.activity.MainActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.helper.ApiMoveHelper;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.MaiPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AleksandrP on 18.09.2017.
 */

@Module
public class MainActivityModule {

    protected MainActivity activity;

    public MainActivityModule(MainActivity activity) {
        this.activity = activity;
    }

    @Provides
    public MaiPresenter presenter(ApiMoveHelper mApiHelper) {
        return new MaiPresenter(mApiHelper, activity);
    }
}
