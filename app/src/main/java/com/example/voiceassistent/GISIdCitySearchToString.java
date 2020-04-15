package com.example.voiceassistent;

import android.util.Log;

import androidx.core.util.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GISIdCitySearchToString {
    public static void getIdByCity(String city, final Consumer<Integer> callback) {
        GISCityId api = GISIdCitySearchService.getGISCityId();
        Call<GISIdCitySearch> call = api.getGISCityId(city);
        call.enqueue(new Callback<GISIdCitySearch>() {

            @Override
            public void onResponse(Call<GISIdCitySearch> call, Response<GISIdCitySearch> response) {
                GISIdCitySearch result = response.body();
//                Integer fail = 1;
                if (result != null) {
                    callback.accept(result.id);
                }
                else callback.accept(4292); // возвращаю id Кирова напрямую
            }

            @Override
            public void onFailure(Call<GISIdCitySearch> call, Throwable t) {
                Log.w("WEATHER", t.getMessage());
            }
        });
    }
}

