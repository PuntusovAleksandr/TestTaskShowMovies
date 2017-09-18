package com.aleksandrp.testapplicationalinataranovskaya.di;

import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.AppModule;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.DetailsActivityModule;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.MainActivityModule;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.SearchActivityModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by AleksandrP on 18.09.2017.
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    MainActivityComponent plus(MainActivityModule module);

    SearchActivityComponent plus(SearchActivityModule module);

    DetailsActivityComponent plus(DetailsActivityModule module);
}
