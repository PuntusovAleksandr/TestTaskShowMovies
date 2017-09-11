package com.aleksandrp.testapplicationalinataranovskaya.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AleksandrP on 12.09.2017.
 */

public class ListGenresModel {

    @SerializedName("genres")
    @Expose
    public List<GenresModel> genres;
}
