package com.aleksandrp.testapplicationalinataranovskaya.api.interfaces;

import com.aleksandrp.testapplicationalinataranovskaya.api.model.FullInfoMoveModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.GenresModel;
import com.aleksandrp.testapplicationalinataranovskaya.api.model.ListMoveModel;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public interface ServiceTask {

    @GET("discover/movie")
    Observable<Response<ListMoveModel>> getListPopular(
            @Query("api_key") String api_key,
            @Query("page") int page,
            @Query("sort_by") String sort_by,
            @Query("with_genres") String with_genres);

    @GET("genre/movie/list")
    Observable<Response<List<GenresModel>>> getListOficial(
            @Query("api_key") String api_key);

    @GET("search/movie")
    Observable<Response<ListMoveModel>> searchMove(
            @Query("api_key") String api_key,
            @Query("query") String query);

    @GET("movie/{id}")
    Observable<Response<FullInfoMoveModel>> getPrimaryInfo(
            @Path("id") long id,
            @Query("api_key") String api_key);

}
