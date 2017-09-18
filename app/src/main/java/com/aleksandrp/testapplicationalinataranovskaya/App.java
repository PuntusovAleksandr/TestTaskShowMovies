package com.aleksandrp.testapplicationalinataranovskaya;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.aleksandrp.testapplicationalinataranovskaya.di.AppComponent;
import com.aleksandrp.testapplicationalinataranovskaya.di.DaggerAppComponent;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.AppModule;
import com.aleksandrp.testapplicationalinataranovskaya.di.modulies.RealmModule;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    public static final String TAG = App.class.getSimpleName();

    private static Context context;
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        App.context = this.getApplicationContext();
        registerActivityLifecycleCallbacks(this);

        appComponent = DaggerAppComponent
                .builder()
                .appModule(new AppModule(this))
                .realmModule(new RealmModule(this))
                .build();
    }

    public static Context getContext() {
        return context;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onActivityCreated(Activity mActivity, Bundle mBundle) {

    }

    @Override
    public void onActivityStarted(Activity mActivity) {

    }

    @Override
    public void onActivityResumed(Activity mActivity) {

    }

    @Override
    public void onActivityPaused(Activity mActivity) {

    }

    @Override
    public void onActivityStopped(Activity mActivity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity mActivity, Bundle mBundle) {

    }

    @Override
    public void onActivityDestroyed(Activity mActivity) {

    }

}
