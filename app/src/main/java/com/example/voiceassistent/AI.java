package com.example.voiceassistent;

import android.os.AsyncTask;

import androidx.core.util.Consumer;

import java.io.IOException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AI {
    Map<String, String> m;
    Map<String, String> is;
    Date d = new Date();
    SimpleDateFormat sdf;



    public String getDate(String text) throws ParseException {
        String[] textLessPoints = text.split(" ");
        String[] m = text.split(" ");
        SimpleDateFormat sdf;
        String res = "";
        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + (24*60*60*1000));
        Date yesterday = new Date(today.getTime() - (24*60*60*1000));

        is=new HashMap<>();
        is.put("1", "января");
        is.put("01", "января");
        is.put("2", "февраля");
        is.put("02", "февраля");
        is.put("3", "марта");
        is.put("03", "марта");
        is.put("4", "апреля");
        is.put("04", "апреля");
        is.put("5", "мая");
        is.put("05", "мая");
        is.put("6", "июня");
        is.put("06", "июня");
        is.put("7", "июля");
        is.put("07", "июля");
        is.put("8", "августа");
        is.put("08", "августа");
        is.put("9", "сентября");
        is.put("09", "сентября");
        is.put("10", "октября");
        is.put("11", "ноября");
        is.put("12", "декабря");
        Integer index = 0;
        if(text.contains("праздник")) {
            String[] textLessIntervals = text.split(" ");
            for (int i = 0; i!= textLessIntervals.length-1; i++) {
                if(textLessIntervals[i].equals("праздник")) {
                    index = i;
                    break;
                }
            }

//            if(textLessIntervals[index+1].matches("[-+]?\\d+") && is.get()[index+2]) {
//
//            }
            if(textLessIntervals[index+1].contains(".")) {
                text = text.replace("праздник ", "");
                textLessPoints = text.split("\\.");

                res += textLessPoints[0] + " " + is.get(textLessPoints[1]) + " " + textLessPoints[2];
                return res;
            }
            else {
                sdf = new SimpleDateFormat("dd");


                if(textLessPoints[0].equals("праздник")) {
                    if (textLessPoints[1].equals("сегодня")) {
                        res += sdf.format(today);
                        sdf = new SimpleDateFormat("M");
                        res += " " + is.get(sdf.format(today));
                        sdf = new SimpleDateFormat("yyyy");
                        res += " " + sdf.format(today);
                        return res;
                    }
                    if (m[1].equals("завтра")) {
                        res += sdf.format(tomorrow);
                        sdf = new SimpleDateFormat("M");
                        res += " " + is.get(sdf.format(tomorrow));
                        sdf = new SimpleDateFormat("yyyy");
                        res += " " + sdf.format(tomorrow);
                        return res;
                    }
                    if (m[1].equals("вчера")) {
                        res += sdf.format(yesterday);
                        sdf = new SimpleDateFormat("M");
                        res += " " + is.get(sdf.format(yesterday));
                        sdf = new SimpleDateFormat("yyyy");
                        res += " " + sdf.format(yesterday);
                        return res;
                    }
                    if(textLessPoints.length >= 4) {
                        res += textLessPoints[1] + " " + textLessPoints[2] + " " + textLessPoints[3];
                    }
                    return res;
                }
            }
        }
//        String[] textLessPoints = text.split("\\.");
//        String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
//        String[] m = text.split(" ");



//
//        Locale loc = new Locale("ru", "RU");
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
//        Date d = sdf.parse(text);

//        return text;

//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String month = "january";
//        try {
//            month = br.readLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String out = month + " is the ";
//
//        SimpleDateFormat sdt = new SimpleDateFormat("MMMM", Locale.ENGLISH);
//        Date date = sdt.parse(month, new ParsePosition(0));
//        Calendar cal = new GregorianCalendar();
//        cal.setTime(date);
//        System.out.println(cal.get(Calendar.MONTH));
        return "";
    }

    public int daysCalc()
    {
        Calendar c=Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR,2020);
        c.set(Calendar.MONTH,3);
        c.set(Calendar.DATE,8);
        Date d1 = c.getTime();
        sdf=new SimpleDateFormat("D");
        int diff = 365 - (Integer.parseInt(sdf.format(d)) - Integer.parseInt(sdf.format(d1)));
        return diff;
    }

    public String getDegreeEnding(int b) {
        int a = Math.abs(b);
        if(a>=11 && a <=14)
            return " градусов ";
        if(a%10==0 || a%10 >=5 && a%10 <=9)
            return " градусов ";
        if(a%10 == 1)
            return " градус ";
        if(a >= 2 && a <=4)
            return " градуса ";
        return "";
    }

