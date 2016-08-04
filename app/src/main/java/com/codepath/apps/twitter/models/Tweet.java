package com.codepath.apps.twitter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tweet {
    // Define database columns and associated fields
    private String uId;
    private String createdAt;
    private String body;
    private User user;

    // Make sure to always define this constructor with no arguments
    public Tweet() {
        super();
    }

    public Tweet(JSONObject object) {
        super();
        try {
            this.uId = object.getString("id");
            this.createdAt = object.getString("created_at");
            this.body = object.getString("text");
            this.user = User.fromJSON(object.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Tweet> fromJSON(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = new Tweet(tweetJson);
            tweets.add(tweet);
        }

        return tweets;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getuId() {
        return uId;
    }

    public User getUser() {
        return user;
    }
}