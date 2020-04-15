package com.example.voiceassistent;

import android.icu.text.Edits;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ParsingHtmlService {
    private static final String URL = "http://mirkosmosa.ru/holiday/2020";

    public String getHoliday(String date) throws IOException {
        if(date.equals("")) {
            return "Неверный запрос.";
        }
        Document doc = Jsoup.connect(URL).get();
        Element body = doc.body();
        Elements elements = body.select("div.next_phase");
        ListIterator<Element> it = elements.listIterator();

        while(it.hasNext()) {

            Element el1 = (Element) it.next().childNode(0);
            Element el2 = (Element) it.previous().childNode(1);
            if(el1.text().contains(date)) {
//                new Callback<String>() {
//                    @Override
//                    public void onResponse(Call<String> call, Response<String> response) {
//                        callback.accept(el2.text());
//                    }
//
//                    @Override
//                    public void onFailure(Call<String> call, Throwable t) {
//                        Log.w("HOLIDAYS", t.getMessage());
//                    }
//                };
//                System.out.println(el2.text());
                return el2.text();
            }
            it.next();
        }
        return "";
    }
}
