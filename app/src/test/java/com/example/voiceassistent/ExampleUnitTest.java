package com.example.voiceassistent;

import androidx.core.util.Consumer;

import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws IOException, ParseException {
//        AI a = new AI();
//        ParsingHtmlService phs = new ParsingHtmlService();
//        phs.getHoliday(a.getDate("праздник завтра"));

        Message msg = new Message("hi", true);
        MessageEntity entity = new MessageEntity(msg);

        Date d = new Date();
        String s = d.toString();
//        System.out.print(s);
//        AI a = new AI();
//
//        a.getAnswer("погода в городе Киров", new Consumer<String>() {
//            @Override
//            public void accept(String s) {
//                System.out.println(s);
//            }
//        });
    }
}