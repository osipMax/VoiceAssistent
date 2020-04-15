package com.example.voiceassistent;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ForecastService {

    public static GISWeatherApi getGISApi() {
        String BASE_URL = "https://api.gismeteo.net";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create().create())
                .build();

        return retrofit.create(GISWeatherApi.class);
    }

    public static YanWeatherApi getYanApi() {
        String BASE_URL = "https://api.weather.yandex.ru";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create().create())
                .build();

        return retrofit.create(YanWeatherApi.class);
    }


    public static ForecastApi getApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.weatherstack.com") // базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create().create()) // конвертер для преобразования JSON в объекты
                .build();

        return retrofit.create(ForecastApi.class); // создание объекта, при помощи которого будут выполняться запросы
    }
}
