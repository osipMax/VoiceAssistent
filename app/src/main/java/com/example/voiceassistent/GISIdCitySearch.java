package com.example.voiceassistent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class GISIdCitySearch implements Serializable {
    @SerializedName("id")
    @Expose
    public Integer id;
}

interface GISCityId {
    @Headers("X-Gismeteo-Token:56b30cb255.3443075")
    @GET("/v2/search/cities/?")
    Call<GISIdCitySearch> getGISCityId(@Query("query") String city);
}
