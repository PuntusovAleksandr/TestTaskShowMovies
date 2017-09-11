package com.aleksandrp.testapplicationalinataranovskaya.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AleksandrP on 11.09.2017.
 */

public class LanguagesModel {

    @SerializedName("iso_639_1")
    @Expose
    public String iso_639_1;
    @SerializedName("name")
    @Expose
    public String name;

}
