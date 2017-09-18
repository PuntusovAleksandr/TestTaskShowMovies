package com.aleksandrp.testapplicationalinataranovskaya.di;

import com.aleksandrp.testapplicationalinataranovskaya.activity.DetailsMoveActivity;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.DetailsActivityModule;

import javax.inject.Singleton;

import dagger.Subcomponent;

/**
 * Created by AleksandrP on 18.09.2017.
 */

@Singleton
@Subcomponent(modules = {DetailsActivityModule.class})
public interface DetailsActivityComponent {
    void inject(DetailsMoveActivity activity);
}
