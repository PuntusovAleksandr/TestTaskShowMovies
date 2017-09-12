package com.aleksandrp.testapplicationalinataranovskaya.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class FullInfoMoveModel {

    @SerializedName("adult")
    @Expose
    public boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    public String backdrop_path;
    @SerializedName("budget")
    @Expose
    public String budget;
    @SerializedName("genres")
    @Expose
    public List<GenresModel> genres;
    @SerializedName("homepage")
    @Expose
    public String homepage;
    @SerializedName("id")
    @Expose
    public long id;
    @SerializedName("imdb_id")
    @Expose
    public String imdb_id;
    @SerializedName("original_language")
    @Expose
    public String original_language;
    @SerializedName("original_title")
    @Expose
    public String original_title;
    @SerializedName("overview")
    @Expose
    public String overview;
    @SerializedName("popularity")
    @Expose
    public String popularity;
    @SerializedName("poster_path")
    @Expose
    public String poster_path;
    @SerializedName("release_date")
    @Expose
    public String release_date;
    @SerializedName("revenue")
    @Expose
    public String revenue;
    @SerializedName("runtime")
    @Expose
    public String runtime;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("tagline")
    @Expose
    public String tagline;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("video")
    @Expose
    public boolean video;
    @SerializedName("vote_average")
    @Expose
    public String vote_average;
    @SerializedName("vote_count")
    @Expose
    public String vote_count;

    @SerializedName("production_companies")
    @Expose
    public List<CompanyModel> production_companies;
    @SerializedName("production_countries")
    @Expose
    public List<CountryModel> production_countries;
    @SerializedName("spoken_languages")
    @Expose
    public List<LanguagesModel> spoken_languages;


}
