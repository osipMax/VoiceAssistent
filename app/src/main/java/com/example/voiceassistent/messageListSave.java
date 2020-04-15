package com.example.voiceassistent;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class messageListSave implements Parcelable {
    private List<Message> list;

    public messageListSave(List<Message> list) {
        this.list = new ArrayList<>();
        for (Message m:list)
            this.list.add(m);
    }

    public List<Message> getMessageList() {
        return list;
    }

    protected messageListSave(Parcel in) {
        list = in.createTypedArrayList(Message.CREATOR);
//        text = in.readString();
//        byte tmpIsSend = in.readByte();
//        isSend = tmpIsSend == 0 ? null : tmpIsSend == 1;
    }

    public static final Creator<messageListSave> CREATOR = new Creator<messageListSave>() {
        @Override
        public messageListSave createFromParcel(Parcel in) {
            return new messageListSave(in);
        }

        @Override
        public messageListSave[] newArray(int size) {
            return new messageListSave[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
    }
}
