package com.example.voiceassistent;

import java.text.SimpleDateFormat;

public class MessageEntity {
    public String text;
    public String date;
    public int isSend;

    public MessageEntity(String text, String date, int isSend) {
        this.text=text;
        this.date=date;
        this.isSend=isSend;
    }

    public MessageEntity(Message msg) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        this.text=msg.text;
        this.date=sdf.format(msg.date);
        if(msg.isSend)
            this.isSend=1;
        else this.isSend=0;
    }
}
