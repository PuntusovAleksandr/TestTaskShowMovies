package com.aleksandrp.testapplicationalinataranovskaya.db;

import com.aleksandrp.testapplicationalinataranovskaya.App;
import com.aleksandrp.testapplicationalinataranovskaya.R;
import com.aleksandrp.testapplicationalinataranovskaya.db.models.MoveModelDb;
import com.aleksandrp.testapplicationalinataranovskaya.presenter.DetailsPresenter;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class RealmObj {

    // version DB
    public static final long VERSION_DB = 1;

    private static RealmObj sRealmObj;


    public synchronized static RealmObj getInstance() {
        if (sRealmObj == null) {
            sRealmObj = new RealmObj();
        }
        return sRealmObj;
    }


    private RealmObj() {

    }

    //    ===============================================================

    public void updateMove(final MoveModelDb mModelDb, final DetailsPresenter mPresenter, Realm mRealm) {
        mRealm.executeTransactionAsync(
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

    public RealmResults<MoveModelDb> getMoveModelDb(Realm mRealm) {
        return mRealm
                .where(MoveModelDb.class)
                .equalTo("save", true)
                .findAll();
    }
}
