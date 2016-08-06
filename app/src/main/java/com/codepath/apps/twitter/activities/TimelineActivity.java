package com.codepath.apps.twitter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.RestApplication;
import com.codepath.apps.twitter.adapters.TweetsAdapter;
import com.codepath.apps.twitter.clients.TwitterClient;
import com.codepath.apps.twitter.listeners.EndlessRecyclerViewScrollListener;
import com.codepath.apps.twitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private List<Tweet> mTweets;
    private TweetsAdapter mTweetsAdapter;
    private Toolbar mToolBar;
    private TextView mToolBarTitle;
    private TwitterClient mClient = RestApplication.getRestClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        mToolBar = (Toolbar)findViewById(R.id.toolbar);
        mToolBarTitle = (TextView)findViewById(R.id.tvToolbarTitle);
        setSupportActionBar(mToolBar);
        //Display icon in the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Remove default title text
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().setLogo(R.drawable.twitter_logo);
        //getSupportActionBar().setDisplayUseLogoEnabled(true);
        RecyclerView rvTweets = (RecyclerView)findViewById(R.id.rvTweets);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(layoutManager);
        mTweets = new ArrayList<>();
        mTweetsAdapter = new TweetsAdapter(this, mTweets);
        rvTweets.setAdapter(mTweetsAdapter);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                fetchTweets(page);
            }
        });
        fetchTweets(1);
        mClient.getCurrentUser(new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String screenName = response.getString("screen_name");
                    mToolBarTitle.setText("@"+screenName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }


    private void fetchTweets(int page){
        mClient.getHomeTimeline(page, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray jsonArray) {
                Log.d("DEBUG", "timeline: " + jsonArray.toString());
                ArrayList<Tweet> tweets = Tweet.fromJSON(jsonArray);
                mTweetsAdapter.addAll(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}
