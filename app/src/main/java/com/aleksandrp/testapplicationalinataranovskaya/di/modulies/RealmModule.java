package com.aleksandrp.testapplicationalinataranovskaya.di.modulies;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AleksandrP on 18.09.2017.
 */

@Module
public class RealmModule {

    public RealmModule(Context context) {
        Realm.init(context);
    }

    @Provides
    @Singleton
    Realm provideRealm(RealmConfiguration realmConfiguration) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
        } catch (Exception e) {
            realm = Realm.getInstance(realmConfiguration);
        }
        return realm;
    }

    @Provides
    @Singleton
    RealmConfiguration provideRealmConfiguration() {
        // Get a Realm instance for this thread
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        return config;
    }
}
