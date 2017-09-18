package com.aleksandrp.testapplicationalinataranovskaya.di.modulies;

import com.aleksandrp.testapplicationalinataranovskaya.activity.DetailsMoveActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.helper.ApiMoveHelper;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.DetailsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AleksandrP on 18.09.2017.
 */

@Module
public class DetailsActivityModule {
    protected DetailsMoveActivity activity;

    public DetailsActivityModule(DetailsMoveActivity activity) {
        this.activity = activity;
    }

    @Provides
    public DetailsPresenter presenter(ApiMoveHelper mApiHelper) {
        return new DetailsPresenter(mApiHelper, activity);
    }
}
