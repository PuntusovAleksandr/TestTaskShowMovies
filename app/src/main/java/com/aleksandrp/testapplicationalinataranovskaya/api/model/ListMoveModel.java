package com.aleksandrp.testapplicationalinataranovskaya.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class ListMoveModel {

    @SerializedName("page")
    @Expose
    public int page;
    @SerializedName("total_results")
    @Expose
    public long total_results;
    @SerializedName("total_pages")
    @Expose
    public int total_pages;
    @SerializedName("results")
    @Expose
    public List<MoveModel> results;

}
