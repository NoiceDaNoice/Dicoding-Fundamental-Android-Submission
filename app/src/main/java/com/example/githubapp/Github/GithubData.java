package com.example.githubapp.Github;

import android.content.Context;

import com.example.githubapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GithubData {

    private static String[] username = {
            "JakeWharton",
            "amitshekhariitbhu",
            "romainguy",
            "chrisbanes",
            "tipsy",
            "ravi8x",
            "jasoet",
            "budioktaviyan",
            "hendisantika",
            "sidiqpermana"
    };

    private static String[] name = {
            "Jake Wharton",
            "AMIT SHEKHAR",
            "Romain Guy",
            "Chris Banes",
            "David",
            "Ravi Tamada",
            "Deny Prasetyo",
            "Budi Oktaviyan",
            "Hendi Santika",
            "Sidiq Permana"

    };

    private static int[] image = {
            R.drawable.user1,
            R.drawable.user2,
            R.drawable.user3,
            R.drawable.user4,
            R.drawable.user5,
            R.drawable.user6,
            R.drawable.user7,
            R.drawable.user8,
            R.drawable.user9,
            R.drawable.user10
    };

    private static String[] follower = {
            "56995",
            "5153",
            "7972",
            "14725",
            "788",
            "18628",
            "277",
            "178",
            "428",
            "465"
    };

    private static String[] following = {
            "12",
            "2",
            "0",
            "1",
            "0",
            "3",
            "39",
            "23",
            "61",
            "10"
    };

    private static String[] repository= {
            "102",
            "37",
            "9",
            "30",
            "56",
            "28",
            "44",
            "110",
            "1064",
            "65"

    };

    private static String[] location= {
            "Pittsburgh, PA, USA",
            "New Delhi, India",
            "California",
            "Sydney, Australia",
            "Trondheim, Norway",
            "India",
            "Kotagede, Yogyakarta, Indonesia",
            "Jakarta, Indonesia",
            "Bojongsoang - Bandung Jawa Barat",
            "Jakarta Indonesia"

    };

    private static String[] company= {
            "Google, Inc.",
            "@MindOrksOpenSource",
            "Google",
            "@google working on @android",
            "Working Group Two",
            "AndroidHive | Droid5",
            "@gojek-engineering",
            "@KotlinID",
            "@JVMDeveloperID @KotlinID @IDDevOps",
            "Nusantara Beta Studio",
    };


    public static ArrayList<GithubModel> getListData() {
        ArrayList<GithubModel> list = new ArrayList<>();
        for (int position = 0; position < username.length; position++) {
            GithubModel githubModel = new GithubModel();
            githubModel.setUsername(username[position]);
            githubModel.setName(name[position]);
            githubModel.setImg(image[position]);
            githubModel.setFollower(follower[position]);
            githubModel.setFollowing(following[position]);
            githubModel.setRepository(repository[position]);
            githubModel.setCompany(company[position]);
            githubModel.setLocation(location[position]);
            list.add(githubModel);
        }
        return list;
    }


}
