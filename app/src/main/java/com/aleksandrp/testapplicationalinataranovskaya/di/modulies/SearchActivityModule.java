package com.aleksandrp.testapplicationalinataranovskaya.di.modulies;

import com.aleksandrp.testapplicationalinataranovskaya.activity.SearchActivity;
import com.aleksandrp.testapplicationalinataranovskaya.api.helper.ApiMoveHelper;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.SearchPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by AleksandrP on 18.09.2017.
 */

@Module
public class SearchActivityModule {
    protected SearchActivity activity;

    public SearchActivityModule(SearchActivity activity) {
        this.activity = activity;
    }

    @Provides
    public SearchPresenter presenter(ApiMoveHelper mApiHelper) {
        return new SearchPresenter(mApiHelper, activity);
    }
}
