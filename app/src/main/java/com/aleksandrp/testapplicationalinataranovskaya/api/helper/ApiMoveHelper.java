package com.aleksandrp.testapplicationalinataranovskaya.api.helper;

import com.aleksandrp.testapplicationalinataranovskaya.api.RestAdapter;
import com.aleksandrp.testapplicationalinataranovskaya.api.interfaces.ServiceTask;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.FullInfoMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListGenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.aleksandrp.testapplicationalinataranovskaya.api.RestAdapter.API_KEY;

/**
 * Created by AleksandrP on 11.09.2017.
 */

@Singleton
public class ApiMoveHelper {

    protected RestAdapter restAdapter;

    @Inject
    public ApiMoveHelper() {
        restAdapter = new RestAdapter();
    }

    public Observable<Response<ListMoveModel>> getListPopular(int mPage, String mGenres) {
        restAdapter.init(false, "getListPopular");
        ServiceTask serviceUser =
                restAdapter.getRetrofit().create(ServiceTask.class);
        Observable<Response<ListMoveModel>> allSources =
                serviceUser.getListPopular(API_KEY, mPage, "popularity.des", mGenres);
        return allSources.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<Response<ListGenresModel>> getListGenres() {
        restAdapter.init(false, "getListGenres");
        ServiceTask serviceUser =
                restAdapter.getRetrofit().create(ServiceTask.class);
        Observable<Response<ListGenresModel>> allSources =
                serviceUser.getListOficial(API_KEY);
        return allSources.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }


    public Observable<Response<ListMoveModel>> searchMovies(String mSearch) {
        restAdapter.init(false, "searchMovies");
        ServiceTask serviceUser =
                restAdapter.getRetrofit().create(ServiceTask.class);
        Observable<Response<ListMoveModel>> allSources =
                serviceUser.searchMove(API_KEY, mSearch);
        return allSources.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<Response<FullInfoMoveModel>> getDetailsMove(long mId) {
        restAdapter.init(false, "getDetailsMove");
        ServiceTask serviceUser =
                restAdapter.getRetrofit().create(ServiceTask.class);
        Observable<Response<FullInfoMoveModel>> allSources =
                serviceUser.getPrimaryInfo(mId, API_KEY);
        return allSources.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

    }

}
