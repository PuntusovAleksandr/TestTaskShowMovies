package com.aleksandrp.testapplicationalinataranovskaya.fragment;

import android.annotation.SuppressLint;

import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.MoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.db.RealmObj;
import com.aleksandrp.testapplicationalinataranovskaya.db.models.MoveModelDb;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class FavoriteListMovesFragment extends BaseFragment {

    private Realm mRealm;

    public FavoriteListMovesFragment() {
    }

    @SuppressLint("ValidFragment")
    public FavoriteListMovesFragment(Realm mRealm) {
        this.mRealm = mRealm;
    }

    @Override
    public void onStart() {
        super.onStart();

        RealmResults<MoveModelDb> moveModelDb = RealmObj.getInstance().getMoveModelDb(mRealm);
        ListMoveModel mListMoveModel = new ListMoveModel();
        mListMoveModel.results = new ArrayList<>();
        for (MoveModelDb db : moveModelDb) {
            MoveModel model = new MoveModel();
            model.id = db.getId();
            model.title = db.getTitle();
            model.overview = db.getOverview();
            model.poster_path = db.getPoster_path();
            mListMoveModel.results.add(model);
        }
        adapter.addAll(mListMoveModel);
    }
}
