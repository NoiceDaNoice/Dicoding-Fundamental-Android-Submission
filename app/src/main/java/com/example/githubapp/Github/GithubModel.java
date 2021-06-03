package com.example.githubapp.Github;

import android.os.Parcel;
import android.os.Parcelable;

public class GithubModel implements Parcelable {

    private String username;
    private String name;
    private int img;
    private String follower;
    private String following;
    private String repository;
    private String company;
    private String location;

    public GithubModel() {

    }

    protected GithubModel(Parcel in) {
        username = in.readString();
        name = in.readString();
        img = in.readInt();
        follower = in.readString();
        following = in.readString();
        repository = in.readString();
        company = in.readString();
        location = in.readString();
    }

    public static final Creator<GithubModel> CREATOR = new Creator<GithubModel>() {
        @Override
        public GithubModel createFromParcel(Parcel in) {
            return new GithubModel(in);
        }

        @Override
        public GithubModel[] newArray(int size) {
            return new GithubModel[size];
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

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
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

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(name);
        dest.writeInt(img);
        dest.writeString(follower);
        dest.writeString(following);
        dest.writeString(repository);
        dest.writeString(company);
        dest.writeString(location);
    }
}
