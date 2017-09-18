package com.aleksandrp.testapplicationalinataranovskaya.di;

import com.aleksandrp.testapplicationalinataranovskaya.activity.MainActivity;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.MainActivityModule;

import javax.inject.Singleton;

import dagger.Subcomponent;

/**
 * Created by AleksandrP on 17.09.2017.
 */

@Singleton
@Subcomponent(modules = {MainActivityModule.class})
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
