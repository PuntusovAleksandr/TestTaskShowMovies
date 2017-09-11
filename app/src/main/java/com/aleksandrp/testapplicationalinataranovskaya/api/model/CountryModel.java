package com.aleksandrp.testapplicationalinataranovskaya.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class CountryModel {

    @SerializedName("release_date")
    @Expose
    public String release_date;
    @SerializedName("revenue")
    @Expose
    public long revenue;
    @SerializedName("runtime")
    @Expose
    public long runtime;

}
