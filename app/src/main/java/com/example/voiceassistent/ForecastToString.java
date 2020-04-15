package com.example.voiceassistent;

import android.util.Log;

import androidx.core.util.Consumer;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//public class ForecastToString {
//    public static void getForecast(Integer cityId, final Consumer<String> callback) {
//        AI ai = new AI();
//        GISWeatherApi api = ForecastService.getGISApi();
//        Call<Forecast> call = api.getGISWeather();
//        call.enqueue(new Callback<Forecast>() {
//            @Override
//            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
//                Forecast result = response.body();
//                if(result!=null) {
//
////                    String answer = "Сейчас где-то " + result.current.temperature.toString() + ai.getDegreeEnding(result.current.temperature) + " и " + result.current.weather_descriptions.get(0);
//
//                    String answer = "Сейчас где-то " + result.fact.temp.toString() + ai.getDegreeEnding(result.fact.temp) + ", чувствуется как " + result.fact.feels_like.toString() + ", " + ai.getCondTranslate(result.fact.cond);
//
////                    String answer = "Сейчас где-то " + result.temp.air.c.toString() + ai.getDegreeEnding(result.temp.air.c.intValue()) + ", чувствуется как " + result.temp.comfort.c.toString() + ", " + result.press.press.value.toString();
//                    callback.accept(answer);
//                }
//                else callback.accept("Не могу узнать погоду, чё ныть то");
//
//            }
//
//            @Override
//            public void onFailure(Call<Forecast> call, Throwable t) {
//                Log.w("WEATHER", t.getMessage());
//            }
//        });
//    }
//}



public class ForecastToString {
    public static void getForecast(String city, final Consumer<String> callback) {

        AI ai = new AI();
        YanWeatherApi api = ForecastService.getYanApi();
        Call<Forecast> call = api.getYanWeather(city);
        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                Forecast result = response.body();
                if(result!=null) {

//                    String answer = "Сейчас где-то " + result.current.temperature.toString() + ai.getDegreeEnding(result.current.temperature) + " и " + result.current.weather_descriptions.get(0);

                    String answer = "Сейчас где-то " + result.fact.temp.toString() + ai.getDegreeEnding(result.fact.temp) + ", чувствуется как " + result.fact.feels_like.toString() + ", " + ai.getCondTranslate(result.fact.cond);

//                    String answer = "Сейчас где-то " + result.temp.air.c.toString() + ai.getDegreeEnding(result.temp.air.c.intValue()) + ", чувствуется как " + result.temp.comfort.c.toString() + ", " + result.press.press.value.toString();
                    callback.accept(answer);
                }
                else callback.accept("Не могу узнать погоду, чё ныть то");

            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.w("WEATHER", t.getMessage());
            }
        });
    }
}


//        ForecastApi api = ForecastService.getApi();
//        Call<Forecast> call = api.getCurrentWeather(city);

//        YanWeatherApi api = ForecastService.getYanApi();
//        Call<Forecast> call = api.getYanWeather(city);
