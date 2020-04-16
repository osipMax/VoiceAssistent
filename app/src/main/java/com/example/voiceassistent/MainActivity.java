package com.example.voiceassistent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    protected Button sendButton;
    protected EditText questionText;
    protected RecyclerView chatMessageList;
    protected LinearLayoutManager llm;
    protected MessageListAdapter messageListAdapter;

    protected SharedPreferences sPref;
    public static final String APP_PREFERENCES="mysettings";

    protected TextToSpeech textToSpeech;
    private static final String URL = "http://mirkosmosa.ru/holiday/2020";
    private boolean isLight = true;
    private String THEME="THEME";

    DBHelper dbHelper;
    SQLiteDatabase database;
    Cursor cursor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.day_settings:
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                isLight=true;
                break;
            case R.id.night_settings:
                isLight=false;
                getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        // сохранение настроек темы
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean(THEME, isLight);
        editor.apply();
        // сохранение данных в БД
        database.delete(dbHelper.TABLE_MESSAGES, null, null);
        for (Message msg:messageListAdapter.messageList) {
            MessageEntity entity = new MessageEntity(msg);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.FIELD_MESSAGE, entity.text);
            contentValues.put(DBHelper.FIELD_DATE, entity.date);
            contentValues.put(DBHelper.FIELD_SEND, entity.isSend);
            database.insert(dbHelper.TABLE_MESSAGES, null, contentValues);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        database.close();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // изымаем значение сохраненной в предыдущем сеансе темы
        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        isLight=sPref.getBoolean("THEME", true);
        if(!isLight)
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        super.onCreate(savedInstanceState);
        // работа с БД
        dbHelper = new DBHelper(this);
        database = dbHelper.getWritableDatabase();

        setContentView(R.layout.activity_mainn_new);

        // привязываем переменные к соответствующим id элементов на форме
        sendButton = findViewById(R.id.sendButton);
        questionText = findViewById(R.id.questionText);
        chatMessageList = findViewById(R.id.chatMessageList);

        chatMessageList.setHasFixedSize(true);

        messageListAdapter = new MessageListAdapter(this, chatMessageList);
        llm = new LinearLayoutManager(this);
        chatMessageList.setLayoutManager(llm);
        llm.setStackFromEnd(true);

        if(savedInstanceState != null) {
            messageListSave messageListSave = savedInstanceState.getParcelable("msg");
            messageListAdapter.messageList = messageListSave.getMessageList();
        }
        else {
            cursor = database.query(dbHelper.TABLE_MESSAGES, null, null, null, null, null, null);
            if(cursor.moveToFirst()) {
//                int messageIndex = cursor.getColumnIndex(dbHelper.FIELD_MESSAGE);
//                int dateIndex = cursor.getColumnIndex(dbHelper.FIELD_DATE);
//                int sendIndex = cursor.getColumnIndex(dbHelper.FIELD_SEND);
                do {
                    MessageEntity entity = new MessageEntity(
                            cursor.getString(cursor.getColumnIndex(dbHelper.FIELD_MESSAGE)),
                            cursor.getString(cursor.getColumnIndex(dbHelper.FIELD_DATE)),
                            cursor.getInt(cursor.getColumnIndex(dbHelper.FIELD_SEND)));
                    try {
                        Message message = new Message(entity);
                        messageListAdapter.messageList.add(message);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }while (cursor.moveToNext());
            }
            cursor.close();
        }
        chatMessageList.setAdapter(messageListAdapter);

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR)
                    textToSpeech.setLanguage(new Locale("ru"));
            }
        });
        sendButton.setOnClickListener(new View.OnClickListener() {

            protected void onSend() throws ParseException, IOException {
                AI a = new AI();
                ParsingHtmlService psh = new ParsingHtmlService();
                String text = questionText.getText().toString();
                messageListAdapter.messageList.add(new Message(text, true));
                messageListAdapter.notifyDataSetChanged();
                a.getAnswer(text, new Consumer<String>() {
                    @Override
                    public void accept(String s) { // определяем, как будет использоваться информация, полученная через callback
                        messageListAdapter.messageList.add(new Message(s, false));
                        messageListAdapter.notifyDataSetChanged();
                        chatMessageList.scrollToPosition(messageListAdapter.messageList.size() - 1);
                        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null);
                    }
                });
                questionText.setText("");
            }
            @Override
            public void onClick(View view) {
                try {
                    onSend();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.i("LOG", "onCreate");
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        messageListSave messageListSave = new messageListSave(messageListAdapter.messageList);
        outState.putParcelable("msg", messageListSave);
        super.onSaveInstanceState(outState);
    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        layoutManagerState=chatMessageList.getLayoutManager().onSaveInstanceState();
//    }

//    @Override
//    public void onRestoreInstanceState(@NonNull Bundle outState) {
//        if(outState != null) {
//            LinearLayoutManager outMsg = new LinearLayoutManager(this);
//
//        }
//    }
}
