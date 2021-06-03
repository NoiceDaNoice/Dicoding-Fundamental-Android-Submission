package com.example.consumerapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailUserGithubModel implements Parcelable {

    private String username;
    private String name;
    private String follower;
    private String following;
    private String image;
    private String location;
    private String company;
    private String favorite;


    protected DetailUserGithubModel(Parcel in) {
        username = in.readString();
        name = in.readString();
        follower = in.readString();
        following = in.readString();
        image = in.readString();
        location = in.readString();
        company = in.readString();
        favorite = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(name);
        dest.writeString(follower);
        dest.writeString(following);
        dest.writeString(image);
        dest.writeString(location);
        dest.writeString(company);
        dest.writeString(favorite);
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

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
}