//    for yandexApi translate
    public String getCondTranslate(String cond) {
        if(cond.equals("overcast"))
            return "пасмурно";
        return cond;
    }

    public AI()
    {
        m=new HashMap<>();
        m.put("как дела?", "ну нормально и нормально");
        m.put("привет", "ну привет и привет");
        m.put("чем занимаешься?", "ну занимаюсь и занимаюсь");
        sdf = new SimpleDateFormat("dd.MM.yyyy");
        m.put("какой сегодня день?", sdf.format(d));
        sdf = new SimpleDateFormat("hh.mm");
        m.put("который час?", sdf.format(d));
        sdf = new SimpleDateFormat("EEEE");
        m.put("какой день недели?", sdf.format(d));
        m.put("сколько дней до дня рождения моей девушки?", Integer.toString(daysCalc())+", чё ныть то");
    }

    public void getAnswer(String text, final Consumer<String> callback) throws ParseException, IOException {
            if(text.contains("праздник")) {
                String date = getDate(text);
                new AsyncTask<String, Integer, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        for (String h : strings) {
                            try {
                                ParsingHtmlService phs = new ParsingHtmlService();
                                return phs.getHoliday(h);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        return "";
                    }

                    protected void onPostExecute(String res) {
                        super.onPostExecute(res);
                        callback.accept(res);
                    }
                }.execute(date.split(","));
            }
        Pattern cityPattern = Pattern.compile("погода в городе (\\p{L}+)", Pattern.CASE_INSENSITIVE); // задаем текст запроса
        Matcher matcher = cityPattern.matcher(text); // проверяем, удовлетворяет ли строка заданному регулярному выражению

//        if(matcher.find()) {
//            final String cityName = matcher.group(1);
//            GISIdCitySearchToString.getIdByCity(cityName, new Consumer<Integer>() { // ПОЛУЧАЕМ ID ГОРОДА ПО НАЗВАНИЮ
//                @Override
//                public void accept(Integer cityId) {
//
//                    if(cityId != null)
//                        ForecastToString.getForecast(cityId, new Consumer<String>() { // ПОЛУЧАЕМ ИНФУ О ПОГОДЕ ПО ID ГОРОДА
//                            @Override
//                            public void accept(String s) {
//                                if(s!=null)
//                                    callback.accept(s);
//                                else
//                                {
//                                    String answer = "Не знаю я, какая у вас там погода, " + cityName + " = " + cityId.toString() + ", че ныть то";
//                                    callback.accept(answer);
//                                }
//                            }
//                        });
//                    else accept(null);
//                }
//            });
//        }

        if(matcher.find()) {
            final String cityName = matcher.group(1);
            ForecastToString.getForecast(text, new Consumer<String>() {
                @Override
                public void accept(String s) { // определяем, что будет возвращать данный метод
                    if(s!=null)
                        callback.accept(s);
                    else
                    {
                        String answer = "Не знаю я, какая у вас там погода, " + cityName + ", че ныть то";
                        callback.accept(answer);
                    }
                }
            });
        }
        for(Map.Entry<String, String> item : m.entrySet()){
            if(text.contains(item.getKey()))
                callback.accept(item.getValue());
        }
    }
}