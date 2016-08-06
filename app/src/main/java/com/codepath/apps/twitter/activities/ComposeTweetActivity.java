package com.codepath.apps.twitter.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.codepath.apps.twitter.Constants;
import com.codepath.apps.twitter.R;
import com.codepath.apps.twitter.beans.NewTweet;
import com.codepath.apps.twitter.databinding.ActivityComposeBinding;
import com.codepath.apps.twitter.models.Tweet;
import com.codepath.apps.twitter.models.User;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class ComposeTweetActivity extends AppCompatActivity {

    private ActivityComposeBinding binding;
    private ImageView mIvComposeUserProfile;
    private NewTweet mNewTweet = new NewTweet();
    private User mCurrentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = getIntent().getParcelableExtra(Constants.CURRENT_USER_KEY);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compose);
        binding.setNewTweet(mNewTweet);
        mIvComposeUserProfile = binding.composeToolBar.ivComposeUserProfile;
        setSupportActionBar(binding.composeToolBar.composeToolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Picasso.with(ComposeTweetActivity.this)
                .load(mCurrentUser.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(3,1))
                .into(mIvComposeUserProfile);
    }


    public void onClickTweet(View view){
        Intent resultIntent = new Intent();
        if(!mNewTweet.isEmpty()){
            Tweet tweet = new Tweet();
            tweet.setUser(mCurrentUser);
            tweet.setBody(mNewTweet.text.get());
            tweet.setCreatedAt(getDateAsString(new Date()));
            resultIntent.putExtra(Constants.NEW_TWEET_KEY, tweet);
        }
        setResult(RESULT_OK, resultIntent);
        finish();

    }

    private String getDateAsString(Date date){
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        return  sf.format(date);
    }
}
