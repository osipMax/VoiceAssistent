package com.example.voiceassistent;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Message implements Parcelable {
    public String text;
    public Date date;
    public Boolean isSend;
    public Message(String text, Boolean isSend) {
        this.text = text;
        this.isSend = isSend;
        this.date = new Date();
    }

    protected Message(Parcel in) {
        text = in.readString();
        byte tmpIsSend = in.readByte();
        isSend = tmpIsSend == 0 ? null : tmpIsSend == 1;
    }

    protected Message(MessageEntity entity) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        this.text=entity.text;
        this.date = new SimpleDateFormat("hh:mm").parse(entity.date);
        if(entity.isSend == 1)
            this.isSend=true;
        else this.isSend=false;
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeByte((byte) (isSend == null ? 0 : isSend ? 1 : 2));
    }
}