package com.aleksandrp.testapplicationalinataranovskaya.db.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class MoveModelDb extends RealmObject {

    @PrimaryKey
    private long id;

    private String poster_path;
    private String title;
    private String overview;
    private boolean save;


    public MoveModelDb() {
    }

    public long getId() {
        return id;
    }

    public void setId(long mId) {
        id = mId;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String mPoster_path) {
        poster_path = mPoster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String mTitle) {
        title = mTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String mOverview) {
        overview = mOverview;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean mSave) {
        save = mSave;
    }
}
