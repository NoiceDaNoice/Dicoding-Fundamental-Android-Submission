package com.example.consumerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FollowingModel implements Parcelable{

    public final String login;
    public final String avatar_url;

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    protected FollowingModel(Parcel in) {
        login = in.readString();
        avatar_url = in.readString();
    }

    public static final Creator<UserGithubModel> CREATOR = new Creator<UserGithubModel>() {
        @Override
        public UserGithubModel createFromParcel(Parcel in) {
            return new UserGithubModel(in);
        }

        @Override
        public UserGithubModel[] newArray(int size) {
            return new UserGithubModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(avatar_url);
    }
}
