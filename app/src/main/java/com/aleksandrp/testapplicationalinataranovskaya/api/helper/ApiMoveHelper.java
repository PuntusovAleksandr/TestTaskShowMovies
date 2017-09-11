package com.aleksandrp.testapplicationalinataranovskaya.api.helper;

import com.aleksandrp.testapplicationalinataranovskaya.api.RestAdapter;
import com.aleksandrp.testapplicationalinataranovskaya.api.bus.BusProvider;
import com.aleksandrp.testapplicationalinataranovskaya.api.constants.ApiConstants;
import com.aleksandrp.testapplicationalinataranovskaya.api.event.NetworkResponseEvent;
import com.aleksandrp.testapplicationalinataranovskaya.api.interfaces.ServiceTask;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListGenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;

import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.aleksandrp.testapplicationalinataranovskaya.api.RestAdapter.API_KEY;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class ApiMoveHelper {

    protected RestAdapter restAdapter;

    public ApiMoveHelper() {
        restAdapter = new RestAdapter();
    }

    public void getListPopular(int mPage, String mGenres) {
        restAdapter.init(true, "getListPopular");
        ServiceTask serviceUser =
                restAdapter.getRetrofit().create(ServiceTask.class);
        Observable<Response<ListMoveModel>> allSources =
                serviceUser.getListPopular(API_KEY, mPage, "popularity.des", mGenres);
        allSources.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ListMoveModel>>() {
                    private NetworkResponseEvent event;
                    private NetworkResponseEvent<String> eventError;
                    private ListMoveModel body;

                    @Override
                    public void onCompleted() {
                        if (event != null) {
                            event.setSucess(true);
                            event.setData(body);
                        } else {
                            event = new NetworkResponseEvent<>();
                            event.setId(ApiConstants.ERROR);
                            event.setSucess(false);
                        }
                        BusProvider.send(event);
                    }

                    @Override
                    public void onError(Throwable e) {
                        eventError = new NetworkResponseEvent<>();
                        eventError.setId(ApiConstants.ERROR);
                        eventError.setData("Error load Sources ::: " + e.getMessage());
                        eventError.setSucess(false);
                        BusProvider.send(eventError);
                    }

                    @Override
                    public void onNext(Response<ListMoveModel> mResponse) {
                        if (mResponse.isSuccessful()) {
                            event = new NetworkResponseEvent<>();
                            event.setId(ApiConstants.LIST_POPULAR);
                            body = mResponse.body();
                        }
                    }
                });
    }


    public void getListGenres() {
        restAdapter.init(true, "getListPopular");
        ServiceTask serviceUser =
                restAdapter.getRetrofit().create(ServiceTask.class);
        Observable<Response<ListGenresModel>> allSources =
                serviceUser.getListOficial(API_KEY);
        allSources.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ListGenresModel>>() {
                    private NetworkResponseEvent event;
                    private NetworkResponseEvent<String> eventError;
                    private ListGenresModel body;

                    @Override
                    public void onCompleted() {
                        if (event != null) {
                            event.setSucess(true);
                            event.setData(body);
                        } else {
                            event = new NetworkResponseEvent<>();
                            event.setId(ApiConstants.ERROR);
                            event.setSucess(false);
                        }
                        BusProvider.send(event);
                    }

                    @Override
                    public void onError(Throwable e) {
                        eventError = new NetworkResponseEvent<>();
                        eventError.setId(ApiConstants.ERROR);
                        eventError.setData("Error load Sources ::: " + e.getMessage());
                        eventError.setSucess(false);
                        BusProvider.send(eventError);
                    }

                    @Override
                    public void onNext(Response<ListGenresModel> mResponse) {
                        if (mResponse.isSuccessful()) {
                            event = new NetworkResponseEvent<>();
                            event.setId(ApiConstants.LIST_OFFICIAL);
                            body = mResponse.body();
                        }
                    }
                });
    }


    public void searchMovies(String mSearch) {
        restAdapter.init(true, "getListPopular");
        ServiceTask serviceUser =
                restAdapter.getRetrofit().create(ServiceTask.class);
        Observable<Response<ListMoveModel>> allSources =
                serviceUser.searchMove(API_KEY, mSearch);
        allSources.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<ListMoveModel>>() {
                    private NetworkResponseEvent event;
                    private NetworkResponseEvent<String> eventError;
                    private ListMoveModel body;

                    @Override
                    public void onCompleted() {
                        if (event != null) {
                            event.setSucess(true);
                            event.setData(body);
                        } else {
                            event = new NetworkResponseEvent<>();
                            event.setId(ApiConstants.ERROR);
                            event.setSucess(false);
                        }
                        BusProvider.send(event);
                    }

                    @Override
                    public void onError(Throwable e) {
                        eventError = new NetworkResponseEvent<>();
                        eventError.setId(ApiConstants.ERROR);
                        eventError.setData("Error load Sources ::: " + e.getMessage());
                        eventError.setSucess(false);
                        BusProvider.send(eventError);
                    }

                    @Override
                    public void onNext(Response<ListMoveModel> mResponse) {
                        if (mResponse.isSuccessful()) {
                            event = new NetworkResponseEvent<>();
                            event.setId(ApiConstants.LIST_POPULAR);
                            body = mResponse.body();
                        }
                    }
                });
    }

}
