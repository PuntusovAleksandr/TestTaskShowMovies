package com.aleksandrp.testapplicationalinataranovskaya.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class CompanyModel {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("id")
    @Expose
    public long id;

}
