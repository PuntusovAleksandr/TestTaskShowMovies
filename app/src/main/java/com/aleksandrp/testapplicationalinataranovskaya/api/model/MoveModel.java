package com.aleksandrp.testapplicationalinataranovskaya.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class MoveModel {

    @SerializedName("vote_count")
    @Expose
    public int vote_count;
    @SerializedName("id")
    @Expose
    public long id;
    @SerializedName("video")
    @Expose
    public boolean video;
    @SerializedName("vote_average")
    @Expose
    public int vote_average;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("popularity")
    @Expose
    public String popularity;
    @SerializedName("poster_path")
    @Expose
    public String poster_path;
    @SerializedName("original_language")
    @Expose
    public String original_language;
    @SerializedName("original_title")
    @Expose
    public String original_title;
    @SerializedName("backdrop_path")
    @Expose
    public String backdrop_path;
    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("release_date")
    @Expose
    public String release_date;
    @SerializedName("genre_ids")
    @Expose
    public List<Integer> genre_ids;

}
