package com.example.voiceassistent;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GISIdCitySearchService {
    public static GISCityId getGISCityId() {
        String BASE_URL = "https://api.gismeteo.net";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create().create())
                .build();

        return retrofit.create(GISCityId.class);
    }
}
