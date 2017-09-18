package com.aleksandrp.testapplicationalinataranovskaya.di;

import com.aleksandrp.testapplicationalinataranovskaya.activity.SearchActivity;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.SearchActivityModule;

import javax.inject.Singleton;

import dagger.Subcomponent;

/**
 * Created by AleksandrP on 18.09.2017.
 */

@Singleton
@Subcomponent(modules = {SearchActivityModule.class})
public interface SearchActivityComponent {
    void inject(SearchActivity activity);
}
