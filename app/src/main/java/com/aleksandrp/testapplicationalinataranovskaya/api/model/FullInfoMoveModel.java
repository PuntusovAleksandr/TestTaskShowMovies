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
    @SerializedName("belongs_to_collection")
    @Expose
    public List belongs_to_collection;
    @SerializedName("budget")
    @Expose
    public long budget;
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
    public long imdb_id;
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
    public long revenue;
    @SerializedName("runtime")
    @Expose
    public long runtime;
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
