package com.example.voiceassistent;

import android.net.http.HttpResponseCache;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class Forecast implements Serializable { // в этом классе указываем поля, которые собираемся использовать из возвращаемых данных

    // поля @SerializedName должны иметь такие же названия, которые указаны в JSON-файле, который возвращает нам сайт, иначе он их не найдет
    @SerializedName("fact")
    @Expose
    public YanWeather fact;

    public class YanWeather {
        @SerializedName("temp")
        @Expose
        public Integer temp;

        @SerializedName("condition")
        @Expose
        public String cond;

        @SerializedName("feels_like")
        @Expose
        public Integer feels_like;
    }

// GISMETEO API (НЕ РАЗДАЮТ)
//        @SerializedName("temperature")
//        @Expose
//        public GISTemp temp;
//
//        public class GISTemp {
//            @SerializedName("air")
//            @Expose
//            public Air air;
//
//            public class Air {
//                @SerializedName("C")
//                @Expose
//                public Float c;
//            }
//
//            @SerializedName("comfort")
//            @Expose
//            public Comfort comfort;
//
//            public class Comfort {
//                @SerializedName("C")
//                @Expose
//                public Float c;
//            }
//
//            @SerializedName("water")
//            @Expose
//            public Water water;
//
//            public class Water {
//                @SerializedName("C")
//                @Expose
//                public Float c;
//            }
//        }

        @SerializedName("description")
        @Expose
        public GISCond cond;

        public class GISCond {
            public Cond cond;

            public class Cond {
                @SerializedName("full")
                @Expose
                public String full;
            }
        }

        @SerializedName("pressure")
        @Expose
        public GISPressure press;

        public class GISPressure {
            public Press press;

            public class Press {
                @SerializedName("mm_hg_atm")
                @Expose
                public Integer value;
            }
        }

}

interface ForecastApi {
    @GET("/current?access_key=dcd410e3-5c3e-4b0d-a22a-ee08987b233b")
    Call<Forecast> getCurrentWeather(@Query("query") String city);
}



interface YanWeatherApi {
    @Headers("X-Yandex-API-Key:dcd410e3-5c3e-4b0d-a22a-ee08987b233b") // указываем ключ, выданный сайтом
    @GET("/v1/forecast?extra=true") // указываем параметры запроса (например: широта и долгота, кол-во дней в прогнозе, расширенная инф-я об осадках...)
    Call<Forecast> getYanWeather(@Query("query") String city); // вызываем метод с указанием класса, который мы сформировали для получения данных (Forecast)
    // @Query("") аннотация, с которой параметр будет обращаться на сервер в составе запроса, в данном случае название города
}

interface GISWeatherApi {
    @Headers("X-Gismeteo-Token:56b30cb255.3443075")
    @GET("/v2/weather/current/4292/") // попробовал напрямую вбить id Кирова, все равно не выдает
    Call<Forecast> getGISWeather();

//    нормальный запрос
//    @Headers("X-Gismeteo-Token:56b30cb255.3443075")
//    @GET("/v2/weather/current/{id}")
//    Call<Forecast> getGISWeather(@Path("id") Integer id);
}
