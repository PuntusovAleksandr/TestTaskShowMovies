package com.aleksandrp.testapplicationalinataranovskaya.db;

import android.content.Context;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.db.models.MoveModelDb;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.DetailsPresenter;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class RealmObj {

    public static final String LOG_REALM = "LOG_REALM";
    // version DB
    public static final long VERSION_DB = 1;

    private static RealmObj sRealmObj;
    private Context context;
    private Realm realm;
    private int allNameTypeCalendarsByUser;


    public synchronized static RealmObj getInstance() {
        if (sRealmObj == null) {
            sRealmObj = new RealmObj();
        }
        return sRealmObj;
    }

    /**
     * for creating (or change) data base, need reopen Realm
     * This method need calling after save data in Shared preference
     */
    public static void stopRealm() {
        if (sRealmObj != null) {
            sRealmObj.closeRealm(App.getContext());
        }
    }

    private void closeRealm(Context context) {
        if (realm != null) {
            realm.close();
            realm = null;
            setRealmData(context);
        }
    }


    private RealmObj() {
        this.context = App.getContext();
        if (realm == null) {
            setRealmData(context);
        }
    }

    private void setRealmData(Context context) {
        String nameDB = RealmObj.class.getName();
        realm.init(context);
        RealmConfiguration myConfig = new RealmConfiguration.Builder()
                .name(nameDB)
                .deleteRealmIfMigrationNeeded()
                .schemaVersion(VERSION_DB)
                .build();
        realm = Realm.getInstance(myConfig);
    }

    //    ===============================================================

    public void updateMove(final MoveModelDb mModelDb, final DetailsPresenter mPresenter) {
        realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.copyToRealmOrUpdate(mModelDb);
                    }
                }, new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        mPresenter.showMessageError(App.getContext().getString(R.string.data_update));
                    }
                }, new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        mPresenter.showMessageError(App.getContext().getString(R.string.error_data_update));
                    }
                });
    }

}
