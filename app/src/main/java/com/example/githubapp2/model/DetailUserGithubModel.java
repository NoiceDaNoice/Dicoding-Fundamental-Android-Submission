package com.example.githubapp2.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailUserGithubModel implements Parcelable {

    private final String username;
    private final String name;
    private final String follower;
    private final String following;
    private final int img;
    private final String location;
    private final String company;

    protected DetailUserGithubModel(Parcel in) {
        username = in.readString();
        name = in.readString();
        follower = in.readString();
        following = in.readString();
        img = in.readInt();
        location = in.readString();
        company = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(follower);
        dest.writeString(following);
        dest.writeInt(img);
        dest.writeString(location);
        dest.writeString(company);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DetailUserGithubModel> CREATOR = new Creator<DetailUserGithubModel>() {
        @Override
        public DetailUserGithubModel createFromParcel(Parcel in) {
            return new DetailUserGithubModel(in);
        }

        @Override
        public DetailUserGithubModel[] newArray(int size) {
            return new DetailUserGithubModel[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getFollower() {
        return follower;
    }

    public String getFollowing() {
        return following;
    }

    public int getImg() {
        return img;
    }

    public String getLocation() {
        return location;
    }

    public String getCompany() {
        return company;
    }
}
